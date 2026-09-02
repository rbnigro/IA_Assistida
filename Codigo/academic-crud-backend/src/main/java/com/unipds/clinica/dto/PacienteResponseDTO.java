package com.unipds.clinica.dto;

import com.unipds.clinica.model.Paciente;

import java.time.LocalDate;

public record PacienteResponseDTO(Integer id, String nome, String cpf, LocalDate dataNascimento,
        String endereco, Integer numero, String complemento, String cidade, String estado, String cep,
        String telefone, String email, String genero, String convenio, String especialidade,
        String tipagemSanguinea, String fatorRh, String alergias, String usoContinuoMedicamentos,
        String doencasPreexistentes, String observacoes) {
    public static PacienteResponseDTO from(Paciente paciente) {
        return new PacienteResponseDTO(paciente.getId(), paciente.getNome(), paciente.getCpf(), paciente.getDataNascimento(),
                paciente.getEndereco(), paciente.getNumero(), paciente.getComplemento(), paciente.getCidade(),
                paciente.getEstado(), paciente.getCep(), paciente.getTelefone(), paciente.getEmail(), paciente.getGenero(),
                paciente.getConvenio(), paciente.getEspecialidade(), paciente.getTipagemSanguinea(), paciente.getFatorRh(),
                paciente.getAlergias(), paciente.getUsoContinuoMedicamentos(), paciente.getDoencasPreexistentes(), paciente.getObservacoes());
    }
}
