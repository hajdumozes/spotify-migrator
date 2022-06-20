package com.mozeshajdu.spotifymigrator.tagging.web;

import com.mozeshajdu.spotifymigrator.spotify.entity.SpotifySearchParameter;
import com.mozeshajdu.spotifymigrator.spotify.entity.ConnectedSpotifyTrack;
import com.mozeshajdu.spotifymigrator.tagging.service.TagService;
import com.mozeshajdu.spotifymigrator.tagging.web.dto.QueryDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Tag(name = "Tag query API")
@RestController
@RequestMapping(value = "query/tags/", produces = MediaType.APPLICATION_JSON_VALUE)
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class TagQueryController {
    TagService tagService;

    @Operation(summary = "Query spotify tracks by tags")
    @PostMapping(value = "/tracks", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<ConnectedSpotifyTrack>> query(@RequestBody QueryDto queryDto) {
        return ResponseEntity.ok(tagService.queryByAudioTags(queryDto.getSpotifySearchParameters(), queryDto.getAudioTagQuery()));
    }

    @Operation(summary = "Query spotify tracks for audio tag")
    @PostMapping(value = "/{audioTagId}/tracks", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<ConnectedSpotifyTrack>> queryByAudioTagId(@PathVariable Long audioTagId, @RequestBody List<SpotifySearchParameter> spotifySearchParameters) {
        List<ConnectedSpotifyTrack> result = tagService.queryByAudioTagId(spotifySearchParameters, audioTagId);
        return ResponseEntity.ok(result);
    }
}
