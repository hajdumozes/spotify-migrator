package com.mozeshajdu.spotifymigrator.config.spotify;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import se.michaelthelin.spotify.SpotifyApi;
import se.michaelthelin.spotify.SpotifyHttpManager;

@Configuration
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class SpotifyConfiguration {
    SpotifyProperties spotifyProperties;

    @Bean
    public SpotifyApi spotifyApi() {
        return SpotifyApi.builder()
                .setClientId(spotifyProperties.getClientId())
                .setClientSecret(spotifyProperties.getClientSecret())
                .setRedirectUri(SpotifyHttpManager.makeUri(spotifyProperties.getRedirectUri()))
                .build();
    }
}
