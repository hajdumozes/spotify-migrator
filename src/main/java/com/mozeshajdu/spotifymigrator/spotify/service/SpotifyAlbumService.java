package com.mozeshajdu.spotifymigrator.spotify.service;

import com.mozeshajdu.spotifymigrator.spotify.entity.FollowedAlbum;
import com.mozeshajdu.spotifymigrator.spotify.exception.SpotifyApiException;
import com.mozeshajdu.spotifymigrator.spotify.mapper.AlbumMapper;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;
import se.michaelthelin.spotify.SpotifyApi;
import se.michaelthelin.spotify.model_objects.specification.Paging;
import se.michaelthelin.spotify.model_objects.specification.SavedAlbum;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class SpotifyAlbumService {
    SpotifyApi spotifyApi;
    AlbumMapper albumMapper;

    public List<FollowedAlbum> getFollowedAlbums() {
        Paging<SavedAlbum> savedAlbums = getFollowedAlbumsRequestResult();
        return Arrays.stream(savedAlbums.getItems())
                .map(SavedAlbum::getAlbum)
                .map(albumMapper::of)
                .collect(Collectors.toList());
    }

    private Paging<SavedAlbum> getFollowedAlbumsRequestResult() {
        try {
            return spotifyApi.getCurrentUsersSavedAlbums().build().execute();
        } catch (Exception e) {
            throw new SpotifyApiException(e.getMessage());
        }
    }
}
