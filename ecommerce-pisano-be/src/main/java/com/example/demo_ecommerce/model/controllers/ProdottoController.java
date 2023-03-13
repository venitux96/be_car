package com.example.demo_ecommerce.model.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo_ecommerce.model.services.ProdottoService;

@RestController
@RequestMapping("/prodotti")
public class ProdottoController {
	
	@Autowired
	private ProdottoService prodottoService;

	@GetMapping("/cerca")
	public ResponseEntity<Object> getProdottiByNomeAndDescrizione(
			@RequestParam(value = "ricerca", defaultValue = "") String ricerca,
			@RequestParam(value = "numeroPagina", defaultValue = "0") int numeroPagina,
			@RequestParam(value = "prodottiPerPagina", defaultValue = "9999") int prodottiPerPagina,
			@RequestParam(value = "ordinaPer", defaultValue = "id") String sortBy) {
		
		return prodottoService.mostraProdottiNomeDescrizione(ricerca, numeroPagina, prodottiPerPagina, sortBy);
	}

}
