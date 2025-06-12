package com.alepizzetti.qrcodegen.dto;

import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class QrCodeGenRequestMapper {

    private static final int DEFAULT_SIZE = 200;

    public QrCodeGenRequest toModel(QrCodeGenRequest dto) {
        int width = Optional.ofNullable(dto.width()).orElse(DEFAULT_SIZE);
        int height = Optional.ofNullable(dto.height()).orElse(DEFAULT_SIZE);
        return new QrCodeGenRequest(dto.link(), width, height);
    }
}