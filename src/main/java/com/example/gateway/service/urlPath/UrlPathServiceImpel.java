package com.example.gateway.service.urlPath;

import com.example.gateway.model.UrlPath;
import com.example.gateway.repository.UrlPathRepository;

public class UrlPathServiceImpel implements UrlPathService{

    private final UrlPathRepository repository;

    public UrlPathServiceImpel(UrlPathRepository repository) {
        this.repository = repository;
    }

    @Override
    public void save(UrlPath urlPath) {
        repository.save(urlPath);
    }
}
