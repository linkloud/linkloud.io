package io.linkloud.api.global.config;

import io.linkloud.api.global.converter.StringToArticleSortByConverter;
import io.linkloud.api.global.converter.StringToTagSortByConverter;
import io.linkloud.api.global.security.auth.jwt.JwtProvider;
import io.linkloud.api.global.security.resolver.LoginMemberIdArgumentResolver;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.format.FormatterRegistry;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@RequiredArgsConstructor
@Configuration
public class WebConfig implements WebMvcConfigurer {

    private final JwtProvider jwtProvider;

    @Bean
    public WebClient webClient() {
        return WebClient.builder().build();
    }

    @Override
    public void addFormatters(FormatterRegistry registry) {
        // config에 converter 등록
        registry.addConverter(new StringToTagSortByConverter());
        registry.addConverter(new StringToArticleSortByConverter());
    }

    @Override
    public void addArgumentResolvers( List<HandlerMethodArgumentResolver> resolvers) {
        // config 에 resolver 등록
        resolvers.add(new LoginMemberIdArgumentResolver(jwtProvider));
    }
}