package com.mozeshajdu.spotifymigrator.tagging.event;

import com.mozeshajdu.spotifymigrator.spotify.entity.event.PlaylistDeletedMessage;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Sinks;

@Component
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class PlaylistDeletedMessageProducer {
    Sinks.Many<PlaylistDeletedMessage> playlistDeletedMessageMany;

    public void produce(PlaylistDeletedMessage playlistDeletedMessage) {
        playlistDeletedMessageMany.tryEmitNext(playlistDeletedMessage);
    }
}
