package com.example.gateway.service;

import com.example.gateway.model.UrlPath;
import com.example.gateway.service.urlPath.UrlPathServiceImpel;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.logging.log4j.ThreadContext;
import org.springframework.context.ApplicationContext;
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

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Enumeration;
import java.util.List;
import java.util.Optional;

public class ProxyService {

    private final UrlPathServiceImpel urlPathServiceImpel;
    private final ApplicationContext context;

    public ProxyService(UrlPathServiceImpel urlPathServiceImpel, ApplicationContext context) {
        this.urlPathServiceImpel = urlPathServiceImpel;
        this.context = context;
    }

    public ResponseEntity<?> processProxyRequest(String body,
                                                 HttpMethod method,
                                                 HttpServletRequest request,
                                                 HttpServletResponse response,
                                                 String traceId) throws URISyntaxException, NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        ThreadContext.put("traceId", traceId);
        URI uri = createUri(request);
        String requestUrl = request.getRequestURI();

        Optional<UrlPath> urlPath = urlPathServiceImpel.findBySource(requestUrl);
        if (urlPath.isPresent()) {
            requestUrl = urlPath.get().getDestination();
            List<String> address = List.of(requestUrl.split("\\."));
            String className = address.get(0);
            String methodName = address.get(1);

            Object myClassObject = context.getBean(className);
            Method methodCalled;
            methodCalled = myClassObject.getClass().getMethod(methodName, String.class, String.class, String.class);
            return (ResponseEntity<?>) methodCalled.invoke(myClassObject, request.getQueryString(), body, method.name());
        }

        HttpHeaders headers = createHttpHeaders(request, traceId);

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

    private HttpHeaders createHttpHeaders(HttpServletRequest request, String traceId) {
        HttpHeaders headers = new HttpHeaders();
        Enumeration<String> headerNames = request.getHeaderNames();

        while (headerNames.hasMoreElements()) {
            String headerName = headerNames.nextElement();
            headers.set(headerName, request.getHeader(headerName));
        }

        headers.set("TRACE", traceId);
        headers.remove(HttpHeaders.ACCEPT_ENCODING);
        return headers;
    }

    private URI createUri(HttpServletRequest request) throws URISyntaxException {
        String requestUrl = request.getRequestURI();
        String SCHEME = "http";
        String DOMAIN = "localhost";
        int DOMAIN_PORT = 80;
        URI uri = new URI(SCHEME, null, DOMAIN, DOMAIN_PORT, null, null, null);

        uri = UriComponentsBuilder.fromUri(uri)
                .path(requestUrl)
                .query(request.getQueryString())
                .build(true).toUri();
        return uri;
    }
}
