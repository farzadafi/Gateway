package com.example.gateway.controller;

import com.example.gateway.exception.InvalidRequestException;
import com.google.common.collect.Lists;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;

public class TestController {

    private final Utility utility;

    public TestController(Utility utility) {
        this.utility = utility;
    }

    public ResponseEntity<String> test(String queryParameter, String body, String method) {
        utility.checkMethod("GET", method);
        if (queryParameter == null)
            throw new InvalidRequestException("send valid parameter");

        ArrayList<String> parameterName = Lists.newArrayList("firstname", "lastname");
        ArrayList<String> parameterValue = utility.processGetParameter(queryParameter, parameterName);
        String firstname = parameterValue.get(0);
        String lastname  = parameterValue.get(1);

        return ResponseEntity.ok(String.format("%s %s", firstname, lastname));
    }
}
