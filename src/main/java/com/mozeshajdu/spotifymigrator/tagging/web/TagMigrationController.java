package com.mozeshajdu.spotifymigrator.tagging.web;

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


@Tag(name = "Tag migration API")
@RestController
@RequestMapping(value = "/migrate/tags", produces = MediaType.APPLICATION_JSON_VALUE)
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class TagMigrationController {
    TagService tagService;

    @Operation(summary = "Find most popular spotify tracks for tags")
    @PostMapping( consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Void> syncDisconnected(@RequestBody QueryDto queryDto) {
        tagService.produceSpotifyTracksForAudioTags(queryDto.getSpotifySearchParameters(), queryDto.getAudioTagQuery());
        return ResponseEntity.ok().build();
    }

    @Operation(summary = "Update all migrated spotify tracks")
    @PostMapping(value = "/tracks")
    public ResponseEntity<Void> updateSpotifyTracks() {
        tagService.updateSpotifyTracks();
        return ResponseEntity.ok().build();
    }

    @Operation(summary = "Attach spotify track to audio tag")
    @PostMapping(value = "/{audioTagId}/tracks/{spotifyId}")
    public ResponseEntity<Void> addToAudioTag(@PathVariable String spotifyId, @PathVariable Long audioTagId) {
        tagService.connect(spotifyId, audioTagId);
        return ResponseEntity.ok().build();
    }
}
