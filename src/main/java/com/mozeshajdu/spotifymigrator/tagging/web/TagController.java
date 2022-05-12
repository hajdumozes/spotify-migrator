package com.mozeshajdu.spotifymigrator.tagging.web;

import com.mozeshajdu.spotifymigrator.tagging.service.TagService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/tag", produces = MediaType.APPLICATION_JSON_VALUE)
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class TagController {
    TagService tagService;

    @GetMapping(value = "/sync-unconnected")
    public ResponseEntity<Void> getAll() {
        tagService.sendSpotifyTracksForUnconnectedAudioTags();
        return ResponseEntity.ok().build();
    }
}
