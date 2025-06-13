package com.alepizzetti.qrcodegen.dto;

import jakarta.validation.constraints.*;

public record QrCodeGenRequest(
        @NotBlank(message = "Il campo 'link' è obbligatorio e non può essere vuoto.")
        @Size(max = 2048, message = "Il campo 'link' deve avere al massimo 2048 caratteri.")
        String link,

        @Min(value = 100, message = "La larghezza minima è 100.")
        @Max(value = 1000, message = "La larghezza massima è 1000.")
        Integer width,

        @Min(value = 100, message = "L'altezza minima è 100.")
        @Max(value = 1000, message = "L'altezza massima è 1000.")
        Integer height
) {}
