package com.example.DesafioTecnico.controller;

import com.example.DesafioTecnico.dto.ClienteRequestDTO;
import com.example.DesafioTecnico.dto.ClienteResponseDTO;
import com.example.DesafioTecnico.mapper.ClienteMapper;
import com.example.DesafioTecnico.model.Cliente;
import com.example.DesafioTecnico.service.ClienteService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.Parameter;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/clientes")
@Tag(name = "Clientes", description = "Gerenciamento de clientes")
public class ClienteController {

    private final ClienteService clienteService;

    public ClienteController(ClienteService clienteService) {
        this.clienteService = clienteService;
    }

    @PostMapping
    @Operation(summary = "Cadastrar novo cliente")
    @ApiResponse(responseCode = "201", description = "Cliente criado com sucesso")
    public ResponseEntity<ClienteResponseDTO> criarCliente(
            @Valid @RequestBody
            @Parameter(description = "Dados do cliente para cadastro")
            ClienteRequestDTO dto) {

        Cliente novoCliente = clienteService.cadastrarCliente(dto);
        ClienteResponseDTO responseDTO = ClienteMapper.toDTO(novoCliente);
        return ResponseEntity.status(HttpStatus.CREATED).body(responseDTO);
    }
    
    @PutMapping("/{id}")
    @Operation(summary = "Atualizar cliente existente")
    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "Cliente atualizado com sucesso"),@ApiResponse(responseCode = "404", description = "Cliente não encontrado")})
    public ResponseEntity<ClienteResponseDTO> atualizarCliente(
            @Parameter(description = "ID do cliente a ser atualizado", example = "1")
            @PathVariable Long id,
            @Valid @RequestBody
            @Parameter(description = "Novos dados para atualização do cliente")
            ClienteRequestDTO dto) {

        Cliente clienteAtualizado = clienteService.atualizarCliente(id, dto);
        ClienteResponseDTO responseDTO = ClienteMapper.toDTO(clienteAtualizado);
        return ResponseEntity.ok(responseDTO);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Excluir cliente")
    @ApiResponses(value = {@ApiResponse(responseCode = "204", description = "Cliente excluído com sucesso"),@ApiResponse(responseCode = "404", description = "Cliente não encontrado")})
    public ResponseEntity<Void> excluirCliente(
            @Parameter(description = "ID do cliente a ser excluído", example = "1")
            @PathVariable Long id) {
        
        clienteService.excluirCliente(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping
    @Operation(summary = "Listar todos os clientes")
    @ApiResponse(responseCode = "200", description = "Lista de clientes retornada com sucesso")
    public ResponseEntity<List<ClienteResponseDTO>> listarClientes() {
        
        List<Cliente> clientes = clienteService.listarClientes();
        List<ClienteResponseDTO> dtos = clientes.stream()
                .map(ClienteMapper::toDTO)
                .collect(Collectors.toList());
        return ResponseEntity.ok(dtos);
    }
}
