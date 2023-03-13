package com.example.demo_ecommerce.model.entities;

import java.util.Date;
import java.util.List;

import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.CreationTimestamp;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@EqualsAndHashCode
@ToString
@Entity
@Table(name = "acquisto")
public class Acquisto {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private int id;

	@Basic
	@CreationTimestamp
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "data")
	private Date data;

	@ManyToOne
	@JoinColumn(name = "cliente")
	private Cliente cliente;

	@OneToMany(mappedBy = "acquisto", cascade = CascadeType.MERGE)
	@ToString.Exclude
	private List<ProdottoAcquisto> prodottoAcquistoList;
	/*
	 * Acquisto ha una relazione uno-a-molti con la tabella prodotto_acquisto per
	 * memorizzare tutti i prodotti relativi ad un acquisto
	 */

}
