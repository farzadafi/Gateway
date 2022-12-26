package com.example.gateway.controller;

import com.example.gateway.service.ProxyService;
import com.example.gateway.service.accounting.AccountingServiceImpel;
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
    private final AccountingServiceImpel accountingServiceImpel;

    public ReverseController(ProxyService proxyService, AccountingServiceImpel accountingServiceImpel) {
        this.proxyService = proxyService;
        this.accountingServiceImpel = accountingServiceImpel;
    }

    @RequestMapping("/**")
    public ResponseEntity<?> reverseProxyMethod(@RequestBody(required = false) String body,
                                              HttpMethod method,
                                              HttpServletRequest request,
                                              HttpServletResponse response) throws URISyntaxException {
        accountingServiceImpel.save(request,method.toString(),body);
        return proxyService.processProxyRequest(body, method, request, response, UUID.randomUUID().toString());
    }
}
