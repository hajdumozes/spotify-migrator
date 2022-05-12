package com.mozeshajdu.spotifymigrator.tagging.entity;

import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Genre {
    String name;
}
