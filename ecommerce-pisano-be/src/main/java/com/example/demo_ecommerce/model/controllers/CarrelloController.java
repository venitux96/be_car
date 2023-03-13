package com.example.demo_ecommerce.model.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo_ecommerce.model.services.CarrelloService;

@RestController
@RequestMapping("/carrello")
public class CarrelloController {

	@Autowired
	private CarrelloService carrelloService;

	@GetMapping("/prodotti")
	public ResponseEntity<Object> getProdottiNelCarrelloPerCliente(
			@RequestParam(value = "username", defaultValue = "") String username) {
		
		return this.carrelloService.getProdottiCarrello(username);
	}

	@PostMapping("/aggiorna")
	public ResponseEntity<Object> aggiornaPrdodottoNelCarrello(
			@RequestParam(value = "email", defaultValue = "") String emailCliente,
			@RequestParam(value = "id", defaultValue = "") String id,
			@RequestParam(value = "quantita", defaultValue = "0") String quantita) {
		return this.carrelloService.aggiornaPrdodottoCarrello(emailCliente, id, quantita);
	}

	@GetMapping("/quantita")
	public ResponseEntity<Object> getQuantitaProdotto(
			@RequestParam(value = "username", defaultValue = "") String username, 
			@RequestParam(value = "id", defaultValue = "") String id) {
		
		return this.carrelloService.getQuantitaProdotto(username, id);
	}
}
