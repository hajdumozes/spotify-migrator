package com.mozeshajdu.spotifymigrator.tagging.client;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Data;
import lombok.experimental.FieldDefaults;

@Data
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class AudioTagQuery {
    String title;
    Integer ratingAtLeast;
    Boolean spotifyTrackPresence;
    String grouping;
}
