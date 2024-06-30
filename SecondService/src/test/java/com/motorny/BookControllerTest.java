package com.motorny;

import com.motorny.dto.BookDto;
import com.motorny.mappers.BookMapper;
import com.motorny.services.impl.BookServiceImpl;
import io.restassured.RestAssured;
import io.restassured.common.mapper.TypeRef;
import io.restassured.response.ValidatableResponse;
import org.junit.jupiter.api.*;

import static io.restassured.RestAssured.get;
import static io.restassured.RestAssured.given;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.when;
import static org.springframework.http.HttpStatus.OK;

import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.web.server.LocalServerPort;

import org.springframework.test.context.ActiveProfiles;

import java.util.*;


@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@ActiveProfiles({"integration"})
class BookControllerTest {

    private static final String HEADER_NAME = "auth";
    private static final String HEADER_VALUE = "let_me_in";

    @MockBean
    private BookServiceImpl bookService;

    @MockBean
    private BookMapper bookMapper;

    @LocalServerPort
    private int port;

    @BeforeEach
    public void setUp() {
        RestAssured.baseURI = "http://localhost";
        RestAssured.port = port;
    }

    @Test
    void checkStatusCode_successfulReceiptOfTheList() {
        given()
                .header(HEADER_NAME, HEADER_VALUE)
                .when()
                .get("/api/books")
                .then()
                .statusCode(200);
    }

    @Test
    void unauthorizedUser_withoutPassingHeader() {
        given()
                .when()
                .get("/api/books")
                .then()
                .statusCode(401);
    }

    @Test
    void get_AllBooks_returnsAllBooks_200() {
        Long[] bookIds = {5L, 6L, 7L, 8L, 9L, 10L, 11L, 12L, 13L, 14L, 15L, 16L, 17L, 18L, 19L};

        BookDto book1 = new BookDto(7L, "Head First Java", 750, new HashSet<>(List.of(2L)));
        BookDto book2 = new BookDto(8L, "21 seconds", 450, new HashSet<>(List.of(2L)));

        List<BookDto> bookDtoList = new ArrayList<>(Arrays.asList(book1, book2));

        when(bookService.getAllBook()).thenReturn(bookDtoList);

        ValidatableResponse response = given()
                .header(HEADER_NAME, HEADER_VALUE)
                .when()
                .get("/api/books")
                .then();

        System.out.println("'get_AllBooks_returnsAllBooks_200' response:\n" + response.extract().asString());

        response
                .assertThat()
                .statusCode(OK.value())
                .body("find {it.id == 7}.pages", equalTo(750))
                .body("size()", is(2))
                .body("title", hasItems("Head First Java", "21 seconds")).body("title", not(hasItem("C#")));

//
//                .body(containsString("Head First Java")).body("find {it.id == 7}.pages", equalTo(750))
//                .body("find {it.id == 7}.pages", equalTo(750))
//                .body("title", hasItems("Head First Java", "Algorithms", "Programming Patterns")).body("title", not(hasItem("C#")))
//                .body("id", hasItems(bookIds)).body("findAll {it.pages > 500}.size()", is(10));
    }


    @Test
    void checkExistingBook() {

        BookDto book = new BookDto(7L, "Head First Java", 750, new HashSet<>(List.of(7L)));

        List<BookDto> books = new ArrayList<>(List.of(book));

        when(bookService.getAllBook()).thenReturn(books);

        given()
                .header(HEADER_NAME, HEADER_VALUE)
                .when()
                .get("/api/books")
                .then()
                .body("[0].title", equalTo("Head First Java"));
    }

    @Test
    void checkingCollectionSizeCompliance() {

        List<BookDto> books = given()
                .header(HEADER_NAME, HEADER_VALUE)
                .when()
                .get("/api/books")
                .then()
                .statusCode(200)
                .extract()
                .as(new TypeRef<>() {
                });

        assertThat(books, hasSize(15));
    }

    @Test
    void whenUseMultiplePathParam_thenOK() {

        given()
                .header(HEADER_NAME, HEADER_VALUE)
                .queryParam("age", 10)
                .queryParam("limit", 3)
                .when()
                .get("/api/books/popular")
                .then()
                .statusCode(200);
    }
}