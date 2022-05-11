package com.mozeshajdu.spotifymigrator.spotify.service;

import com.mozeshajdu.spotifymigrator.tag.entity.AudioTag;
import org.springframework.stereotype.Component;

@Component
public class SpotifyQueryStringGenerator {
    public static final String SPOTIFY_TRACK_QUERY_PARAM = "track";
    public static final String SPOTIFY_ALBUM_QUERY_PARAM = "album";
    public static final String SPOTIFY_ARTIST_QUERY_PARAM = "artist";
    public static final String SPOTIFY_YEAR_QUERY_PARAM = "year";
    public static final String SPOTIFY_QUERY_DELIMITER = " ";
    public static final String SPOTIFY_SEARCH_PARAM_FORMAT = "%s:%s";

    public String generateFrom(AudioTag audioTag) {
        String trackPart = String.format(SPOTIFY_SEARCH_PARAM_FORMAT, SPOTIFY_TRACK_QUERY_PARAM, audioTag.getTitle());
        String albumPart = String.format(SPOTIFY_SEARCH_PARAM_FORMAT, SPOTIFY_ALBUM_QUERY_PARAM, audioTag.getAlbum());
        String artistPart = String.format(SPOTIFY_SEARCH_PARAM_FORMAT, SPOTIFY_ARTIST_QUERY_PARAM, audioTag.getArtists().get(0).getName());
        String yearPart = String.format(SPOTIFY_SEARCH_PARAM_FORMAT, SPOTIFY_YEAR_QUERY_PARAM, audioTag.getYear());
        return String.join(SPOTIFY_QUERY_DELIMITER, trackPart, albumPart, artistPart, yearPart);
    }
}
