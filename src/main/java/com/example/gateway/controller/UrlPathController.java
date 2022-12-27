package com.example.gateway.controller;

import com.example.gateway.dto.UrlPathDto;
import com.example.gateway.mapper.UrlPathMapper;
import com.example.gateway.model.UrlPath;
import com.example.gateway.service.urlPath.UrlPathServiceImpel;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UrlPathController {

    private final UrlPathServiceImpel urlPathServiceImpel;

    public UrlPathController(UrlPathServiceImpel urlPathServiceImpel) {
        this.urlPathServiceImpel = urlPathServiceImpel;
    }

    @PostMapping("/urlPath/add")
    public String addUrlPath(@RequestBody UrlPathDto urlPathDto) {
        UrlPath urlPath = UrlPathMapper.INSTANCE.dtoToModel(urlPathDto);
        urlPathServiceImpel.save(urlPath);
        return "OK";
    }
}
