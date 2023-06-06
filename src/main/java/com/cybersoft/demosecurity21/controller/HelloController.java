package com.cybersoft.demosecurity21.controller;

import com.cybersoft.demosecurity21.utils.JwtHelper;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Encoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.crypto.SecretKey;

@RestController
@RequestMapping("/hello")
public class HelloController {

    @Autowired
    private JwtHelper jwtHelper;
    @GetMapping("")
    ResponseEntity<?> hello(){
//        SecretKey key = Keys.secretKeyFor(SignatureAlgorithm.HS256);
//        String strKey = Encoders.BASE64.encode(key.getEncoded());
//        System.out.println(strKey);
        String token = jwtHelper.generateToken("hello");

        return new ResponseEntity<>(token, HttpStatus.OK);
    }
    @GetMapping("/add")
    ResponseEntity<?> add(@RequestParam String token){
        Claims claims = jwtHelper.decodeToken(token);
        String data = claims.getSubject();
        return new ResponseEntity<>(data, HttpStatus.OK);
    }
}
