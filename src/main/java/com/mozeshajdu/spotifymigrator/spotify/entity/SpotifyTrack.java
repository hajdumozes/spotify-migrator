package com.mozeshajdu.spotifymigrator.spotify.entity;

import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

import java.util.List;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class SpotifyTrack {
    String id;
    String name;
    String album;
    String releaseDate;
    List<String> artists;
    String href;
    Integer popularity;
    String trackNumber;

}
