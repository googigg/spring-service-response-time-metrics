package com.ascendcorp.spring.serviceresponsetimemetrics.configuration;

import com.ascendcorp.spring.serviceresponsetimemetrics.dto.ServiceResponseTimeConfig;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Map;


@Configuration
public class RestTemplateConfiguration {

    private ServiceResponseTimeConfig serviceResponseTimeConfig;

    public RestTemplateConfiguration(ApplicationContext ctx, ServiceResponseTimeConfig serviceResponseTimeConfig) {
        this.serviceResponseTimeConfig = serviceResponseTimeConfig;

        System.out.println("Service Request interceptor is now added to RestTemplate.");

        Map<String, RestTemplate> restTemplateMap = ctx.getBeansOfType(RestTemplate.class);

        for (String key : restTemplateMap.keySet()) {
            RestTemplate restTemplate = restTemplateMap.get(key);
            setClientRequestInterceptorToInterceptor(restTemplate);
        }
    }

    private void setClientRequestInterceptorToInterceptor(RestTemplate restTemplate) {
        List interceptors = restTemplate.getInterceptors();
        if (interceptors == null) {
            // restTemplate.setInterceptors(Collections.singletonList(new ClientRequestInterceptor()));
        } else {
            interceptors.add(new ServiceRequestInterceptor(serviceResponseTimeConfig.getGroupedUrls()));
            restTemplate.setInterceptors(interceptors);
        }
    }
}