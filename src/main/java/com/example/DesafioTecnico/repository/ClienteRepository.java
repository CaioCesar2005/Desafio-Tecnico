package com.example.DesafioTecnico.repository;

import com.example.DesafioTecnico.model.Cliente;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface ClienteRepository extends JpaRepository<Cliente, Long> {
    
    Optional<Cliente> findByCpf(String cpf);
    
    boolean existsByCpf(String cpf);
}
