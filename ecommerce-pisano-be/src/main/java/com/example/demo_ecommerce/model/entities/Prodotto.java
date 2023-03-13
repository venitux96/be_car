package com.example.demo_ecommerce.model.entities;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Version;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@EqualsAndHashCode
@ToString
@Entity
@Table(name = "prodotto")
public class Prodotto {
	public static final int LUNGHEZZA_NOME = 50, LUNGHEZZA_DESCRIZIONE = 500;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private int id;

	@Basic
	@Column(name = "nome")
	private String nome;

	@Basic
	@Column(name = "bar_code", unique = true)
	private String barCode;

	@Basic
	@Column(name = "descrizione")
	private String descrizione;

	@Basic
	@Column(name = "prezzo")
	private float prezzo;

	@Basic
	@Column(name = "quantita")
	private int quantita;

	@Version
	@Column(name = "version", nullable = false)
	private long version;

	@Basic
	@Column(name = "categoria")
	private String categoria;

}
