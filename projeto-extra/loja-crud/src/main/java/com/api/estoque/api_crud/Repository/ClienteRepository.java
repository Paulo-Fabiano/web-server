package com.api.estoque.api_crud.Repository;

import com.api.estoque.api_crud.Entity.Cliente;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ClienteRepository extends JpaRepository<Cliente, Long> {
}
