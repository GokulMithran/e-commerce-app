package com.gokul.ecommerce.config;

import lombok.RequiredArgsConstructor;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClient;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClientManager;
import org.springframework.security.oauth2.client.OAuth2AuthorizeRequest;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class OAuth2TokenProvider {

    private final OAuth2AuthorizedClientManager clientManager;

    public String getAccessToken() {
        OAuth2AuthorizeRequest authorizeRequest = OAuth2AuthorizeRequest
                .withClientRegistrationId("common-client")
                .principal("common-client-principal")
                .build();

        OAuth2AuthorizedClient client = clientManager.authorize(authorizeRequest);

        if (client == null || client.getAccessToken() == null) {
            throw new IllegalStateException("Unable to retrieve access token");
        }

        return client.getAccessToken().getTokenValue();
    }
}
