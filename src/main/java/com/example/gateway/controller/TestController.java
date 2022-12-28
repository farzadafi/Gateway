package com.example.gateway.controller;

import com.example.gateway.exception.InvalidRequestException;
import com.example.gateway.model.UrlPath;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.base.Strings;
import com.google.common.collect.Lists;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;

@RestController
public class TestController {

    private final Utility utility;
    private final ObjectMapper mapper;

    public TestController(Utility utility, ObjectMapper mapper) {
        this.utility = utility;
        this.mapper = mapper;
    }

    public ResponseEntity<String> test(String queryParameter, String body, String method) {
        utility.checkMethod("GET", method);
        if (queryParameter == null)
            throw new InvalidRequestException("send valid parameter");

        ArrayList<String> parameterName = Lists.newArrayList("firstname", "lastname");
        ArrayList<String> parameterValue = utility.processGetParameter(queryParameter, parameterName);
        String firstname = parameterValue.get(0);
        String lastname = parameterValue.get(1);

        return ResponseEntity.ok(String.format("%s %s", firstname, lastname));
    }

    public ResponseEntity<String> postTest(String queryParameter, String body, String method) {
        utility.checkMethod("POST", method);
        UrlPath urlPath;
        try {
            urlPath = createUrlPath(body);
        } catch (JsonProcessingException e) {
            throw new InvalidRequestException("please send Valid parameter");
        }
        return ResponseEntity.ok(urlPath.toString());
    }

    public UrlPath createUrlPath(String body) throws JsonProcessingException {
        if (body == null)
            throw new InvalidRequestException("please send valid data");
        UrlPath urlPath = mapper.readValue(body, UrlPath.class);
        if (Strings.isNullOrEmpty(urlPath.getSource()) || Strings.isNullOrEmpty(urlPath.getDestination()))
            throw new InvalidRequestException("please send valid data");
        return urlPath;
    }
}
