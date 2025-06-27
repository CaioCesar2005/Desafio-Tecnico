package com.example.DesafioTecnico.dto;

import com.example.DesafioTecnico.model.SituacaoConta;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import io.swagger.v3.oas.annotations.media.Schema;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Dados para criação ou atualização de uma conta")
// DTO para receber dados de conta 
public class ContaRequestDTO {

    @NotBlank(message = "Referência é obrigatória") // Referência não pode ser vazia ou nula
    @Schema(description = "Referência da conta", example = "06-2024", required = true)
    private String referencia;

    @NotNull(message = "Valor é obrigatório") // Valor não pode ser nulo
    @DecimalMin(value = "0.0", inclusive = true, message = "Valor não pode ser negativo") // Valor mínimo é zero (não negativo)
    @Schema(description = "Valor da conta", example = "1500.00", required = true)
    private BigDecimal valor;

    @NotNull(message = "Situação é obrigatória") // Situação não pode ser nula
    @Schema(description = "Situação atual da conta", example = "PENDENTE", required = true)
    private SituacaoConta situacao;
}
