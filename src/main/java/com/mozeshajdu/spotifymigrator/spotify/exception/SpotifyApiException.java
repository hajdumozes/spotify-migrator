package com.mozeshajdu.spotifymigrator.spotify.exception;

import lombok.Value;

import java.net.URI;

public class SpotifyApiException extends RuntimeException {
    public <T> SpotifyApiException(SpotifyApiExceptionDetail<T> request) {
        super(request.toString());
    }

    @Value
    public static class SpotifyApiExceptionDetail<T> {
        Class<T> requestClass;
        URI uri;
    }
}
