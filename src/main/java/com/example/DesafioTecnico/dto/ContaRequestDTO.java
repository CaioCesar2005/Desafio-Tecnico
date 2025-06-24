package com.example.DesafioTecnico.dto;

import java.math.BigDecimal;
import com.example.DesafioTecnico.model.SituacaoConta;

public record ContaRequestDTO(
    String referencia,
    BigDecimal valor,
    SituacaoConta situacao
) {}
