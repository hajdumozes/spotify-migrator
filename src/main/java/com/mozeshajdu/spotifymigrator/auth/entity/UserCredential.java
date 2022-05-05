package com.mozeshajdu.spotifymigrator.auth.entity;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Data;
import lombok.experimental.FieldDefaults;

@Data
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UserCredential {
    String accessToken;
    String refreshToken;
    Integer expiresIn;
}
