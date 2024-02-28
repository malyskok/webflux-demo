/*
 * Copyright (c) 2024. Lorem ipsum dolor sit amet, consectetur adipiscing elit.
 * Morbi non lorem porttitor neque feugiat blandit. Ut vitae ipsum eget quam lacinia accumsan.
 * Etiam sed turpis ac ipsum condimentum fringilla. Maecenas magna.
 * Proin dapibus sapien vel ante. Aliquam erat volutpat. Pellentesque sagittis ligula eget metus.
 * Vestibulum commodo. Ut rhoncus gravida arcu.
 */
package com.malyskok.webfluxdemo.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.ClientRequest;
import org.springframework.web.reactive.function.client.ClientResponse;
import org.springframework.web.reactive.function.client.ExchangeFunction;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Configuration
public class WebClientConfig {

    @Bean
    public WebClient webClient() {
        return WebClient.builder()
                .baseUrl("http://localhost:8080")
                .filter(WebClientConfig::sessionToken)
                .build();
    }

//    private static Mono<ClientResponse> sessionToken(ClientRequest request, ExchangeFunction next) {
//        ClientRequest clientRequest = ClientRequest.from(request)
//                .headers(httpHeaders -> httpHeaders.setBearerAuth("some-jwt"))
//                .build();
//        return next.exchange(clientRequest);
//    }

    private static Mono<ClientResponse> sessionToken(ClientRequest request, ExchangeFunction next) {
        ClientRequest clientRequest = request.attribute("auth")
                .map(v -> v.equals("basic") ? basicAuth(request) : oauth(request))
                .orElse(request);
        return next.exchange(clientRequest);
    }

    private static ClientRequest basicAuth(ClientRequest request) {
        return ClientRequest.from(request)
                .headers(httpHeaders -> httpHeaders.setBasicAuth("username", "password"))
                .build();
    }

    private static ClientRequest oauth(ClientRequest request) {
        return ClientRequest.from(request)
                .headers(httpHeaders -> httpHeaders.setBearerAuth("some-jwt"))
                .build();
    }
}