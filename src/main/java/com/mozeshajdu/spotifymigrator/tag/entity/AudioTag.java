package com.mozeshajdu.spotifymigrator.tag.entity;

import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

import java.util.List;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class AudioTag {
    String id;
    String title;
    String year;
    String album;
    List<Artist> artists;
    List<AlbumArtist> albumArtists;
    List<Genre> genres;
}
