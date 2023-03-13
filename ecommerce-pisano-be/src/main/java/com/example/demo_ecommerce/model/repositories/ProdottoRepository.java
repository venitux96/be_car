package com.example.demo_ecommerce.model.repositories;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.example.demo_ecommerce.model.entities.Prodotto;

@Repository
public interface ProdottoRepository extends JpaRepository<Prodotto, Integer> {
	
	@Query(nativeQuery = true, value = "SELECT p.* "
			+ "FROM prodotto p "
			+ "WHERE LOWER(p.nome) LIKE LOWER(CONCAT('%', :ricerca, '%')) "
			+ "OR LOWER(p.descrizione) LIKE LOWER(CONCAT('%', :ricerca, '%'))", 
			countQuery = "COUNT (*) F"
					+ "ROM prodotto p "
					+ "WHERE LOWER(p.nome) LIKE LOWER(CONCAT('%', :ricerca, '%')) "
					+ "OR LOWER(p.descrizione) LIKE LOWER(CONCAT('%', :ricerca, '%'))")
	public Page<Prodotto> findBySearch(String ricerca, Pageable paging);
	
}
