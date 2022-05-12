package com.mozeshajdu.spotifymigrator.tagging.service;

import com.mozeshajdu.spotifymigrator.spotify.entity.SearchParameter;
import com.mozeshajdu.spotifymigrator.spotify.entity.SpotifyTrack;
import com.mozeshajdu.spotifymigrator.spotify.service.SpotifySearcher;
import com.mozeshajdu.spotifymigrator.tagging.client.AudioTagManagerClient;
import com.mozeshajdu.spotifymigrator.tagging.entity.AudioTag;
import com.mozeshajdu.spotifymigrator.tagging.event.SpotifyTrackProducer;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class TagService {
    AudioTagManagerClient audioTagManagerClient;
    SpotifySearcher spotifySearcher;
    SpotifyTrackProducer spotifyTrackProducer;

    public void produceSpotifyTracksForUnconnectedAudioTags(List<SearchParameter> searchParameters) {
        List<AudioTag> audioTags = audioTagManagerClient.getUnconnectedAudioTags();
        audioTags.stream()
                .map(audioTag -> spotifySearcher.getMostPopularForTag(audioTag, searchParameters))
                .flatMap(Optional::stream)
                .forEach(spotifyTrackProducer::produce);
    }

    public List<SpotifyTrack> searchForUnconnected(List<SearchParameter> searchParameters) {
        List<AudioTag> audioTags = audioTagManagerClient.getUnconnectedAudioTags();
        return audioTags.stream()
                .map(audioTag -> spotifySearcher.search(audioTag, searchParameters))
                .flatMap(Collection::stream)
                .collect(Collectors.toList());
    }

    public List<SpotifyTrack> searchTracksForTag(List<SearchParameter> searchParameters, Long audioTagId) {
        AudioTag audioTag = audioTagManagerClient.getAudioTagById(audioTagId);
        return spotifySearcher.search(audioTag, searchParameters);
    }
}
