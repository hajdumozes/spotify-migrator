package com.mozeshajdu.spotifymigrator.spotify.service;

import com.mozeshajdu.spotifymigrator.spotify.entity.FollowedArtist;
import com.mozeshajdu.spotifymigrator.spotify.mapper.ArtistMapper;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;
import se.michaelthelin.spotify.SpotifyApi;
import se.michaelthelin.spotify.enums.ModelObjectType;
import se.michaelthelin.spotify.model_objects.specification.Artist;
import se.michaelthelin.spotify.model_objects.specification.PagingCursorbased;
import se.michaelthelin.spotify.requests.data.follow.GetUsersFollowedArtistsRequest;
import se.michaelthelin.spotify.requests.data.follow.UnfollowArtistsOrUsersRequest;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static com.mozeshajdu.spotifymigrator.spotify.util.SpotifyApiUtil.executeRequest;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class SpotifyArtistService {
    SpotifyApi spotifyApi;
    ArtistMapper artistMapper;

    public List<FollowedArtist> getFollowedArtists() {
        GetUsersFollowedArtistsRequest request = spotifyApi.getUsersFollowedArtists(ModelObjectType.ARTIST).build();
        PagingCursorbased<Artist> followedArtistResult = executeRequest(request);
        return Arrays.stream(followedArtistResult.getItems())
                .map(artistMapper::of)
                .collect(Collectors.toList());
    }

    public String unfollowArtists(List<String> ids) {
        UnfollowArtistsOrUsersRequest request = spotifyApi.unfollowArtistsOrUsers(
                ModelObjectType.ARTIST,
                ids.toArray(String[]::new)).build();
        return executeRequest(request);
    }
}
