package com.example.DesafioTecnico.controller;

import com.example.DesafioTecnico.model.Conta;
import com.example.DesafioTecnico.service.ContaService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class ContaController {

    private final ContaService contaService;

    public ContaController(ContaService contaService) {
        this.contaService = contaService;
    }

    @PostMapping("/clientes/{idCliente}/contas")
    public ResponseEntity<Conta> criarConta(@PathVariable Long idCliente, @RequestBody Conta conta) {
        Conta novaConta = contaService.cadastrarConta(idCliente, conta);
        return ResponseEntity.ok(novaConta);
    }

    // @PutMapping("/contas/{id}")
    // public ResponseEntity<Conta> atualizarConta(@PathVariable Long id, @RequestBody Conta conta) {
    // 
    //     return ResponseEntity.status(501).build();
    // }

    @DeleteMapping("/contas/{id}")
    public ResponseEntity<Void> excluirConta(@PathVariable Long id) {
        contaService.cancelarContaLogicamente(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/clientes/{idCliente}/contas")
    public ResponseEntity<List<Conta>> listarContasPorCliente(@PathVariable Long idCliente) {
        contaService.listarContasDoCliente(idCliente);
        return ResponseEntity.status(501).build();
    }
}
