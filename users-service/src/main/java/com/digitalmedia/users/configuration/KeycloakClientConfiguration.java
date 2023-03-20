package com.digitalmedia.users.configuration;

import org.keycloak.OAuth2Constants;
import org.keycloak.admin.client.Keycloak;
import org.keycloak.admin.client.KeycloakBuilder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

@Component
public class KeycloakClientConfiguration {

    @Value("http://localhost:8082")
    private String serverUrl;
    @Value("DigitalMedia")
    private String realm;
    @Value("microservicios")
    private String clientId;
    @Value("o4qr0tJsbARPAGvPunIJ1RtY0VKZ4EQK")
    private String clientSecret;

    @Bean
    public Keycloak buildClient() {

        return KeycloakBuilder.builder()
                .serverUrl(serverUrl)
                .realm(realm)
                .clientId(clientId)
                .clientSecret(clientSecret)
                .grantType(OAuth2Constants.CLIENT_CREDENTIALS)
                .build();
    }
}
