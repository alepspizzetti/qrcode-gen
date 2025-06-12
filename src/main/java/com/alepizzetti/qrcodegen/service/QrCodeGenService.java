package com.alepizzetti.qrcodegen.service;

import com.alepizzetti.qrcodegen.dto.QrCodeGenResponse;
import com.alepizzetti.qrcodegen.ports.StoragePort;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.WriterException;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.UUID;

@Service
public class QrCodeGenService {

    private final StoragePort storage;

    public QrCodeGenService(StoragePort storage) {
        this.storage = storage;
    }

    public QrCodeGenResponse generateAndUploadQrCode(String text) throws WriterException, IOException {
        QRCodeWriter qrCodeWriter = new QRCodeWriter();
        BitMatrix bitMatrix = qrCodeWriter.encode(text, BarcodeFormat.QR_CODE, 200, 200);
        ByteArrayOutputStream pngOutputStream = new ByteArrayOutputStream();
        MatrixToImageWriter.writeToStream(bitMatrix, "PNG", pngOutputStream);
        byte[] pngQrCodeData = pngOutputStream.toByteArray();

        String url = storage.uploadFile(
                pngQrCodeData,
                UUID.randomUUID().toString(),
                "image/png"
        );

        return new QrCodeGenResponse(url);
    }
}
