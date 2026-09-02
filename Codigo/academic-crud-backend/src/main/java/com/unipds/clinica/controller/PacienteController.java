package com.unipds.clinica.controller;

import com.unipds.clinica.dto.PacienteRequestDTO;
import com.unipds.clinica.dto.PacienteResponseDTO;
import com.unipds.clinica.dto.PacienteUpdateDTO;
import com.unipds.clinica.service.PacienteService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/pacientes")
public class PacienteController {
    private final PacienteService service;

    public PacienteController(PacienteService service) { this.service = service; }

    @GetMapping("/health")
    public ResponseEntity<String> health() { return ResponseEntity.ok("UP"); }

    @PostMapping
    public ResponseEntity<PacienteResponseDTO> criar(@Valid @RequestBody PacienteRequestDTO dto) {
        PacienteResponseDTO response = service.criar(dto);
        URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(response.id()).toUri();
        return ResponseEntity.created(location).body(response);
    }

    @GetMapping
    public List<PacienteResponseDTO> listar() { return service.listar(); }

    @GetMapping("/buscar-nome")
    public List<PacienteResponseDTO> buscarPorNome(@RequestParam String nome) { return service.buscarPorNome(nome); }

    @GetMapping("/{id}")
    public PacienteResponseDTO buscarPorId(@PathVariable Integer id) { return service.buscarPorId(id); }

    @GetMapping("/cpf/{cpf}")
    public PacienteResponseDTO buscarPorCpf(@PathVariable String cpf) { return service.buscarPorCpf(cpf); }

    @PutMapping("/{id}")
    public PacienteResponseDTO atualizar(@PathVariable Integer id, @Valid @RequestBody PacienteUpdateDTO dto) { return service.atualizar(id, dto); }

    @DeleteMapping("/cpf/{cpf}")
    public ResponseEntity<Void> excluirPorCpf(@PathVariable String cpf) { service.excluirPorCpf(cpf); return ResponseEntity.noContent().build(); }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> excluirPorId(@PathVariable Integer id) { service.excluirPorId(id); return ResponseEntity.noContent().build(); }
}
