package com.ascendcorp.spring.serviceresponsetimemetrics.configuration;

import io.micrometer.core.instrument.Metrics;
import io.micrometer.core.instrument.Tags;


public class MetricPublisherConfig {

	private static String NAMESPACE = "spring_response_time_ms";

    public static void publish(MetricInfo metricInfo) {
        Metrics.gauge(NAMESPACE, Tags.of("status", Integer.toString(metricInfo.getHttpStatus()), "uri", metricInfo.getEndpoint()), metricInfo.getDurationInMilliSec());
    }
}
