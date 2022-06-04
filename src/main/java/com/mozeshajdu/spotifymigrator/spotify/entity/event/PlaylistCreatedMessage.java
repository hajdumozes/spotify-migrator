package com.mozeshajdu.spotifymigrator.spotify.entity.event;

import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class PlaylistCreatedMessage {
    String id;
    String name;
    String url;
}
