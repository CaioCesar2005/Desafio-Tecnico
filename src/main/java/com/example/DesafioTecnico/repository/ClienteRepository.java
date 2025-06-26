package com.example.DesafioTecnico.repository;

import com.example.DesafioTecnico.model.Cliente;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

// Interface para operações de acesso a dados da entidade Cliente
public interface ClienteRepository extends JpaRepository<Cliente, Long> {
    
    Optional<Cliente> findByCpf(String cpf);
    
    boolean existsByCpf(String cpf);
}
