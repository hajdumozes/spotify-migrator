package com.mozeshajdu.spotifymigrator.spotify.service;

import com.mozeshajdu.spotifymigrator.spotify.entity.PlaylistItem;
import com.mozeshajdu.spotifymigrator.spotify.entity.SpotifyPlaylist;
import com.mozeshajdu.spotifymigrator.spotify.entity.SpotifyPlaylistDetail;
import com.mozeshajdu.spotifymigrator.spotify.entity.event.PlaylistDeletedMessage;
import com.mozeshajdu.spotifymigrator.spotify.entity.event.PlaylistItemAddedMessage;
import com.mozeshajdu.spotifymigrator.spotify.mapper.PlaylistMapper;
import com.mozeshajdu.spotifymigrator.tagging.client.AudioTagManagerClient;
import com.mozeshajdu.spotifymigrator.tagging.client.AudioTagQuery;
import com.mozeshajdu.spotifymigrator.tagging.entity.AudioTag;
import com.mozeshajdu.spotifymigrator.tagging.entity.AudioTagSpotifyTrack;
import com.mozeshajdu.spotifymigrator.tagging.event.PlaylistCreatedMessageProducer;
import com.mozeshajdu.spotifymigrator.tagging.event.PlaylistDeletedMessageProducer;
import com.mozeshajdu.spotifymigrator.tagging.event.PlaylistItemAddedMessageProducer;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import se.michaelthelin.spotify.SpotifyApi;
import se.michaelthelin.spotify.model_objects.specification.Playlist;
import se.michaelthelin.spotify.model_objects.specification.User;
import se.michaelthelin.spotify.requests.data.follow.UnfollowPlaylistRequest;
import se.michaelthelin.spotify.requests.data.playlists.AddItemsToPlaylistRequest;
import se.michaelthelin.spotify.requests.data.playlists.CreatePlaylistRequest;
import se.michaelthelin.spotify.requests.data.playlists.GetListOfUsersPlaylistsRequest;
import se.michaelthelin.spotify.requests.data.playlists.GetPlaylistRequest;
import se.michaelthelin.spotify.requests.data.users_profile.GetCurrentUsersProfileRequest;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static com.mozeshajdu.spotifymigrator.spotify.util.SpotifyApiUtil.executeRequest;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
public class SpotifyPlaylistService {
    SpotifyApi spotifyApi;
    PlaylistMapper playlistMapper;
    AudioTagManagerClient audioTagManagerClient;
    PlaylistCreatedMessageProducer playlistCreatedMessageProducer;
    PlaylistDeletedMessageProducer playlistDeletedMessageProducer;
    PlaylistItemAddedMessageProducer playlistItemAddedMessageProducer;

    public void createPlaylist(String name) {
        User currentUser = getUserProfile();
        CreatePlaylistRequest request = spotifyApi.createPlaylist(currentUser.getId(), name).build();
        SpotifyPlaylist spotifyPlaylistCreated = playlistMapper.toSpotifyPlaylist(executeRequest(request));
        playlistCreatedMessageProducer.produce(playlistMapper.of(spotifyPlaylistCreated));
    }

    public SpotifyPlaylistDetail getPlaylist(String id) {
        GetPlaylistRequest request = spotifyApi.getPlaylist(id).build();
        Playlist playlist = executeRequest(request);
        return playlistMapper.toSpotifyPlaylistDetail(playlist);
    }

    public List<SpotifyPlaylist> getPlaylists() {
        User currentUser = getUserProfile();
        GetListOfUsersPlaylistsRequest request = spotifyApi.getListOfUsersPlaylists(currentUser.getId()).build();
        return Arrays.stream(executeRequest(request).getItems()).map(playlistMapper::of).collect(Collectors.toList());
    }

    public void deletePlaylist(String spotifyId) {
        UnfollowPlaylistRequest request = spotifyApi.unfollowPlaylist(spotifyId).build();
        executeRequest(request);
        playlistDeletedMessageProducer.produce(new PlaylistDeletedMessage(spotifyId));
    }

    public void addToPlaylist(String playlistId, List<String> spotifyUris) {
        AddItemsToPlaylistRequest request = spotifyApi.addItemsToPlaylist(playlistId, spotifyUris.toArray(String[]::new)).build();
        executeRequest(request);
        playlistItemAddedMessageProducer.produce(new PlaylistItemAddedMessage(playlistId, spotifyUris));
    }

    public void addToPlaylist(String playlistId, AudioTagQuery audioTagQuery) {
        SpotifyPlaylistDetail playlist = getPlaylist(playlistId);
        List<String> urisAlreadyInPlaylist = playlist.getTracks().stream()
                .map(PlaylistItem::getUri)
                .collect(Collectors.toList());
        List<String> urisToAdd = audioTagManagerClient.find(audioTagQuery).stream()
                .map(AudioTag::getSpotifyTrack)
                .map(AudioTagSpotifyTrack::getUri)
                .collect(Collectors.toList());
        urisToAdd.removeAll(urisAlreadyInPlaylist);
        if (urisToAdd.size() > 0) {
            addToPlaylist(playlistId, urisToAdd);
        } else {
            log.warn("No tracks to add to playlist {}", playlist.getName());
        }
    }

    private User getUserProfile() {
        GetCurrentUsersProfileRequest request = spotifyApi.getCurrentUsersProfile().build();
        return executeRequest(request);
    }
}
