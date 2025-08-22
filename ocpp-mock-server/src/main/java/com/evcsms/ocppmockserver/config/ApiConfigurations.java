package com.evcsms.ocppmockserver.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Getter
@Setter
@Configuration
@EnableConfigurationProperties
public class ApiConfigurations {

    @Value("${websocket.url}")
    private String webSocketBaseUrl;

}
