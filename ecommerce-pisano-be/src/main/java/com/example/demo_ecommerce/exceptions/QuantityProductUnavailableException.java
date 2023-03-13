package com.example.demo_ecommerce.exceptions;

import com.example.demo_ecommerce.model.entities.Prodotto;

public class QuantityProductUnavailableException extends Exception {
	private static final long serialVersionUID = -2955893685333809382L;
	private Prodotto prodotto;

	public QuantityProductUnavailableException(Prodotto prodotto) {
		this.prodotto = prodotto;
	}

	public Prodotto getProdotto() {
		return prodotto;
	}
}
