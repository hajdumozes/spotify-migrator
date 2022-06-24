package com.mozeshajdu.spotifymigrator.config.event;

import com.mozeshajdu.spotifymigrator.spotify.entity.event.PlaylistCreatedMessage;
import com.mozeshajdu.spotifymigrator.spotify.entity.event.PlaylistDeletedMessage;
import com.mozeshajdu.spotifymigrator.spotify.entity.event.PlaylistItemAddedMessage;
import com.mozeshajdu.spotifymigrator.spotify.entity.event.SpotifyTrackCreatedMessage;
import com.mozeshajdu.spotifymigrator.spotify.entity.event.TracksLikedMessage;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Sinks;

import java.util.function.Supplier;

@Configuration
public class EventProducerConfig {

    @Bean
    public Sinks.Many<SpotifyTrackCreatedMessage> spotifyTrackMany() {
        return Sinks.many().unicast().onBackpressureBuffer();
    }

    @Bean
    Supplier<Flux<SpotifyTrackCreatedMessage>> produceSpotifyTracks(Sinks.Many<SpotifyTrackCreatedMessage> spotifyTrackMany) {
        return spotifyTrackMany::asFlux;
    }

    @Bean
    public Sinks.Many<TracksLikedMessage> tracksLikedMessageMany() {
        return Sinks.many().unicast().onBackpressureBuffer();
    }

    @Bean
    Supplier<Flux<TracksLikedMessage>> produceTracksLiked(Sinks.Many<TracksLikedMessage> tracksLikedMessageMany) {
        return tracksLikedMessageMany::asFlux;
    }

    @Bean
    public Sinks.Many<PlaylistCreatedMessage> playlistCreatedMessageMany() {
        return Sinks.many().unicast().onBackpressureBuffer();
    }

    @Bean
    Supplier<Flux<PlaylistCreatedMessage>> producePlaylistCreated(Sinks.Many<PlaylistCreatedMessage> playlistCreatedMessageMany) {
        return playlistCreatedMessageMany::asFlux;
    }

    @Bean
    public Sinks.Many<PlaylistDeletedMessage> playlistDeletedMessageMany() {
        return Sinks.many().unicast().onBackpressureBuffer();
    }

    @Bean
    Supplier<Flux<PlaylistDeletedMessage>> producePlaylistDeleted(Sinks.Many<PlaylistDeletedMessage> playlistDeletedMessageMany) {
        return playlistDeletedMessageMany::asFlux;
    }

    @Bean
    public Sinks.Many<PlaylistItemAddedMessage> playlistItemAddedMessageMany() {
        return Sinks.many().unicast().onBackpressureBuffer();
    }

    @Bean
    Supplier<Flux<PlaylistItemAddedMessage>> producePlaylistItemAdded(Sinks.Many<PlaylistItemAddedMessage> playlistItemAddedMessageMany) {
        return playlistItemAddedMessageMany::asFlux;
    }
}
