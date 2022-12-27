package com.example.gateway.model;


import jakarta.persistence.Column;
import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;

import java.io.Serializable;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
@RedisHash("UrlPath")
public class UrlPath implements Serializable {
    @Id
    @Column(nullable = false)
    private String source;

    @Column(nullable = false)
    private String destination;
}
