package com.mozeshajdu.spotifymigrator.spotify.service;

import com.mozeshajdu.spotifymigrator.spotify.SpotifyTrackQuery;
import com.mozeshajdu.spotifymigrator.spotify.entity.SpotifySearchParameter;
import com.mozeshajdu.spotifymigrator.tagging.entity.AudioTag;
import org.apache.logging.log4j.util.Strings;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
public class SpotifyQueryStringGenerator {
    public static final String SPOTIFY_QUERY_DELIMITER = " ";
    public static final String SPOTIFY_SEARCH_PARAM_FORMAT = "%s:%s";
    public static final String APOSTROPHE = "'";
    public static final List<SpotifySearchParameter> DEFAULT_SEARCH_PARAMETERS = List.of(
            SpotifySearchParameter.TITLE,
            SpotifySearchParameter.ALBUM,
            SpotifySearchParameter.ARTIST,
            SpotifySearchParameter.YEAR);

    public String generateFrom(List<SpotifySearchParameter> spotifySearchParameters, AudioTag audioTag) {
        return spotifySearchParameters.stream()
                .map(searchParameter -> getQueryPart(searchParameter.getSearchField(), searchParameter.getAudioTagFieldValueGetter().apply(audioTag)))
                .collect(Collectors.joining(SPOTIFY_QUERY_DELIMITER));
    }

    public String generateFrom(SpotifyTrackQuery spotifyTrackQuery) {
        return DEFAULT_SEARCH_PARAMETERS.stream()
                .map(searchParameter -> getQueryPart(searchParameter.getSearchField(), searchParameter.getSpotifyTrackQueryFieldValueGetter().apply(spotifyTrackQuery)))
                .collect(Collectors.joining(SPOTIFY_QUERY_DELIMITER));
    }

    private String getQueryPart(String field, String fieldValue) {
        return Optional.ofNullable(fieldValue)
                .map(value -> String.format(SPOTIFY_SEARCH_PARAM_FORMAT, field, removeApostrophe(value)))
                .orElse(Strings.EMPTY);
    }

    private String removeApostrophe(String string) {
        return string.replace(APOSTROPHE, Strings.EMPTY);
    }
}
