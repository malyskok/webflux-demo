/*
 * Copyright (c) 2024. Lorem ipsum dolor sit amet, consectetur adipiscing elit.
 * Morbi non lorem porttitor neque feugiat blandit. Ut vitae ipsum eget quam lacinia accumsan.
 * Etiam sed turpis ac ipsum condimentum fringilla. Maecenas magna.
 * Proin dapibus sapien vel ante. Aliquam erat volutpat. Pellentesque sagittis ligula eget metus.
 * Vestibulum commodo. Ut rhoncus gravida arcu.
 */
package com.malyskok.webfluxdemo.webtestclient;

import com.malyskok.webfluxdemo.controller.ReactiveMathController;
import com.malyskok.webfluxdemo.dto.Response;
import com.malyskok.webfluxdemo.service.ReactiveMathService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.time.Duration;

@WebFluxTest(ReactiveMathController.class)
public class Lec02GetWebTest {

    @Autowired
    WebTestClient client;

    @MockBean
    ReactiveMathService mathService;

    @Test
    void fluentAssertions() {

        Mockito.when(mathService.doSquare(Mockito.anyInt())).thenReturn(Mono.just(new Response(25)));

        client
                .get()
                .uri("/reactive-math/square/{input}", 5)
                .exchange()
                .expectStatus().is2xxSuccessful()
                .expectHeader().contentType(MediaType.APPLICATION_JSON)
                .expectBody(Response.class)
                .value(response -> Assertions.assertEquals(25, response.getOutput()));
    }

    @Test
    void list() {

        Flux<Response> responseFlux = Flux
                .range(1, 3)
                .map(Response::new);
        Mockito.when(mathService.doMultiplicationTable(Mockito.anyInt())).thenReturn(responseFlux);

        client
                .get()
                .uri("/reactive-math/table/{input}", 5)
                .exchange()
                .expectStatus().is2xxSuccessful()
                .expectHeader().contentType(MediaType.APPLICATION_JSON)
                .expectBodyList(Response.class)
                .hasSize(3);
    }

    @Test
    void stream() {

        Flux<Response> responseFlux = Flux
                .range(1, 3)
                .map(Response::new)
                .delayElements(Duration.ofSeconds(1));
        Mockito.when(mathService.doMultiplicationTable(Mockito.anyInt())).thenReturn(responseFlux);

        client
                .get()
                .uri("/reactive-math/table/{input}/stream", 5)
                .exchange()
                .expectStatus().is2xxSuccessful()
                .expectHeader().contentTypeCompatibleWith(MediaType.TEXT_EVENT_STREAM)
                .expectBodyList(Response.class)
                .hasSize(3);
    }
}