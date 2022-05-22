package com.mozeshajdu.spotifymigrator.spotify.util;

import com.mozeshajdu.spotifymigrator.spotify.exception.SpotifyApiException;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import se.michaelthelin.spotify.requests.AbstractRequest;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class SpotifyApiUtil {

    public static <T> T executeRequest(AbstractRequest<T> request) {
        try {
            return request.execute();
        } catch (Exception e) {
            throw new SpotifyApiException(e.getMessage());
        }
    }
}
