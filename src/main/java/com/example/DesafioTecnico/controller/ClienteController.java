package com.example.DesafioTecnico.controller;

import com.example.DesafioTecnico.dto.ClienteRequestDTO;
import com.example.DesafioTecnico.dto.ClienteResponseDTO;
import com.example.DesafioTecnico.mapper.ClienteMapper;
import com.example.DesafioTecnico.model.Cliente;
import com.example.DesafioTecnico.service.ClienteService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/clientes")
public class ClienteController {

    private final ClienteService clienteService;

    public ClienteController(ClienteService clienteService){
        this.clienteService = clienteService;
    }

    @PostMapping
    public ResponseEntity<ClienteResponseDTO> criarCliente(@Valid @RequestBody ClienteRequestDTO clienteRequestDTO) {
        Cliente clienteEntity = ClienteMapper.toEntity(clienteRequestDTO);
        Cliente clienteNovo = clienteService.cadastrarCliente(clienteEntity);
        ClienteResponseDTO responseDTO = ClienteMapper.toDTO(clienteNovo);
        return ResponseEntity.ok(responseDTO);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ClienteResponseDTO> atualizarCliente(@PathVariable Long id, @Valid @RequestBody ClienteRequestDTO clienteRequestDTO){
        Cliente clienteAtualizado = ClienteMapper.toEntity(clienteRequestDTO);
        Cliente cliente = clienteService.atualizarCliente(id, clienteAtualizado);
        ClienteResponseDTO responseDTO = ClienteMapper.toDTO(cliente);
        return ResponseEntity.ok(responseDTO);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> excluirCliente(@PathVariable Long id) {
        clienteService.excluirCliente(id);
        return ResponseEntity.noContent().build();
    }
}
