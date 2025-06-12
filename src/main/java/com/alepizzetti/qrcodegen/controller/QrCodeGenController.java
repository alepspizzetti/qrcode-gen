package com.alepizzetti.qrcodegen.controller;

import com.alepizzetti.qrcodegen.dto.QrCodeGenRequest;
import com.alepizzetti.qrcodegen.dto.QrCodeGenResponse;
import com.alepizzetti.qrcodegen.service.QrCodeGenService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/qrcode")
public class QrCodeGenController {

    private final QrCodeGenService qrCodeGenService;

    public QrCodeGenController(QrCodeGenService qrCodeGenService) {
        this.qrCodeGenService = qrCodeGenService;
    }

    @PostMapping
    public ResponseEntity<QrCodeGenResponse> generateQrCode(@RequestBody QrCodeGenRequest request) {
        try {
            QrCodeGenResponse response = this.qrCodeGenService.generateAndUploadQrCode(request.getText());
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            System.out.println(e);
            return ResponseEntity.internalServerError().build();
        }
     }
}
