package com.mozeshajdu.spotifymigrator.spotify.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Named;
import se.michaelthelin.spotify.model_objects.specification.ExternalUrl;

@Mapper(componentModel = "spring")
public interface UrlMapper {

    @Named("externalUrl")
    default String of(ExternalUrl externalUrl) {
        return externalUrl.get("spotify");
    }
}
