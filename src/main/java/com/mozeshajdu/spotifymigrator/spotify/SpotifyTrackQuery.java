package com.mozeshajdu.spotifymigrator.spotify;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

@Data
@JsonInclude(JsonInclude.Include.NON_EMPTY)
@FieldDefaults(level = AccessLevel.PRIVATE)
public class SpotifyTrackQuery {
    String title;
    String album;
    String year;
    String artist;
}
