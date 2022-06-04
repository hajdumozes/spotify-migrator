package com.mozeshajdu.spotifymigrator.spotify.web;

import com.mozeshajdu.spotifymigrator.spotify.entity.LikedTrack;
import com.mozeshajdu.spotifymigrator.spotify.service.SpotifyTrackService;
import com.mozeshajdu.spotifymigrator.tagging.client.AudioTagQuery;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
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

    @PostMapping(value = "liked/list", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<LikedTrack>> likeTracks(@RequestBody List<String> ids) {
        spotifyTrackService.likeTracksById(ids);
        return ResponseEntity.ok().build();
    }

    @PostMapping(value = "liked/query", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<LikedTrack>> likeTracks(@RequestBody AudioTagQuery audioTagQuery) {
        spotifyTrackService.likeTracksByQuery(audioTagQuery);
        return ResponseEntity.ok().build();
    }

    @PostMapping(value = "liked/remove", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<LikedTrack>> removeLikedTracks(@RequestBody List<String> ids) {
        spotifyTrackService.removeLikedTracks(ids);
        return ResponseEntity.ok().build();
    }
}
