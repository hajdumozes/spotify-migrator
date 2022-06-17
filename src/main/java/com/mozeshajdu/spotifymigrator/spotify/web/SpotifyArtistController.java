package com.mozeshajdu.spotifymigrator.spotify.web;

import com.mozeshajdu.spotifymigrator.spotify.entity.FollowedArtist;
import com.mozeshajdu.spotifymigrator.spotify.service.SpotifyArtistService;
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

@Tag(name = "Spotify artist API")
@RestController
@RequestMapping(value = "/spotify/artists")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class SpotifyArtistController {
    SpotifyArtistService spotifyArtistService;

    @Operation(summary = "Get followed artists")
    @GetMapping(value = "/me")
    public ResponseEntity<List<FollowedArtist>> getFollowedArtists() {
        return ResponseEntity.ok(spotifyArtistService.getFollowedArtists());
    }

    @Operation(summary = "Remove followed artists by id")
    @PostMapping(value = "/me/remove", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> getFollowedAlbums(@RequestBody List<String> ids) {
        return ResponseEntity.ok(spotifyArtistService.unfollowArtists(ids));
    }
}
