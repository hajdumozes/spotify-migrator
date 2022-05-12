package com.mozeshajdu.spotifymigrator.tagging.web;

import com.mozeshajdu.spotifymigrator.spotify.entity.SearchParameter;
import com.mozeshajdu.spotifymigrator.tagging.service.TagService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
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

    @PostMapping(value = "/sync-unconnected", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Void> getAll(@RequestBody List<SearchParameter> searchParameters) {
        tagService.sendSpotifyTracksForUnconnectedAudioTags(searchParameters);
        return ResponseEntity.ok().build();
    }
}
