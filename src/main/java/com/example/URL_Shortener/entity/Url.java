package com.example.URL_Shortener.entity;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "urls")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Url {

    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private Long id;

    @Column(nullable=false, length=2048)
    private String originalUrl;

    @Column(nullable=false, length=20)
    private String shortUrl;

    @Column(nullable=false)
    private LocalDateTime creationDate;

    @Builder.Default
    @Column(nullable=false)
    private Long accessCount = 0L;


}
