package com.alepizzetti.qrcodegen.controller;

import com.alepizzetti.qrcodegen.dto.QrCodeGenRequest;
import com.alepizzetti.qrcodegen.dto.QrCodeGenRequestMapper;
import com.alepizzetti.qrcodegen.dto.QrCodeGenResponse;
import com.alepizzetti.qrcodegen.service.QrCodeGenService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/qrcode")
public class QrCodeGenController {

    private final QrCodeGenService qrCodeGenService;
    private final QrCodeGenRequestMapper mapper;

    public QrCodeGenController(QrCodeGenService qrCodeGenService, QrCodeGenRequestMapper mapper) {
        this.qrCodeGenService = qrCodeGenService;
        this.mapper = mapper;
    }

    @PostMapping
    public ResponseEntity<QrCodeGenResponse> generateQrCode(@Valid @RequestBody QrCodeGenRequest request) {
        request = mapper.toModel(request);
        try {
            QrCodeGenResponse response = this.qrCodeGenService.generateAndUploadQrCode(
                    request.link(),
                    request.width(),
                    request.height()
                    );
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.internalServerError().build();
        }
     }
}
