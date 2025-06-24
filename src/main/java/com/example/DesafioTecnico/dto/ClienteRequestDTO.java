package com.example.DesafioTecnico.dto;

public record ClienteRequestDTO(
    String nome,
    String cpf,
    String telefone,
    String email
) {}
