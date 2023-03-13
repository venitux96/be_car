package com.example.demo_ecommerce.model.entities;

import java.util.List;

import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@EqualsAndHashCode
@ToString
@Entity
@Table(name = "cliente")
public class Cliente {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id", nullable = false)
	private int id;

	@Basic
	@Column(name = "nome")
	private String nome;

	@Basic
	@Column(name = "cognome")
	private String cognome;

	@Basic
	@Column(name = "telefono")
	private String telefono;

	@Basic
	@Column(name = "email")
	private String email;

	@Basic
	@Column(name = "indirizzo")
	private String indirizzo;

	@Basic
	@Column(name = "username")
	private String username;
		
	/*
	 * Mediante questa relazione uno-a-molti prendiamo
	 * la lista degli acquisti effettuati dal cliente
	 * mediante il campo cliente in acquisto
	 */
	@OneToMany(mappedBy = "cliente", cascade = CascadeType.MERGE) 
	@ToString.Exclude
	private List<Acquisto> acquisti;

}
