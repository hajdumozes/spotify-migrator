package com.mozeshajdu.spotifymigrator.tagging.client;

import com.mozeshajdu.spotifymigrator.tagging.entity.AudioTag;
import com.mozeshajdu.spotifymigrator.tagging.entity.AudioTagSpotifyTrack;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;
import java.util.Optional;

@FeignClient(value = "audio-tag-manager", url = "${client.audio-tag-manager-url}")
public interface AudioTagManagerClient {

    @GetMapping(value = "/audio-tags", produces = MediaType.APPLICATION_JSON_VALUE)
    List<AudioTag> getAudioTags();

    @GetMapping(value = "/audio-tags/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    Optional<AudioTag> getAudioTagById(@PathVariable Long id);

    @PostMapping(value = "/audio-tags/query", consumes = MediaType.APPLICATION_JSON_VALUE)
    List<AudioTag> find(@RequestBody AudioTagQuery audioTagQuery);

    @GetMapping(value = "/spotify-tracks", produces = MediaType.APPLICATION_JSON_VALUE)
    List<AudioTagSpotifyTrack> getSpotifyTracks();
}
