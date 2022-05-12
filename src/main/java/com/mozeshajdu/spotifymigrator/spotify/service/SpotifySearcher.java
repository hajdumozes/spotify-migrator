package com.mozeshajdu.spotifymigrator.spotify.service;

import com.mozeshajdu.spotifymigrator.spotify.entity.SearchParameters;
import com.mozeshajdu.spotifymigrator.spotify.exception.CredentialGenerationException;
import com.mozeshajdu.spotifymigrator.spotify.exception.SpotifySearchException;
import com.mozeshajdu.spotifymigrator.tag.entity.AudioTag;
import com.neovisionaries.i18n.CountryCode;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import se.michaelthelin.spotify.SpotifyApi;
import se.michaelthelin.spotify.model_objects.credentials.ClientCredentials;
import se.michaelthelin.spotify.model_objects.specification.Paging;
import se.michaelthelin.spotify.model_objects.specification.Track;
import se.michaelthelin.spotify.requests.data.search.simplified.SearchTracksRequest;

import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;

@Component
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
public class SpotifySearcher {
    SpotifyApi spotifyApi;
    SpotifyQueryStringGenerator searchTrackQueryParam;

    public Optional<Track> getFromSpotify(AudioTag audioTag) {
        ClientCredentials credentials = getClientCredentials();
        spotifyApi.setAccessToken(credentials.getAccessToken());
        SearchTracksRequest request = createDefaultRequest(audioTag);
        Paging<Track> result = executeSearch(request);
        if (result.getTotal() == 0) {
            SearchTracksRequest requestWithoutAlbum = createRequestWithoutAlbum(audioTag);
            result = executeSearch(requestWithoutAlbum);
        }
        return filterMostPopular(Arrays.asList(result.getItems()), audioTag);
    }

    private SearchTracksRequest createDefaultRequest(AudioTag audioTag) {
        SearchParameters searchParameters = SearchParameters.builder()
                .title(audioTag.getTitle())
                .artist(audioTag.getArtists().get(0).getName())
                .album(audioTag.getAlbum())
                .year(audioTag.getYear())
                .build();
        return getRequest(searchParameters);
    }

    private SearchTracksRequest createRequestWithoutAlbum(AudioTag audioTag) {
        SearchParameters searchParameters = SearchParameters.builder()
                .title(audioTag.getTitle())
                .artist(audioTag.getArtists().get(0).getName())
                .year(audioTag.getYear())
                .build();
        return getRequest(searchParameters);
    }

    private SearchTracksRequest getRequest(SearchParameters searchParameters) {
        return spotifyApi.searchTracks(searchTrackQueryParam.generateFrom(searchParameters))
                .market(CountryCode.HU)
                .build();
    }

    private Optional<Track> filterMostPopular(List<Track> tracks, AudioTag audioTag) {
        Optional<Track> result = tracks.stream().max(Comparator.comparing(Track::getPopularity));
        result.ifPresentOrElse(
                track -> log.trace("search result found for {}", audioTag.toString()),
                () -> log.warn("search result not found for {}", audioTag.toString()));
        return result;
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
