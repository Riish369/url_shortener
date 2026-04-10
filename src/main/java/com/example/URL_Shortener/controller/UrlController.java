package com.example.URL_Shortener.controller;

import com.example.URL_Shortener.dto.UrlRequest;
import com.example.URL_Shortener.dto.UrlResponse;
import com.example.URL_Shortener.service.UrlService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/urls")
@RequiredArgsConstructor
public class UrlController {
    private final UrlService urlService;

    // 1. Create a new short URL
    @PostMapping
    public ResponseEntity<UrlResponse> createUrl(@Valid @RequestBody UrlRequest request) {
        return new ResponseEntity<>(urlService.createShortUrl(request), HttpStatus.CREATED);
    }

    // 2. Get statistics/details on the short URL
    @GetMapping("/{shortUrl}/stats")
    public ResponseEntity<UrlResponse> getUrlStats(@PathVariable String shortUrl) {
        return ResponseEntity.ok(urlService.getStats(shortUrl));
    }

    // 3. Update an existing short URL
    @PutMapping("/{shortUrl}")
    public ResponseEntity<UrlResponse> updateUrl(@PathVariable String shortUrl, @Valid @RequestBody UrlRequest request) {
        return ResponseEntity.ok(urlService.updateShortUrl(shortUrl, request));
    }

    // 4. Delete an existing short URL
    @DeleteMapping("/{shortUrl}")
    public ResponseEntity<Void> deleteUrl(@PathVariable String shortUrl) {
        urlService.deleteShortUrl(shortUrl);
        return ResponseEntity.noContent().build();

    }
}

