package com.mozeshajdu.spotifymigrator.spotify.service;

import com.mozeshajdu.spotifymigrator.spotify.entity.SpotifyAction;
import com.mozeshajdu.spotifymigrator.spotify.entity.SpotifyTrack;
import com.mozeshajdu.spotifymigrator.spotify.entity.event.TracksLikedMessage;
import com.mozeshajdu.spotifymigrator.spotify.mapper.SpotifyTrackMapper;
import com.mozeshajdu.spotifymigrator.tagging.client.AudioTagManagerClient;
import com.mozeshajdu.spotifymigrator.tagging.client.AudioTagQuery;
import com.mozeshajdu.spotifymigrator.tagging.entity.AudioTag;
import com.mozeshajdu.spotifymigrator.tagging.entity.AudioTagSpotifyTrack;
import com.mozeshajdu.spotifymigrator.tagging.event.TracksLikedMessageProducer;
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

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import static com.mozeshajdu.spotifymigrator.spotify.util.SpotifyApiUtil.executeRequest;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class SpotifyTrackService {
    SpotifyApi spotifyApi;
    SpotifyTrackMapper spotifyTrackMapper;
    AudioTagManagerClient audioTagManagerClient;
    TracksLikedMessageProducer tracksLikedMessageProducer;

    public List<SpotifyTrack> getLikedTracks() {
        List<SavedTrack> savedTrackList = getAllSavedTrackPages();
        return savedTrackList.stream()
                .map(SavedTrack::getTrack)
                .map(spotifyTrackMapper::toSpotifyTrack)
                .collect(Collectors.toList());
    }

    public List<SpotifyTrack> getLikedTracksToMigrate() {
        List<SpotifyTrack> likedTracksOnSpotify = getLikedTracks();
        List<AudioTag> audioTagsWithSpotifyConnection = audioTagManagerClient.find(AudioTagQuery.builder()
                .spotifyTrackPresence(true)
                .build());
        List<String> migratedSpotifyIds = audioTagsWithSpotifyConnection.stream()
                .map(AudioTag::getSpotifyTrack)
                .filter(Objects::nonNull)
                .map(AudioTagSpotifyTrack::getSpotifyId)
                .collect(Collectors.toList());
        return likedTracksOnSpotify.stream()
                .filter(likedTrack -> !migratedSpotifyIds.contains(likedTrack.getSpotifyId()))
                .collect(Collectors.toList());
    }

    public List<String> getLikedTrackUrisToMigrate() {
        return getLikedTracksToMigrate().stream()
                .map(SpotifyTrack::getUri)
                .collect(Collectors.toList());
    }

    public void likeTracksById(List<String> ids) {
        SaveTracksForUserRequest request = spotifyApi.saveTracksForUser(ids.toArray(String[]::new)).build();
        executeRequest(request);
        TracksLikedMessage message = TracksLikedMessage.builder()
                .spotifyIds(ids)
                .spotifyAction(SpotifyAction.ADDED)
                .build();
        tracksLikedMessageProducer.produce(message);
    }

    public void likeTracksByQuery(AudioTagQuery audioTagQuery) {
        List<String> ids = audioTagManagerClient.find(audioTagQuery)
                .stream()
                .map(AudioTag::getSpotifyTrack)
                .filter(Objects::nonNull)
                .map(AudioTagSpotifyTrack::getSpotifyId)
                .collect(Collectors.toList());
        likeTracksById(ids);
    }

    public void removeLikedTracks(List<String> ids) {
        RemoveUsersSavedTracksRequest request = spotifyApi.removeUsersSavedTracks(ids.toArray(String[]::new)).build();
        executeRequest(request);
        TracksLikedMessage message = TracksLikedMessage.builder()
                .spotifyIds(ids)
                .spotifyAction(SpotifyAction.REMOVED)
                .build();
        tracksLikedMessageProducer.produce(message);
    }

    public void removeLikedTracksByQuery(AudioTagQuery audioTagQuery) {
        List<String> ids = audioTagManagerClient.find(audioTagQuery)
                .stream()
                .map(AudioTag::getSpotifyTrack)
                .filter(Objects::nonNull)
                .map(AudioTagSpotifyTrack::getSpotifyId)
                .collect(Collectors.toList());
        removeLikedTracks(ids);
    }

    private List<SavedTrack> getAllSavedTrackPages() {
        int offset = 0;
        int limit = 20;
        List<SavedTrack> savedTrackList = new ArrayList<>();
        Paging<SavedTrack> savedTrackPaging;
        do {
            savedTrackPaging = getSavedTrackPaging(offset);
            savedTrackList.addAll(List.of(savedTrackPaging.getItems()));
            offset += limit;
        } while (savedTrackPaging.getNext() != null);
        savedTrackPaging = getSavedTrackPaging(offset);
        savedTrackList.addAll(List.of(savedTrackPaging.getItems()));
        return savedTrackList;
    }

    private Paging<SavedTrack> getSavedTrackPaging(int offset) {
        GetUsersSavedTracksRequest request = spotifyApi.getUsersSavedTracks().offset(offset).build();
        return executeRequest(request);
    }
}
