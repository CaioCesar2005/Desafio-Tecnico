package com.example.DesafioTecnico.dto;

import com.example.DesafioTecnico.model.SituacaoConta;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor

public class ContaRequestDTO {

    @NotBlank(message = "Referência é obrigatória")
    private String referencia;

    @NotNull(message = "Valor é obrigatório")
    @DecimalMin(value = "0.0", inclusive = true, message = "Valor não pode ser negativo")
    private BigDecimal valor;

    @NotNull(message = "Situação é obrigatória")
    private SituacaoConta situacao;
}
