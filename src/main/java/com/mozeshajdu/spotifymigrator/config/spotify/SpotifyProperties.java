package com.mozeshajdu.spotifymigrator.config.spotify;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix = "spotify")
@Data
public class SpotifyProperties {
    String clientId;
    String clientSecret;
    String redirectUri;
    int searchParamLength;
}
