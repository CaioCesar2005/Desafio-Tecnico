package com.example.DesafioTecnico.service;

import com.example.DesafioTecnico.dto.ContaRequestDTO;
import com.example.DesafioTecnico.exception.ClienteNotFoundException;
import com.example.DesafioTecnico.exception.ContaNotFoundException;
import com.example.DesafioTecnico.exception.RegraNegocioException;
import com.example.DesafioTecnico.mapper.ContaMapper;
import com.example.DesafioTecnico.model.Cliente;
import com.example.DesafioTecnico.model.Conta;
import com.example.DesafioTecnico.model.SituacaoConta;
import com.example.DesafioTecnico.repository.ClienteRepository;
import com.example.DesafioTecnico.repository.ContaRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.MockedStatic;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.Optional;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@DisplayName("ContaService – Testes Unitários")
class ContaServiceTest {

    private final ContaRepository contaRepository = mock(ContaRepository.class);
    private final ClienteRepository clienteRepository = mock(ClienteRepository.class);
    private final ContaService service = new ContaService(contaRepository, clienteRepository);

    private ContaRequestDTO dto(BigDecimal valor, SituacaoConta situacao) {
        ContaRequestDTO dto = new ContaRequestDTO();
        dto.setReferencia("REF-01");
        dto.setValor(valor);
        dto.setSituacao(situacao);
        return dto;
    }

    @Nested
    @DisplayName("Cadastrar conta")
    class Cadastrar {
     // Testa criação de conta

        @Test
        @DisplayName("Deve cadastrar conta com sucesso")
        void deveCadastrarConta() {
            Cliente cliente = new Cliente(1L, "Ana", "111", "", "");
            when(clienteRepository.findById(1L)).thenReturn(Optional.of(cliente));

            ContaRequestDTO dto = dto(BigDecimal.valueOf(100), SituacaoConta.PENDENTE);

            try (MockedStatic<ContaMapper> mk = Mockito.mockStatic(ContaMapper.class)) {
                Conta contaEsperada = new Conta("REF-01", dto.getValor(), dto.getSituacao(), cliente);
                mk.when(() -> ContaMapper.toEntity(dto, cliente)).thenReturn(contaEsperada);
                when(contaRepository.save(contaEsperada)).thenReturn(contaEsperada);

                Conta salvo = service.cadastrarConta(1L, dto);

                assertThat(salvo).isSameAs(contaEsperada);
                verify(contaRepository).save(contaEsperada);
            }
        }

        // Testa regra de nao criar conta com valor negativo
        @Test
        @DisplayName("Não deve cadastrar com valor negativo")
        void naoDeveCadastrarValorNegativo() {
            ContaRequestDTO dto = dto(BigDecimal.valueOf(-10), SituacaoConta.PENDENTE);

            assertThatThrownBy(() -> service.cadastrarConta(1L, dto))
                    .isInstanceOf(RegraNegocioException.class)
                    .hasMessageContaining("menor que zero");
        }

         // Testa regra de nao criar conta CANCELADA
        @Test
        @DisplayName("Não deve cadastrar conta já CANCELADA")
        void naoDeveCadastrarContaCancelada() {
            ContaRequestDTO dto = dto(BigDecimal.TEN, SituacaoConta.CANCELADA);

            assertThatThrownBy(() -> service.cadastrarConta(1L, dto))
                    .isInstanceOf(RegraNegocioException.class)
                    .hasMessageContaining("CANCELADA");
        }

        // Testa exceção de cliente nao existe
        @Test
        @DisplayName("Deve lançar exceção se cliente não existir")
        void deveLancarSeClienteNaoExistir() {
            when(clienteRepository.findById(99L)).thenReturn(Optional.empty());

            assertThatThrownBy(() -> service.cadastrarConta(99L, dto(BigDecimal.TEN, SituacaoConta.PENDENTE)))
                    .isInstanceOf(ClienteNotFoundException.class);
        }
    }

    @Nested
    @DisplayName("Cancelar conta logicamente")
    class Cancelar {

