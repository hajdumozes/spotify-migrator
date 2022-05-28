package com.mozeshajdu.spotifymigrator.spotify.service;

import com.mozeshajdu.spotifymigrator.spotify.entity.LikedTrack;
import com.mozeshajdu.spotifymigrator.spotify.mapper.SpotifyTrackMapper;
import com.mozeshajdu.spotifymigrator.tagging.client.AudioTagManagerClient;
import com.mozeshajdu.spotifymigrator.tagging.entity.AudioTag;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;
import se.michaelthelin.spotify.SpotifyApi;
import se.michaelthelin.spotify.model_objects.specification.Paging;
import se.michaelthelin.spotify.model_objects.specification.SavedTrack;
import se.michaelthelin.spotify.requests.data.library.GetUsersSavedTracksRequest;
import se.michaelthelin.spotify.requests.data.library.RemoveUsersSavedTracksRequest;
import se.michaelthelin.spotify.requests.data.library.SaveTracksForUserRequest;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static com.mozeshajdu.spotifymigrator.spotify.util.SpotifyApiUtil.executeRequest;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class SpotifyTrackService {
    SpotifyApi spotifyApi;
    SpotifyTrackMapper spotifyTrackMapper;
    AudioTagManagerClient audioTagManagerClient;

    public List<LikedTrack> getLikedTracks() {
        GetUsersSavedTracksRequest request = spotifyApi.getUsersSavedTracks().build();
        Paging<SavedTrack> savedTracks = executeRequest(request);
        return Arrays.stream(savedTracks.getItems())
                .map(SavedTrack::getTrack)
                .map(spotifyTrackMapper::toLikedTrack)
                .collect(Collectors.toList());
    }

    public List<LikedTrack> getDisconnectedLikedTracks() {
        List<LikedTrack> likedTracks = getLikedTracks();
        List<String> spotifyTracksInCollection = audioTagManagerClient.getConnectedAudioTags().stream()
                .map(AudioTag::getSpotifyTrackId)
                .collect(Collectors.toList());
        return likedTracks.stream()
                .filter(likedTrack -> !spotifyTracksInCollection.contains(likedTrack.getSpotifyId()))
                .collect(Collectors.toList());
    }

    public void likeTracks(List<String> ids) {
        SaveTracksForUserRequest request = spotifyApi.saveTracksForUser(ids.toArray(String[]::new)).build();
        executeRequest(request);
    }

    public void removeLikedTracks(List<String> ids) {
        RemoveUsersSavedTracksRequest request = spotifyApi.removeUsersSavedTracks(ids.toArray(String[]::new)).build();
        executeRequest(request);
    }
}
