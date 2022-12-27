package com.example.gateway.dto;

import com.example.gateway.model.UrlPath;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class UrlPathWrapper {

    private List<UrlPath> urlPaths;
}
