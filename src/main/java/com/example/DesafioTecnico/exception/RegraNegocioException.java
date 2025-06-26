package com.example.DesafioTecnico.exception;

// Exceção genérica para violação de regras de negócio
public class RegraNegocioException extends RuntimeException {
    public RegraNegocioException(String mensagem) {
        super(mensagem);
    }
}