package com.example.DesafioTecnico.controller;

import com.example.DesafioTecnico.dto.ClienteRequestDTO;
import com.example.DesafioTecnico.model.Cliente;
import com.example.DesafioTecnico.service.ClienteService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.hamcrest.Matchers.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


@WebMvcTest(ClienteController.class)
@DisplayName("ClienteController - Testes de Integração")
class ClienteControllerIntegrationTest
 {

    @Autowired
    private MockMvc mvc;

    @Autowired
    private ObjectMapper mapper;

    @MockBean
    private ClienteService clienteService;

    private Cliente clienteStub() {
        return new Cliente(1L, "Ana", "11111111111", "8199998888", "ana@ex.com");
    }

    private ClienteRequestDTO dtoStub() {
        ClienteRequestDTO dto = new ClienteRequestDTO();
        dto.setNome("Ana");
        dto.setCpf("11111111111");
        dto.setTelefone("8199998888");
        dto.setEmail("ana@ex.com");
        return dto;
    }

    @Nested
    @DisplayName("POST /clientes")
    class CriarCliente {

        @Test
        @DisplayName("201 - deve cadastrar cliente e retornar DTO")
        void criarComSucesso() throws Exception {
            when(clienteService.cadastrarCliente(any())).thenReturn(clienteStub());

            mvc.perform(post("/clientes")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(mapper.writeValueAsString(dtoStub())))
               .andExpect(status().isCreated())
               .andExpect(jsonPath("$.id").value(1))
               .andExpect(jsonPath("$.nome").value("Ana"))
               .andExpect(jsonPath("$.cpf").value("11111111111"));
        }
    }

    @Nested
    @DisplayName("PUT /clientes/{id}")
    class AtualizarCliente {

        @Test
        @DisplayName("200 - deve atualizar cliente existente")
        void atualizarComSucesso() throws Exception {
            when(clienteService.atualizarCliente(eq(1L), any())).thenReturn(clienteStub());

            mvc.perform(put("/clientes/{id}", 1)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(mapper.writeValueAsString(dtoStub())))
               .andExpect(status().isOk())
               .andExpect(jsonPath("$.nome").value("Ana"));
        }
    }

    @Nested
    @DisplayName("DELETE /clientes/{id}")
    class ExcluirCliente {

        @Test
        @DisplayName("204 - deve excluir cliente")
        void excluirComSucesso() throws Exception {
            doNothing().when(clienteService).excluirCliente(1L);

            mvc.perform(delete("/clientes/{id}", 1))
               .andExpect(status().isNoContent());
        }
    }

    @Nested
    @DisplayName("GET /clientes")
    class ListarClientes {

        @Test
        @DisplayName("200 - deve listar todos os clientes")
        void listar() throws Exception {
            when(clienteService.listarClientes()).thenReturn(List.of(clienteStub(), clienteStub()));

            mvc.perform(get("/clientes"))
               .andExpect(status().isOk())
               .andExpect(jsonPath("$", hasSize(2)))
               .andExpect(jsonPath("$[0].cpf").value("111"));
        }
    }
}
