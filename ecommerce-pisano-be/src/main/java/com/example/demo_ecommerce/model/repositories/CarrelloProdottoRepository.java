package com.example.demo_ecommerce.model.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.demo_ecommerce.model.entities.Carrello;
import com.example.demo_ecommerce.model.entities.CarrelloProdotto;
import com.example.demo_ecommerce.model.entities.Prodotto;

@Repository
public interface CarrelloProdottoRepository extends JpaRepository<CarrelloProdotto, Integer> {
	public CarrelloProdotto findByCarrelloAndProdotto(Carrello carrello, Prodotto prodotto);
}
