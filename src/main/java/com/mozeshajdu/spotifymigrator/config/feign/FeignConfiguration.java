package com.mozeshajdu.spotifymigrator.config.feign;

import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableFeignClients(basePackages = "com.mozeshajdu.spotifymigrator")
public class FeignConfiguration {
}
