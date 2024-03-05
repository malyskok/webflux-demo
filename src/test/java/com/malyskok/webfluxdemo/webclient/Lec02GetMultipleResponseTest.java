/*
 * Copyright (c) 2024. Lorem ipsum dolor sit amet, consectetur adipiscing elit.
 * Morbi non lorem porttitor neque feugiat blandit. Ut vitae ipsum eget quam lacinia accumsan.
 * Etiam sed turpis ac ipsum condimentum fringilla. Maecenas magna.
 * Proin dapibus sapien vel ante. Aliquam erat volutpat. Pellentesque sagittis ligula eget metus.
 * Vestibulum commodo. Ut rhoncus gravida arcu.
 */
package com.malyskok.webfluxdemo.webclient;

import com.malyskok.webfluxdemo.dto.Response;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import reactor.test.StepVerifier;

public class Lec02GetMultipleResponseTest extends BaseTest {

    @Autowired
    WebClient webClient;

    @Test
    void multipleResponse() {

        Flux<Response> mono = webClient
                .get()
                .uri("reactive-math/table/{input}", 5)
                .retrieve()
                .bodyToFlux(Response.class);

        StepVerifier.create(mono)
                .expectNextCount(5)
                .verifyComplete();
    }

    @Test
    void multipleResponseStream() {

        Flux<Response> flux = webClient
                .get()
                .uri("reactive-math/table/{input}/stream", 5)
                .retrieve()
                .bodyToFlux(Response.class);

        StepVerifier.create(flux)
                .expectNextMatches(response -> response.getOutput() == 5)
                .expectNextMatches(response -> response.getOutput() == 10)
                .expectNextCount(3)
                .verifyComplete();
    }
}