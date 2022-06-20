package com.mozeshajdu.spotifymigrator.spotify.mapper;

import com.mozeshajdu.spotifymigrator.spotify.entity.SpotifyPlaylist;
import com.mozeshajdu.spotifymigrator.spotify.entity.SpotifyPlaylistDetail;
import com.mozeshajdu.spotifymigrator.spotify.entity.event.PlaylistCreatedMessage;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import se.michaelthelin.spotify.model_objects.specification.Playlist;
import se.michaelthelin.spotify.model_objects.specification.PlaylistSimplified;

@Mapper(componentModel = "spring", uses = {UrlMapper.class, SpotifyTrackMapper.class})
public interface PlaylistMapper {

    @Mapping(target = "url", source = "externalUrls", qualifiedByName = "externalUrl")
    SpotifyPlaylist of(PlaylistSimplified source);

    @Mapping(target = "url", source = "externalUrls", qualifiedByName = "externalUrl")
    SpotifyPlaylist toSpotifyPlaylist(Playlist source);

    @Mapping(target = "url", source = "externalUrls", qualifiedByName = "externalUrl")
    SpotifyPlaylistDetail toSpotifyPlaylistDetail(Playlist source);

    PlaylistCreatedMessage of(SpotifyPlaylist source);
}
