package com.mozeshajdu.spotifymigrator.spotify.mapper;

import com.mozeshajdu.spotifymigrator.spotify.entity.ConnectedSpotifyTrack;
import com.mozeshajdu.spotifymigrator.spotify.entity.PlaylistItem;
import com.mozeshajdu.spotifymigrator.spotify.entity.SpotifyTrack;
import com.mozeshajdu.spotifymigrator.spotify.entity.event.SpotifyTrackCreatedMessage;
import org.mapstruct.AfterMapping;
import org.mapstruct.Context;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import se.michaelthelin.spotify.model_objects.specification.Paging;
import se.michaelthelin.spotify.model_objects.specification.PlaylistTrack;
import se.michaelthelin.spotify.model_objects.specification.Track;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring", uses = {ArtistMapper.class, UrlMapper.class})
public interface SpotifyTrackMapper {

    @Mapping(target = "spotifyId", source = "id")
    @Mapping(target = "audioTagId", ignore = true)
    @Mapping(target = "releaseDate", source = "album.releaseDate")
    @Mapping(target = "album", source = "album.name")
    @Mapping(target = "artists", qualifiedByName = "artistName")
    @Mapping(target = "url", source = "externalUrls", qualifiedByName = "externalUrl")
    ConnectedSpotifyTrack toConnectedSpotifyTrack(Track track, @Context Long audioTagId);

    SpotifyTrackCreatedMessage of(ConnectedSpotifyTrack track);

    @Mapping(target = "spotifyId", source = "id")
    @Mapping(target = "releaseDate", source = "album.releaseDate")
    @Mapping(target = "album", source = "album.name")
    @Mapping(target = "artists", qualifiedByName = "artistName")
    @Mapping(target = "url", source = "externalUrls", qualifiedByName = "externalUrl")
    SpotifyTrack toSpotifyTrack(Track track);

    @Mapping(target = "id", source = "track.id")
    @Mapping(target = "name", source = "track.name")
    @Mapping(target = "uri", source = "track.uri")
    @Mapping(target = "url", source = "track.externalUrls", qualifiedByName = "externalUrl")
    PlaylistItem toSpotifyTrack(PlaylistTrack track);

    default List<PlaylistItem> ofPaging(Paging<PlaylistTrack> tracks) {
        return Arrays.stream(Arrays.stream(tracks.getItems()).toArray(PlaylistTrack[]::new))
                .map(this::toSpotifyTrack)
                .collect(Collectors.toList());
    }

    @AfterMapping
    default void addAudioTagId(@MappingTarget ConnectedSpotifyTrack target, @Context Long audioTagId) {
        target.setAudioTagId(audioTagId);
    }
}
