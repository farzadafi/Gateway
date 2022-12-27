package com.example.gateway.model;


import jakarta.persistence.Column;
import org.springframework.data.annotation.Id;

public class UrlPath {
    @Id
    @Column(nullable = false)
    private String source;

    @Column(nullable = false)
    private String destination;
}
