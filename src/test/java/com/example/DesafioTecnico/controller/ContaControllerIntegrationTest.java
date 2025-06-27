package com.example.DesafioTecnico.controller;

import com.example.DesafioTecnico.dto.ContaRequestDTO;
import com.example.DesafioTecnico.model.Cliente;
import com.example.DesafioTecnico.model.Conta;
import com.example.DesafioTecnico.model.SituacaoConta;
import com.example.DesafioTecnico.service.ContaService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;
import java.util.List;

import static org.hamcrest.Matchers.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Integração “slice” para ContaController – garante mapeamentos
 * e contratos HTTP; lógica de negócio permanece coberta pelos testes de service.
 */

@WebMvcTest(ContaController.class)
@DisplayName("ContaController – Testes de Integração")
class ContaControllerIntegrationTest {

    @Autowired
    private MockMvc mvc;

    @Autowired
    private ObjectMapper mapper;

    @MockBean
    private ContaService contaService;

    private Cliente cliente() {
        return new Cliente(1L, "Ana", "111", "", "");
    }

    private Conta contaStub() {
        Conta c = new Conta("06-2024", BigDecimal.valueOf(1500), SituacaoConta.PENDENTE, cliente());
        c.setId(10L);
        return c;
    }

    private ContaRequestDTO dtoStub() {
        return new ContaRequestDTO("06-2024", BigDecimal.valueOf(1500), SituacaoConta.PENDENTE);
    }

    @Nested
    @DisplayName("POST /clientes/{id}/contas")
    class CriarConta {

        @Test
        @DisplayName("200 - deve criar conta para cliente")
        void criar() throws Exception {
            when(contaService.cadastrarConta(eq(1L), any())).thenReturn(contaStub());

            mvc.perform(post("/clientes/{idCliente}/contas", 1)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(mapper.writeValueAsString(dtoStub())))
               .andExpect(status().isOk())
               .andExpect(jsonPath("$.id").value(10))
               .andExpect(jsonPath("$.situacao").value("PENDENTE"));
        }
    }

    @Nested
    @DisplayName("PUT /contas/{id}")
    class AtualizarConta {

        @Test
        @DisplayName("200 - deve atualizar conta existente")
        void atualizar() throws Exception {
            when(contaService.atualizarConta(eq(10L), any())).thenReturn(contaStub());

            mvc.perform(put("/contas/{id}", 10)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(mapper.writeValueAsString(dtoStub())))
               .andExpect(status().isOk())
               .andExpect(jsonPath("$.referencia").value("06-2024"));
        }
    }

    @Nested
    @DisplayName("DELETE /contas/{id}")
    class ExcluirConta {

        @Test
        @DisplayName("204 - deve cancelar conta")
        void excluir() throws Exception {
            when(contaService.cancelarContaLogicamente(10L)).thenReturn(contaStub());

            mvc.perform(delete("/contas/{id}", 10))
                .andExpect(status().isNoContent());
        }
    }


    @Nested
    @DisplayName("GET /clientes/{id}/contas")
    class ListarContasCliente {

        @Test
        @DisplayName("200 - deve listar contas do cliente")
        void listarContas() throws Exception {
            when(contaService.listarContasDoCliente(1L)).thenReturn(List.of(contaStub()));

            mvc.perform(get("/clientes/{idCliente}/contas", 1))
               .andExpect(status().isOk())
               .andExpect(jsonPath("$", hasSize(1)))
               .andExpect(jsonPath("$[0].valor").value(1500));
        }
    }
}


