package com.example.DesafioTecnico.dto;

import java.math.BigDecimal;
import com.example.DesafioTecnico.model.SituacaoConta;

public record ContaResponseDTO(
    Long id,
    String referencia,
    BigDecimal valor,
    SituacaoConta situacao,
    Long clienteId  
) {}
