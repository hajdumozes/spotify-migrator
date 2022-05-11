package com.mozeshajdu.spotifymigrator.spotify.service;

import com.mozeshajdu.spotifymigrator.tag.entity.AudioTag;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Component;
import se.michaelthelin.spotify.SpotifyApi;
import se.michaelthelin.spotify.model_objects.credentials.ClientCredentials;
import se.michaelthelin.spotify.model_objects.specification.Paging;
import se.michaelthelin.spotify.model_objects.specification.Track;
import se.michaelthelin.spotify.requests.data.search.simplified.SearchTracksRequest;

import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

@Component
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class SpotifySearcher {
    SpotifyApi spotifyApi;
    SpotifyQueryStringGenerator searchTrackQueryParam;

    @SneakyThrows
    public Track getFromSpotify(AudioTag audioTag) {
        ClientCredentials credentials = spotifyApi.clientCredentials().build().execute();
        spotifyApi.setAccessToken(credentials.getAccessToken());
        SearchTracksRequest request =
                spotifyApi.searchTracks(searchTrackQueryParam.generateFrom(audioTag)).build();
        Paging<Track> result = request.execute();
        return filterMostPopular(Arrays.asList(result.getItems()));
    }

    private Track filterMostPopular(List<Track> tracks) {
        return tracks.stream().min(Comparator.comparing(Track::getPopularity)).orElseGet(() -> new Track.Builder().build());
    }
}
