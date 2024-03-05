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
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

public class Lec01GetSingleResponseTest extends BaseTest {

    @Autowired
    WebClient webClient;

    @Test
    void singleResponse() {
//        Response result = webClient
//                .get()
//                .uri("reactive-math/square/{input}", 5)
//                .retrieve()
//                .bodyToMono(Response.class)
//                .block();
//        System.out.println(result);

        Mono<Response> mono = webClient
                .get()
                .uri("reactive-math/square/{input}", 5)
                .retrieve()
                .bodyToMono(Response.class);

        StepVerifier.create(mono)
                .expectNextMatches(result -> result.getOutput() == 25)
                .verifyComplete();


    }
}