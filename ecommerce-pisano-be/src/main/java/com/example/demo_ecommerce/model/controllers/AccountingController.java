package com.example.demo_ecommerce.model.controllers;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo_ecommerce.model.entities.Cliente;
import com.example.demo_ecommerce.model.services.ClienteService;

@RestController
@RequestMapping(value = "/clienti")
public class AccountingController {

	@Autowired
	private ClienteService clienteService;

	@PostMapping()
	public ResponseEntity<Object> aggiungiCliente(@RequestBody @Valid Cliente cliente,
			@RequestParam(value = "password") String password) {
		
		return this.clienteService.aggiungiCliente(cliente, password);
	}

}
