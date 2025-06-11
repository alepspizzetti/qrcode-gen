package com.alepizzetti.qrcodegen.dto;

public record QrCodeGenRequest(String text) {

    public String getText() {
        return text;
    }
}
