package com.mozeshajdu.spotifymigrator.spotify.entity.event;

import com.mozeshajdu.spotifymigrator.spotify.entity.SpotifyAction;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Data;
import lombok.experimental.FieldDefaults;

import java.util.List;

@Data
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class TracksLikedMessage {
    List<String> spotifyIds;
    SpotifyAction spotifyAction;
}