package com.cybersoft.demosecurity21.controller;

import com.cybersoft.demosecurity21.utils.JwtHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/login")
public class LoginController {

    /*
    *
    * Cách 1:   Nhận username and password
    *           Kiểm tra username và password trong database
    *           Nếu tồn tại trả token
    *
    * Cách 2:   Sử dụng Spring Security
    *           Gọi AuthenticationManager
    *           thực thi phương thức authenticate() trong AuthenticationManager
    * */

@Autowired
private AuthenticationManager authenticationManager;
@Autowired
    JwtHelper jwtHelper;
    @PostMapping("")
    public ResponseEntity<?>login(
            @RequestParam String username,
            @RequestParam String password
    ){
        UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(username, password);
        authenticationManager.authenticate(token);
        // Nếu chứng thực thành công sẽ chạy code tiếp theo còn nếu thất bại sẽ trả ra lỗi chưa chứng thực
        String jwt = jwtHelper.generateToken(username);


        return new ResponseEntity<>(jwt, HttpStatus.OK);
    }
}
