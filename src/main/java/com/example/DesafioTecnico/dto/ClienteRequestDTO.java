package com.example.DesafioTecnico.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data                      
@NoArgsConstructor          
@AllArgsConstructor   

public class ClienteRequestDTO {

    @NotBlank(message = "Nome é obrigatório")
    @Size(max = 100, message = "Nome deve ter no máximo 100 caracteres")
    private String nome;

    @NotBlank(message = "CPF é obrigatório")
    @Pattern(regexp = "\\d{11}", message = "CPF deve conter 11 dígitos numéricos")
    private String cpf;

    @Pattern(regexp = "\\d{10,11}", message = "Telefone deve conter 10 ou 11 dígitos numéricos")
    private String telefone;

    @Email(message = "Email inválido")
    private String email;
}
