package com.example.DesafioTecnico.mapper;

import com.example.DesafioTecnico.dto.ContaRequestDTO;
import com.example.DesafioTecnico.dto.ContaResponseDTO;
import com.example.DesafioTecnico.model.Cliente;
import com.example.DesafioTecnico.model.Conta;

public class ContaMapper {

    public static ContaResponseDTO toDTO(Conta conta) {
        return new ContaResponseDTO(
            conta.getId(),
            conta.getReferencia(),
            conta.getValor(),
            conta.getSituacao(),
            conta.getCliente().getId()
        );
    }

    public static Conta toEntity(ContaRequestDTO dto, Cliente cliente) {
        Conta conta = new Conta();
        conta.setReferencia(dto.getReferencia());
        conta.setValor(dto.getValor());
        conta.setSituacao(dto.getSituacao());
        conta.setCliente(cliente);
        return conta;
    }

    public static void copyToEntity(ContaRequestDTO dto, Conta conta) {
        conta.setReferencia(dto.getReferencia());
        conta.setValor(dto.getValor());
        conta.setSituacao(dto.getSituacao());
    }
}
