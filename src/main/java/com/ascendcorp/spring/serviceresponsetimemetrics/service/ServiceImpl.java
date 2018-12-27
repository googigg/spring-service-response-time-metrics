package com.ascendcorp.spring.serviceresponsetimemetrics.service;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

@Service
public class ServiceImpl {

    private RestTemplate restTemplate;

    public ServiceImpl(@Qualifier("restTemplate") RestTemplate restTemplate) {

        this.restTemplate = restTemplate;
    }

    public ResponseEntity<String> welcome() {

        UriComponents uriComponents = UriComponentsBuilder.fromHttpUrl("http://www.mocky.io/v2/5b31c0e7310000703a1293ad?mocky-delay=2500ms")
                .build()
                .encode();
        try {
            ResponseEntity<String> response = restTemplate.getForEntity(uriComponents.toUri(), String.class);

            if (response != null) {
                String responseCode = response.getBody();

                return ResponseEntity.ok(responseCode);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return ResponseEntity.ok("default welcome API return to you");
    }
}
