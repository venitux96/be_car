package com.example.demo_ecommerce.model.dto;

import com.example.demo_ecommerce.model.entities.Cliente;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class ClienteResponse {

	private int id;
	private String nome;
	private String cognome;
	private String telefono;
	private String email;
	private String indirizzo;
	private String username;
	
	public ClienteResponse(Cliente cliente) {
		
		if(cliente!=null) {
			this.id = cliente.getId();
			this.nome = cliente.getNome();
			this.cognome = cliente.getCognome();
			this.telefono = cliente.getTelefono();
			this.email = cliente.getEmail();
			this.indirizzo = cliente.getIndirizzo();
			this.username = cliente.getUsername();
			
		}
	}
}
