package com.motorny.controllers;

import com.github.tomakehurst.wiremock.WireMockServer;
import com.maciejwalkowiak.wiremock.spring.ConfigureWireMock;
import com.maciejwalkowiak.wiremock.spring.EnableWireMock;
import com.maciejwalkowiak.wiremock.spring.InjectWireMock;
import com.motorny.client.FirstServiceClient;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static com.github.tomakehurst.wiremock.client.WireMock.aResponse;
import static com.github.tomakehurst.wiremock.client.WireMock.get;
import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@EnableWireMock({
        @ConfigureWireMock(name = "second-service", property = "second.service.config.url")
})
public class HelloWorldControllerTest {

    private static final String PATH = "/api/hello-world";

    @InjectWireMock("second-service")
    private WireMockServer wiremock;

    @Autowired
    private FirstServiceClient firstServiceClient;

    @Test
    void checkingTheResult_ofTheSecondService() {
        wiremock.stubFor(get(PATH).willReturn(aResponse()
                .withHeader("Content-Type", "application/json")
                .withBody("FirstService a calling: hello world from SecondService!")
        ));

        System.out.println("Response: " + firstServiceClient.getHelloWorld());

        assertThat(firstServiceClient.getHelloWorld()).isNotNull();
        assertThat(firstServiceClient.getHelloWorld()).isEqualTo(
                "FirstService a calling: hello world from SecondService!"
        );
    }
}
