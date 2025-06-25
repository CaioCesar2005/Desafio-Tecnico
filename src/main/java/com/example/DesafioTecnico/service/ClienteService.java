package com.example.DesafioTecnico.service;

import com.example.DesafioTecnico.dto.ClienteRequestDTO;
import com.example.DesafioTecnico.dto.ClienteResponseDTO;
import com.example.DesafioTecnico.exception.ClienteNotFoundException;
import com.example.DesafioTecnico.exception.RegraNegocioException;
import com.example.DesafioTecnico.mapper.ClienteMapper;
import com.example.DesafioTecnico.model.Cliente;
import com.example.DesafioTecnico.repository.ClienteRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ClienteService {

    private final ClienteRepository clienteRepository;
    private final ContaService contaService;
    

    public ClienteService(ClienteRepository clienteRepository, ContaService contaService) {
        this.clienteRepository = clienteRepository;
        this.contaService = contaService;    
    }

    @Transactional
    public ClienteResponseDTO cadastrarCliente(ClienteRequestDTO dto) {

        if (clienteRepository.existsByCpf(dto.getCpf())) {
            throw new RegraNegocioException("Já existe um cliente com o CPF informado.");
        }

        Cliente cliente = ClienteMapper.toEntity(dto);
        Cliente salvo   = clienteRepository.save(cliente);

        return ClienteMapper.toDTO(salvo);
    }

    @Transactional
    public ClienteResponseDTO atualizarCliente(Long id,ClienteRequestDTO dto) {

        Cliente cliente = clienteRepository.findById(id)
               .orElseThrow(() -> new ClienteNotFoundException("Cliente não encontrado."));

        ClienteMapper.copyToEntity(dto, cliente);
        Cliente atualizado = clienteRepository.save(cliente);

        return ClienteMapper.toDTO(atualizado);
    }

    @Transactional(readOnly = true)
    public List<ClienteResponseDTO> listarClientes() {
        return clienteRepository.findAll()
                .stream()
                .map(ClienteMapper::toDTO)
                .collect(Collectors.toList());
    }

    @Transactional
    public void excluirCliente(Long id) {
        Cliente cliente = clienteRepository.findById(id)
            .orElseThrow(() -> new ClienteNotFoundException("Cliente não encontrado."));

        contaService.cancelarContasDoClienteExcluido(cliente);
        clienteRepository.delete(cliente);
    }
}