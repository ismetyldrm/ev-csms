package com.evcsms.ocppmockserver;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;


@EnableScheduling
@SpringBootApplication
public class OcppMockServer {

    public static void main(String[] args) {
        SpringApplication.run(OcppMockServer.class, args);
    }

}
