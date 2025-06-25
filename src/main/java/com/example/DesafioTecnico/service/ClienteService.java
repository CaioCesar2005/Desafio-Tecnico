package com.example.DesafioTecnico.service;

import com.example.DesafioTecnico.dto.ClienteRequestDTO;
import com.example.DesafioTecnico.exception.ClienteNotFoundException;
import com.example.DesafioTecnico.exception.RegraNegocioException;
import com.example.DesafioTecnico.mapper.ClienteMapper;
import com.example.DesafioTecnico.model.Cliente;
import com.example.DesafioTecnico.repository.ClienteRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class ClienteService {

    private final ClienteRepository clienteRepository;
    private final ContaService contaService;

    public ClienteService(ClienteRepository clienteRepository,ContaService contaService) {
        this.clienteRepository = clienteRepository;
        this.contaService      = contaService;
    }

    @Transactional
    public Cliente cadastrarCliente(ClienteRequestDTO dto) {

        if (clienteRepository.existsByCpf(dto.getCpf())) {
            throw new RegraNegocioException("Já existe um cliente com o CPF informado.");
        }

        Cliente cliente = ClienteMapper.toEntity(dto);
        return clienteRepository.save(cliente);
    }

   
    @Transactional
    public Cliente atualizarCliente(Long id, ClienteRequestDTO dto) {

        Cliente cliente = clienteRepository.findById(id)
                .orElseThrow(() -> new ClienteNotFoundException("Cliente não encontrado."));

        ClienteMapper.copyToEntity(dto, cliente);
        return clienteRepository.save(cliente);
    }

    @Transactional(readOnly = true)
    public List<Cliente> listarClientes() {
        return clienteRepository.findAll();
    }

    @Transactional
    public void excluirCliente(Long id) {
        Cliente cliente = clienteRepository.findById(id)
            .orElseThrow(() -> new ClienteNotFoundException("Cliente não encontrado."));

        contaService.cancelarContasDoClienteExcluido(cliente);
        clienteRepository.delete(cliente);
    }
}