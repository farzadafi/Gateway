package com.example.gateway.controller;

import com.example.gateway.exception.InvalidRequestException;
import com.example.gateway.exception.MethodNotAllowedException;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.stream.Collectors;

@Component
public class Utility {

    public ArrayList<String> processGetParameter(String parameter, ArrayList<String> parameterName) {
        parameterName = addEqualSignToEndEachElement(parameterName);
        ArrayList<String> parameterValue = new ArrayList<>();
        try {
            String[] split = parameter.split("&");
            for (int i = 0; i < parameterName.size(); i++) {
                String[] Array = split[i].split(parameterName.get(i));
                parameterValue.add(i, Array[1]);
            }
        } catch (Exception e) {
            throw new InvalidRequestException("send valid parameter");
        }
        return parameterValue;
    }

    public ArrayList<String> addEqualSignToEndEachElement(ArrayList<String> parameterName) {
        return (ArrayList<String>) parameterName.stream()
                .map(f -> f + "=")
                .collect(Collectors.toList());
    }

    public void checkMethod(String expectedMethod, String actualMethod) {
        if (!actualMethod.equals(expectedMethod))
            throw new MethodNotAllowedException("send valid method");
    }

}
