package com.example.gateway.dto;


import jakarta.validation.constraints.NotNull;
import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class UrlPathDto {

    @NotNull
    private String source;

    @NotNull
    private String destination;
}
