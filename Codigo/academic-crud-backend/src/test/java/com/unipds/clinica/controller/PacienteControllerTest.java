package com.unipds.clinica.controller;

import com.unipds.clinica.service.PacienteService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.Mockito.when;
import static org.mockito.ArgumentMatchers.any;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
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

    @Test
    void createRejectsInvalidCpf() throws Exception {
        mockMvc.perform(post("/api/pacientes")
                        .contentType("application/json")
                        .content("{\"nome\":\"Paciente Teste\",\"cpf\":\"123\",\"dataNascimento\":\"1990-05-20\"}"))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.erro").value(org.hamcrest.Matchers.containsString("cpf")));
    }

    @Test
    void createReturnsCreatedPatient() throws Exception {
        when(service.criar(any())).thenReturn(new com.unipds.clinica.dto.PacienteResponseDTO(
                1, "Paciente Teste", "12345678901", java.time.LocalDate.of(1990, 5, 20),
                null, null, null, null, null, null, null, null, null, null, null, null,
                null, null, null, null, null));

        mockMvc.perform(post("/api/pacientes")
                        .contentType("application/json")
                        .content("{\"nome\":\"Paciente Teste\",\"cpf\":\"12345678901\",\"dataNascimento\":\"1990-05-20\"}"))
                .andExpect(status().isCreated())
                .andExpect(content().contentTypeCompatibleWith("application/json"))
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.cpf").value("12345678901"));
    }
}
