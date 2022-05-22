package com.mozeshajdu.spotifymigrator.spotify.service;

import com.mozeshajdu.spotifymigrator.spotify.entity.FollowedAlbum;
import com.mozeshajdu.spotifymigrator.spotify.mapper.AlbumMapper;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;
import se.michaelthelin.spotify.SpotifyApi;
import se.michaelthelin.spotify.model_objects.specification.Paging;
import se.michaelthelin.spotify.model_objects.specification.SavedAlbum;
import se.michaelthelin.spotify.requests.data.library.GetCurrentUsersSavedAlbumsRequest;
import se.michaelthelin.spotify.requests.data.library.RemoveAlbumsForCurrentUserRequest;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static com.mozeshajdu.spotifymigrator.spotify.util.SpotifyApiUtil.executeRequest;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class SpotifyAlbumService {
    SpotifyApi spotifyApi;
    AlbumMapper albumMapper;

    public List<FollowedAlbum> getFollowedAlbums() {
        GetCurrentUsersSavedAlbumsRequest request = spotifyApi.getCurrentUsersSavedAlbums().build();
        Paging<SavedAlbum> savedAlbums = executeRequest(request);
        return Arrays.stream(savedAlbums.getItems())
                .map(SavedAlbum::getAlbum)
                .map(albumMapper::of)
                .collect(Collectors.toList());
    }

    public String unfollowAlbums(List<String> ids) {
        RemoveAlbumsForCurrentUserRequest request =
                spotifyApi.removeAlbumsForCurrentUser(ids.toArray(String[]::new)).build();
        return executeRequest(request);
    }
}
