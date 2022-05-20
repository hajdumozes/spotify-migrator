package com.mozeshajdu.spotifymigrator.spotify.service;

import com.mozeshajdu.spotifymigrator.spotify.entity.FollowedArtist;
import com.mozeshajdu.spotifymigrator.spotify.exception.SpotifyApiException;
import com.mozeshajdu.spotifymigrator.spotify.mapper.ArtistMapper;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;
import se.michaelthelin.spotify.SpotifyApi;
import se.michaelthelin.spotify.enums.ModelObjectType;
import se.michaelthelin.spotify.model_objects.specification.Artist;
import se.michaelthelin.spotify.model_objects.specification.PagingCursorbased;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class SpotifyArtistService {
    SpotifyApi spotifyApi;
    ArtistMapper artistMapper;

    public List<FollowedArtist> getFollowedArtists() {
        PagingCursorbased<Artist> followedArtistResult = getFollowedArtistResult();
        return Arrays.stream(followedArtistResult.getItems())
                .map(artistMapper::of)
                .collect(Collectors.toList());
    }

    public String unfollowArtists(List<String> ids) {
        return unfollowArtistsRequest(ids);
    }

    private PagingCursorbased<Artist> getFollowedArtistResult() {
        try {
            return spotifyApi.getUsersFollowedArtists(ModelObjectType.ARTIST).build().execute();
        } catch (Exception e) {
            throw new SpotifyApiException(e.getMessage());
        }
    }

    private String unfollowArtistsRequest(List<String> ids) {
        try {
            return spotifyApi.unfollowArtistsOrUsers(
                    ModelObjectType.ARTIST,
                    ids.toArray(String[]::new)).build().execute();
        } catch (Exception e) {
            throw new SpotifyApiException(e.getMessage());
        }
    }
}
