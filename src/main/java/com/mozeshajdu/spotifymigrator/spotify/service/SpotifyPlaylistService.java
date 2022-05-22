package com.mozeshajdu.spotifymigrator.spotify.service;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;
import se.michaelthelin.spotify.SpotifyApi;
import se.michaelthelin.spotify.model_objects.specification.PlaylistSimplified;
import se.michaelthelin.spotify.model_objects.specification.User;
import se.michaelthelin.spotify.requests.data.playlists.CreatePlaylistRequest;
import se.michaelthelin.spotify.requests.data.playlists.GetListOfUsersPlaylistsRequest;
import se.michaelthelin.spotify.requests.data.users_profile.GetCurrentUsersProfileRequest;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static com.mozeshajdu.spotifymigrator.spotify.util.SpotifyApiUtil.executeRequest;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class SpotifyPlaylistService {
    SpotifyApi spotifyApi;

    public void createPlaylist(String name) {
        User currentUser = getUserProfile();
        CreatePlaylistRequest request = spotifyApi.createPlaylist(currentUser.getId(), name).build();
        executeRequest(request);
    }

    public List<PlaylistSimplified> getPlaylists() {
        User currentUser = getUserProfile();
        GetListOfUsersPlaylistsRequest request = spotifyApi.getListOfUsersPlaylists(currentUser.getId()).build();
        return Arrays.stream(executeRequest(request).getItems()).collect(Collectors.toList());
    }

    private User getUserProfile() {
        GetCurrentUsersProfileRequest request = spotifyApi.getCurrentUsersProfile().build();
        return executeRequest(request);
    }
}
