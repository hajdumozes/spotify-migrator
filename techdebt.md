### Spotify operation
- only like tracks, which were not liked before

### Refactor
- ignore empty string on spotify track query (Spotify track controller / query)
- Controller Advice for both regular exceptions and feign exceptions
- SpotifyApiException should respond with detailed error message

### Auth
- controller advice for missing token
- refresh token usage
