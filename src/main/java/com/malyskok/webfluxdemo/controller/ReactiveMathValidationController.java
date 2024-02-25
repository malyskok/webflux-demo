/*
 * Copyright (c) 2024. Lorem ipsum dolor sit amet, consectetur adipiscing elit.
 * Morbi non lorem porttitor neque feugiat blandit. Ut vitae ipsum eget quam lacinia accumsan.
 * Etiam sed turpis ac ipsum condimentum fringilla. Maecenas magna.
 * Proin dapibus sapien vel ante. Aliquam erat volutpat. Pellentesque sagittis ligula eget metus.
 * Vestibulum commodo. Ut rhoncus gravida arcu.
 */
package com.malyskok.webfluxdemo.controller;

import com.malyskok.webfluxdemo.dto.Response;
import com.malyskok.webfluxdemo.exception.InputValidationException;
import com.malyskok.webfluxdemo.service.ReactiveMathService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("reactive-math")
public class ReactiveMathValidationController {

    @Autowired
    private ReactiveMathService mathService;

    @GetMapping("square/{input}/throw")
    public Mono<Response> findSquare(@PathVariable int input) {
        if (input < 10 || input > 20)
            throw new InputValidationException(input);
        return this.mathService.doSquare(input);
    }

    @GetMapping("square/{input}/throw/mono-error")
    public Mono<Response> findSquareMonoError(@PathVariable int input) {
        return Mono.just(input)
                .handle((i, synchronousSink) -> {
                    if (input < 10 || input > 20) {
                        synchronousSink.error(new InputValidationException(i));
                    } else {
                        synchronousSink.next(i);
                    }
                })
                .cast(Integer.class)
                .flatMap(i -> mathService.doSquare(i));
    }

    @GetMapping("square/{input}/throw/mono-error-400")
    public Mono<ResponseEntity<Response>> findSquareMonoError400(@PathVariable int input) {
        return Mono.just(input)
                .filter(i-> i >= 10 && i <= 20)
                .flatMap(i -> mathService.doSquare(i))
                .map(body -> ResponseEntity.ok(body))
                .defaultIfEmpty(ResponseEntity.badRequest().build());
    }
}