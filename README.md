### About

Spring web application, which connects to spotify api, and lets the user to migrate between local tags (which are stored in db) and tracks on spotify.

Migration stands for the following: following users, following albums, like tracks, add to playlists, fetch liked tracks, which are missing from local collection.

### Environment variables

| Name                                      | Format   | Default value                                      | Comment                                                    |
|-------------------------------------------|----------|----------------------------------------------------|------------------------------------------------------------|
| `CLIENT_ID`                               | string   |                                                    | Given by Spotify |
| `CLIENT_SECRET`                           | string   |                                                    | Given by Spotify |
| `REDIRECT_URI`                            | string   |                                                    | Given by Spotify |
| `AUDIO_TAG_MANAGER_URL`                   | string   |                                                    | Host of audio-tag-manager application |
| `KAFKA_SERVER`                            | string   |                                                    |  |
| `KAFKA_SPOTIFY_TRACK_TOPIC`               | string   | spotify-track-created                             |  |
| `KAFKA_TRACK_LIKED_TOPIC`                 | string   | track-liked                                       |  |
| `KAFKA_PLAYLIST_CREATED_TOPIC`            | string   | playlist-created                                  |  |
| `KAFKA_PLAYLIST_DELETED_TOPIC`            | string   | playlist-deleted                                  |  |