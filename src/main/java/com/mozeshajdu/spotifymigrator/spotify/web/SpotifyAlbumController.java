package com.mozeshajdu.spotifymigrator.spotify.web;

import com.mozeshajdu.spotifymigrator.spotify.entity.FollowedAlbum;
import com.mozeshajdu.spotifymigrator.spotify.service.SpotifyAlbumService;
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

@Tag(name = "Spotify album API")
@RestController
@RequestMapping(value = "/spotify/albums")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class SpotifyAlbumController {
    SpotifyAlbumService spotifyAlbumService;

    @Operation(summary = "Get followed albums")
    @GetMapping(value = "/me")
    public ResponseEntity<List<FollowedAlbum>> getFollowedAlbums() {
        return ResponseEntity.ok(spotifyAlbumService.getFollowedAlbums());
    }

    @Operation(summary = "Remove followed albums by id")
    @PostMapping(value = "/me/remove", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> getFollowedAlbums(@RequestBody List<String> ids) {
        return ResponseEntity.ok(spotifyAlbumService.unfollowAlbums(ids));
    }
}
