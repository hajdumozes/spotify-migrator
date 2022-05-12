package com.mozeshajdu.spotifymigrator.tagging.web.dto;

import com.mozeshajdu.spotifymigrator.spotify.entity.SearchParameter;
import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

import java.util.List;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class SearchTracksForTagDto {
    List<SearchParameter> searchParameters;
    Long audioTagId;
}
