package com.ascendcorp.springserviceresponsetime.configuration;

import io.micrometer.core.instrument.Metrics;
import io.micrometer.core.instrument.Tags;


public class MetricPublisherConfig {

	private static String NAMESPACE = "spring_response_time_ms";

    public static void publish(MetricInfo metricInfo) {
        System.out.println("***********" + metricInfo.getEndpoint());
        // Gauge.build().namespace("my_gauge").name("service").labelNames("service", "status_code", "isexpect").help("Hello").register(registry);
        Metrics.gauge(NAMESPACE, Tags.of("status", Integer.toString(metricInfo.getHttpStatus()), "uri", metricInfo.getEndpoint()), metricInfo.getDurationInMilliSec());
    }
}
