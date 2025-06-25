package com.example.DesafioTecnico.exception;

public class ContaNotFoundException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    public ContaNotFoundException(Long id) {
        super("Conta com ID " + id + " não encontrada.");
    }

    public ContaNotFoundException(String mensagem) {
        super(mensagem);
    }
}
