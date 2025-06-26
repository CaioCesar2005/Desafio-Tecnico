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

    // Cadastra um novo cliente, validando se o CPF já existe
    @Transactional
    public Cliente cadastrarCliente(ClienteRequestDTO dto) {
        if (clienteRepository.existsByCpf(dto.getCpf())) {
            // Regra: não permitir CPF duplicado
            throw new RegraNegocioException("Já existe um cliente com o CPF informado.");
        }
        Cliente cliente = ClienteMapper.toEntity(dto);
        return clienteRepository.save(cliente);
    }

    // Atualiza dados do cliente pelo id, lança exceção se não encontrado
    @Transactional
    public Cliente atualizarCliente(Long id, ClienteRequestDTO dto) {
        Cliente cliente = clienteRepository.findById(id)
                .orElseThrow(() -> new ClienteNotFoundException("Cliente não encontrado."));

        ClienteMapper.copyToEntity(dto, cliente);
        return clienteRepository.save(cliente);
    }

    // Lista todos os clientes cadastrados (somente leitura)
    @Transactional(readOnly = true)
    public List<Cliente> listarClientes() {
        return clienteRepository.findAll();
    }

    // Exclui cliente e cancela todas as contas associadas a ele
    @Transactional
    public void excluirCliente(Long id) {
        Cliente cliente = clienteRepository.findById(id)
            .orElseThrow(() -> new ClienteNotFoundException("Cliente não encontrado."));

        // Cancela contas antes de excluir o cliente
        contaService.cancelarContasDoClienteExcluido(cliente);
        clienteRepository.delete(cliente);
    }
}