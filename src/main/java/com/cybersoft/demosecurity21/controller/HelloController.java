package com.cybersoft.demosecurity21.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/hello")
public class HelloController {

    @GetMapping("")
    ResponseEntity<?> hello(){
        return new ResponseEntity<>("Hello Security", HttpStatus.OK);
    }
    @GetMapping("/add")
    ResponseEntity<?> add(){
        return new ResponseEntity<>("Hello Add", HttpStatus.OK);
    }
}
