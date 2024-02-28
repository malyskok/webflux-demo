/*
 * Copyright (c) 2024. Lorem ipsum dolor sit amet, consectetur adipiscing elit.
 * Morbi non lorem porttitor neque feugiat blandit. Ut vitae ipsum eget quam lacinia accumsan.
 * Etiam sed turpis ac ipsum condimentum fringilla. Maecenas magna.
 * Proin dapibus sapien vel ante. Aliquam erat volutpat. Pellentesque sagittis ligula eget metus.
 * Vestibulum commodo. Ut rhoncus gravida arcu.
 */
package com.malyskok.webfluxdemo;

import com.malyskok.webfluxdemo.dto.InputFailedValidationResponse;
import com.malyskok.webfluxdemo.dto.Response;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;
import org.springframework.web.util.UriComponentsBuilder;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.net.URI;
import java.util.Map;

public class Lec07QueryParamTest extends BaseTest {

    @Autowired
    WebClient webClient;

    String queryString = "http://localhost:8080/jobs/search?count={count}&page={page}";

    @Test
    void queryParam() {

        URI uri = UriComponentsBuilder.fromUriString(queryString)
                .build(10, 1);

        Map<String, Integer> params = Map.of(
                "count", 10,
                "page", 1
        );

        Flux<Integer> flux = webClient
                .get()
//                .uri(uri)
                .uri(builder -> builder.path("jobs/search").query("count={count}&page={page}").build(params))
                .retrieve()
                .bodyToFlux(Integer.class)
                .doOnNext(integer -> System.out.println(integer));

        StepVerifier.create(flux)
                .expectNextCount(2)
                .verifyComplete();

    }
}