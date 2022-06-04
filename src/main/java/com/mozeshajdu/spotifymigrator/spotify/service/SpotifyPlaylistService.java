package com.mozeshajdu.spotifymigrator.spotify.service;

import com.mozeshajdu.spotifymigrator.spotify.entity.Playlist;
import com.mozeshajdu.spotifymigrator.spotify.mapper.PlaylistMapper;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;
import se.michaelthelin.spotify.SpotifyApi;
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
    PlaylistMapper playlistMapper;

    public void createPlaylist(String name) {
        User currentUser = getUserProfile();
        CreatePlaylistRequest request = spotifyApi.createPlaylist(currentUser.getId(), name).build();
        executeRequest(request);
    }

    public List<Playlist> getPlaylists() {
        User currentUser = getUserProfile();
        GetListOfUsersPlaylistsRequest request = spotifyApi.getListOfUsersPlaylists(currentUser.getId()).build();
        return Arrays.stream(executeRequest(request).getItems()).map(playlistMapper::of).collect(Collectors.toList());
    }

    private User getUserProfile() {
        GetCurrentUsersProfileRequest request = spotifyApi.getCurrentUsersProfile().build();
        return executeRequest(request);
    }
}
