package com.example.DesafioTecnico.exception;

// Exceções personalizadas lançadas quando uma conta não é encontrada no sistema
public class ContaNotFoundException extends RuntimeException {
 
    public ContaNotFoundException(Long id) {
        super("Conta com ID " + id + " não encontrada.");
    }

    public ContaNotFoundException(String mensagem) {
        super(mensagem);
    }
}
