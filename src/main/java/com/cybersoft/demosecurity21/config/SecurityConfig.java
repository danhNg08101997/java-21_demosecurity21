package com.cybersoft.demosecurity21.config;


import com.cybersoft.demosecurity21.filter.JwtFilter;
import com.cybersoft.demosecurity21.provider.CustomAuthenProvider;
import com.cybersoft.demosecurity21.service.CustomUserDetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfig {
    // Khai báo chuẩn mã hoá Bcrypt và lưu trữ trên IOC
    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }


//    @Bean
//    public UserDetailsService userDetailsService(PasswordEncoder passwordEncoder){
//        UserDetails admin = User.withUsername("admin")
//                .password(passwordEncoder().encode("123456"))
//                .roles("ADMIN", "DELETE")
//                .build();
//        UserDetails user = User.withUsername("user")
//                .password(passwordEncoder().encode("123456"))
//                .roles("USER", "SAVE")
//                .build();
//
//        return new InMemoryUserDetailsManager(admin, user);
//    }

    @Autowired
    private CustomUserDetailService customUserDetailService;
    @Autowired
    private CustomAuthenProvider customAuthenProvider;
    @Autowired
    private JwtFilter jwtFilter;

    @Bean
    public AuthenticationManager authenticationManager(HttpSecurity httpSecurity) throws Exception{
        return httpSecurity.getSharedObject(AuthenticationManagerBuilder.class)
//                .userDetailsService(customUserDetailService)
//                .passwordEncoder(passwordEncoder())
                .authenticationProvider(customAuthenProvider)
                .build();

    }

    /**
     * Đây là filter dùng để custom rule liên quan tới link hoặc cấu hình của security
     * Java 8, 11 : antMatchers
     * Java 17+: requestMatchers
     */
    @Bean
    public SecurityFilterChain filterChain (HttpSecurity http) throws Exception {
        return http.csrf().disable() // Tắt cấu hình liên quan tới tấn công csrf
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS) //Khai báo không sử dụng session trong project
                .and()
                .authorizeHttpRequests() //Quy định lại các rule liên quan đến chứng thực cho link được gọi
                .antMatchers("/hello/**", "/login").permitAll()
                .antMatchers("/admin").hasRole("ADMIN") //hasRole: phải có quyền mới vào được
                .antMatchers("/admin/save").hasAnyRole("ADMIN", "SAVE")
                .antMatchers("/admin/delete").hasRole("DELETE")
                .anyRequest().authenticated() //Tất cả các link còn lại đều phải chứng thực
                .and()
//                .httpBasic()
//                .and()
                .addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class)
                .build();
    }
}
