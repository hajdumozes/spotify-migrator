package com.mozeshajdu.spotifymigrator.tag.client;

import com.mozeshajdu.spotifymigrator.tag.entity.AudioTag;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@FeignClient(value = "audio-tag-manager", url = "${client.audio-tag-manager-url}")
public interface AudioTagManagerClient {

    @GetMapping(value = "/audio-tags", produces = MediaType.APPLICATION_JSON_VALUE)
    List<AudioTag> getAudioTags();
}
