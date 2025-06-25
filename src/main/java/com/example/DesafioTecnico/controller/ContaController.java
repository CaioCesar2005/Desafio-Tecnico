package com.example.DesafioTecnico.controller;

import com.example.DesafioTecnico.dto.ContaRequestDTO;
import com.example.DesafioTecnico.dto.ContaResponseDTO;
import com.example.DesafioTecnico.mapper.ContaMapper;
import com.example.DesafioTecnico.model.Conta;
import com.example.DesafioTecnico.service.ContaService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
public class ContaController {

    private final ContaService contaService;

    public ContaController(ContaService contaService) {
        this.contaService = contaService;
    }

    @PostMapping("/clientes/{idCliente}/contas")
    public ResponseEntity<ContaResponseDTO> criarConta(@PathVariable Long idCliente, @Valid @RequestBody ContaRequestDTO contaRequestDTO) {
        Conta novaConta = contaService.cadastrarConta(idCliente, contaRequestDTO);
        ContaResponseDTO responseDTO = ContaMapper.toDTO(novaConta);
        return ResponseEntity.ok(responseDTO);
    }

    @PutMapping("/contas/{id}")
    public ResponseEntity<ContaResponseDTO> atualizarConta(@PathVariable Long id, @Valid @RequestBody ContaRequestDTO contaRequestDTO) {
        Conta contaAtualizada = contaService.atualizarConta(id, contaRequestDTO);
        ContaResponseDTO responseDTO = ContaMapper.toDTO(contaAtualizada);
        return ResponseEntity.ok(responseDTO);
    }

    @DeleteMapping("/contas/{id}")
    public ResponseEntity<Void> excluirConta(@PathVariable Long id) {
        contaService.cancelarContaLogicamente(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/clientes/{idCliente}/contas")
    public ResponseEntity<List<ContaResponseDTO>> listarContasPorCliente(@PathVariable Long idCliente) {
        List<Conta> contas = contaService.listarContasDoCliente(idCliente);
        List<ContaResponseDTO> contasDTO = contas.stream()
            .map(ContaMapper::toDTO)
            .collect(Collectors.toList());
        return ResponseEntity.ok(contasDTO);
    }
}
