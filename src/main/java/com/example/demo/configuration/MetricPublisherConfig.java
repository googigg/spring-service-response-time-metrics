package com.example.demo.configuration;

import io.prometheus.client.CollectorRegistry;
import io.prometheus.client.Counter;
import io.prometheus.client.Gauge;

public class MetricPublisherConfig {
	public static CollectorRegistry registry = CollectorRegistry.defaultRegistry;

    // register prometheus
    private final Gauge apiProcessingTime = Gauge.build().namespace("my_gauge").name("api").labelNames("api", "status_code", "isexpect").help("Hello").register(registry);

    private final Gauge serviceRequestResponseTime = Gauge.build().namespace("my_gauge").name("service").labelNames("service", "status_code", "isexpect").help("Hello").register(registry);


    private static MetricPublisherConfig INSTANCE = new MetricPublisherConfig();

    public static MetricPublisherConfig getInstance() {
        return INSTANCE;
    }

    public Gauge getAPIProcessingTimeGauge() {
        return apiProcessingTime;
    }

    public Gauge getServiceRequestResponseTimeGauge() {
        return serviceRequestResponseTime;
    }
}
