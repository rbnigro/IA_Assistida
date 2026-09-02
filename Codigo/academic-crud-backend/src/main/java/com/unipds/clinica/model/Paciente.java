package com.unipds.clinica.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import java.time.LocalDate;

@Entity
@Table(name = "pacientes")
public class Paciente {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column(name = "nome", nullable = false, length = 50) private String nome;
    @Column(name = "cpf", nullable = false, unique = true, length = 11) private String cpf;
    @Column(name = "data_nascimento", nullable = false) private LocalDate dataNascimento;
    @Column(name = "endereco", length = 50) private String endereco;
    @Column(name = "numero") private Integer numero;
    @Column(name = "complemento", length = 5) private String complemento;
    @Column(name = "cidade", length = 30) private String cidade;
    @Column(name = "estado", length = 2) private String estado;
    @Column(name = "cep", length = 8) private String cep;
    @Column(name = "telefone", length = 11) private String telefone;
    @Column(name = "email", length = 30) private String email;
    @Column(name = "genero", length = 1) private String genero;
    @Column(name = "convenio", length = 12) private String convenio;
    @Column(name = "especialidade", length = 30) private String especialidade;
    @Column(name = "tipagem_sanguinea", length = 2) private String tipagemSanguinea;
    @Column(name = "fator_rh", length = 1) private String fatorRh;
    @Column(name = "alergias", columnDefinition = "TEXT") private String alergias;
    @Column(name = "uso_continuo_medicamentos", columnDefinition = "TEXT") private String usoContinuoMedicamentos;
    @Column(name = "doencas_preexistentes", columnDefinition = "TEXT") private String doencasPreexistentes;
    @Column(name = "observacoes", columnDefinition = "TEXT") private String observacoes;

    protected Paciente() { }

    public Paciente(String nome, String cpf, LocalDate dataNascimento, String endereco, Integer numero,
            String complemento, String cidade, String estado, String cep, String telefone, String email,
            String genero, String convenio, String especialidade, String tipagemSanguinea, String fatorRh,
            String alergias, String usoContinuoMedicamentos, String doencasPreexistentes, String observacoes) {
        this.nome = nome; this.cpf = cpf; this.dataNascimento = dataNascimento; this.endereco = endereco;
        this.numero = numero; this.complemento = complemento; this.cidade = cidade; this.estado = estado;
        this.cep = cep; this.telefone = telefone; this.email = email; this.genero = genero;
        this.convenio = convenio; this.especialidade = especialidade; this.tipagemSanguinea = tipagemSanguinea;
        this.fatorRh = fatorRh; this.alergias = alergias; this.usoContinuoMedicamentos = usoContinuoMedicamentos;
        this.doencasPreexistentes = doencasPreexistentes; this.observacoes = observacoes;
    }

    public Integer getId() { return id; }
    public String getNome() { return nome; } public void setNome(String value) { nome = value; }
    public String getCpf() { return cpf; } public void setCpf(String value) { cpf = value; }
    public LocalDate getDataNascimento() { return dataNascimento; } public void setDataNascimento(LocalDate value) { dataNascimento = value; }
    public String getEndereco() { return endereco; } public void setEndereco(String value) { endereco = value; }
    public Integer getNumero() { return numero; } public void setNumero(Integer value) { numero = value; }
    public String getComplemento() { return complemento; } public void setComplemento(String value) { complemento = value; }
    public String getCidade() { return cidade; } public void setCidade(String value) { cidade = value; }
    public String getEstado() { return estado; } public void setEstado(String value) { estado = value; }
    public String getCep() { return cep; } public void setCep(String value) { cep = value; }
    public String getTelefone() { return telefone; } public void setTelefone(String value) { telefone = value; }
    public String getEmail() { return email; } public void setEmail(String value) { email = value; }
    public String getGenero() { return genero; } public void setGenero(String value) { genero = value; }
    public String getConvenio() { return convenio; } public void setConvenio(String value) { convenio = value; }
    public String getEspecialidade() { return especialidade; } public void setEspecialidade(String value) { especialidade = value; }
    public String getTipagemSanguinea() { return tipagemSanguinea; } public void setTipagemSanguinea(String value) { tipagemSanguinea = value; }
    public String getFatorRh() { return fatorRh; } public void setFatorRh(String value) { fatorRh = value; }
    public String getAlergias() { return alergias; } public void setAlergias(String value) { alergias = value; }
    public String getUsoContinuoMedicamentos() { return usoContinuoMedicamentos; } public void setUsoContinuoMedicamentos(String value) { usoContinuoMedicamentos = value; }
    public String getDoencasPreexistentes() { return doencasPreexistentes; } public void setDoencasPreexistentes(String value) { doencasPreexistentes = value; }
    public String getObservacoes() { return observacoes; } public void setObservacoes(String value) { observacoes = value; }
}
