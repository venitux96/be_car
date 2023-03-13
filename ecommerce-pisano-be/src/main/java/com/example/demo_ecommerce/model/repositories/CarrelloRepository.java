package com.example.demo_ecommerce.model.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.demo_ecommerce.model.entities.Carrello;
import com.example.demo_ecommerce.model.entities.Cliente;

@Repository
public interface CarrelloRepository extends JpaRepository<Carrello, Integer> {
	public Carrello findByCliente(Cliente cliente);
}
