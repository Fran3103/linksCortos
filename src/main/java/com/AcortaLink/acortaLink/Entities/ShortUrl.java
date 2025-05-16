package com.AcortaLink.acortaLink.Entities;


import jakarta.persistence.*;
import lombok.*;
import org.springframework.cglib.core.Local;

import java.time.LocalDateTime;


@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ShortUrl {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 2048)
    private String urlOriginal;

    @Column(nullable = false, unique = true , length = 10)
    private String code;

    private LocalDateTime localDateTime;

    private Long clickCount;

    @Column(name = "url_short", length = 2048)
    private String urlShort;



}
