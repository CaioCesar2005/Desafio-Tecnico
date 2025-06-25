package com.example.DesafioTecnico.exception;

public class RegraNegocioException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    public RegraNegocioException(String mensagem) {
        super(mensagem);
    }
}
