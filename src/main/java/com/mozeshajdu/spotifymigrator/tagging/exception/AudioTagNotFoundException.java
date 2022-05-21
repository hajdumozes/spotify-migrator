package com.mozeshajdu.spotifymigrator.tagging.exception;

public class AudioTagNotFoundException extends RuntimeException {

    public AudioTagNotFoundException(String id) {
        super(String.format("Audio tag '%s' not found", id));
    }
}