        // Testa cancelar conta
        @Test
        @DisplayName("Deve cancelar conta pendente com sucesso")
        void deveCancelarContaPendente() {
            Conta conta = new Conta("R", BigDecimal.TEN, SituacaoConta.PENDENTE, new Cliente());
            conta.setId(1L);
            when(contaRepository.findById(1L)).thenReturn(Optional.of(conta));
            when(contaRepository.save(conta)).thenReturn(conta);

            Conta cancelada = service.cancelarContaLogicamente(1L);

            assertThat(cancelada.getSituacao()).isEqualTo(SituacaoConta.CANCELADA);
        }

        // Testa regra de nao cancelar conta ja cancelada
        @Test
        @DisplayName("Não deve cancelar se conta já estiver CANCELADA")
        void naoDeveCancelarContaJaCancelada() {
            Conta conta = new Conta("R", BigDecimal.TEN, SituacaoConta.CANCELADA, new Cliente());
            when(contaRepository.findById(1L)).thenReturn(Optional.of(conta));

            assertThatThrownBy(() -> service.cancelarContaLogicamente(1L))
                    .isInstanceOf(RegraNegocioException.class);
        }

        // Testa exceção de conta não existe
        @Test
        @DisplayName("Deve lançar exceção se conta não existir")
        void deveLancarSeContaNaoExistir() {
            when(contaRepository.findById(1L)).thenReturn(Optional.empty());

            assertThatThrownBy(() -> service.cancelarContaLogicamente(1L))
                    .isInstanceOf(ContaNotFoundException.class);
        }
    }
 
    @Nested
    @DisplayName("Atualizar conta")
    class Atualizar {
    // Testa atualização de conta

        @Test
        @DisplayName("Deve atualizar valor com sucesso")
        void deveAtualizarValor() {
            Conta conta = new Conta("R", BigDecimal.TEN, SituacaoConta.PENDENTE, new Cliente());
            conta.setId(1L);
            when(contaRepository.findById(1L)).thenReturn(Optional.of(conta));
            when(contaRepository.save(conta)).thenReturn(conta);

            ContaRequestDTO dto = dto(BigDecimal.valueOf(50), SituacaoConta.PENDENTE);

            Conta atualizado = service.atualizarConta(1L, dto);

            assertThat(atualizado.getValor()).isEqualByComparingTo("50");
        }

        // Testa não atualizar conta co valor negativo
        @Test
        @DisplayName("Não deve aceitar valor negativo")
        void naoDeveAtualizarValorNegativo() {
            Conta conta = new Conta("R", BigDecimal.TEN, SituacaoConta.PENDENTE, new Cliente());
            when(contaRepository.findById(1L)).thenReturn(Optional.of(conta));

            assertThatThrownBy(() -> service.atualizarConta(1L, dto(BigDecimal.valueOf(-5), SituacaoConta.PENDENTE)))
                    .isInstanceOf(RegraNegocioException.class);
        }
        
        // Testa não atualizar conta para cancelada
        @Test
        @DisplayName("Não deve atualizar situação para CANCELADA por este método")
        void naoDeveAtualizarSituacaoParaCancelada() {
            Conta conta = new Conta("R", BigDecimal.TEN, SituacaoConta.PENDENTE, new Cliente());
            when(contaRepository.findById(1L)).thenReturn(Optional.of(conta));

            ContaRequestDTO dto = dto(BigDecimal.TEN, SituacaoConta.CANCELADA);

            assertThatThrownBy(() -> service.atualizarConta(1L, dto))
                    .isInstanceOf(RegraNegocioException.class);
        }

        // Testa exceção de conta não existe
        @Test
        @DisplayName("Deve lançar exceção se conta não existir")
        void deveLancarSeContaNaoExistirAoAtualizar() {
            when(contaRepository.findById(1L)).thenReturn(Optional.empty());

            assertThatThrownBy(() -> service.atualizarConta(1L, dto(BigDecimal.TEN, SituacaoConta.PENDENTE)))
                    .isInstanceOf(ContaNotFoundException.class);
        }
    }
}
