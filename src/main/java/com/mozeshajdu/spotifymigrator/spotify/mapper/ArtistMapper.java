package com.mozeshajdu.spotifymigrator.spotify.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Named;
import se.michaelthelin.spotify.model_objects.specification.ArtistSimplified;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring")
public interface ArtistMapper {

    @Named("artistName")
    default List<String> of(ArtistSimplified[] artistSimplified) {
        return Arrays.stream(artistSimplified).map(ArtistSimplified::getName).collect(Collectors.toList());
    }
}
