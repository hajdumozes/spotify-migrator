package com.mozeshajdu.spotifymigrator.spotify.entity;

import com.mozeshajdu.spotifymigrator.spotify.SpotifyTrackQuery;
import com.mozeshajdu.spotifymigrator.tagging.entity.AudioTag;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;

import java.util.function.Function;

@Getter
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public enum SpotifySearchParameter {
    TITLE("track", AudioTag::getTitle, SpotifyTrackQuery::getTitle),
    ARTIST("artist", tag -> tag.getArtists().get(0).getName(), SpotifyTrackQuery::getArtist),
    YEAR("year", AudioTag::getYear, SpotifyTrackQuery::getYear),
    ALBUM("album", AudioTag::getAlbum, SpotifyTrackQuery::getAlbum),
    ALBUM_ARTIST("artist", tag -> tag.getAlbumArtists().get(0).getName(), SpotifyTrackQuery::getArtist);

    String searchField;
    Function<AudioTag, String> audioTagFieldValueGetter;
    Function<SpotifyTrackQuery, String> spotifyTrackQueryFieldValueGetter;
}
