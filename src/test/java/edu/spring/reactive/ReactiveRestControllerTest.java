package edu.spring.reactive;

import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.reactive.server.WebTestClient;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class ReactiveRestControllerTest {

    @Autowired
    private WebTestClient webClient;

    private final String URL = "/";

    @Test
    @Order(1)
    void testEndPointSaveE() {

        webClient.post().uri(URL)
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue("")
                .exchange()//envia el request
                .expectStatus().isCreated()
                .expectHeader().contentType(MediaType.APPLICATION_JSON)
                .expectBody(String.class)
                .consumeWith(response -> {
                    System.out.println(response);
                });
    }
}
