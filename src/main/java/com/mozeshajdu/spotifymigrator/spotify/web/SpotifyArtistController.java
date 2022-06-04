package com.mozeshajdu.spotifymigrator.spotify.web;

import com.mozeshajdu.spotifymigrator.spotify.entity.FollowedArtist;
import com.mozeshajdu.spotifymigrator.spotify.service.SpotifyArtistService;
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
@RequestMapping(value = "/artist")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class SpotifyArtistController {
    SpotifyArtistService spotifyArtistService;

    @GetMapping(value = "followed")
    public ResponseEntity<List<FollowedArtist>> getFollowedArtists() {
        return ResponseEntity.ok(spotifyArtistService.getFollowedArtists());
    }

    @PostMapping(value = "followed/remove", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> getFollowedAlbums(@RequestBody List<String> ids) {
        return ResponseEntity.ok(spotifyArtistService.unfollowArtists(ids));
    }
}
