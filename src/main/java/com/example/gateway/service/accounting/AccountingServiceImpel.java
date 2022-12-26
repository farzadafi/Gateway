package com.example.gateway.service.accounting;

import com.example.gateway.model.Accounting;
import com.example.gateway.repository.AccountingRepository;
import jakarta.servlet.http.HttpServletRequest;

import java.time.LocalDateTime;
import java.util.Enumeration;

public class AccountingServiceImpel implements AccountingService {

    private final AccountingRepository repository;

    public AccountingServiceImpel(AccountingRepository repository) {
        this.repository = repository;
    }

    @Override
    public void save(HttpServletRequest request, String method, String body) {
        String notLocalDataTime = LocalDateTime.now().toString();
        Accounting accounting = Accounting.builder().id(notLocalDataTime)
                .user(getCurrentUser(request))
                .urlCalled(request.getRequestURI())
                .userAgent(request.getHeader("user-agent"))
                .ip(getIpAddress(request))
                .referer(request.getHeader("referer"))
                .method(method)
                .bodyParameter(body)
                .requestParameter(getRequestParameter(request)).build();

        repository.save(accounting);
    }

    public String getIpAddress(HttpServletRequest request) {
        String ipAddress = request.getHeader("X-FORWARDED-FOR");
        if (ipAddress == null || "".equals(ipAddress)) {
            ipAddress = request.getRemoteAddr();
        }
        return ipAddress;
    }

    public String getCurrentUser(HttpServletRequest request) {
        String currentUser = "anonymous";
        if (request.getUserPrincipal() != null)
            currentUser = request.getUserPrincipal().toString();
        return currentUser;
    }

    public String getRequestParameter(HttpServletRequest request) {
        StringBuilder requestParameter = new StringBuilder();
        Enumeration<String> parameterNames = request.getParameterNames();

        while (parameterNames.hasMoreElements()) {
            String paramName = parameterNames.nextElement();
            requestParameter.append(paramName);
            requestParameter.append("=");
            String[] paramValues = request.getParameterValues(paramName);
            for (String paramValue : paramValues) {
                requestParameter.append(paramValue);
                requestParameter.append("/");
            }
        }
        return String.valueOf(requestParameter);
    }
}
