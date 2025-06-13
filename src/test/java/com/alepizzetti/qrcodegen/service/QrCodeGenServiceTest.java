package com.alepizzetti.qrcodegen.service;

import com.alepizzetti.qrcodegen.dto.QrCodeGenResponse;
import com.alepizzetti.qrcodegen.ports.StoragePort;
import com.google.zxing.WriterException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class QrCodeGenServiceTest {

    private StoragePort storage;
    private QrCodeGenService service;

    @BeforeEach
    void setup() {
        storage = mock(StoragePort.class);
        service = new QrCodeGenService(storage);
    }

    @Test
    void deveGerarQrCodeEChamarUploadComSucesso() throws WriterException, IOException {
        when(storage.uploadFile(any(), anyString(), eq("image/png")))
                .thenReturn("https://s3.com/fake.png");

        QrCodeGenResponse response = service.generateAndUploadQrCode("https://test.com", 300, 300);

        assertNotNull(response);
        assertEquals("https://s3.com/fake.png", response.data());
        verify(storage, times(1)).uploadFile(any(), anyString(), eq("image/png"));
    }

    @Test
    void deveLancarWriterExceptionSeTextoForInvalido() {
        assertThrows(NullPointerException.class, () ->
                service.generateAndUploadQrCode(null, 200, 200)
        );
    }

    @Test
    void deveLancarIOExceptionSeUploadFalhar() throws IOException {
        when(storage.uploadFile(any(), anyString(), eq("image/png")))
                .thenThrow(new RuntimeException("Falha no upload"));

        assertThrows(RuntimeException.class, () ->
                service.generateAndUploadQrCode("https://test.com", 300, 300)
        );
    }
}