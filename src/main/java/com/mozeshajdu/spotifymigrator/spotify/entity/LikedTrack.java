package com.mozeshajdu.spotifymigrator.spotify.entity;

import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

import java.util.List;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class LikedTrack {
    String spotifyId;
    String name;
    String album;
    String releaseDate;
    List<String> artists;
    Integer popularity;
    String trackNumber;
    String url;
}
