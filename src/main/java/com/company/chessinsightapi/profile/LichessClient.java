package com.company.chessinsightapi.profile;

import com.company.chessinsightapi.profile.dto.LichessUserResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;

@Component
public class LichessClient {

    private final RestClient restClient;

    public LichessClient(
            RestClient.Builder restClientBuilder,
            @Value("${lichess.api.base-url}") String baseUrl
    ) {
        this.restClient = restClientBuilder
                .baseUrl(baseUrl)
                .build();
    }

    public LichessUserResponse fetchUser(String username) {
        return restClient.get()
                .uri("user/{username}", username)
                .retrieve()
                .body(LichessUserResponse.class);
    }
}
