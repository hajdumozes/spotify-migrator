package com.mozeshajdu.spotifymigrator.config.event;

import com.mozeshajdu.spotifymigrator.spotify.entity.SpotifyTrack;
import com.mozeshajdu.spotifymigrator.spotify.entity.TracksLikedMessage;
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
    Supplier<Flux<SpotifyTrack>> produceSpotifyTracks(Sinks.Many<SpotifyTrack> spotifyTrackSink) {
        return spotifyTrackSink::asFlux;
    }

    @Bean
    public Sinks.Many<TracksLikedMessage> tracksLikedMessageSink() {
        return Sinks.many().unicast().onBackpressureBuffer();
    }

    @Bean
    Supplier<Flux<TracksLikedMessage>> produceTracksLikedMessage(Sinks.Many<TracksLikedMessage> tracksLikedMessageSink) {
        return tracksLikedMessageSink::asFlux;
    }
}
