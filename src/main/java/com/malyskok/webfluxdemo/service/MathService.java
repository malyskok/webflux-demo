/*
 * Copyright (c) 2024. Lorem ipsum dolor sit amet, consectetur adipiscing elit.
 * Morbi non lorem porttitor neque feugiat blandit. Ut vitae ipsum eget quam lacinia accumsan.
 * Etiam sed turpis ac ipsum condimentum fringilla. Maecenas magna.
 * Proin dapibus sapien vel ante. Aliquam erat volutpat. Pellentesque sagittis ligula eget metus.
 * Vestibulum commodo. Ut rhoncus gravida arcu.
 */
package com.malyskok.webfluxdemo.service;

import com.malyskok.webfluxdemo.dto.Response;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Service
public class MathService {
    public Response doSquare(int input){
        return new Response(input * input);
    }

    public List<Response> doMultiplicationTable(int input){
        return IntStream.range(1, input)
                .peek(i -> SleepUtil.sleepSeconds(1))
                .peek(i -> System.out.println("Math service - processing: " + i))
                .mapToObj(i -> new Response(i * input))
                .collect(Collectors.toList());
    }
}