package com.example.DesafioTecnico.controller;

import com.example.DesafioTecnico.dto.ContaRequestDTO;
import com.example.DesafioTecnico.dto.ContaResponseDTO;
import com.example.DesafioTecnico.mapper.ContaMapper;
import com.example.DesafioTecnico.model.Conta;
import com.example.DesafioTecnico.service.ContaService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.Parameter;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@Tag(name = "Contas", description = "Gerenciamento de contas")
public class ContaController {

    private final ContaService contaService;

    public ContaController(ContaService contaService) {
        this.contaService = contaService;
    }

    // POST: CRIAR CONTA
    @PostMapping("/clientes/{idCliente}/contas")
    @Operation(summary = "Cadastrar nova conta para cliente")
    @ApiResponse(responseCode = "200", description = "Conta criada com sucesso")
    public ResponseEntity<ContaResponseDTO> criarConta(
            @Parameter(description = "ID do cliente para quem a conta será criada", example = "1")
            @PathVariable Long idCliente,
            @Valid @RequestBody
            @Parameter(description = "Dados da conta para cadastro")
            ContaRequestDTO contaRequestDTO) {
        
        Conta novaConta = contaService.cadastrarConta(idCliente, contaRequestDTO);
        ContaResponseDTO responseDTO = ContaMapper.toDTO(novaConta);
        return ResponseEntity.ok(responseDTO);
    }

    // PUT: ATUALIZAR CONTA
    @PutMapping("/contas/{id}")
    @Operation(summary = "Atualizar conta existente")
    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "Conta atualizada com sucesso"),@ApiResponse(responseCode = "404", description = "Conta não encontrada")})
    public ResponseEntity<ContaResponseDTO> atualizarConta(
            @Parameter(description = "ID da conta a ser atualizada", example = "1")
            @PathVariable Long id,
            @Valid @RequestBody
            @Parameter(description = "Novos dados para atualização da conta")
            ContaRequestDTO contaRequestDTO) {
        
        Conta contaAtualizada = contaService.atualizarConta(id, contaRequestDTO);
        ContaResponseDTO responseDTO = ContaMapper.toDTO(contaAtualizada);
        return ResponseEntity.ok(responseDTO);
    }

    // DELETE: EXCLUIR CONTA
    @DeleteMapping("/contas/{id}")
    @Operation(summary = "Excluir conta (cancelamento lógico)")
    @ApiResponses(value = {@ApiResponse(responseCode = "204", description = "Conta cancelada com sucesso"),@ApiResponse(responseCode = "404", description = "Conta não encontrada")})
    public ResponseEntity<Void> excluirConta(
            @Parameter(description = "ID da conta a ser cancelada", example = "1")
            @PathVariable Long id) {
        
        contaService.cancelarContaLogicamente(id);
        return ResponseEntity.noContent().build();
    }

    // GET: LISTAR CONTAS POR CLIENTE
    @GetMapping("/clientes/{idCliente}/contas")
    @Operation(summary = "Listar contas de um cliente")
    @ApiResponse(responseCode = "200", description = "Lista de contas do cliente retornada com sucesso")
    public ResponseEntity<List<ContaResponseDTO>> listarContasPorCliente(
            @Parameter(description = "ID do cliente", example = "1")
            @PathVariable Long idCliente) {
        
        List<Conta> contas = contaService.listarContasDoCliente(idCliente);
        List<ContaResponseDTO> contasDTO = contas.stream()
            .map(ContaMapper::toDTO)
            .collect(Collectors.toList());
        return ResponseEntity.ok(contasDTO);
    }
}
