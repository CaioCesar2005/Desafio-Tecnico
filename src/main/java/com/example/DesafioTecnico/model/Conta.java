package com.example.DesafioTecnico.model;

import jakarta.persistence.*;
import java.math.BigDecimal;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "contas")
@Getter
@Setter

public class Conta {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Pattern(regexp = "\\d{2}-\\d{4}", message = "Formato inválido. Use MM-AAAA.")
    @NotBlank(message = "A referência é obrigatória.")
    @Column(nullable = false)
    private String referencia; 

    @NotNull(message = "O valor é obrigatório.")
    @DecimalMin(value = "0.0", inclusive = true, message = "O valor não pode ser menor que 0.")
    @Column(nullable = false)
    private BigDecimal valor;

    @NotNull(message = "A situação é obrigatória.")
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private SituacaoConta situacao;

    @NotNull(message = "Cliente associado é obrigatório.")
    @ManyToOne(optional = false)
    @JoinColumn(name = "cliente_id")
    private Cliente cliente;


     public Conta() {}


    public Conta(String referencia, BigDecimal valor, SituacaoConta situacao, Cliente cliente) {
        this.referencia = referencia;
        this.valor = valor;
        this.situacao = situacao;
        this.cliente = cliente;
    }

}
