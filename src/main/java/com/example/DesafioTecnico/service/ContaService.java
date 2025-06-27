package com.example.DesafioTecnico.service;

import com.example.DesafioTecnico.dto.ContaRequestDTO;
import com.example.DesafioTecnico.mapper.ContaMapper;
import com.example.DesafioTecnico.model.Cliente;
import com.example.DesafioTecnico.model.Conta;
import com.example.DesafioTecnico.model.SituacaoConta;
import com.example.DesafioTecnico.repository.ClienteRepository;
import com.example.DesafioTecnico.repository.ContaRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.example.DesafioTecnico.exception.ClienteNotFoundException;
import com.example.DesafioTecnico.exception.ContaNotFoundException;
import com.example.DesafioTecnico.exception.RegraNegocioException;

import java.math.BigDecimal;
import java.util.List;

@Service
public class ContaService {

    private final ContaRepository contaRepository;
    private final ClienteRepository clienteRepository;

    public ContaService(ContaRepository contaRepository,ClienteRepository clienteRepository) {
        this.contaRepository = contaRepository;
        this.clienteRepository = clienteRepository;
    }

    // Cadastra nova conta para um cliente, validando valor e situação inicial
    @Transactional
    public Conta cadastrarConta(Long idCliente, ContaRequestDTO dto) {
        BigDecimal valor = dto.getValor();
        if (valor == null || valor.compareTo(BigDecimal.ZERO) < 0) {
             // Regra: valor não pode ser negativo ou nulo
             throw new RegraNegocioException("O valor da conta não pode ser menor que zero.");
        }

        if (dto.getSituacao() == SituacaoConta.CANCELADA) {
             // Regra: não pode criar conta já cancelada
             throw new RegraNegocioException("Não é permitido criar uma conta já com situação CANCELADA.");
        }

        Cliente cliente = clienteRepository.findById(idCliente)
            .orElseThrow(() -> new ClienteNotFoundException("Cliente não encontrado."));

        Conta conta = ContaMapper.toEntity(dto, cliente);

        return contaRepository.save(conta);
    }

    // Cancela uma conta alterando sua situação para CANCELADA
    @Transactional
    public Conta cancelarContaLogicamente(Long idConta) {
        Conta conta = contaRepository.findById(idConta)
            .orElseThrow(() -> new ContaNotFoundException("Conta não encontrada."));

        if (conta.getSituacao() == SituacaoConta.CANCELADA) {
            // Regra: não cancelar conta já cancelada
            throw new RegraNegocioException("Conta já está cancelada.");
        }

        conta.setSituacao(SituacaoConta.CANCELADA);
        return contaRepository.save(conta);
    }

    // Atualiza dados da conta, com validações específicas para valor e situação
    @Transactional
    public Conta atualizarConta(Long idConta, ContaRequestDTO dto) {
        Conta conta = contaRepository.findById(idConta)
            .orElseThrow(() -> new ContaNotFoundException("Conta não encontrada."));

        if (dto.getValor() != null && dto.getValor().compareTo(BigDecimal.ZERO) < 0) {
            // Regra: valor não pode ser negativo
            throw new RegraNegocioException("O valor da conta não pode ser menor que zero.");
        }

        if (dto.getSituacao() == SituacaoConta.CANCELADA && conta.getSituacao() != SituacaoConta.CANCELADA) {
            // Regra: não pode atualizar a situação para CANCELADA por este método
            throw new RegraNegocioException("A conta não pode ser atualizada para CANCELADA por este método.");
        }

        ContaMapper.copyToEntity(dto, conta);
        return contaRepository.save(conta);
    }

    // Confere cada conta do cliente excluido, se nao estiver cancelada, chama o metodo de cancelsamento
    @Transactional
    public void cancelarContasDoClienteExcluido(Cliente cliente) {
        List<Conta> contas = contaRepository.findByCliente(cliente);
        contas.stream()
          .filter(c -> c.getSituacao() != SituacaoConta.CANCELADA)
          .forEach(c -> cancelarContaLogicamente(c.getId()));
}

    // Lista todas as contas associadas a um cliente
    @Transactional(readOnly = true)
    public List<Conta> listarContasDoCliente(Long clienteId) {
        Cliente cliente = clienteRepository.findById(clienteId)
            .orElseThrow(() -> new IllegalArgumentException("Cliente não encontrado"));
        return contaRepository.findByCliente(cliente);
    }
}
