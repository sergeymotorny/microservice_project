package com.motorny.controllers;

import com.motorny.dto.BookDto;
import com.motorny.dto.UserDto;
import com.motorny.models.Book;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.response.ValidatableResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;

import static io.restassured.RestAssured.given;
import static io.restassured.RestAssured.when;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.springframework.http.HttpStatus.NOT_FOUND;
import static org.springframework.http.HttpStatus.OK;


@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Transactional
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
    void get_AllBooks_returnsTheFoundBook_200() {

        given()
                .header(HEADER_NAME, HEADER_VALUE)
                .when()
                .get("/api/books")
                .then()
                .statusCode(OK.value())
                .body("find {it.id == 11}.title", equalTo("Programming Patterns"));
    }

    @Test
    void get_AllBooks_returnsAllBooks_200() {

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
                .body("title", hasItems("Head First Java", "Algorithms", "C++")).body("title", not(hasItem("C#")))
                .body("id", not(empty())).body("findAll {it.pages > 500}.size()", is(10));
    }

    @Test
    void delete_book_returnsNotFound_404() {
        given()
                .header(HEADER_NAME, HEADER_VALUE)
                .when()
                .delete("/api/books/" + 100)
                .then()
                .assertThat()
                .statusCode(NOT_FOUND.value());
    }


    // ------- 1 query: /api/books/user/{userId}

    @Test
    void theListSizeMatchesTheUsersBookCount() {

        given()
                .header(HEADER_NAME, HEADER_VALUE)
                .when()
                .get("/api/books/user/" + 2)
                .then()
                .body("size()", is(6));
    }

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

    @Test
    void receiveProcessing_whenTheUserDoesNotExist() {

        ValidatableResponse response = given()
                .log().all()
                .header(HEADER_NAME, HEADER_VALUE)
                .when()
                .get("/api/books/user/" + 100)
                .then();

        System.out.println("'receiveProcessing_whenTheUserDoesNotExist' response:\n" + response.extract().asString());

        response.assertThat()
                .statusCode(NOT_FOUND.value())
                .body("error", equalTo("Not Found"));
    }


    // ------- 2 query: /api/books/popular

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
                .statusCode(200)
                .body("size()", is(3))
                .body("age", not(empty())).body("findAll {it.age < 9}.size()", is(2));
    }

    @Test
    void test_query2() {

    }

    void test_query3() {

    }


    // object mapping

    @Test
    void mappingObjects_fromBook_toBookMapper() {

    }

    @Test
    void mappingObjects_fromBookMapper_toBook() {

    }



}