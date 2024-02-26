/*
 * Copyright (c) 2024. Lorem ipsum dolor sit amet, consectetur adipiscing elit.
 * Morbi non lorem porttitor neque feugiat blandit. Ut vitae ipsum eget quam lacinia accumsan.
 * Etiam sed turpis ac ipsum condimentum fringilla. Maecenas magna.
 * Proin dapibus sapien vel ante. Aliquam erat volutpat. Pellentesque sagittis ligula eget metus.
 * Vestibulum commodo. Ut rhoncus gravida arcu.
 */
package com.malyskok.webfluxdemo.config;

import com.malyskok.webfluxdemo.dto.InputFailedValidationResponse;
import com.malyskok.webfluxdemo.exception.InputValidationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

import java.util.function.BiFunction;

@Configuration
public class RouterConfig {

    @Autowired
    RequestHandler requestHandler;

    @Bean
    RouterFunction<ServerResponse> highLevelRouterFunction() {
        return RouterFunctions
                .route()
                .path("router", this::serverResponseRouterFunction)
                .build();
    }

    @Bean
    RouterFunction<ServerResponse> serverResponseRouterFunction() {
        return RouterFunctions
                .route()
                .GET("square/{input}", requestHandler::squareHandler)
                .GET("square/{input}/validation", requestHandler::squareHandlerWithValidation)
                .GET("table/{input}", requestHandler::tableHandler)
                .GET("table/{input}/stream", requestHandler::tableHandlerStream)
                .POST("multiply", requestHandler::multiplyHandler)
                .onError(InputValidationException.class, inputValidationExceptionHandler())
                .build();
    }

    public BiFunction<Throwable, ServerRequest, Mono<ServerResponse>> inputValidationExceptionHandler() {
        return (t, r) -> {
            InputValidationException ex = (InputValidationException) t;
            InputFailedValidationResponse response = new InputFailedValidationResponse();
            response.setErrorCode(ex.getErrorCode());
            response.setInput(ex.getInput());
            response.setMessage(ex.getMessage());
            return ServerResponse.badRequest().bodyValue(response);
        };
    }
}