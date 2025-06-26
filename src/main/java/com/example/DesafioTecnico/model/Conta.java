package com.example.DesafioTecnico.model;

import jakarta.persistence.*;
import java.math.BigDecimal;

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
    
    @Column(nullable = false)
    private String referencia; 

    @Column(nullable = false)
    private BigDecimal valor;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private SituacaoConta situacao;

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
