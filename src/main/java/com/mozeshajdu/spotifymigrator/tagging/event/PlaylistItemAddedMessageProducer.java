package com.mozeshajdu.spotifymigrator.tagging.event;

import com.mozeshajdu.spotifymigrator.spotify.entity.event.PlaylistItemAddedMessage;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Sinks;

@Component
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class PlaylistItemAddedMessageProducer {
    Sinks.Many<PlaylistItemAddedMessage> playlistItemAddedMessageMany;

    public void produce(PlaylistItemAddedMessage playlistItemAddedMessage) {
        playlistItemAddedMessageMany.tryEmitNext(playlistItemAddedMessage);
    }
}
