package com.mozeshajdu.spotifymigrator.config.event;

import com.mozeshajdu.spotifymigrator.spotify.entity.SpotifyTrack;
import com.mozeshajdu.spotifymigrator.spotify.entity.event.PlaylistCreatedMessage;
import com.mozeshajdu.spotifymigrator.spotify.entity.event.PlaylistDeletedMessage;
import com.mozeshajdu.spotifymigrator.spotify.entity.event.TracksLikedMessage;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Sinks;

import java.util.function.Supplier;

@Configuration
public class EventProducerConfig {

    @Bean
    public Sinks.Many<SpotifyTrack> spotifyTrackSink() {
        return Sinks.many().unicast().onBackpressureBuffer();
    }

    @Bean
    Supplier<Flux<SpotifyTrack>> produceSpotifyTracks(Sinks.Many<SpotifyTrack> spotifyTrackMany) {
        return spotifyTrackMany::asFlux;
    }

    @Bean
    public Sinks.Many<TracksLikedMessage> tracksLikedMessageSink() {
        return Sinks.many().unicast().onBackpressureBuffer();
    }

    @Bean
    Supplier<Flux<TracksLikedMessage>> produceTracksLikedMessage(Sinks.Many<TracksLikedMessage> tracksLikedMessageMany) {
        return tracksLikedMessageMany::asFlux;
    }

    @Bean
    public Sinks.Many<PlaylistCreatedMessage> playlistCreatedMessageSink() {
        return Sinks.many().unicast().onBackpressureBuffer();
    }

    @Bean
    Supplier<Flux<PlaylistCreatedMessage>> producePlaylistCreatedMessage(Sinks.Many<PlaylistCreatedMessage> playlistCreatedMessageMany) {
        return playlistCreatedMessageMany::asFlux;
    }

    @Bean
    public Sinks.Many<PlaylistDeletedMessage> playlistDeletedMessageSink() {
        return Sinks.many().unicast().onBackpressureBuffer();
    }

    @Bean
    Supplier<Flux<PlaylistDeletedMessage>> producePlaylistDeleted(Sinks.Many<PlaylistDeletedMessage> playlistDeletedMessageMany) {
        return playlistDeletedMessageMany::asFlux;
    }
}
