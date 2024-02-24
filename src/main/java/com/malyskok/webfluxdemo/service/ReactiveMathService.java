/*
 * Copyright (c) Flooid Limited 2024. All rights reserved.
 * This source code is confidential to and the copyright of Flooid Limited ("Flooid"), and must not be
 * (i) copied, shared, reproduced in whole or in part; or
 * (ii) used for any purpose other than the purpose for which it has expressly been provided by Flooid under the terms of a license agreement; or
 * (iii) given or communicated to any third party without the prior written consent of Flooid.
 * Flooid at all times reserves the right to modify the delivery and capabilities of its products and/or services.
 * "Flooid", "FlooidCore", "FlooidCommerce", "Flooid Hub", "PCMS", "Vision", "VISION Commerce Suite", "VISION OnDemand", "VISION eCommerce",
 * "VISION Engaged", "DATAFIT", "PCMS DATAFIT" and "BeanStore" are registered trademarks of Flooid.
 * All other brands and logos (that are not registered and/or unregistered trademarks of Flooid) are registered and/or
 * unregistered trademarks of their respective holders and should be treated as such.
 */
package com.malyskok.webfluxdemo.service;

import com.malyskok.webfluxdemo.dto.Response;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

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
        return Flux.range(1, input)
                .doOnNext(i -> SleepUtil.sleepSeconds(1))
                .doOnNext(i -> System.out.println("Math service - processing: " + i))
                .map(i -> new Response(i * input));
    }
}