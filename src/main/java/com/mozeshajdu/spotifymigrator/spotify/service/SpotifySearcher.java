package com.mozeshajdu.spotifymigrator.spotify.service;

import com.mozeshajdu.spotifymigrator.spotify.entity.SpotifySearchParameter;
import com.mozeshajdu.spotifymigrator.spotify.entity.ConnectedSpotifyTrack;
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
import se.michaelthelin.spotify.requests.authorization.client_credentials.ClientCredentialsRequest;
import se.michaelthelin.spotify.requests.data.search.simplified.SearchTracksRequest;
import se.michaelthelin.spotify.requests.data.tracks.GetTrackRequest;

import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static com.mozeshajdu.spotifymigrator.spotify.util.SpotifyApiUtil.executeRequest;

@Component
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
public class SpotifySearcher {
    SpotifyApi spotifyApi;
    SpotifyQueryStringGenerator searchTrackQueryParam;
    SpotifyTrackMapper spotifyTrackMapper;

    public ConnectedSpotifyTrack getById(String spotifyId, Long audioTagId) {
        ClientCredentialsRequest credentialsRequest = spotifyApi.clientCredentials().build();
        ClientCredentials credentials = executeRequest(credentialsRequest);
        spotifyApi.setAccessToken(credentials.getAccessToken());
        GetTrackRequest request = spotifyApi.getTrack(spotifyId)
                .market(CountryCode.HU)
                .build();
        Track result = executeRequest(request);
        return spotifyTrackMapper.toConnectedSpotifyTrack(result, audioTagId);
    }

    public List<ConnectedSpotifyTrack> search(AudioTag audioTag, List<SpotifySearchParameter> spotifySearchParameters) {
        ClientCredentialsRequest credentialsRequest = spotifyApi.clientCredentials().build();
        ClientCredentials credentials = executeRequest(credentialsRequest);
        spotifyApi.setAccessToken(credentials.getAccessToken());
        SearchTracksRequest request = getRequest(spotifySearchParameters, audioTag);
        Paging<Track> result = executeRequest(request);
        return toSpotifyTracks(audioTag, result);
    }

    public Optional<ConnectedSpotifyTrack> getMostPopularForTag(AudioTag audioTag, List<SpotifySearchParameter> spotifySearchParameters) {
        List<ConnectedSpotifyTrack> connectedSpotifyTracks = search(audioTag, spotifySearchParameters);
        return filterMostPopular(connectedSpotifyTracks, audioTag);
    }


    private SearchTracksRequest getRequest(List<SpotifySearchParameter> spotifySearchParameters, AudioTag audioTag) {
        return spotifyApi.searchTracks(searchTrackQueryParam.generateFrom(spotifySearchParameters, audioTag))
                .market(CountryCode.HU)
                .build();
    }

    private List<ConnectedSpotifyTrack> toSpotifyTracks(AudioTag audioTag, Paging<Track> result) {
        return Arrays.stream(result.getItems())
                .map(track -> spotifyTrackMapper.toConnectedSpotifyTrack(track, audioTag.getId()))
                .collect(Collectors.toList());
    }

    private Optional<ConnectedSpotifyTrack> filterMostPopular(List<ConnectedSpotifyTrack> tracks, AudioTag audioTag) {
        Optional<ConnectedSpotifyTrack> result = tracks.stream()
                .max(Comparator.comparing(ConnectedSpotifyTrack::getPopularity));
        result.ifPresentOrElse(
                track -> log.info("search result found for {}", audioTag.toString()),
                () -> log.warn("search result not found for {}", audioTag.toString()));
        return result;
    }
}
