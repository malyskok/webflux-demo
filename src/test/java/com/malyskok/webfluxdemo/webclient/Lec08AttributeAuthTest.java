/*
 * Copyright (c) 2024. Lorem ipsum dolor sit amet, consectetur adipiscing elit.
 * Morbi non lorem porttitor neque feugiat blandit. Ut vitae ipsum eget quam lacinia accumsan.
 * Etiam sed turpis ac ipsum condimentum fringilla. Maecenas magna.
 * Proin dapibus sapien vel ante. Aliquam erat volutpat. Pellentesque sagittis ligula eget metus.
 * Vestibulum commodo. Ut rhoncus gravida arcu.
 */
package com.malyskok.webfluxdemo.webclient;

import com.malyskok.webfluxdemo.dto.MultiplyRequest;
import com.malyskok.webfluxdemo.dto.Response;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

public class Lec08AttributeAuthTest extends BaseTest {

    @Autowired
    WebClient webClient;

    @Test
    void attributeTest() {

        Mono<Response> mono = webClient
                .post()
                .uri("reactive-math/multiply")
                .bodyValue(new MultiplyRequest(2, 2))
                .attributes(map -> map.put("auth", "basic"))
                .retrieve()
                .bodyToMono(Response.class)
                .doOnNext(System.out::println);

        StepVerifier.create(mono)
                .expectNextCount(1)
                .verifyComplete();

        StepVerifier.create(mono)
                .expectNextCount(1)
                .verifyComplete();
    }
}