package com.mozeshajdu.spotifymigrator.tagging.event;

import com.mozeshajdu.spotifymigrator.spotify.entity.SpotifyTrack;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Sinks;

@Component
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class SpotifyTrackProducer {
    Sinks.Many<SpotifyTrack> spotifyTrackSink;

    public void produce(SpotifyTrack spotifyTrack) {
        spotifyTrackSink.tryEmitNext(spotifyTrack);
    }
}
