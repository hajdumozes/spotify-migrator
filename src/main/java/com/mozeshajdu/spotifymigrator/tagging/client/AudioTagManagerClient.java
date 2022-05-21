package com.mozeshajdu.spotifymigrator.tagging.client;

import com.mozeshajdu.spotifymigrator.tagging.entity.AudioTag;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;
import java.util.Optional;

@FeignClient(value = "audio-tag-manager", url = "${client.audio-tag-manager-url}")
public interface AudioTagManagerClient {

    @GetMapping(value = "/audio-tags", produces = MediaType.APPLICATION_JSON_VALUE)
    List<AudioTag> getAudioTags();

    @GetMapping(value = "/audio-tag/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    Optional<AudioTag> getAudioTagById(@PathVariable Long id);

    @GetMapping(value = "/audio-tag/disconnected", produces = MediaType.APPLICATION_JSON_VALUE)
    List<AudioTag> getDisconnectedAudioTags();

    @GetMapping(value = "/audio-tag/connected", produces = MediaType.APPLICATION_JSON_VALUE)
    List<AudioTag> getConnectedAudioTags();
}
