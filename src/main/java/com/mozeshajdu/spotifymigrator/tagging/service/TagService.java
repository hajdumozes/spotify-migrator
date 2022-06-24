package com.mozeshajdu.spotifymigrator.tagging.service;

import com.mozeshajdu.spotifymigrator.spotify.entity.ConnectedSpotifyTrack;
import com.mozeshajdu.spotifymigrator.spotify.entity.SpotifySearchParameter;
import com.mozeshajdu.spotifymigrator.spotify.mapper.SpotifyTrackMapper;
import com.mozeshajdu.spotifymigrator.spotify.service.SpotifySearcher;
import com.mozeshajdu.spotifymigrator.tagging.client.AudioTagManagerClient;
import com.mozeshajdu.spotifymigrator.tagging.client.AudioTagQuery;
import com.mozeshajdu.spotifymigrator.tagging.entity.AudioTag;
import com.mozeshajdu.spotifymigrator.tagging.event.SpotifyTrackProducer;
import com.mozeshajdu.spotifymigrator.tagging.exception.AudioTagNotFoundException;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Comparator;
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
    SpotifyTrackMapper spotifyTrackMapper;

    public void produceSpotifyTracksForAudioTags(List<SpotifySearchParameter> spotifySearchParameters, AudioTagQuery audioTagQuery) {
        List<AudioTag> audioTags = audioTagManagerClient.find(audioTagQuery);
        audioTags.stream()
                .map(audioTag -> spotifySearcher.getMostPopularForTag(audioTag, spotifySearchParameters))
                .flatMap(Optional::stream)
                .map(spotifyTrackMapper::of)
                .forEach(spotifyTrackProducer::produce);
    }

    public List<ConnectedSpotifyTrack> queryByAudioTags(List<SpotifySearchParameter> spotifySearchParameters, AudioTagQuery audioTagQuery) {
        List<AudioTag> audioTags = audioTagManagerClient.find(audioTagQuery);
        return audioTags.stream()
                .map(audioTag -> spotifySearcher.search(audioTag, spotifySearchParameters))
                .flatMap(Collection::stream)
                .collect(Collectors.toList());
    }

    public List<ConnectedSpotifyTrack> queryByAudioTagId(List<SpotifySearchParameter> spotifySearchParameters, Long audioTagId) {
        AudioTag audioTag = audioTagManagerClient.getAudioTagById(audioTagId)
                .orElseThrow(() -> new AudioTagNotFoundException(Long.toString(audioTagId)));
        return spotifySearcher.search(audioTag, spotifySearchParameters)
                .stream()
                .sorted(Comparator.comparing(ConnectedSpotifyTrack::getPopularity, Comparator.reverseOrder()))
                .collect(Collectors.toList());
    }

    public void updateSpotifyTracks() {
        audioTagManagerClient.getSpotifyTracks()
                .forEach(spotifyTrack -> connect(spotifyTrack.getSpotifyId(), spotifyTrack.getAudioTagId()));
    }

    public void connect(String spotifyId, Long audioTagId) {
        ConnectedSpotifyTrack track = spotifySearcher.getById(spotifyId, audioTagId);
        spotifyTrackProducer.produce(spotifyTrackMapper.of(track));
    }
}
