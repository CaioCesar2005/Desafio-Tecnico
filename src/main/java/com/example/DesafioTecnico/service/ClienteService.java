package com.example.DesafioTecnico.service;

import com.example.DesafioTecnico.model.Cliente;
import com.example.DesafioTecnico.repository.ClienteRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class ClienteService {

    private final ClienteRepository clienteRepository;
    private final ContaService contaService;
    

    public ClienteService(ClienteRepository clienteRepository, ContaService contaService) {
        this.clienteRepository = clienteRepository;
        this.contaService = contaService;    
    }

    @Transactional
    public Cliente cadastrarCliente(Cliente cliente) {
        if (clienteRepository.existsByCpf(cliente.getCpf())) {
            throw new IllegalArgumentException("Já existe um cliente com o CPF informado.");
        }

        return clienteRepository.save(cliente);
    }

    @Transactional
    public Cliente atualizarCliente(Long id, Cliente clienteAtualizado) {
        Cliente cliente = clienteRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Cliente não encontrado"));

        cliente.setNome(clienteAtualizado.getNome());
        cliente.setTelefone(clienteAtualizado.getTelefone());
        cliente.setEmail(clienteAtualizado.getEmail());

        return clienteRepository.save(cliente);
    }

    @Transactional(readOnly = true)
    public List<Cliente> listarClientes() {
        return clienteRepository.findAll();
    }

    @Transactional
    public void excluirCliente(Long id) {
        Cliente cliente = clienteRepository.findById(id)
            .orElseThrow(() -> new IllegalArgumentException("Cliente não encontrado"));

        contaService.cancelarContasDoClienteExcluido(cliente);
        clienteRepository.delete(cliente);
    }
}