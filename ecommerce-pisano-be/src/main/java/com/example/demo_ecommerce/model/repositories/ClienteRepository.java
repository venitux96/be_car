package com.example.demo_ecommerce.model.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.demo_ecommerce.model.entities.Cliente;

@Repository
public interface ClienteRepository extends JpaRepository<Cliente, Integer> {
	Cliente findByEmail(String email);

	Cliente findByUsername(String username);

	boolean existsByUsername(String username);

	boolean existsByEmail(String email);
}
