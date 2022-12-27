package com.example.gateway.service.urlPath;

import com.example.gateway.dto.UrlPathWrapper;
import com.example.gateway.model.UrlPath;

import java.util.Optional;

public interface UrlPathService {

    void save(UrlPath urlPath);

    void saveAll(UrlPathWrapper urlPathWrapper);

    Optional<UrlPath> findBySource(String source);
}
