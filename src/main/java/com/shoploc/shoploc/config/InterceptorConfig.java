package com.shoploc.shoploc.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;


@Configuration
@EnableWebMvc
public class InterceptorConfig implements WebMvcConfigurer {


    @Bean
    public HeaderInterceptor headerInterceptor(){
        return new HeaderInterceptor();
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(headerInterceptor())
                .addPathPatterns("/account/password/**","/typeProduct/*"); 
    }

}

