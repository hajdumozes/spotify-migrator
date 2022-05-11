package com.mozeshajdu.spotifymigrator.spotify.service;

import com.mozeshajdu.spotifymigrator.spotify.entity.SearchParameters;
import org.springframework.stereotype.Component;

@Component
public class SpotifyQueryStringGenerator {
    public static final String SPOTIFY_TRACK_QUERY_PARAM = "track";
    public static final String SPOTIFY_ALBUM_QUERY_PARAM = "album";
    public static final String SPOTIFY_ARTIST_QUERY_PARAM = "artist";
    public static final String SPOTIFY_YEAR_QUERY_PARAM = "year";
    public static final String SPOTIFY_QUERY_DELIMITER = " ";
    public static final String SPOTIFY_SEARCH_PARAM_FORMAT = "%s:%s";

    public String generateFrom(SearchParameters searchParameters) {
        String trackPart = String.format(SPOTIFY_SEARCH_PARAM_FORMAT, SPOTIFY_TRACK_QUERY_PARAM, searchParameters.getTitle());
        String albumPart = String.format(SPOTIFY_SEARCH_PARAM_FORMAT, SPOTIFY_ALBUM_QUERY_PARAM, searchParameters.getAlbum());
        String artistPart = String.format(SPOTIFY_SEARCH_PARAM_FORMAT, SPOTIFY_ARTIST_QUERY_PARAM, searchParameters.getArtist());
        String yearPart = String.format(SPOTIFY_SEARCH_PARAM_FORMAT, SPOTIFY_YEAR_QUERY_PARAM, searchParameters.getYear());
        return String.join(SPOTIFY_QUERY_DELIMITER, trackPart, albumPart, artistPart, yearPart);
    }
}
