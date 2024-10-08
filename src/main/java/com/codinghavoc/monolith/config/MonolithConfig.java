package com.codinghavoc.monolith.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpHeaders;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class MonolithConfig {
    @Bean
    public WebMvcConfigurer corsConfig(){
        return new WebMvcConfigurer() {
            @Override
            public void addCorsMappings(CorsRegistry registry){
                registry.addMapping("/**")
                // .allowedOrigins("http://localhost:4200")
                .allowedOrigins("https://monolithfrontend-795a0b654edf.herokuapp.com/",
                    "http://monolithfrontend-795a0b654edf.herokuapp.com/",
                    "http://reactfrontend-d90587a2ba10.herokuapp.com",
                    "https://reactfrontend-d90587a2ba10.herokuapp.com")
                .allowedMethods(HttpMethod.GET.name(),
                HttpMethod.POST.name(),
                HttpMethod.DELETE.name())
                .allowedHeaders(HttpHeaders.CONTENT_TYPE,
                HttpHeaders.AUTHORIZATION);

            }
        };
    }
    
}
