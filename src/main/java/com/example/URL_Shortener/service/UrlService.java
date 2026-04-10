package com.example.URL_Shortener.service;

import com.example.URL_Shortener.dto.UrlRequest;
import com.example.URL_Shortener.dto.UrlResponse;
import com.example.URL_Shortener.entity.Url;
import com.example.URL_Shortener.repository.UrlRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.util.StreamUtils;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class UrlService {

    private final UrlRepository urlRepository;
    private final Base62Encoder base62Encoder;
    private static final int SHORT_URL_LENGTH = 7;

    public UrlResponse createShortUrl(UrlRequest request) {
        String shortUrl;
        do{
            shortUrl = base62Encoder.generateShortUrl(SHORT_URL_LENGTH);
        } while(urlRepository.existsByShortUrl(shortUrl));

        Url url = Url.builder()
                .originalUrl(request.getOriginalUrl())
                .shortUrl(shortUrl)
                .creationDate(LocalDateTime.now())
                .build();

        Url savedUrl = urlRepository.save(url);
        return mapToResponse(savedUrl);
    }

    @Transactional
    public String getOriginalUrlAndIncrementCount(String shortUrl) {
        Url url = urlRepository.findByShortUrl(shortUrl)
                .orElseThrow(() -> new EntityNotFoundException("Short URL not found"));

        url.setAccessCount(url.getAccessCount() + 1);
        urlRepository.save(url);

        return url.getOriginalUrl();
    }

    @Transactional
    public UrlResponse updateShortUrl(String shortUrl, UrlRequest request) {
        Url url = urlRepository.findByShortUrl(shortUrl)
                .orElseThrow(() -> new EntityNotFoundException("Short URL not found"));

        url.setOriginalUrl(request.getOriginalUrl());
        Url updatedUrl = urlRepository.save(url);

        return mapToResponse(updatedUrl);
    }

    @Transactional
    public void deleteShortUrl(String shortUrl) {
        Url url = urlRepository.findByShortUrl(shortUrl)
                .orElseThrow(() -> new EntityNotFoundException("Short URL not found"));
        urlRepository.delete(url);
    }

    @Transactional
    public UrlResponse getStats(String shortUrl) {
        Url url = urlRepository.findByShortUrl(shortUrl)
                .orElseThrow(() -> new EntityNotFoundException("Short URL not found"));
        return mapToResponse(url);
    }

    private UrlResponse mapToResponse(Url url) {
        return UrlResponse.builder()
                .originalUrl(url.getOriginalUrl())
                .shortUrl(url.getShortUrl())
                .creationDate(url.getCreationDate())
                .accessCount(url.getAccessCount())
                .build();
    }

}
