package com.example.demo.configuration;

import org.springframework.http.HttpRequest;
import org.springframework.http.client.ClientHttpRequestExecution;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.http.client.ClientHttpResponse;

import java.io.IOException;


/*

This class for measuring Service (call to) response time

*/


public class ServiceRequestInterceptor implements ClientHttpRequestInterceptor {

    @Override
    public ClientHttpResponse intercept(HttpRequest httpRequest, byte[] bytes, ClientHttpRequestExecution clientHttpRequestExecution) throws IOException {

        long startAtTime = System.currentTimeMillis();

        ClientHttpResponse response = clientHttpRequestExecution.execute(httpRequest, bytes);

        long completedTime = System.currentTimeMillis() - startAtTime;

        String label = httpRequest.getURI().getRawSchemeSpecificPart();
        String expected = "0";
        MetricPublisherConfig.getInstance().getServiceRequestResponseTimeGauge().labels(label, String.valueOf(response.getStatusCode()), expected).set(completedTime);

        return response;
    }
}
