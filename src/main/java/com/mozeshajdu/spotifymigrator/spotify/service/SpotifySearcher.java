package com.mozeshajdu.spotifymigrator.spotify.service;

import com.mozeshajdu.spotifymigrator.spotify.entity.SearchParameter;
import com.mozeshajdu.spotifymigrator.spotify.entity.SpotifyTrack;
import com.mozeshajdu.spotifymigrator.spotify.exception.CredentialGenerationException;
import com.mozeshajdu.spotifymigrator.spotify.exception.SpotifySearchException;
import com.mozeshajdu.spotifymigrator.spotify.mapper.SpotifyTrackMapper;
import com.mozeshajdu.spotifymigrator.tagging.entity.AudioTag;
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
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
public class SpotifySearcher {
    SpotifyApi spotifyApi;
    SpotifyQueryStringGenerator searchTrackQueryParam;
    SpotifyTrackMapper spotifyTrackMapper;

    public Optional<SpotifyTrack> getFromSpotify(AudioTag audioTag, List<SearchParameter> searchParameters) {
        searchParameters.get(0).getFieldValueGetter().apply(audioTag);
        ClientCredentials credentials = getClientCredentials();
        spotifyApi.setAccessToken(credentials.getAccessToken());
        SearchTracksRequest request = getRequest(searchParameters, audioTag);
        Paging<Track> result = executeSearch(request);
        List<SpotifyTrack> spotifyTracks = toSpotifyTracks(audioTag, result);
        return filterMostPopular(spotifyTracks, audioTag);
    }


    private SearchTracksRequest getRequest(List<SearchParameter> searchParameters, AudioTag audioTag) {
        return spotifyApi.searchTracks(searchTrackQueryParam.generateFrom(searchParameters, audioTag))
                .market(CountryCode.HU)
                .build();
    }

    private List<SpotifyTrack> toSpotifyTracks(AudioTag audioTag, Paging<Track> result) {
        return Arrays.stream(result.getItems())
                .map(track -> spotifyTrackMapper.toSpotifyTrack(track, audioTag.getId()))
                .collect(Collectors.toList());
    }

    private Optional<SpotifyTrack> filterMostPopular(List<SpotifyTrack> tracks, AudioTag audioTag) {
        Optional<SpotifyTrack> result = tracks.stream()
                .max(Comparator.comparing(SpotifyTrack::getPopularity));
        result.ifPresentOrElse(
                track -> log.info("search result found for {}", audioTag.toString()),
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
