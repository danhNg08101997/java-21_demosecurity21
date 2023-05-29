package com.cybersoft.demosecurity21.controller;


import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/admin")
public class AdminController {

    @GetMapping("")
    public ResponseEntity<?> admin() {
        return new ResponseEntity<>("Hello Admin", HttpStatus.OK);
    }

    @GetMapping("/save")
    public ResponseEntity<?> save(){
        return new ResponseEntity<>("Admin Save", HttpStatus.OK);
    }

    @GetMapping("/delete")
    public ResponseEntity<?> delete(){
        return new ResponseEntity<>("Admin Delete", HttpStatus.OK);
    }
}
