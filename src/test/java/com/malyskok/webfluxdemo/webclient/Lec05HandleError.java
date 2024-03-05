/*
 * Copyright (c) 2024. Lorem ipsum dolor sit amet, consectetur adipiscing elit.
 * Morbi non lorem porttitor neque feugiat blandit. Ut vitae ipsum eget quam lacinia accumsan.
 * Etiam sed turpis ac ipsum condimentum fringilla. Maecenas magna.
 * Proin dapibus sapien vel ante. Aliquam erat volutpat. Pellentesque sagittis ligula eget metus.
 * Vestibulum commodo. Ut rhoncus gravida arcu.
 */
package com.malyskok.webfluxdemo.webclient;

import com.malyskok.webfluxdemo.dto.InputFailedValidationResponse;
import com.malyskok.webfluxdemo.dto.Response;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

public class Lec05HandleError extends BaseTest {

    @Autowired
    WebClient webClient;

    @Test
    void retrieveBadRequest() {

        Mono<Response> mono = webClient
                .get()
                .uri("reactive-math/square/{input}/throw", 5)
                .retrieve()
                .bodyToMono(Response.class)
                .doOnNext(response -> System.out.println(response.getOutput()))
                .doOnError(throwable -> System.out.println(throwable.getMessage()));

        StepVerifier.create(mono)
                .verifyError(WebClientResponseException.BadRequest.class);

    }

    @Test
    void exchangeBadRequest() {

        Mono<Object> mono = webClient
                .get()
                .uri("reactive-math/square/{input}/throw", 5)
                .exchangeToMono(clientResponse -> {
                    if (clientResponse.statusCode().is4xxClientError()) {
                        return clientResponse.bodyToMono(InputFailedValidationResponse.class);
                    } else {
                        return clientResponse.bodyToMono(Response.class);
                    }
                })
                .doOnNext(System.out::println)
                .doOnError(throwable -> System.out.println(throwable.getMessage()));

        StepVerifier.create(mono)
                .expectNextCount(1)
                .verifyComplete();

    }
}