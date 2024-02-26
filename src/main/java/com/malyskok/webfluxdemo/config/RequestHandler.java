/*
 * Copyright (c) 2024. Lorem ipsum dolor sit amet, consectetur adipiscing elit.
 * Morbi non lorem porttitor neque feugiat blandit. Ut vitae ipsum eget quam lacinia accumsan.
 * Etiam sed turpis ac ipsum condimentum fringilla. Maecenas magna.
 * Proin dapibus sapien vel ante. Aliquam erat volutpat. Pellentesque sagittis ligula eget metus.
 * Vestibulum commodo. Ut rhoncus gravida arcu.
 */
package com.malyskok.webfluxdemo.config;

import com.malyskok.webfluxdemo.dto.MultiplyRequest;
import com.malyskok.webfluxdemo.dto.Response;
import com.malyskok.webfluxdemo.exception.InputValidationException;
import com.malyskok.webfluxdemo.service.ReactiveMathService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class RequestHandler {

    @Autowired
    ReactiveMathService mathService;

    public Mono<ServerResponse> squareHandler(ServerRequest serverRequest) {
        int input = Integer.parseInt(serverRequest.pathVariable("input"));
        Mono<Response> square = mathService.doSquare(input);
        return ServerResponse.ok().body(square, Response.class);
    }

    public Mono<ServerResponse> squareHandlerWithValidation(ServerRequest serverRequest) {
        int input = Integer.parseInt(serverRequest.pathVariable("input"));
        if (input < 10 || input > 20){
            return Mono.error(new InputValidationException(input));
        }
        Mono<Response> square = mathService.doSquare(input);
        return ServerResponse.ok().body(square, Response.class);
    }

    public Mono<ServerResponse> tableHandler(ServerRequest serverRequest) {
        int input = Integer.parseInt(serverRequest.pathVariable("input"));
        Flux<Response> table = mathService.doMultiplicationTable(input);
        return ServerResponse.ok().body(table, Response.class);
    }

    public Mono<ServerResponse> tableHandlerStream(ServerRequest serverRequest) {
        int input = Integer.parseInt(serverRequest.pathVariable("input"));
        Flux<Response> table = mathService.doMultiplicationTable(input);
        return ServerResponse
                .ok()
                .contentType(MediaType.TEXT_EVENT_STREAM)
                .body(table, Response.class);
    }

    public Mono<ServerResponse> multiplyHandler(ServerRequest serverRequest) {
        Mono<MultiplyRequest> multiplyRequestMono = serverRequest.bodyToMono(MultiplyRequest.class);
        Mono<Response> result = mathService.multiply(multiplyRequestMono);
        return ServerResponse.ok().body(result, Response.class);
    }
}