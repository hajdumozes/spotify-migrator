package com.mozeshajdu.spotifymigrator.spotify.service;

import com.mozeshajdu.spotifymigrator.spotify.entity.SearchParameters;
import com.mozeshajdu.spotifymigrator.spotify.exception.CredentialGenerationException;
import com.mozeshajdu.spotifymigrator.spotify.exception.SpotifySearchException;
import com.mozeshajdu.spotifymigrator.spotify.exception.SpotifyTrackNotFound;
import com.mozeshajdu.spotifymigrator.tag.entity.AudioTag;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
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

    public Track getFromSpotify(AudioTag audioTag) {
        ClientCredentials credentials = getClientCredentials();
        spotifyApi.setAccessToken(credentials.getAccessToken());
        SearchTracksRequest request = createDefaultRequest(audioTag);
        Paging<Track> result = executeSearch(request);
        if (result.getTotal() == 0) {
            SearchTracksRequest requestWithoutAlbum = createRequestWithoutAlbum(audioTag);
            result = executeSearch(requestWithoutAlbum);
        }
        return filterMostPopular(Arrays.asList(result.getItems()));
    }

    private SearchTracksRequest createDefaultRequest(AudioTag audioTag) {
        SearchParameters searchParameters = SearchParameters.builder()
                .title(audioTag.getTitle())
                .artist(audioTag.getArtists().get(0).getName())
                .album(audioTag.getAlbum())
                .year(audioTag.getYear())
                .build();
        return spotifyApi.searchTracks(searchTrackQueryParam.generateFrom(searchParameters)).build();
    }

    private SearchTracksRequest createRequestWithoutAlbum(AudioTag audioTag) {
        SearchParameters searchParameters = SearchParameters.builder()
                .title(audioTag.getTitle())
                .artist(audioTag.getArtists().get(0).getName())
                .year(audioTag.getYear())
                .build();
        return spotifyApi.searchTracks(searchTrackQueryParam.generateFrom(searchParameters)).build();
    }

    private Track filterMostPopular(List<Track> tracks) {
        return tracks.stream().max(Comparator.comparing(Track::getPopularity))
                .orElseThrow(SpotifyTrackNotFound::new);
    }

    private ClientCredentials getClientCredentials() {
        try {
            return spotifyApi.clientCredentials().build().execute();
        } catch (Exception e) {
            throw new CredentialGenerationException(e.getMessage());
        }
    }

    private Paging<Track> executeSearch(SearchTracksRequest request) {
        try {
            return request.execute();
        } catch (Exception e) {
            throw new SpotifySearchException(e.getMessage());
        }
    }
}
