package com.mozeshajdu.spotifymigrator.auth.service;

import com.mozeshajdu.spotifymigrator.auth.entity.SpotifyScope;
import com.mozeshajdu.spotifymigrator.auth.entity.UserCredential;
import com.mozeshajdu.spotifymigrator.auth.exception.CodeRequestException;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;
import se.michaelthelin.spotify.SpotifyApi;
import se.michaelthelin.spotify.model_objects.credentials.AuthorizationCodeCredentials;
import se.michaelthelin.spotify.requests.authorization.authorization_code.AuthorizationCodeRequest;
import se.michaelthelin.spotify.requests.authorization.authorization_code.AuthorizationCodeUriRequest;

import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class AuthService {
    public static final String SCOPE_DELIMITER = " ";
    SpotifyApi spotifyApi;

    public String generateAuthorizationCodeUri() {
        List<SpotifyScope> requiredScopes = List.of(
                SpotifyScope.PLAYLIST_READ_PRIVATE,
                SpotifyScope.PLAYLIST_MODIFY_PUBLIC,
                SpotifyScope.PLAYLIST_MODIFY_PRIVATE,
                SpotifyScope.USER_LIBRARY_READ,
                SpotifyScope.USER_LIBRARY_MODIFY,
                SpotifyScope.USER_FOLLOW_READ,
                SpotifyScope.USER_FOLLOW_MODIFY);
        String joinedScopes = requiredScopes.stream()
                .map(SpotifyScope::getValue)
                .collect(Collectors.joining(SCOPE_DELIMITER));
        AuthorizationCodeUriRequest request = spotifyApi
                .authorizationCodeUri()
                .scope(joinedScopes)
                .build();
        URI uri = request.execute();
        return uri.toString();
    }

    public UserCredential initializeTokens(String code) {
        AuthorizationCodeRequest request = spotifyApi.authorizationCode(code).build();
        AuthorizationCodeCredentials authorizationCodeCredentials = executeCodeRequest(request);
        spotifyApi.setAccessToken(authorizationCodeCredentials.getAccessToken());
        spotifyApi.setRefreshToken(authorizationCodeCredentials.getRefreshToken());
        return UserCredential.builder()
                .accessToken(authorizationCodeCredentials.getAccessToken())
                .refreshToken(authorizationCodeCredentials.getRefreshToken())
                .expiresIn(authorizationCodeCredentials.getExpiresIn())
                .build();
    }

    private AuthorizationCodeCredentials executeCodeRequest(AuthorizationCodeRequest request) {
        try {
            return request.execute();
        } catch (Exception e) {
            throw new CodeRequestException(e.getMessage());
        }
    }
}
