package com.mozeshajdu.spotifymigrator.spotify.entity;

import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class FollowedArtist {
    String id;
    String name;
}
