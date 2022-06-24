### Spotify operation
- only like tracks, which were not liked before

### Refactor
- replace get disconnected liked tracks with audio tag query, which filters for liked
- ignore empty string on spotify track query (Spotify track controller / query)

### Auth
- controller advice for missing token
- refresh token usage