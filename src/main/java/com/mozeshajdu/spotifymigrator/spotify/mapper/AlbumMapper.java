package com.mozeshajdu.spotifymigrator.spotify.mapper;

import com.mozeshajdu.spotifymigrator.spotify.entity.FollowedAlbum;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import se.michaelthelin.spotify.model_objects.specification.Album;

@Mapper(componentModel = "spring", uses = {ArtistMapper.class, UrlMapper.class})
public interface AlbumMapper {

    @Mapping(target = "artists", qualifiedByName = "artistName")
    @Mapping(target = "url", source = "externalUrls", qualifiedByName = "externalUrl")
    FollowedAlbum of(Album source);
}
