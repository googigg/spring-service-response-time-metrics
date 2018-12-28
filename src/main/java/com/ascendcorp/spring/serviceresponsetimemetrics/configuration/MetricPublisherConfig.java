package com.ascendcorp.spring.serviceresponsetimemetrics.configuration;

import io.micrometer.core.instrument.Gauge;
import io.micrometer.core.instrument.Metrics;
import io.micrometer.core.instrument.Tags;
import io.micrometer.core.instrument.simple.SimpleMeterRegistry;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;


public class MetricPublisherConfig {

	private static String NAME_RESPONSE_TIME_MS = "spring_response_time_ms";
    private static String NAME_RESPONSE_TIME_COUNT = "spring_response_time_count";

	private static int count = 0;

    // private static CollectorRegistry registry = CollectorRegistry.defaultRegistry;
    private static Gauge apiProcessingTime;

    private static MetricPublisherConfig instance = new MetricPublisherConfig();
    static SimpleMeterRegistry registry = new SimpleMeterRegistry();

    public MetricPublisherConfig() {
        System.out.println("MetricPublisherConfigMetricPublisherConfigMetricPublisherConfigMetricPublisherConfig");
        try {
            // apiProcessingTime = Gauge.build().namespace("my_gauge").name("api").help("Hello").register(registry);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void publish(MetricInfo metricInfo) {

        System.out.println("publish duration ----> " + NAME_RESPONSE_TIME_MS + " " + metricInfo.getDurationInMilliSec());

        try {
            List<String> list = new ArrayList<>(4);

            Gauge gauge = Gauge.builder("gauge", metricInfo, MetricInfo::getDurationInMilliSec)
                    .description("a description of what this gauge does") // optional
                    .tags("region", "test") // optional
                    .register(registry);


            Metrics.gauge(NAME_RESPONSE_TIME_COUNT, Tags.of("status", Integer.toString(metricInfo.getHttpStatus()), "uri", metricInfo.getEndpoint()), count++);
            // Metrics.counter("hello", "info", "Request to get user balance in PMC").increment();

            // Object g = Metrics.gauge(NAME_RESPONSE_TIME_MS, Tags.of("status", Integer.toString(metricInfo.getHttpStatus()), "uri", metricInfo.getEndpoint()), metricInfo.getDurationInMilliSec());
            // apiProcessingTime.labels().set(1.0);

            // System.out.println(g.getClass().toString());

        } catch (Exception e) {
            e.printStackTrace();
        }

        System.out.println("end");
    }
}
