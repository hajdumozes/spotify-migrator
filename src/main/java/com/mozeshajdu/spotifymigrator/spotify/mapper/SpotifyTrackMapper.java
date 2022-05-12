package com.mozeshajdu.spotifymigrator.spotify.mapper;

import com.mozeshajdu.spotifymigrator.spotify.entity.SpotifyTrack;
import org.mapstruct.AfterMapping;
import org.mapstruct.Context;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.Named;
import se.michaelthelin.spotify.model_objects.specification.ArtistSimplified;
import se.michaelthelin.spotify.model_objects.specification.ExternalUrl;
import se.michaelthelin.spotify.model_objects.specification.Track;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring")
public interface SpotifyTrackMapper {

    @Mapping(target = "spotifyId", source = "id")
    @Mapping(target = "audioTagId", ignore = true)
    @Mapping(target = "releaseDate", source = "album.releaseDate")
    @Mapping(target = "album", source = "album.name")
    @Mapping(target = "artists", qualifiedByName = "artistName")
    @Mapping(target = "url", source = "externalUrls", qualifiedByName = "externalUrl")
    SpotifyTrack toSpotifyTrack(Track track, @Context Long audioTagId);

    @AfterMapping
    default void addAudioTagId(@MappingTarget SpotifyTrack target, @Context Long audioTagId) {
        target.setAudioTagId(audioTagId);
    }

    @Named("artistName")
    default List<String> of(ArtistSimplified[] artistSimplified) {
        return Arrays.stream(artistSimplified).map(ArtistSimplified::getName).collect(Collectors.toList());
    }

    @Named("externalUrl")
    default String of(ExternalUrl externalUrl) {
        return externalUrl.get("spotify");
    }
}
