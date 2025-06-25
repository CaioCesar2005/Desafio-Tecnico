package com.example.DesafioTecnico.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
@AllArgsConstructor
public class FieldValidationError {
    private String campo;
    private String mensagem;
}
