package com.mozeshajdu.spotifymigrator.tagging.service;

import com.mozeshajdu.spotifymigrator.spotify.entity.SearchParameter;
import com.mozeshajdu.spotifymigrator.spotify.service.SpotifySearcher;
import com.mozeshajdu.spotifymigrator.tagging.client.AudioTagManagerClient;
import com.mozeshajdu.spotifymigrator.tagging.entity.AudioTag;
import com.mozeshajdu.spotifymigrator.tagging.event.SpotifyTrackProducer;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class TagService {
    AudioTagManagerClient audioTagManagerClient;
    SpotifySearcher spotifySearcher;
    SpotifyTrackProducer spotifyTrackProducer;

    public void sendSpotifyTracksForUnconnectedAudioTags(List<SearchParameter> searchParameters) {
        List<AudioTag> audioTags = audioTagManagerClient.getUnconnectedAudioTags();
        audioTags.stream()
                .map(audioTag -> spotifySearcher.getFromSpotify(audioTag, searchParameters))
                .flatMap(Optional::stream)
                .forEach(spotifyTrackProducer::produce);
    }
}
