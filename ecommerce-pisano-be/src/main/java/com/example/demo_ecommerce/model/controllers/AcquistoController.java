package com.example.demo_ecommerce.model.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo_ecommerce.model.services.AcquistoService;

@RestController
@RequestMapping("/acquisti")
public class AcquistoController {

	@Autowired
	private AcquistoService acquistoService;

	@PostMapping
	@ResponseStatus(code = HttpStatus.OK)
	public ResponseEntity<Object> acquista(@RequestParam(value = "email") String emailUser,
			@RequestParam(value = "id") String idProdotto) {
		
		return this.acquistoService.acquista(emailUser, idProdotto);
	}

	@PostMapping
	@ResponseStatus(code = HttpStatus.OK)
	public ResponseEntity<Object> acquistaCarrello(@RequestParam(value = "email") String emailUser,
										   @RequestParam(value = "id") String[] idProdotti) {

		return this.acquistoService.acquista(emailUser, idProdotti);
	}

	@GetMapping("/dettagli")
	public String getDettagliOrdine(@RequestParam(value = "id", defaultValue = "") String idOrdine) {
		
		return this.acquistoService.getDettagliOrdine(Integer.parseInt(idOrdine));
	}

	@GetMapping("/ordini")
	public ResponseEntity<Object> getAcquistiperCliente(
			@RequestParam(value = "cliente", defaultValue = "") String userName) {
		
		return this.acquistoService.getAcquistiCliente(userName);
	}

}
