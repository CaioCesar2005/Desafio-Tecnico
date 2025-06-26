package com.example.DesafioTecnico.exception;

// Exceções personalizadas lançadas quando um cliente não é encontrado no sistema
public class ClienteNotFoundException extends RuntimeException {

    public ClienteNotFoundException(Long id) {
        super("Cliente com ID " + id + " não encontrado.");
    }

    public ClienteNotFoundException(String mensagem) {
        super(mensagem);
    }
}
