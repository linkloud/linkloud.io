package io.linkloud.api.global.config;

import io.linkloud.api.global.security.auth.jwt.JwtProvider;
import io.linkloud.api.global.security.resolver.LoginMemberIdArgumentResolver;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
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
    public void addArgumentResolvers( List<HandlerMethodArgumentResolver> resolvers) {
        resolvers.add(new LoginMemberIdArgumentResolver(jwtProvider));
    }
}
