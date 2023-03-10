package com.example.gateway.service.urlPath;

import com.example.gateway.dto.UrlPathWrapper;
import com.example.gateway.model.UrlPath;
import com.example.gateway.repository.UrlPathRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UrlPathServiceImpel implements UrlPathService{

    private final UrlPathRepository repository;

    public UrlPathServiceImpel(UrlPathRepository repository) {
        this.repository = repository;
    }

    @Override
    public void save(UrlPath urlPath) {
        repository.save(urlPath);
    }

    @Override
    public void saveAll(UrlPathWrapper urlPathWrapper) {
        Iterable<UrlPath> iterable = urlPathWrapper.getUrlPaths();
        repository.saveAll(iterable);
    }

    @Override
    public Optional<UrlPath> findBySource(String source) {
        return repository.findById(source);
    }
}
