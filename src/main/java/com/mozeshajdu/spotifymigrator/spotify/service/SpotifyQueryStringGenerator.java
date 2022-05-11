package com.mozeshajdu.spotifymigrator.spotify.service;

import com.mozeshajdu.spotifymigrator.spotify.entity.SearchParameters;
import org.apache.logging.log4j.util.Strings;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class SpotifyQueryStringGenerator {
    public static final String TITLE_FIELD = "track";
    public static final String ALBUM_FIELD = "album";
    public static final String ARTIST_FIELD = "artist";
    public static final String YEAR_FIELD = "year";
    public static final String SPOTIFY_QUERY_DELIMITER = " ";
    public static final String SPOTIFY_SEARCH_PARAM_FORMAT = "%s:%s";

    public String generateFrom(SearchParameters searchParameters) {
        String trackPart = getQueryPart(TITLE_FIELD, searchParameters.getTitle());
        String albumPart = getQueryPart(ALBUM_FIELD, searchParameters.getAlbum());
        String artistPart = getQueryPart(ARTIST_FIELD, searchParameters.getArtist());
        String yearPart = getQueryPart(YEAR_FIELD, searchParameters.getYear());
        return String.join(SPOTIFY_QUERY_DELIMITER, trackPart, albumPart, artistPart, yearPart);
    }

    private String getQueryPart(String field, String fieldValue) {
        return Optional.ofNullable(fieldValue)
                .map(value -> String.format(SPOTIFY_SEARCH_PARAM_FORMAT, field, value))
                .orElse(Strings.EMPTY);
    }
}
