/*
 * Copyright (c) 2024. Lorem ipsum dolor sit amet, consectetur adipiscing elit.
 * Morbi non lorem porttitor neque feugiat blandit. Ut vitae ipsum eget quam lacinia accumsan.
 * Etiam sed turpis ac ipsum condimentum fringilla. Maecenas magna.
 * Proin dapibus sapien vel ante. Aliquam erat volutpat. Pellentesque sagittis ligula eget metus.
 * Vestibulum commodo. Ut rhoncus gravida arcu.
 */
package com.malyskok.webfluxdemo.service;

import com.malyskok.webfluxdemo.dto.MultiplyRequest;
import com.malyskok.webfluxdemo.dto.Response;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.Duration;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Service
public class ReactiveMathService {

    public Mono<Response> doSquare(int input) {
        return Mono.fromSupplier(() -> input * input)
                .map(Response::new);
    }

    public Flux<Response> doMultiplicationTable(int input) {
        //THIS IS NOT REACTIVE!!!
//        List<Response> list = IntStream.rangeClosed(1, input)
//                .peek(i -> SleepUtil.sleepSeconds(1))
//                .peek(i -> System.out.println("Math service - processing: " + i))
//                .mapToObj(i -> new Response(i * input))
//                .collect(Collectors.toList());
//        return Flux.fromIterable(list);

        return Flux.range(1, input)
                .delayElements(Duration.ofSeconds(1))
//                .doOnNext(i -> SleepUtil.sleepSeconds(1))
                .doOnNext(i -> System.out.println("Math service - processing: " + i))
                .map(i -> new Response(i * input));
    }

    public Mono<Response> multiply(Mono<MultiplyRequest> request) {
        return request
                .map(request1 -> request1.getFirst() * request1.getSecond())
                .map(Response::new);
    }
}