package com.mozeshajdu.spotifymigrator.spotify.mapper;

import com.mozeshajdu.spotifymigrator.spotify.entity.SpotifyTrack;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import se.michaelthelin.spotify.model_objects.specification.ArtistSimplified;
import se.michaelthelin.spotify.model_objects.specification.Track;

import java.util.Arrays;
import java.util.List;

@Mapper(componentModel = "spring")
public interface SpotifyTrackMapper {

    @Mapping(target = "releaseDate", source = "album.releaseDate")
    @Mapping(target = "album", source = "album.name")
    @Mapping(target = "artists", qualifiedByName = "artistName")
    SpotifyTrack toSpotifyTrack(Track track);

    List<SpotifyTrack> toSpotifyTrackList(List<Track> track);

    @Named("artistName")
    default List<String> of(ArtistSimplified[] artistSimplified) {
        return Arrays.stream(artistSimplified).map(ArtistSimplified::getName).toList();
    }
}
