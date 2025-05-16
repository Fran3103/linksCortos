package com.AcortaLink.acortaLink.DTO;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class ShortUrlResponseDTO {

    @Schema(description = "ID interno asignado por la base de datos", example = "42")
    private Long id;

    @Schema(description = "URL Original enviada", example = "https://example.com")
    private String urlOriginal;

    @Schema(description = "Código corto generado ", example = "hG12Sa")
    private String code;

    @Schema(description = "Fecha y hora de creacion", example = "2025-05-16T12:00:00")
    private LocalDateTime localDateTime;

    @Schema(description = "Cantidad de clics recibidos", example = "20")
    private Long clickCount;

    @Schema(description = "Url final generada", example = "https://example.com/hG12Sa")
    private String urlShort;
}
