package com.alepizzetti.qrcodegen.dto;

import com.alepizzetti.qrcodegen.dto.QrCodeGenRequest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
public class QrCodeGenRequestMapperTest {

    private QrCodeGenRequestMapper mapper;

    @BeforeEach
    void setup() {
        mapper = new QrCodeGenRequestMapper();
    }

    @Test
    void deveUsarValoresInformadosQuandoNaoForemNull() {
        QrCodeGenRequest dto = new QrCodeGenRequest("https://test.com", 300, 400);
        QrCodeGenRequest model = mapper.toModel(dto);

        assertEquals("https://test.com", model.link());
        assertEquals(300, model.width());
        assertEquals(400, model.height());
    }

    @Test
    void deveAplicarValoresPadraoQuandoWidthEHeightForemNull() {
        QrCodeGenRequest dto = new QrCodeGenRequest("https://test.com", null, null);
        QrCodeGenRequest model = mapper.toModel(dto);

        assertEquals(200, model.width(), "width deve ser o valor padrão");
        assertEquals(200, model.height(), "height deve ser o valor padrão");
    }

    @Test
    void deveAplicarPadraoSomenteNoCampoQueEstiverNull() {
        QrCodeGenRequest dto = new QrCodeGenRequest("https://test.com", null, 500);
        QrCodeGenRequest model = mapper.toModel(dto);

        assertEquals(200, model.width(), "width deve ser padrão");
        assertEquals(500, model.height(), "height deve usar valor enviado");
    }
}