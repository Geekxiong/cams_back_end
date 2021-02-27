package com.must.mit19bxw.cams;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class MyWebConfigurer implements WebMvcConfigurer {
    private String rootPath;
    private String staticPath;

    @Value("${system.rootPath}")
    public void setRootPath(String rootPath) {
        this.rootPath = rootPath;
    }

    @Value("${system.staticPath}")
    public void setStaticPath(String staticPath) {
        this.staticPath = staticPath;
    }

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/api/static/**").addResourceLocations("file:"+rootPath+staticPath);
    }
}