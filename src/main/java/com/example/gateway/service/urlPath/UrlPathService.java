package com.example.gateway.service.urlPath;

import com.example.gateway.dto.UrlPathWrapper;
import com.example.gateway.model.UrlPath;

public interface UrlPathService {

    void save(UrlPath urlPath);

    void saveAll(UrlPathWrapper urlPathWrapper);
}
