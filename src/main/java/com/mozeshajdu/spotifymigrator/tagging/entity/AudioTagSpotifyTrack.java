package com.mozeshajdu.spotifymigrator.tagging.entity;

import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

import java.util.List;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class AudioTagSpotifyTrack {
    Long id;

    String spotifyId;

    String name;

    String album;

    String releaseDate;

    List<Artist> artists;

    Integer popularity;

    String trackNumber;

    String url;
}
