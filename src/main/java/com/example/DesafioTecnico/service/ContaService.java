package com.example.DesafioTecnico.service;

import com.example.DesafioTecnico.model.Cliente;
import com.example.DesafioTecnico.model.Conta;
import com.example.DesafioTecnico.model.SituacaoConta;
import com.example.DesafioTecnico.repository.ClienteRepository;
import com.example.DesafioTecnico.repository.ContaRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.math.BigDecimal;

@Service
public class ContaService {

    private final ContaRepository contaRepository;
    private final ClienteRepository clienteRepository;

    public ContaService(ContaRepository contaRepository, ClienteRepository clienteRepository) {
        this.contaRepository = contaRepository;
        this.clienteRepository = clienteRepository;
    }

    @Transactional
    public Conta cadastrarConta(Long idCliente, Conta conta) {
        if (conta.getValor() == null || conta.getValor().compareTo(BigDecimal.ZERO) < 0) {
            throw new IllegalArgumentException("O valor da conta não pode ser menor que zero.");
        }
 
        if (conta.getSituacao() == SituacaoConta.CANCELADA) {
            throw new IllegalArgumentException("Não é permitido criar uma conta já com situação CANCELADA.");
        }

        Cliente cliente = clienteRepository.findById(idCliente)
                .orElseThrow(() -> new IllegalArgumentException("Cliente não encontrado."));

        conta.setCliente(cliente);

        return contaRepository.save(conta);
    }

    @Transactional
    public Conta cancelarContaLogicamente(Long idConta) {
        Conta conta = contaRepository.findById(idConta)
        .orElseThrow(() -> new IllegalArgumentException("Conta não existe no sistema")); 
   
        if (conta.getSituacao() == SituacaoConta.CANCELADA) {
            throw new IllegalStateException("Conta ja esta cancelada");
        }

        conta.setSituacao(SituacaoConta.CANCELADA);
        return contaRepository.save(conta);
    }

    @Transactional
    public void cancelarContasDoClienteExcluido(Cliente cliente) {
        List<Conta> contas = contaRepository.findByCliente(cliente);

        for (Conta conta : contas) {
            cancelarContaLogicamente(conta.getId());
        }
    }
    @Transactional(readOnly = true)
    public List<Conta> listarContasDoCliente(Long clienteId) {
        Cliente cliente = clienteRepository.findById(clienteId)
            .orElseThrow(() -> new IllegalArgumentException("Cliente não encontrado"));
        return contaRepository.findByCliente(cliente);

    }
}
