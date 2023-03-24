package com.finnplay.demo.app.aop;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;


@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Autowired
    UserDetailsService userDetailsService;

    @Bean
    public SecurityFilterChain buildSecurityFilterChain(HttpSecurity httpSecurity) throws Exception {
       
             return httpSecurity.authorizeHttpRequests(authConfig -> authConfig
               .requestMatchers("/").permitAll()
               .requestMatchers("/index").permitAll()
               .requestMatchers("/signup").permitAll()
               .requestMatchers("/register").permitAll()
               .requestMatchers("/success").permitAll()
                .anyRequest().authenticated())
             .formLogin(form -> form.loginPage("/login").usernameParameter("email")
                .successForwardUrl("/userInfo").permitAll())
                .build();  

    }
     @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

   @Bean
    public DaoAuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        provider.setUserDetailsService(userDetailsService);
        provider.setPasswordEncoder(passwordEncoder());
        return provider;
    }   

   
}
