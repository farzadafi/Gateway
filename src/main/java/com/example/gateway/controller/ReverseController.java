package com.example.gateway.controller;

import com.example.gateway.service.ProxyService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import java.net.URISyntaxException;
import java.util.UUID;

public class ReverseController {

    private final ProxyService proxyService;

    public ReverseController(ProxyService proxyService) {
        this.proxyService = proxyService;
    }

    @RequestMapping("/**")
    public ResponseEntity<?> reverseProxyMethod(@RequestBody(required = false) String body,
                                              HttpMethod method,
                                              HttpServletRequest request,
                                              HttpServletResponse response) throws URISyntaxException {
        return proxyService.processProxyRequest(body, method, request, response, UUID.randomUUID().toString());
    }
}
