/*
 * Copyright (c) 2024. Lorem ipsum dolor sit amet, consectetur adipiscing elit.
 * Morbi non lorem porttitor neque feugiat blandit. Ut vitae ipsum eget quam lacinia accumsan.
 * Etiam sed turpis ac ipsum condimentum fringilla. Maecenas magna.
 * Proin dapibus sapien vel ante. Aliquam erat volutpat. Pellentesque sagittis ligula eget metus.
 * Vestibulum commodo. Ut rhoncus gravida arcu.
 */
package com.malyskok.webfluxdemo.config;

import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

import java.util.function.BiFunction;

@Service
public class CalculatorHandler {

    //creating multiple handlers intentionally
    // calculator/{a}/{b}
    public Mono<ServerResponse> additionHandler(ServerRequest request){
        return process(request, (a, b) -> ServerResponse.ok().bodyValue(a + b));
    }

    public Mono<ServerResponse> subtractionHandler(ServerRequest request){
        return process(request, (a, b) -> ServerResponse.ok().bodyValue(a - b));
    }

    public Mono<ServerResponse> multiplicationHandler(ServerRequest request){
        return process(request, (a, b) -> ServerResponse.ok().bodyValue(a * b));
    }

    public Mono<ServerResponse> divisionHandler(ServerRequest request){
        return process(request, (a, b) ->
                b != 0 ? ServerResponse.ok().bodyValue(a / b) :
                        ServerResponse.badRequest().bodyValue("b can not be 0")
        );
    }

    private Mono<ServerResponse> process(ServerRequest request,
                                         BiFunction<Integer, Integer, Mono<ServerResponse>> opLogic){
        int a = getValue(request, "a");
        int b = getValue(request, "b");
        return opLogic.apply(a, b);
    }

    private int getValue(ServerRequest request, String key){
        return Integer.parseInt(request.pathVariable(key));
    }

}