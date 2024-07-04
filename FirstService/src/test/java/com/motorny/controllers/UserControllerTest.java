package com.motorny.controllers;

import com.github.tomakehurst.wiremock.junit5.WireMockExtension;
import com.motorny.client.FirstServiceClient;
import com.motorny.dto.BookDto;
import com.motorny.dto.UserDtoFromSecondService;
import io.restassured.RestAssured;
import io.restassured.response.ValidatableResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.RegisterExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import java.util.Set;

import static com.github.tomakehurst.wiremock.client.WireMock.aResponse;
import static com.github.tomakehurst.wiremock.client.WireMock.get;
import static com.github.tomakehurst.wiremock.core.WireMockConfiguration.wireMockConfig;
import static io.restassured.RestAssured.given;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.*;
import static org.springframework.http.HttpStatus.OK;

@SpringBootTest
class UserControllerTest {

    private static final String PATH = "/api/users";

    @RegisterExtension
    static WireMockExtension staticWireMockExtension = WireMockExtension.newInstance()
            .options(wireMockConfig().port(8089))
            .build();

    @Autowired
    private FirstServiceClient firstServiceClient;

    @BeforeEach
    public void setUp() {
        RestAssured.baseURI = staticWireMockExtension.getRuntimeInfo().getHttpBaseUrl();

        staticWireMockExtension.stubFor(get(PATH).willReturn(aResponse()
                .withHeader("Content-Type", "application/json")
                .withBody("""
                        [
                            {"id":4,"fullName":"Barry Bird","age":9,"bookIds":[15]},
                            {"id":3,"fullName":"Sergey Motorny","age":12,"bookIds":[17,18,19,11,12,13,14]},
                            {"id":2,"fullName":"Katie Sierra","age":8,"bookIds":[5,6,7,8,9,10]},
                            {"id":5,"fullName":"Edward Bill","age":4,"bookIds":[16]},
                            {"id":6,"fullName":"Andrew Run","age":6,"bookIds":[27]}
                        ]
                        """)));
    }

    @Test
    void compareTheCollection_withTheCallToTheSecondService() {
        ValidatableResponse response = given()
                .log().all()
                .when()
                .get(PATH)
                .then();

        System.out.println("'getAllUsers' response:\n" + response.extract().asString());

        response.assertThat()
                .statusCode(OK.value())
                .body("size()", is(5))
                .body("fullName", hasItem("Andrew Run"))
                .body("find {it.id == 2}.age", equalTo(8));
    }

    @Test
    void getFirstUser_feignClient() {
        List<UserDtoFromSecondService> users = firstServiceClient.getAllUsers();

        Set<BookDto> books = users.getFirst().getBooks();

        assertThat(users).isNotNull().hasSize(5);
        assertThat(users.getFirst()).satisfies(user -> {
            assertThat(user.getId()).isEqualTo(4);
            assertThat(user.getFullName()).isEqualTo("Barry Bird");
            assertThat(user.getAge()).isEqualTo(9);
            assertThat(user.getBooks()).isEqualTo(books);
        });
    }
}