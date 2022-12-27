package com.example.gateway.controller;

import com.example.gateway.dto.UrlPathDto;
import com.example.gateway.dto.UrlPathWrapper;
import com.example.gateway.exception.UrlNotFoundException;
import com.example.gateway.mapper.UrlPathMapper;
import com.example.gateway.model.UrlPath;
import com.example.gateway.service.urlPath.UrlPathServiceImpel;
import org.springframework.web.bind.annotation.*;

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

    @PostMapping("/urlPath/addAll") //send json Array with name urlPaths :)
    public String saveAll(@RequestBody UrlPathWrapper urlPathWrapper) {
        urlPathServiceImpel.saveAll(urlPathWrapper);
        return "OK";
    }

    @GetMapping("/urlPath/findByUrl")
    public UrlPath findByUrl(@RequestParam String url) {
        return urlPathServiceImpel.findBySource(url)
                .orElseThrow(() -> new UrlNotFoundException("this url not found!"));
    }
}
