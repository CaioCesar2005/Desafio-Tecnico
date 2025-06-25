package com.example.DesafioTecnico.exception;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter @Setter
public class ValidationError extends StandardError {
    private List<FieldValidationError> erros = new ArrayList<>();

    public void adicionarErroCampo(String campo, String mensagem) {
        this.erros.add(new FieldValidationError(campo, mensagem));
    }
}
