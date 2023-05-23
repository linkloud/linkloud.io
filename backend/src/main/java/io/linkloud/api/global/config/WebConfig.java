package io.linkloud.api.global.config;

import io.linkloud.api.global.converter.StringToTagSortByConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.format.FormatterRegistry;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Bean
    public WebClient webClient() {
        return WebClient.builder().build();
    }

    @Override
    public void addFormatters(FormatterRegistry registry) {
        // config에 converter 등록
        registry.addConverter(new StringToTagSortByConverter());
    }
}
