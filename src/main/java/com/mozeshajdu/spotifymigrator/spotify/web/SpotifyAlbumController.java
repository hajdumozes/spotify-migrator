package com.mozeshajdu.spotifymigrator.spotify.web;

import com.mozeshajdu.spotifymigrator.spotify.entity.FollowedAlbum;
import com.mozeshajdu.spotifymigrator.spotify.service.SpotifyAlbumService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(value = "/album")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class SpotifyAlbumController {
    SpotifyAlbumService spotifyAlbumService;

    @GetMapping(value = "followed")
    public ResponseEntity<List<FollowedAlbum>> getFollowedAlbums() {
        return ResponseEntity.ok(spotifyAlbumService.getFollowedAlbums());
    }
}
