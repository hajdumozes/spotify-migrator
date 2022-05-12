package com.mozeshajdu.spotifymigrator.tagging.web;

import com.mozeshajdu.spotifymigrator.spotify.entity.SearchParameter;
import com.mozeshajdu.spotifymigrator.spotify.entity.SpotifyTrack;
import com.mozeshajdu.spotifymigrator.tagging.service.TagService;
import com.mozeshajdu.spotifymigrator.tagging.web.dto.SearchTracksForTagDto;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(value = "/tag", produces = MediaType.APPLICATION_JSON_VALUE)
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class TagController {
    TagService tagService;

    @PostMapping(value = "/search-unconnected", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<SpotifyTrack>> searchUnconnected(@RequestBody List<SearchParameter> searchParameters) {
        tagService.searchForUnconnected(searchParameters);
        return ResponseEntity.ok(tagService.searchForUnconnected(searchParameters));
    }

    @PostMapping(value = "/search-tag", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<SpotifyTrack>> searchForTag(@RequestBody SearchTracksForTagDto dto) {
        List<SpotifyTrack> result = tagService.searchTracksForTag(dto.getSearchParameters(), dto.getAudioTagId());
        return ResponseEntity.ok(result);
    }

    @PostMapping(value = "/sync-unconnected", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Void> syncUnconnected(@RequestBody List<SearchParameter> searchParameters) {
        tagService.produceSpotifyTracksForUnconnectedAudioTags(searchParameters);
        return ResponseEntity.ok().build();
    }

    @GetMapping(value = "/sync-track/{spotifyId}/{audioTagId}")
    public ResponseEntity<Void> addToAudioTag(@PathVariable String spotifyId, @PathVariable Long audioTagId) {
        tagService.syncTrackWithTag(spotifyId, audioTagId);
        return ResponseEntity.ok().build();
    }
}
