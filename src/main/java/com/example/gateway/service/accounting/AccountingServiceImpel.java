package com.example.gateway.service.accounting;

import com.example.gateway.model.Accounting;
import com.example.gateway.repository.AccountingRepository;
import jakarta.servlet.http.HttpServletRequest;

import java.time.LocalDateTime;

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
}
