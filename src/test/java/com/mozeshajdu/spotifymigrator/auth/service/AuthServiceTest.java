package com.mozeshajdu.spotifymigrator.auth.service;

import com.mozeshajdu.spotifymigrator.auth.entity.UserCredential;
import lombok.SneakyThrows;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import se.michaelthelin.spotify.SpotifyApi;
import se.michaelthelin.spotify.model_objects.credentials.AuthorizationCodeCredentials;
import se.michaelthelin.spotify.requests.authorization.authorization_code.AuthorizationCodeRequest;
import se.michaelthelin.spotify.requests.authorization.authorization_code.AuthorizationCodeUriRequest;

import java.net.URI;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(MockitoExtension.class)
class AuthServiceTest {

    public static final String URI = "uri";
    public static final String CODE = "code";
    public static final String ACCESS_TOKEN = "accessToken";
    public static final String REFRESH_TOKEN = "refreshToken";
    public static final int EXPIRES_IN = 1;

    @InjectMocks
    AuthService underTest;

    @Mock
    SpotifyApi spotifyApi;

    @Mock
    AuthorizationCodeUriRequest.Builder codeUriRequestBuilder;

    @Mock
    AuthorizationCodeUriRequest codeUriRequest;

    @Mock
    AuthorizationCodeRequest.Builder codeRequestBuilder;

    @Mock
    AuthorizationCodeRequest codeRequest;

    @Mock
    AuthorizationCodeCredentials authorizationCodeCredentials;

    @Test
    void shouldReturnUri_onGeneratingAuthCodeUri() {
        // given
        URI uri = java.net.URI.create(URI);
        Mockito.when(spotifyApi.authorizationCodeUri()).thenReturn(codeUriRequestBuilder);
        Mockito.when(codeUriRequestBuilder.scope(Mockito.any(String.class))).thenReturn(codeUriRequestBuilder);
        Mockito.when(codeUriRequestBuilder.build()).thenReturn(codeUriRequest);
        Mockito.when(codeUriRequest.execute()).thenReturn(uri);

        // when
        String result = underTest.generateAuthorizationCodeUri();

        // then
        assertThat(result).isEqualTo(uri.toString());
    }

    @Test
    @SneakyThrows
    void shouldReturnCredential_onInitializingTokens() {
        // given
        Mockito.when(spotifyApi.authorizationCode(CODE)).thenReturn(codeRequestBuilder);
        Mockito.when(codeRequestBuilder.build()).thenReturn(codeRequest);
        Mockito.when(codeRequest.execute()).thenReturn(authorizationCodeCredentials);
        Mockito.when(authorizationCodeCredentials.getAccessToken()).thenReturn(ACCESS_TOKEN);
        Mockito.when(authorizationCodeCredentials.getRefreshToken()).thenReturn(REFRESH_TOKEN);
        Mockito.when(authorizationCodeCredentials.getExpiresIn()).thenReturn(EXPIRES_IN);

        // when
        UserCredential userCredential = underTest.initializeTokens(CODE);

        // then
        assertThat(userCredential.getAccessToken()).isEqualTo(ACCESS_TOKEN);
        assertThat(userCredential.getRefreshToken()).isEqualTo(REFRESH_TOKEN);
        assertThat(userCredential.getExpiresIn()).isEqualTo(EXPIRES_IN);
    }
}