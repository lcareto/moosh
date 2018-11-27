package com.moosh.example.nodiscovery.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created on 08/07/2018
 *
 * @author lyrold
 */
@RestController
@RequestMapping("/no-discovery")
public class HelloController {

    @GetMapping
    public ResponseEntity sayHello() {
        return ResponseEntity.ok("Hello from no-discovery service");
    }

}
