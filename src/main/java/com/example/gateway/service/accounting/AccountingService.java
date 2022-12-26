package com.example.gateway.service.accounting;

import jakarta.servlet.http.HttpServletRequest;

public interface AccountingService {

    void save(HttpServletRequest request, String method, String body);

}
