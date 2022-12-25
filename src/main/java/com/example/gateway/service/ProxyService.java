package com.example.gateway.service;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.logging.log4j.ThreadContext;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.BufferingClientHttpRequestFactory;
import org.springframework.http.client.ClientHttpRequestFactory;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.web.client.HttpStatusCodeException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.Enumeration;

public class ProxyService {

    public ResponseEntity<?> processProxyRequest(String body,
                                                 HttpMethod method,
                                                 HttpServletRequest request,
                                                 HttpServletResponse response,
                                                 String traceId) throws URISyntaxException {
        ThreadContext.put("traceId", traceId);
        String requestUrl = request.getRequestURI();
        String SCHEME = "http";
        String DOMAIN = "localhost";
        int DOMAIN_PORT = 80;
        URI uri = new URI(SCHEME, null, DOMAIN, DOMAIN_PORT, null, null, null);

        uri = UriComponentsBuilder.fromUri(uri)
                .path(requestUrl)
                .query(request.getQueryString())
                .build(true).toUri();

        HttpHeaders headers = new HttpHeaders();
        Enumeration<String> headerNames = request.getHeaderNames();

        while (headerNames.hasMoreElements()) {
            String headerName = headerNames.nextElement();
            headers.set(headerName, request.getHeader(headerName));
        }

        headers.set("TRACE", traceId);
        headers.remove(HttpHeaders.ACCEPT_ENCODING);

        HttpEntity<String> httpEntity = new HttpEntity<>(body, headers);
        ClientHttpRequestFactory factory = new BufferingClientHttpRequestFactory(new SimpleClientHttpRequestFactory());
        RestTemplate restTemplate = new RestTemplate(factory);
        try {
            ResponseEntity<String> serverResponse = restTemplate.exchange(uri, method, httpEntity, String.class);
            serverResponse.getHeaders().get(HttpHeaders.CONTENT_TYPE);
            return serverResponse;
        } catch (HttpStatusCodeException e) {
            return ResponseEntity.status(e.getRawStatusCode())
                    .headers(e.getResponseHeaders())
                    .body(e.getResponseBodyAsString());
        }
    }
}
