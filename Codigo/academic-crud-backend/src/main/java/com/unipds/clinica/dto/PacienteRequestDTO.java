package com.unipds.clinica.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

import java.time.LocalDate;

public record PacienteRequestDTO(
        @NotBlank @Size(max = 50) String nome,
        @NotBlank @Pattern(regexp = "\\d{11}") String cpf,
        @NotNull LocalDate dataNascimento,
        @Size(max = 50) String endereco,
        Integer numero,
        @Size(max = 5) String complemento,
        @Size(max = 30) String cidade,
        @Size(max = 2) String estado,
        @Size(max = 8) String cep,
        @Size(max = 11) String telefone,
        @Email @Size(max = 30) String email,
        @Size(max = 1) String genero,
        @Size(max = 12) String convenio,
        @Size(max = 30) String especialidade,
        @Size(max = 2) String tipagemSanguinea,
        @Size(max = 1) String fatorRh,
        String alergias,
        String usoContinuoMedicamentos,
        String doencasPreexistentes,
        String observacoes) { }
