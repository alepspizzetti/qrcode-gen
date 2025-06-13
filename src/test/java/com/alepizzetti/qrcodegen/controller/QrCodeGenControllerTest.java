package com.alepizzetti.qrcodegen.controller;

import com.alepizzetti.qrcodegen.dto.QrCodeGenRequest;
import com.alepizzetti.qrcodegen.dto.QrCodeGenRequestMapper;
import com.alepizzetti.qrcodegen.dto.QrCodeGenResponse;
import com.alepizzetti.qrcodegen.service.QrCodeGenService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;

import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.Mockito.*;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(QrCodeGenController.class)
public class QrCodeGenControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private QrCodeGenService qrCodeService;

    @MockBean
    private QrCodeGenRequestMapper mapper;

    @Test
    void deveRetornar200ParaRequisicaoValida() throws Exception {
        QrCodeGenRequest request = new QrCodeGenRequest("https://test.com", 300, 300);
        QrCodeGenRequest mapped = new QrCodeGenRequest("https://test.com", 300, 300);
        QrCodeGenResponse response = new QrCodeGenResponse("https://bucket.s3/abc123.png");

        when(mapper.toModel(request)).thenReturn(mapped);
        when(qrCodeService.generateAndUploadQrCode(
                mapped.link(),
                mapped.width(),
                mapped.height()
        )).thenReturn(response);

        mockMvc.perform(post("/qrcode")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk());
    }

    @Test
    void deveRetornar400ParaLinkVazio() throws Exception {
        QrCodeGenRequest request = new QrCodeGenRequest("", 300, 300);

        mockMvc.perform(post("/qrcode")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.errors[0].field").value("link"));
    }

    @Test
    void deveRetornar400ParaJsonMalformado() throws Exception {
        String jsonInvalido = "{\"link\": \"https://test.com\", \"width\": \"abc\"}";

        mockMvc.perform(post("/qrcode")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonInvalido))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.errors[0].field").value("width"));
    }
}