package com.example.gateway.model;

import jakarta.persistence.Column;
import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;


@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
@RedisHash("Accounting")
public class Accounting {
    @Id
    @Column(nullable = false)
    private String id;

    private String user;

    private String urlCalled;

    private String userAgent;

    private String ip;

    private String referer;

    private String method;

    private String bodyParameter;

    private String requestParameter;
}
