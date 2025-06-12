package com.alepizzetti.qrcodegen.dto;

import jakarta.validation.constraints.*;

public record QrCodeGenRequest(
        @NotBlank(message = "O campo 'link' é obrigatório e não pode estar em branco.")
        @Size(max = 2048, message = "O campo 'link' deve ter no máximo 2048 caracteres.")
        String link,

        @Min(value = 100, message = "A largura mínima é 100.")
        @Max(value = 1000, message = "A largura máxima é 1000.")
        Integer width,

        @Min(value = 100, message = "A altura mínima é 100.")
        @Max(value = 1000, message = "A altura máxima é 1000.")
        Integer height
) {}
