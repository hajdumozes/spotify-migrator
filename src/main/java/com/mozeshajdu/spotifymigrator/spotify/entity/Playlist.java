package com.mozeshajdu.spotifymigrator.spotify.entity;

import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Playlist {
    String id;
    String name;
    String url;
}
