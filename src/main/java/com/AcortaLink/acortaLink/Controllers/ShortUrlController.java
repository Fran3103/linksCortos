package com.AcortaLink.acortaLink.Controllers;


import com.AcortaLink.acortaLink.DTO.ShortUrlDTO;
import com.AcortaLink.acortaLink.DTO.ShortUrlRequestDTO;
import com.AcortaLink.acortaLink.DTO.ShortUrlResponseDTO;
import com.AcortaLink.acortaLink.Entities.ShortUrl;
import com.AcortaLink.acortaLink.Services.ShortUrlService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.io.IOException;
import java.net.URI;


@RestController
public class ShortUrlController {

    @Autowired
    ShortUrlService service;


    @Operation(
            summary = "Acorta URL",
            description = "Recibe una URL válida y devuelve un objeto con el enlace  corto generado",
            tags = {"Shortening"}
    )
    @ApiResponses(value= {
            @ApiResponse(responseCode = "200", description = "URL acortada exitosamente"),
            @ApiResponse(responseCode = "400", description = "Validación de URL fallida"),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    @PostMapping("shorten")
    public ResponseEntity<ShortUrlResponseDTO> create(@Valid @RequestBody ShortUrlRequestDTO requestDTO){

        ShortUrl su = service.shortenUrl(requestDTO.getUrl());


        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{code}")
                .buildAndExpand(su.getCode())
                .toUri();


        String shortUrl = ServletUriComponentsBuilder
                .fromCurrentContextPath()
                .path("/{code}")
                .buildAndExpand(su.getCode())
                .toUriString();


        ShortUrlResponseDTO response = new ShortUrlResponseDTO(
                su.getId(),
                su.getUrlOriginal(),
                su.getCode(),
                su.getLocalDateTime(),
                su.getClickCount(),
                shortUrl
        );


        return ResponseEntity.created(location).body(response);
    }



    @Operation(
            summary = "Redirigir por código",
            tags = {"Redirect"}
    )
    @ApiResponses(value= {
            @ApiResponse(responseCode = "302", description = "Redireccion exitosa"),
            @ApiResponse(responseCode = "404", description = "Código no encontrado"),
    })
    @GetMapping("/{code:[A-Za-z0-9]{6}}")
    public void redirect(@PathVariable String code, HttpServletResponse response) throws IOException{
        ShortUrl su = service.getAndIncrement(code);
        response.sendRedirect(su.getUrlOriginal());
    }


    @GetMapping("stats/{code}")
    public ResponseEntity<ShortUrl> stats(@PathVariable String code){
        ShortUrl su = service.getAndIncrement(code);
        return ResponseEntity.ok(su);
    }
}
