package com.mozeshajdu.spotifymigrator.spotify.web;

import com.mozeshajdu.spotifymigrator.spotify.entity.LikedTrack;
import com.mozeshajdu.spotifymigrator.spotify.service.SpotifyTrackService;
import com.mozeshajdu.spotifymigrator.tagging.client.AudioTagQuery;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
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

@Tag(name = "Spotify track API")
@RestController
@RequestMapping(value = "spotify/tracks")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class SpotifyTrackController {
    SpotifyTrackService spotifyTrackService;

    @Operation(summary = "Get liked tracks")
    @GetMapping(value = "/me")
    public ResponseEntity<List<LikedTrack>> getLikedTracks() {
        return ResponseEntity.ok(spotifyTrackService.getLikedTracks());
    }

    @GetMapping(value = "/me/disconnected")
    public ResponseEntity<List<LikedTrack>> getDisconnectedLikesTracks() {
        return ResponseEntity.ok(spotifyTrackService.getDisconnectedLikedTracks());
    }

    @Operation(summary = "Like tracks by id")
    @PostMapping(value = "/me/add", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<LikedTrack>> likeTracks(@RequestBody List<String> ids) {
        spotifyTrackService.likeTracksById(ids);
        return ResponseEntity.ok().build();
    }

    @Operation(summary = "Like tracks by tags")
    @PostMapping(value = "/me/add/tags", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<LikedTrack>> likeTracks(@RequestBody AudioTagQuery audioTagQuery) {
        spotifyTrackService.likeTracksByQuery(audioTagQuery);
        return ResponseEntity.ok().build();
    }

    @Operation(summary = "Remove liked tracks by id")
    @PostMapping(value = "/me/remove", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<LikedTrack>> removeLikedTracks(@RequestBody List<String> ids) {
        spotifyTrackService.removeLikedTracks(ids);
        return ResponseEntity.ok().build();
    }

    @Operation(summary = "Remove liked tracks by tags")
    @PostMapping(value = "/me/remove/tags", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<LikedTrack>> removeLikedTracks(@RequestBody AudioTagQuery audioTagQuery) {
        spotifyTrackService.removeLikedTracksByQuery(audioTagQuery);
        return ResponseEntity.ok().build();
    }
}
