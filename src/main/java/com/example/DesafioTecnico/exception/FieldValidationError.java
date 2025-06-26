package com.example.DesafioTecnico.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

// Representa um erro específico de campo (usado em validações de DTOs)
@Getter @Setter
@AllArgsConstructor
public class FieldValidationError {
    private String campo;
    private String mensagem;
}