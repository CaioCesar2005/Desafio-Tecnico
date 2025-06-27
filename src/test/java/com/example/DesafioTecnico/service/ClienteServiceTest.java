package com.example.DesafioTecnico.service;

import com.example.DesafioTecnico.dto.ClienteRequestDTO;
import com.example.DesafioTecnico.exception.ClienteNotFoundException;
import com.example.DesafioTecnico.exception.RegraNegocioException;
import com.example.DesafioTecnico.mapper.ClienteMapper;
import com.example.DesafioTecnico.model.Cliente;
import com.example.DesafioTecnico.repository.ClienteRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.MockedStatic;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@DisplayName("ClienteService - Testes Unitários")
class ClienteServiceTest {

    private final ClienteRepository clienteRepository = mock(ClienteRepository.class);
    private final ContaService contaService = mock(ContaService.class);
    private final ClienteService service = new ClienteService(clienteRepository, contaService);

    private ClienteRequestDTO dto(String cpf) {
        ClienteRequestDTO dto = new ClienteRequestDTO();
        dto.setNome("Ana");
        dto.setCpf(cpf);
        dto.setTelefone("81-999");
        dto.setEmail("ana@ex.com");
        return dto;
    }

    @Nested
    @DisplayName("Cadastrar cliente")
    class Cadastrar {
     // Testa cadastro de cliente
        
        @Test
        @DisplayName("Deve cadastrar cliente com sucesso")
        void cadastraCliente() {
            when(clienteRepository.existsByCpf("111")).thenReturn(false);
            Cliente entity = new Cliente(1L, "Ana", "111", "", "");

            try (MockedStatic<ClienteMapper> mk = Mockito.mockStatic(ClienteMapper.class)) {
                mk.when(() -> ClienteMapper.toEntity(any())).thenReturn(entity);
                when(clienteRepository.save(entity)).thenReturn(entity);

                Cliente salvo = service.cadastrarCliente(dto("111"));

                assertThat(salvo).isSameAs(entity);
            }
        }

        // Testa não deve cadastrar
        @Test
        @DisplayName("Não deve cadastrar CPF duplicado")
        void cpfDuplicado() {
            when(clienteRepository.existsByCpf("222")).thenReturn(true);

            assertThatThrownBy(() -> service.cadastrarCliente(dto("222")))
                    .isInstanceOf(RegraNegocioException.class)
                    .hasMessageContaining("CPF");
        }
    }
    
    @Nested
    @DisplayName("Atualizar cliente")
    class Atualizar {
    // Testa atualização de cliente existente

        @Test
        @DisplayName("Deve atualizar cliente existente")
        void atualizaCliente() {
            Cliente existente = new Cliente(1L, "Bob", "123", "", "");
            when(clienteRepository.findById(1L)).thenReturn(Optional.of(existente));
            when(clienteRepository.save(existente)).thenReturn(existente);

            try (MockedStatic<ClienteMapper> mk = Mockito.mockStatic(ClienteMapper.class)) {
                mk.when(() -> ClienteMapper.copyToEntity(any(), eq(existente))).thenAnswer(inv -> null);

                Cliente atualizado = service.atualizarCliente(1L, dto("123"));

                assertThat(atualizado).isSameAs(existente);
            }
        }
        // Testa exceção clietne nao encontrado
        @Test
        @DisplayName("Deve lançar exceção se cliente não existir")
        void clienteNaoEncontrado() {
            when(clienteRepository.findById(99L)).thenReturn(Optional.empty());

            assertThatThrownBy(() -> service.atualizarCliente(99L, dto("123")))
                    .isInstanceOf(ClienteNotFoundException.class);
        }
    }

    @Nested
    @DisplayName("Listar clientes")
    class Listar {
    // Testa listagem de clientes
    
        @Test
        @DisplayName("Deve retornar lista de clientes")
        void listaClientes() {
            when(clienteRepository.findAll()).thenReturn(List.of(new Cliente(), new Cliente()));

            assertThat(service.listarClientes()).hasSize(2);
        }
    }
    
    @Nested
    @DisplayName("Excluir cliente")
    class Excluir {
    // Testa exclusão de cliente
        
        @Test
        @DisplayName("Deve excluir cliente e cancelar contas")
        void excluiCliente() {
            Cliente cli = new Cliente(1L, "Ana", "123", "", "");
            when(clienteRepository.findById(1L)).thenReturn(Optional.of(cli));

            service.excluirCliente(1L);

            verify(contaService).cancelarContasDoClienteExcluido(cli);
            verify(clienteRepository).delete(cli);
        }

        // Testa exceção cliente nao existe
        @Test
        @DisplayName("Deve lançar exceção se cliente não existir")
        void clienteNaoExisteAoExcluir() {
            when(clienteRepository.findById(1L)).thenReturn(Optional.empty());

            assertThatThrownBy(() -> service.excluirCliente(1L))
                    .isInstanceOf(ClienteNotFoundException.class);
        }
    }
}
