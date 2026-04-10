package com.example.URL_Shortener.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import org.hibernate.validator.constraints.URL;

@Data
public class UrlRequest {

    @NotBlank(message = "URL can't be empty.")
    @URL(message="URL can't be empty.")
    private String originalUrl;
}
