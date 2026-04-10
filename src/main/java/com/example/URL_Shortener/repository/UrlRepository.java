package com.example.URL_Shortener.repository;

import com.example.URL_Shortener.entity.Url;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public interface UrlRepository extends JpaRepository<Url,Long> {
    Optional<Url> findByShortUrl(String url);
    boolean existsByShortUrl(String url);

}
