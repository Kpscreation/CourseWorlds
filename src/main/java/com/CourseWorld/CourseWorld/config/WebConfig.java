package com.CourseWorld.CourseWorld.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {
    
    @Autowired
    private RoleBasedInterceptor roleBasedInterceptor;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(roleBasedInterceptor)
                .addPathPatterns("/Admin/**", "/User/**","/Institute/**") // Secure these paths
                .excludePathPatterns("/login", "/register"); // Don't secure login or register
    }
}
