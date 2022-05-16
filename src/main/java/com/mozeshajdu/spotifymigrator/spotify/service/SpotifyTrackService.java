package com.mozeshajdu.spotifymigrator.spotify.service;

import com.mozeshajdu.spotifymigrator.spotify.entity.SpotifyTrack;
import com.mozeshajdu.spotifymigrator.spotify.exception.SpotifyApiException;
import com.mozeshajdu.spotifymigrator.spotify.mapper.SpotifyTrackMapper;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;
import se.michaelthelin.spotify.SpotifyApi;
import se.michaelthelin.spotify.model_objects.specification.Paging;
import se.michaelthelin.spotify.model_objects.specification.SavedTrack;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class SpotifyTrackService {
    SpotifyApi spotifyApi;
    SpotifyTrackMapper spotifyTrackMapper;

    public List<SpotifyTrack> getLikedTracks() {
        Paging<SavedTrack> savedTracks = getSavedTracks();
        return Arrays.stream(savedTracks.getItems())
                .map(SavedTrack::getTrack)
                .map(track -> spotifyTrackMapper.toSpotifyTrack(track, null))
                .collect(Collectors.toList());
    }

    private Paging<SavedTrack> getSavedTracks() {
        try {
            return spotifyApi.getUsersSavedTracks().build().execute();
        } catch (Exception e) {
            throw new SpotifyApiException(e.getMessage());
        }
    }
}
