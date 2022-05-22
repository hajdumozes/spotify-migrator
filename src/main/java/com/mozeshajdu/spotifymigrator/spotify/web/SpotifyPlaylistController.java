package com.mozeshajdu.spotifymigrator.spotify.web;

import com.mozeshajdu.spotifymigrator.spotify.service.SpotifyPlaylistService;
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
import se.michaelthelin.spotify.model_objects.specification.PlaylistSimplified;

import java.util.List;

@RestController
@RequestMapping(value = "/playlist")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class SpotifyPlaylistController {
    SpotifyPlaylistService spotifyPlaylistService;

    @GetMapping()
    public ResponseEntity<List<PlaylistSimplified>> getPlaylists() {
        return ResponseEntity.ok(spotifyPlaylistService.getPlaylists());
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Void> getPlaylists(@RequestBody String name) {
        spotifyPlaylistService.createPlaylist(name);
        return ResponseEntity.ok().build();
    }
}
