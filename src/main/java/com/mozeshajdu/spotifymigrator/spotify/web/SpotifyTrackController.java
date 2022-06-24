package com.mozeshajdu.spotifymigrator.spotify.web;

import com.mozeshajdu.spotifymigrator.spotify.SpotifyTrackQuery;
import com.mozeshajdu.spotifymigrator.spotify.entity.SpotifyTrack;
import com.mozeshajdu.spotifymigrator.spotify.service.SpotifySearcher;
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
    SpotifySearcher spotifySearcher;

    @Operation(summary = "Get liked tracks")
    @GetMapping(value = "/me")
    public ResponseEntity<List<SpotifyTrack>> getLikedTracks() {
        return ResponseEntity.ok(spotifyTrackService.getLikedTracks());
    }

    @Operation(summary = "Get liked tracks to migrate")
    @GetMapping(value = "/me/migrate")
    public ResponseEntity<List<SpotifyTrack>> getLikedTracksToMigrate() {
        return ResponseEntity.ok(spotifyTrackService.getLikedTracksToMigrate());
    }

    @Operation(summary = "Like tracks by id")
    @PostMapping(value = "/me/add", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<SpotifyTrack>> likeTracks(@RequestBody List<String> ids) {
        spotifyTrackService.likeTracksById(ids);
        return ResponseEntity.ok().build();
    }

    @Operation(summary = "Like tracks by tags")
    @PostMapping(value = "/me/add/tags", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<SpotifyTrack>> likeTracks(@RequestBody AudioTagQuery audioTagQuery) {
        spotifyTrackService.likeTracksByQuery(audioTagQuery);
        return ResponseEntity.ok().build();
    }

    @Operation(summary = "Remove liked tracks by id")
    @PostMapping(value = "/me/remove", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<SpotifyTrack>> removeLikedTracks(@RequestBody List<String> ids) {
        spotifyTrackService.removeLikedTracks(ids);
        return ResponseEntity.ok().build();
    }

    @Operation(summary = "Remove liked tracks by tags")
    @PostMapping(value = "/me/remove/tags", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<SpotifyTrack>> removeLikedTracks(@RequestBody AudioTagQuery audioTagQuery) {
        spotifyTrackService.removeLikedTracksByQuery(audioTagQuery);
        return ResponseEntity.ok().build();
    }

    @Operation(summary = "Query spotify tracks")
    @PostMapping(value = "/query", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<SpotifyTrack>> querySpotifyTracks(@RequestBody SpotifyTrackQuery spotifyTrackQuery) {
        return ResponseEntity.ok(spotifySearcher.query(spotifyTrackQuery));
    }
}
