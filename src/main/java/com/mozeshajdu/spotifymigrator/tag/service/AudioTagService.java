package com.mozeshajdu.spotifymigrator.tag.service;

import com.mozeshajdu.spotifymigrator.spotify.service.SpotifySearcher;
import com.mozeshajdu.spotifymigrator.tag.client.AudioTagManagerClient;
import com.mozeshajdu.spotifymigrator.tag.entity.AudioTag;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;
import se.michaelthelin.spotify.model_objects.specification.Track;

import java.util.Collection;
import java.util.List;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class AudioTagService {
    AudioTagManagerClient audioTagManagerClient;
    SpotifySearcher spotifySearcher;

    public List<Track> get() {
        List<AudioTag> audioTags = audioTagManagerClient.getAudioTags();
        return audioTags.stream().map(spotifySearcher::getFromSpotify)
                .flatMap(Collection::stream)
                .toList();
    }
}
