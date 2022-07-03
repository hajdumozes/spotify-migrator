package com.mozeshajdu.spotifymigrator.config.spring;

import com.mozeshajdu.spotifymigrator.spotify.exception.SpotifyApiException;
import feign.FeignException;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;


@RestControllerAdvice
public class AdviceController extends ResponseEntityExceptionHandler {

    @ResponseBody
    @ExceptionHandler(FeignException.class)
    ResponseEntity<Object> handleFeignException(FeignException e) {
        HttpStatus httpStatus = HttpStatus.valueOf(e.status());
        return ResponseEntity.status(httpStatus).contentType(MediaType.APPLICATION_JSON).body(e.getMessage());
    }

    @ResponseBody
    @ExceptionHandler(SpotifyApiException.class)
    ResponseEntity<Object> handleSpotifyApiException(SpotifyApiException e) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).contentType(MediaType.APPLICATION_JSON).body(e.getMessage());
    }
}
