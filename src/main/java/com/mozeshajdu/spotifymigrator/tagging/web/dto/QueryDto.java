package com.mozeshajdu.spotifymigrator.tagging.web.dto;

import com.mozeshajdu.spotifymigrator.spotify.entity.SpotifySearchParameter;
import com.mozeshajdu.spotifymigrator.tagging.client.AudioTagQuery;
import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

import java.util.List;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class QueryDto {
    List<SpotifySearchParameter> spotifySearchParameters;
    AudioTagQuery audioTagQuery;
}
