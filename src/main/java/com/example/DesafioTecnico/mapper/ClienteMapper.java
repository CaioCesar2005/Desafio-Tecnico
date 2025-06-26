package com.example.DesafioTecnico.mapper;

import com.example.DesafioTecnico.dto.ClienteRequestDTO;
import com.example.DesafioTecnico.dto.ClienteResponseDTO;
import com.example.DesafioTecnico.model.Cliente;

public class ClienteMapper {

    // Converte entidade Cliente para DTO de resposta (ClienteResponseDTO)
    public static ClienteResponseDTO toDTO(Cliente cliente) {
        return new ClienteResponseDTO(
            cliente.getId(),
            cliente.getNome(),
            cliente.getCpf(),
            cliente.getTelefone(),
            cliente.getEmail()
        );
    }

    // Converte DTO de requisição para entidade Cliente (novo cliente)
    public static Cliente toEntity(ClienteRequestDTO dto) {
        Cliente cliente = new Cliente();
        cliente.setNome(dto.getNome());
        cliente.setCpf(dto.getCpf());
        cliente.setTelefone(dto.getTelefone());
        cliente.setEmail(dto.getEmail());
        return cliente;
    }

    // Copia dados do DTO para uma entidade Cliente já existente (atualização)
    public static void copyToEntity(ClienteRequestDTO dto, Cliente cliente) {
        cliente.setNome(dto.getNome());
        cliente.setTelefone(dto.getTelefone());
        cliente.setEmail(dto.getEmail());
    }
}
