package com.example.demo.config;

import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.example.demo.Filter.JwtValidationFilter;

@Configuration
public class FilterConfig {
    @Bean
    FilterRegistrationBean<JwtValidationFilter> jwtFilter(JwtValidationFilter jwtValidationFilter) {

        FilterRegistrationBean<JwtValidationFilter> registrationBean = new FilterRegistrationBean<>();

        registrationBean.setFilter(jwtValidationFilter);

        registrationBean.addUrlPatterns("/*");

        registrationBean.setOrder(0);

        return registrationBean;
    }
}