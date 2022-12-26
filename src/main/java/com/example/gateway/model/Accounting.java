package com.example.gateway.model;

import lombok.*;
import org.springframework.data.redis.core.RedisHash;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
@RedisHash("Accounting")
public class Accounting {
}
