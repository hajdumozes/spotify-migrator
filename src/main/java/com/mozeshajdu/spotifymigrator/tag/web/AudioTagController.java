package com.mozeshajdu.spotifymigrator.tag.web;

import com.mozeshajdu.spotifymigrator.tag.entity.AudioTag;
import com.mozeshajdu.spotifymigrator.tag.service.AudioTagService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/audio-tag")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class AudioTagController {
    AudioTagService audioTagService;

    @GetMapping
    public ResponseEntity<List<AudioTag>> get() {
        return ResponseEntity.ok(audioTagService.get());
    }
}
