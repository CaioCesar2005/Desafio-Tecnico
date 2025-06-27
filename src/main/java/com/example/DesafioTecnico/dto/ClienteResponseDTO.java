package com.example.DesafioTecnico.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Dados retornados para um cliente")
// DTO para enviar dados de Cliente 
public class ClienteResponseDTO {

    @Schema(description = "ID do cliente", example = "1")
    private Long id;

    @Schema(description = "Nome do cliente", example = "Maria Silva")
    private String nome;

    @Schema(description = "CPF do cliente", example = "12345678901")
    private String cpf;

    @Schema(description = "Telefone do cliente", example = "81999998888")
    private String telefone;

    @Schema(description = "Email do cliente", example = "maria@email.com")
    private String email;
}
