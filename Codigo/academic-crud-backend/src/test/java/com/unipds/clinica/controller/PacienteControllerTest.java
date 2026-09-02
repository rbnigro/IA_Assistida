package com.unipds.clinica.controller;

import com.unipds.clinica.service.PacienteService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(PacienteController.class)
class PacienteControllerTest {
    @Autowired private MockMvc mockMvc;
    @MockitoBean private PacienteService service;

    @Test
    void healthReturnsUp() throws Exception {
        mockMvc.perform(get("/api/pacientes/health")).andExpect(status().isOk()).andExpect(content().string("UP"));
    }

    @Test
    void findByIdReturnsNotFound() throws Exception {
        when(service.buscarPorId(99)).thenThrow(new com.unipds.clinica.service.PacienteNotFoundException(99));
        mockMvc.perform(get("/api/pacientes/99")).andExpect(status().isNotFound());
    }
}
