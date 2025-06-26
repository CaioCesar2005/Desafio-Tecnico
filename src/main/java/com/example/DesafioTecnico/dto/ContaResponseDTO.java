package com.example.DesafioTecnico.dto;

import com.example.DesafioTecnico.model.SituacaoConta;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Dados retornados para uma conta")
public class ContaResponseDTO {

    @Schema(description = "ID da conta", example = "1")
    private Long          id;

    @Schema(description = "Referência da conta", example = "Conta Salário")
    private String        referencia;

    @Schema(description = "Valor da conta", example = "1500.00")
    private BigDecimal    valor;

    @Schema(description = "Situação atual da conta", example = "PENDENTE")
    private SituacaoConta situacao;

    @Schema(description = "Cliente associado à conta")
    private Long          clienteId;   
}
