package com.example.DesafioTecnico.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import io.swagger.v3.oas.annotations.media.Schema; 

@Data                      
@NoArgsConstructor          
@AllArgsConstructor   
@Schema(description = "Dados para criação ou atualização de uma conta")
public class ClienteRequestDTO {

    
    @NotBlank(message = "Nome é obrigatório")
    @Size(max = 100, message = "Nome deve ter no máximo 100 caracteres")
    @Schema(description = "Nome do cliente", example = "Maria Silva", required = true)
    private String nome;

    @NotBlank(message = "CPF é obrigatório")
    @Pattern(regexp = "\\d{11}", message = "CPF deve conter 11 dígitos numéricos")
    @Schema(description = "CPF do cliente (apenas números)", example = "12345678901", required = true)
    private String cpf;
    
    @Pattern(regexp = "\\d{10,11}", message = "Telefone deve conter 10 ou 11 dígitos numéricos")
    @Schema(description = "Telefone do cliente", example = "81999998888", required = false)
    private String telefone;

    @Email(message = "Email inválido")
    @Schema(description = "Email do cliente", example = "maria@email.com", required = false)
    private String email;
}
