package com.AcortaLink.acortaLink.DTO;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.URL;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ShortUrlRequestDTO {

    @NotBlank(message = "La URL no puede estar vacia")
    @URL(message = "Debe ser una URL valida (ej : https://example.com)")
    @Schema(description = "URL larga a acortar", example = "https://example.com")
    private String url;
}
