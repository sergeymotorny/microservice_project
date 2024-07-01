package com.motorny.controllers;

import com.motorny.dto.UserDto;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.ValidatableResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.springframework.http.HttpStatus.*;

@Transactional
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class UserControllerTest {

    private static final String HEADER_NAME = "auth";
    private static final String HEADER_VALUE = "let_me_in";
    private static final Integer USER_ID = 6;

    @LocalServerPort
    private int port;

    @BeforeEach
    public void setUp() {
        RestAssured.baseURI = "http://localhost";
        RestAssured.port = port;
    }

    @Test
    void get_AllUsers_returnsAllUsers() {

        ValidatableResponse response = given()
                .header(HEADER_NAME, HEADER_VALUE)
                .when()
                .get("/api/users")
                .then();

        System.out.println("'get_AllUsers_returnsAllUsers' response:\n" + response.extract().asString());

        response.assertThat()
                .statusCode(OK.value())
                .body("size()", is(5));
    }

    @Rollback // RollBack transaction is not executed!
    @Test
    void post_newBook_returnsCreatedBook() {
        UserDto newUser = UserDto.builder()
                .fullName("Craig Walls")
                .age(8).build();

        ValidatableResponse response = given()
                .header(HEADER_NAME, HEADER_VALUE)
                .contentType(ContentType.JSON)
                .accept(ContentType.JSON)
                .body(newUser)
                .when()
                .post("/api/users")
                .then();

        System.out.println("'post_newBook_returnsCreatedBook' response:\n" + response.extract().asString());

        response.assertThat()
                .statusCode(CREATED.value())
                .body("fullName", equalTo("Craig Walls"))
                .body("age", equalTo(8));
    }

    @Rollback // RollBack transaction is not executed!
    @Test
    void put_updateUser_returnsUpdatedUser() {
        UserDto updateUser = UserDto.builder()
                .fullName("Craig Walls")
                .age(5).build();

        ValidatableResponse response = given()
                .header(HEADER_NAME, HEADER_VALUE)
                .contentType(ContentType.JSON)
                .accept(ContentType.JSON)
                .body(updateUser)
                .when()
                .put("/api/users/" + USER_ID)
                .then();

        System.out.println("'put_updateUser_returnsUpdatedUser' response:\n" + response.extract().asString());

        response.assertThat()
                .statusCode(OK.value())
                .body("fullName", equalTo("Andrew Run"))
                .body("age", equalTo(6));
    }

    @Rollback // RollBack transaction is not executed!
    @Test
    void delete_user_returnsOk() {

        ValidatableResponse response = given()
                .header(HEADER_NAME, HEADER_VALUE)
                .when()
                .delete("/api/users/" + USER_ID)
                .then();

        System.out.println("'delete_user_returnsOk' response:\n" + response.extract().asString());

        response.assertThat()
                .statusCode(OK.value());
    }

    @Test
    void delete_user_returnsNotFound() {
        int userId = 100;

        given()
                .header(HEADER_NAME, HEADER_VALUE)
                .when()
                .delete("/api/users/" + userId)
                .then()
                .assertThat()
                .statusCode(NOT_FOUND.value())
                .body("message", equalTo("User with id '" + userId + "' was not found!"));
    }
}