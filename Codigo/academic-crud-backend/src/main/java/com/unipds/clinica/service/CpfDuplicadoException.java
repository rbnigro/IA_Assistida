package com.unipds.clinica.service;

public class CpfDuplicadoException extends RuntimeException {
    public CpfDuplicadoException(String cpf) { super("CPF ja cadastrado: " + cpf); }
}
