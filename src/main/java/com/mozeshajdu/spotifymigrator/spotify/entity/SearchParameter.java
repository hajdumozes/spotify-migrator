package com.mozeshajdu.spotifymigrator.spotify.entity;

import com.mozeshajdu.spotifymigrator.tagging.entity.AudioTag;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;

import java.util.function.Function;

@Getter
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public enum SearchParameter {
    TITLE("track", AudioTag::getTitle),
    ARTIST("artist", tag -> tag.getArtists().get(0).getName()),
    YEAR("year", AudioTag::getYear),
    ALBUM("album", AudioTag::getAlbum),
    ALBUM_ARTIST("artist", tag -> tag.getAlbumArtists().get(0).getName());

    String searchField;
    Function<AudioTag, String> fieldValueGetter;
}
