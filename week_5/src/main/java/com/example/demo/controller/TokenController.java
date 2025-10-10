// file: com/example/demo/controller/TokenController.java
package com.example.demo.controller;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.client.*;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
public class TokenController {

    private final OAuth2AuthorizedClientService clientService;

    public TokenController(OAuth2AuthorizedClientService clientService) {
        this.clientService = clientService;
    }

    @GetMapping("/tokens")
    public Map<String, Object> tokens(@AuthenticationPrincipal OidcUser user, OAuth2AuthenticationToken auth) {
        if (user == null || auth == null) return Map.of("status", "anonymous");

        var client = clientService.loadAuthorizedClient(auth.getAuthorizedClientRegistrationId(), auth.getName());
        var accessToken = client.getAccessToken();
        var refreshToken = client.getRefreshToken(); // <- có thể null nếu IdP không cấp

        return Map.of(
                "principal", user.getFullName(),
                "access_token_expires_at", accessToken != null ? accessToken.getExpiresAt() : null,
                "has_refresh_token", refreshToken != null
        );
    }
}
