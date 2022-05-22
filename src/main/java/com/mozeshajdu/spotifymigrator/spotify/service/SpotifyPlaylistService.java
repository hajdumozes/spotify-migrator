package com.mozeshajdu.spotifymigrator.spotify.service;

import com.mozeshajdu.spotifymigrator.spotify.exception.SpotifyApiException;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;
import se.michaelthelin.spotify.SpotifyApi;
import se.michaelthelin.spotify.model_objects.specification.PlaylistSimplified;
import se.michaelthelin.spotify.model_objects.specification.User;
import se.michaelthelin.spotify.requests.data.AbstractDataRequest;
import se.michaelthelin.spotify.requests.data.playlists.CreatePlaylistRequest;
import se.michaelthelin.spotify.requests.data.playlists.GetListOfUsersPlaylistsRequest;
import se.michaelthelin.spotify.requests.data.users_profile.GetCurrentUsersProfileRequest;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

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

    private <T> T executeRequest(AbstractDataRequest<T> request) {
        try {
            return request.execute();
        } catch (Exception e) {
            throw new SpotifyApiException(e.getMessage());
        }
    }
}
