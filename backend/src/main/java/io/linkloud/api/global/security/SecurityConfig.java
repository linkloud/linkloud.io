package io.linkloud.api.global.security;

import io.linkloud.api.global.security.auth.handler.LogoutSuccessHandler;
import io.linkloud.api.global.security.auth.handler.MemberAccessDeniedHandler;
import io.linkloud.api.global.security.auth.jwt.filter.JwtAuthenticationFilter;
import java.util.Arrays;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

@RequiredArgsConstructor
@EnableWebSecurity
@Configuration
public class SecurityConfig {

    private final JwtAuthenticationFilter jwtAuthenticationFilter;

    @Bean
    public SecurityFilterChain configure(HttpSecurity http) throws Exception {
        return http
            .cors()
            .and()
            .csrf().disable()
            .formLogin().disable()
            .httpBasic().disable()
            .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
            .and()
            .exceptionHandling()
            .accessDeniedHandler(new MemberAccessDeniedHandler())
            .and()
            .logout(
                logout -> logout
                    .logoutUrl("/api/v1/auth/logout")
                    .logoutSuccessHandler(new LogoutSuccessHandler())
                    .logoutRequestMatcher(new AntPathRequestMatcher("/api/v1/auth/logout","POST"))
            )
                .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class) // JwtAuthenticationFilter 클래스를 먼저 실행하도록 설정
                .authorizeHttpRequests(authorize -> authorize
                        .requestMatchers(HttpMethod.GET, "/api/v1/admin/**").hasAuthority("ADMIN") // 어드민
                        .requestMatchers(HttpMethod.POST, "/api/v1/article/**").hasAnyAuthority("MEMBER", "ADMIN") // 게시글 작성
                        .requestMatchers(HttpMethod.PATCH, "/api/v1/article/**").hasAnyAuthority("MEMBER", "ADMIN") // 게시글 수정
                        .requestMatchers(HttpMethod.DELETE, "/api/v1/article/**").hasAnyAuthority("MEMBER", "ADMIN") // 게시글 삭제
                        .requestMatchers(HttpMethod.GET, "/api/v1/article/**").permitAll()    // 게시글 조회 모두 허용
                        .anyRequest().permitAll() // todo : 일단 전부 허용 나중에 변경
                )
                .build();
    }

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOrigins(List.of("https://linkloud.io"));
        configuration.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "DELETE", "PATCH"));
        configuration.setAllowedHeaders(List.of("*"));
        configuration.setAllowCredentials(true);
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }
}
