package com.example.URL_Shortener.dto;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
public class UrlResponse {

    private String originalUrl;
    private String shortUrl;
    private LocalDateTime creationDate;
    private Long accessCount;

}
