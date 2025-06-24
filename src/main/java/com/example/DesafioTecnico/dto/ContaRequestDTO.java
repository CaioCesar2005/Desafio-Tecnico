package com.example.DesafioTecnico.dto;

import com.example.DesafioTecnico.model.SituacaoConta;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor

public class ContaRequestDTO {
    private String        referencia;   
    private BigDecimal    valor;        
    private SituacaoConta situacao;     
}
