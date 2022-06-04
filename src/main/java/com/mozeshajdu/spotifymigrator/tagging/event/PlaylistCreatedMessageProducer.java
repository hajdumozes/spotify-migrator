package com.mozeshajdu.spotifymigrator.tagging.event;

import com.mozeshajdu.spotifymigrator.spotify.entity.event.PlaylistCreatedMessage;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Sinks;

@Component
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class PlaylistCreatedMessageProducer {
    Sinks.Many<PlaylistCreatedMessage> playlistCreatedMessageSink;

    public void produce(PlaylistCreatedMessage playlistCreatedMessage) {
        playlistCreatedMessageSink.tryEmitNext(playlistCreatedMessage);
    }
}
