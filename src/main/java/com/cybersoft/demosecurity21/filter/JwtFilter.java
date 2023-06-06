package com.cybersoft.demosecurity21.filter;

import com.cybersoft.demosecurity21.utils.JwtHelper;
import io.jsonwebtoken.Claims;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;

// Tất cả các request đều phải chạy vào filter
@Component
public class JwtFilter extends OncePerRequestFilter {
    @Autowired
    JwtHelper jwtHelper;
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        // Lấy giá trị của header có key là Authorization
        String header = request.getHeader("Authorization");
        if(header.startsWith("Bearer ")){
            String token = header.substring(7);
            Claims claims = jwtHelper.decodeToken(token);
            if(claims != null){
                // Tạo chứng thực cho Security
                SecurityContext context = SecurityContextHolder.getContext();
                UsernamePasswordAuthenticationToken userToken = new UsernamePasswordAuthenticationToken("", "", new ArrayList<>());
                context.setAuthentication(userToken);
            }
        }

        filterChain.doFilter(request, response);
    }
}
