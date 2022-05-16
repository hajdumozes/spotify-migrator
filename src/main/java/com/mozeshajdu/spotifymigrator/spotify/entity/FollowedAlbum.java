package com.mozeshajdu.spotifymigrator.spotify.entity;

import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

import java.util.List;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class FollowedAlbum {
    String id;
    String name;
    List<String> artists;
    List<String> genres;
    Integer popularity;
    String releaseDate;
    String url;
}
