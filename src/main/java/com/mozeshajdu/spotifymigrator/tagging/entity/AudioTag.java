package com.mozeshajdu.spotifymigrator.tagging.entity;

import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

import java.util.List;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class AudioTag {
    Long id;
    String title;
    String year;
    String album;
    List<Artist> artists;
    List<AlbumArtist> albumArtists;
    List<Genre> genres;
}
