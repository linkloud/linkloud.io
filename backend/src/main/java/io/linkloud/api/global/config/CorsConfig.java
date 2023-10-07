package io.linkloud.api.global.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class CorsConfig implements WebMvcConfigurer {

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
            .allowedOrigins("https://linkloud.io") // 허용할 도메인을 지정합니다.
            .allowedMethods("GET", "POST", "PUT", "DELETE","PATCH")
            .allowedHeaders("*");
    }
}
