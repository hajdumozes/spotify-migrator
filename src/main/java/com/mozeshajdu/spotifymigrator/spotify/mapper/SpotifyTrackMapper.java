package com.mozeshajdu.spotifymigrator.spotify.mapper;

import com.mozeshajdu.spotifymigrator.spotify.entity.SpotifyTrack;
import org.apache.logging.log4j.util.Strings;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import se.michaelthelin.spotify.model_objects.specification.ArtistSimplified;
import se.michaelthelin.spotify.model_objects.specification.ExternalUrl;
import se.michaelthelin.spotify.model_objects.specification.Track;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

@Mapper(componentModel = "spring")
public interface SpotifyTrackMapper {

    @Mapping(target = "releaseDate", source = "album.releaseDate")
    @Mapping(target = "album", source = "album.name")
    @Mapping(target = "artists", qualifiedByName = "artistName")
    @Mapping(target = "url", source = "externalUrls", qualifiedByName = "externalUrl")
    SpotifyTrack toSpotifyTrack(Track track);

    List<SpotifyTrack> toSpotifyTrackList(List<Track> track);

    @Named("artistName")
    default List<String> of(ArtistSimplified[] artistSimplified) {
        return Optional.ofNullable(artistSimplified)
                .map(Arrays::stream)
                .map(stream -> stream.map(ArtistSimplified::getName))
                .map(Stream::toList)
                .orElse(List.of());
    }

    @Named("externalUrl")
    default String of(ExternalUrl externalUrl) {
        return Optional.ofNullable(externalUrl)
                .map(url -> url.get("spotify"))
                .orElse(Strings.EMPTY);
    }
}
