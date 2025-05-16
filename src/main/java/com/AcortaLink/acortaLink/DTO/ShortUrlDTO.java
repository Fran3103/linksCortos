package com.AcortaLink.acortaLink.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data @AllArgsConstructor @NoArgsConstructor
public class ShortUrlDTO {

    private Long id;


    private String urlOriginal;


    private String code;

    private LocalDateTime localDateTime;

    private Long clickCount;

    private String urlShort;
}
