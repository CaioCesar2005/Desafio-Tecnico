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

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/clientes")
public class ClienteController {

    private final ClienteService clienteService;

    public ClienteController(ClienteService clienteService) {
        this.clienteService = clienteService;
    }

    /** POST /clientes */
    @PostMapping
    public ResponseEntity<ClienteResponseDTO> criarCliente(
            @Valid @RequestBody ClienteRequestDTO dto) {

        Cliente novoCliente = clienteService.cadastrarCliente(dto);
        ClienteResponseDTO responseDTO = ClienteMapper.toDTO(novoCliente);
        return ResponseEntity.status(HttpStatus.CREATED).body(responseDTO);
    }
    
    @PutMapping("/{id}")
    public ResponseEntity<ClienteResponseDTO> atualizarCliente(
            @PathVariable Long id,
            @Valid @RequestBody ClienteRequestDTO dto) {

        Cliente clienteAtualizado = clienteService.atualizarCliente(id, dto);
        ClienteResponseDTO responseDTO = ClienteMapper.toDTO(clienteAtualizado);
        return ResponseEntity.ok(responseDTO);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> excluirCliente(@PathVariable Long id) {
        clienteService.excluirCliente(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping
    public ResponseEntity<List<ClienteResponseDTO>> listarClientes() {
        List<Cliente> clientes = clienteService.listarClientes();
        List<ClienteResponseDTO> dtos = clientes.stream()
                .map(ClienteMapper::toDTO)
                .collect(Collectors.toList());
        return ResponseEntity.ok(dtos);
    }
}
