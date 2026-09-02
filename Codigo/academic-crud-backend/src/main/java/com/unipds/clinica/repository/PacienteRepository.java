package com.unipds.clinica.repository;

import com.unipds.clinica.model.Paciente;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface PacienteRepository extends JpaRepository<Paciente, Integer> {
    List<Paciente> findByNomeContainingIgnoreCase(String nome);
    Optional<Paciente> findByCpf(String cpf);
    boolean existsByCpf(String cpf);
    boolean existsByCpfAndIdNot(String cpf, Integer id);
    void deleteByCpf(String cpf);
}
