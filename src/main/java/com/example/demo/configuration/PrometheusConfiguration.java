package com.example.demo.configuration;

import io.prometheus.client.hotspot.DefaultExports;
import io.prometheus.client.spring.boot.EnablePrometheusEndpoint;
import io.prometheus.client.spring.boot.SpringBootMetricsCollector;
import org.springframework.boot.actuate.endpoint.PublicMetrics;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Collection;

@Configuration
@EnablePrometheusEndpoint
@ConditionalOnWebApplication
public class PrometheusConfiguration {

    @Bean
    @ConditionalOnMissingBean(SpringBootMetricsCollector.class)
    SpringBootMetricsCollector springBootMetricsCollector(Collection<PublicMetrics> publicMetrics) {
        DefaultExports.initialize();
        SpringBootMetricsCollector collector = new SpringBootMetricsCollector(publicMetrics);
        collector.register();
        return collector;
    }

}
