package com.motorny.controllers;

import io.restassured.RestAssured;
import io.restassured.response.ValidatableResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.transaction.annotation.Transactional;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;
import static org.springframework.http.HttpStatus.NOT_FOUND;
import static org.springframework.http.HttpStatus.OK;

@Transactional
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class BookControllerTest {

    private static final String HEADER_NAME = "auth";
    private static final String HEADER_VALUE = "let_me_in";

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
    void get_AllBooks_returnsSizeList() {

        given()
                .header(HEADER_NAME, HEADER_VALUE)
                .when()
                .get("/api/books")
                .then()
                .body("size()", is(15));
    }

    @Test
    void get_AllBooks_returnsFoundTitleBook() {

        given()
                .header(HEADER_NAME, HEADER_VALUE)
                .when()
                .get("/api/books")
                .then()
                .statusCode(OK.value())
                .body("find {it.id == 11}.title", equalTo("Programming Patterns"));
    }

    @Test
    void get_AllBooks_returnsAllBooks() {

        ValidatableResponse response = given()
                .header(HEADER_NAME, HEADER_VALUE)
                .when()
                .get("/api/books")
                .then();

        System.out.println("'get_AllBooks_returnsAllBooks' response:\n" + response.extract().asString());

        response
                .assertThat()
                .statusCode(OK.value())
                .body("find {it.id == 7}.pages", equalTo(750))
                .body("title", hasItems("Head First Java", "Algorithms", "C++")).body("title", not(hasItem("C#")))
                .body("id", not(empty())).body("findAll {it.pages > 500}.size()", is(10));
    }

    @Test
    void delete_book_returnsNotFound() {

        int bookId = 100;

        given()
                .header(HEADER_NAME, HEADER_VALUE)
                .when()
                .delete("/api/books/" + bookId)
                .then()
                .assertThat()
                .statusCode(NOT_FOUND.value())
                .body("message", equalTo("Book with id '" + bookId + "' was not found!"));
    }

    /**
     * Test case for checking the returned size of the user's list of books
     */

    @Test
    void theListSizeMatchesTheUsersBooksCount() {

        given()
                .header(HEADER_NAME, HEADER_VALUE)
                .when()
                .get("/api/books/user/" + 2)
                .then()
                .body("size()", is(6));
    }

    /**
     * Test case for checking a specific book in query
     */

    @Test
    void checkAvailability_ofTheRequiredBook() {

        ValidatableResponse response = given()
                .header(HEADER_NAME, HEADER_VALUE)
                .when()
                .get("/api/books/user/" + 2)
                .then();

        response.assertThat()
                .statusCode(OK.value())
                .body("title", hasItem("clean code 2023"));
    }

    /**
     * Test case of error handling in case of a non-existent user
     */

    @Test
    void returnErrorMessage_whenUserDoesNotExist() {

        int userId = 100;

        ValidatableResponse response = given()
                .log().all()
                .header(HEADER_NAME, HEADER_VALUE)
                .when()
                .get("/api/books/user/" + userId)
                .then();

        System.out.println("'returnErrorMessage_whenUserDoesNotExist' response:\n" + response.extract().asString());

        response.assertThat()
                .statusCode(NOT_FOUND.value())
                .body("message", equalTo("User with id '" + userId + "' was not found!"));
    }

    /**
     * Test case for checking the size of the returned query and the number of users less than 9 years old
     */

    @Test
    void whenUseMultiplePathParam_thenOK() {

        ValidatableResponse response = given()
                .log().all()
                .header(HEADER_NAME, HEADER_VALUE)
                .queryParam("age", 10)
                .queryParam("limit", 3)
                .when()
                .get("/api/books/popular")
                .then();

        System.out.println("'whenUseMultiplePathParam_thenOK' response:\n" + response.extract().asString());

        response.assertThat()
                .statusCode(OK.value())
                .body("size()", is(3))
                .body("age", not(empty())).body("findAll {it.age < 9}.size()", is(2));
    }

    @Test
    void checkBookTitleByUserName() {

        given()
                .header(HEADER_NAME, HEADER_VALUE)
                .queryParam("age", 9)
                .queryParam("limit", 4)
                .when()
                .get("/api/books/popular")
                .then()
                .statusCode(OK.value())
                .body("find {it.fullName == 'Katie Sierra'}.title", equalTo("clean code 2023"));
    }
}