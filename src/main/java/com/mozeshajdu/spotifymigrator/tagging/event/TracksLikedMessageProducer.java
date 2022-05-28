package com.mozeshajdu.spotifymigrator.tagging.event;

import com.mozeshajdu.spotifymigrator.spotify.entity.TracksLikedMessage;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Sinks;

@Component
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class TracksLikedMessageProducer {
    Sinks.Many<TracksLikedMessage> spotifyTrackSink;

    public void produce(TracksLikedMessage tracksLikedMessage) {
        spotifyTrackSink.tryEmitNext(tracksLikedMessage);
    }
}
