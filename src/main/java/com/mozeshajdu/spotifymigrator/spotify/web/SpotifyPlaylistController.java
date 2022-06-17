package com.mozeshajdu.spotifymigrator.spotify.web;

import com.mozeshajdu.spotifymigrator.spotify.entity.SpotifyPlaylist;
import com.mozeshajdu.spotifymigrator.spotify.service.SpotifyPlaylistService;
import com.mozeshajdu.spotifymigrator.tagging.client.AudioTagQuery;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Tag(name = "Spotify playlist API")
@RestController
@RequestMapping(value = "/spotify/playlists")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class SpotifyPlaylistController {
    SpotifyPlaylistService spotifyPlaylistService;

    @Operation(summary = "Get followed playlists")
    @GetMapping(value = "/me")
    public ResponseEntity<List<SpotifyPlaylist>> getPlaylists() {
        return ResponseEntity.ok(spotifyPlaylistService.getPlaylists());
    }

    @Operation(summary = "Create playlist")
    @PostMapping(value = "/me", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Void> createPlaylist(@RequestBody String name) {
        spotifyPlaylistService.createPlaylist(name);
        return ResponseEntity.ok().build();
    }

    @Operation(summary = "Unfollow playlist")
    @DeleteMapping("/{id}/me")
    public ResponseEntity<Void> deletePlaylist(@PathVariable String id) {
        spotifyPlaylistService.deletePlaylist(id);
        return ResponseEntity.ok().build();
    }

    @Operation(summary = "Add tracks to playlist")
    @PostMapping(value = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Void> addToPlaylist(@PathVariable String id, @RequestBody List<String> spotifyUris) {
        spotifyPlaylistService.addToPlaylist(id, spotifyUris);
        return ResponseEntity.ok().build();
    }

    @Operation(summary = "Add tracks by audio tags to playlist")
    @PostMapping(value = "/{id}/tags", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Void> addToPlaylist(@PathVariable String id, @RequestBody AudioTagQuery audioTagQuery) {
        spotifyPlaylistService.addToPlaylist(id, audioTagQuery);
        return ResponseEntity.ok().build();
    }
}
