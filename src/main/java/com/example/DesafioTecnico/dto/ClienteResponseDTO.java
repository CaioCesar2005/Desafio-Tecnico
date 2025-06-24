package com.example.DesafioTecnico.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor

public class ClienteResponseDTO {
    private Long   id;
    private String nome;
    private String cpf;
    private String telefone;
    private String email;
}
