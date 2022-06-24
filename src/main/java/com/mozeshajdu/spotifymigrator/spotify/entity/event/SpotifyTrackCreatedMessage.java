package com.mozeshajdu.spotifymigrator.spotify.entity.event;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Data;
import lombok.experimental.FieldDefaults;

import java.util.List;

@Data
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class SpotifyTrackCreatedMessage {
    String spotifyId;
    String name;
    String album;
    String releaseDate;
    List<String> artists;
    Integer popularity;
    String trackNumber;
    String url;
    String uri;
    Long audioTagId;
}
