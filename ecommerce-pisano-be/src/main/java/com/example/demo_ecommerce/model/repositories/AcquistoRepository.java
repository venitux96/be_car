package com.example.demo_ecommerce.model.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.demo_ecommerce.model.entities.Acquisto;
import com.example.demo_ecommerce.model.entities.Cliente;

import java.util.List;

@Repository
public interface AcquistoRepository extends JpaRepository<Acquisto, Integer> {
	public List<Acquisto> findByCliente(Cliente cliente);

}
