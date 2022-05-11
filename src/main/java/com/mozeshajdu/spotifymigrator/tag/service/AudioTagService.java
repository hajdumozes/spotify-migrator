package com.mozeshajdu.spotifymigrator.tag.service;

import com.mozeshajdu.spotifymigrator.tag.client.AudioTagManagerClient;
import com.mozeshajdu.spotifymigrator.tag.entity.AudioTag;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class AudioTagService {
    AudioTagManagerClient audioTagManagerClient;

    public List<AudioTag> get() {
        return audioTagManagerClient.getAudioTags();
    }
}
