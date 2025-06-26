package com.example.DesafioTecnico.repository;

import com.example.DesafioTecnico.model.Conta;
import com.example.DesafioTecnico.model.Cliente;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

// Interface para operações de acesso a dados da entidade Conta
public interface ContaRepository extends JpaRepository<Conta, Long> {

    List<Conta> findByCliente(Cliente cliente);

}
