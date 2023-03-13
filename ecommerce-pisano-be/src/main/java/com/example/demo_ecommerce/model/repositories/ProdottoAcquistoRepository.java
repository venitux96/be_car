package com.example.demo_ecommerce.model.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.demo_ecommerce.model.entities.Acquisto;
import com.example.demo_ecommerce.model.entities.ProdottoAcquisto;

import java.util.List;

@Repository
public interface ProdottoAcquistoRepository extends JpaRepository<ProdottoAcquisto, Integer> {
	List<ProdottoAcquisto> findByAcquisto(Acquisto acquisto);
}
