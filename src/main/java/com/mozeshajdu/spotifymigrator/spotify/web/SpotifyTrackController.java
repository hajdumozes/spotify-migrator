package com.mozeshajdu.spotifymigrator.spotify.web;

import com.mozeshajdu.spotifymigrator.spotify.entity.LikedTrack;
import com.mozeshajdu.spotifymigrator.spotify.service.SpotifyTrackService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(value = "/track")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class SpotifyTrackController {
    SpotifyTrackService spotifyTrackService;

    @GetMapping(value = "liked")
    public ResponseEntity<List<LikedTrack>> getLikedTracks() {
        return ResponseEntity.ok(spotifyTrackService.getLikedTracks());
    }

    @GetMapping(value = "liked/disconnected")
    public ResponseEntity<List<LikedTrack>> getDisconnectedLikesTracks() {
        return ResponseEntity.ok(spotifyTrackService.getDisconnectedLikedTracks());
    }
}
