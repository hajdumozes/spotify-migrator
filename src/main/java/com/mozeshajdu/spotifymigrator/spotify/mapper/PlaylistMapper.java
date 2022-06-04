package com.mozeshajdu.spotifymigrator.spotify.mapper;

import com.mozeshajdu.spotifymigrator.spotify.entity.Playlist;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import se.michaelthelin.spotify.model_objects.specification.PlaylistSimplified;

@Mapper(componentModel = "spring", uses = {UrlMapper.class})
public interface PlaylistMapper {

    @Mapping(target = "url", source = "externalUrls", qualifiedByName = "externalUrl")
    Playlist of(PlaylistSimplified source);
}
