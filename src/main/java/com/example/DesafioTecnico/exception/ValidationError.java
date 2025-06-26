package com.example.DesafioTecnico.exception;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

// Extensão de StandardError que também carrega uma lista de erros de campo
@Getter @Setter
public class ValidationError extends StandardError {
    private List<FieldValidationError> erros = new ArrayList<>();

    // Adiciona um novo erro de campo à lista
    public void adicionarErroCampo(String campo, String mensagem) {
        this.erros.add(new FieldValidationError(campo, mensagem));
    }
}