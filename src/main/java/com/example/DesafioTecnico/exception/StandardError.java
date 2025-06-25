package com.example.DesafioTecnico.exception;

import lombok.Getter;
import lombok.Setter;

import java.time.Instant;

@Getter @Setter
public class StandardError {
    private Instant instante;
    private Integer status;
    private String erro;
    private String mensagem;
    private String caminho;
}
