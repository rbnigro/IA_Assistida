package com.unipds.clinica.service;

import com.unipds.clinica.dto.PacienteRequestDTO;
import com.unipds.clinica.dto.PacienteResponseDTO;
import com.unipds.clinica.dto.PacienteUpdateDTO;
import com.unipds.clinica.model.Paciente;
import com.unipds.clinica.repository.PacienteRepository;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class PacienteService {
    private final PacienteRepository repository;

    public PacienteService(PacienteRepository repository) { this.repository = repository; }

    @Transactional
    public PacienteResponseDTO criar(PacienteRequestDTO dto) {
        if (repository.existsByCpf(dto.cpf())) throw new CpfDuplicadoException(dto.cpf());
        try {
            return PacienteResponseDTO.from(repository.save(toEntity(dto)));
        } catch (DataIntegrityViolationException exception) {
            throw new CpfDuplicadoException(dto.cpf());
        }
    }

    @Transactional(readOnly = true)
    public List<PacienteResponseDTO> listar() {
        return repository.findAll().stream().map(PacienteResponseDTO::from).toList();
    }

    @Transactional(readOnly = true)
    public List<PacienteResponseDTO> buscarPorNome(String nome) {
        return repository.findByNomeContainingIgnoreCase(nome).stream().map(PacienteResponseDTO::from).toList();
    }

    @Transactional(readOnly = true)
    public PacienteResponseDTO buscarPorId(Integer id) { return toResponse(repository.findById(id).orElseThrow(() -> new PacienteNotFoundException(id))); }

    @Transactional(readOnly = true)
    public PacienteResponseDTO buscarPorCpf(String cpf) { return toResponse(repository.findByCpf(cpf).orElseThrow(() -> new PacienteNotFoundException(cpf))); }

    @Transactional
    public PacienteResponseDTO atualizar(Integer id, PacienteUpdateDTO dto) {
        Paciente paciente = repository.findById(id).orElseThrow(() -> new PacienteNotFoundException(id));
        if (repository.existsByCpfAndIdNot(dto.cpf(), id)) throw new CpfDuplicadoException(dto.cpf());
        copyFields(paciente, dto);
        try { return toResponse(repository.save(paciente)); }
        catch (DataIntegrityViolationException exception) { throw new CpfDuplicadoException(dto.cpf()); }
    }

    @Transactional
    public void excluirPorId(Integer id) {
        if (!repository.existsById(id)) throw new PacienteNotFoundException(id);
        repository.deleteById(id);
    }

    @Transactional
    public void excluirPorCpf(String cpf) {
        if (repository.findByCpf(cpf).isEmpty()) throw new PacienteNotFoundException(cpf);
        repository.deleteByCpf(cpf);
    }

    private PacienteResponseDTO toResponse(Paciente paciente) { return PacienteResponseDTO.from(paciente); }

    private Paciente toEntity(PacienteRequestDTO dto) {
        return new Paciente(dto.nome(), dto.cpf(), dto.dataNascimento(), dto.endereco(), dto.numero(), dto.complemento(),
                dto.cidade(), dto.estado(), dto.cep(), dto.telefone(), dto.email(), dto.genero(), dto.convenio(),
                dto.especialidade(), dto.tipagemSanguinea(), dto.fatorRh(), dto.alergias(), dto.usoContinuoMedicamentos(),
                dto.doencasPreexistentes(), dto.observacoes());
    }

    private void copyFields(Paciente p, PacienteUpdateDTO d) {
        p.setNome(d.nome()); p.setCpf(d.cpf()); p.setDataNascimento(d.dataNascimento()); p.setEndereco(d.endereco());
        p.setNumero(d.numero()); p.setComplemento(d.complemento()); p.setCidade(d.cidade()); p.setEstado(d.estado());
        p.setCep(d.cep()); p.setTelefone(d.telefone()); p.setEmail(d.email()); p.setGenero(d.genero());
        p.setConvenio(d.convenio()); p.setEspecialidade(d.especialidade()); p.setTipagemSanguinea(d.tipagemSanguinea());
        p.setFatorRh(d.fatorRh()); p.setAlergias(d.alergias()); p.setUsoContinuoMedicamentos(d.usoContinuoMedicamentos());
        p.setDoencasPreexistentes(d.doencasPreexistentes()); p.setObservacoes(d.observacoes());
    }
}
