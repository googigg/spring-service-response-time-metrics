package com.example.demo.configuration;


import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Optional;


/*

This class for measuring API response time

*/

@Component
public class MetricPublishInterceptor extends HandlerInterceptorAdapter {

    private static final String GENERAL = "general";
    private static final String REQ_PARAM_TIMING = "timing";
    private static final String REQ_PARAM_API = "api";

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        request.setAttribute(REQ_PARAM_TIMING, System.currentTimeMillis());
        // request.setAttribute(REQ_PARAM_API, getServiceName(handler));
        request.setAttribute(REQ_PARAM_API, Optional.of(request.getServletPath()));

        return true;
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) {
        Long timingAttr = (Long) request.getAttribute(REQ_PARAM_TIMING);
        long completedTime = System.currentTimeMillis() - timingAttr;

        Optional<String> api = (Optional) request.getAttribute(REQ_PARAM_API);
        String label = api.isPresent() ? api.get() : GENERAL;

        String expected = determineExpectation(response);

        MetricPublisherConfig.getInstance().getAPIProcessingTimeGauge().labels(label, String.valueOf(response.getStatus()), expected).set(completedTime);
    }

    private String determineExpectation(HttpServletResponse response) {
        switch (response.getStatus()) {
            case 200:
                return "0";
            case 400:
            case 403:
            case 404:
                return "2";
            default:
                break;
        }
        return "1";
    }

    private Optional<String> getServiceName(Object handler) {
        if(handler instanceof HandlerMethod) {
            return Optional.of(((HandlerMethod) handler).getMethod().getName());
        }
        return Optional.empty();
    }
}