package com.gokul.ecommerce.config;

import feign.RequestInterceptor;
import feign.RequestTemplate;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor
public class FeignOAuth2Config {

    private final OAuth2TokenProvider tokenProvider;

    @Bean
    public RequestInterceptor oauth2FeignRequestInterceptor() {
        return (RequestTemplate template) ->
                template.header("Authorization", "Bearer " + tokenProvider.getAccessToken());
    }
}
