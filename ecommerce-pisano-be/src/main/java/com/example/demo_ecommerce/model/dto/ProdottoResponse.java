package com.example.demo_ecommerce.model.dto;

import java.util.List;
import java.util.stream.Collectors;

import com.example.demo_ecommerce.model.entities.Prodotto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class ProdottoResponse {

	private int id;
	private String nome;
	private String barCode;
	private String descrizione;
	private float prezzo;
	private int quantita;
	private String categoria;
	
	public ProdottoResponse(Prodotto p) {
		this.id=p.getId();
		this.nome=p.getNome();
		this.barCode=p.getBarCode();
		this.descrizione=p.getDescrizione();
		this.prezzo=p.getPrezzo();
		this.quantita=p.getQuantita();
		this.categoria=p.getCategoria();
	}
	
	public static List<ProdottoResponse> toResponses(List<Prodotto> prods){
		if(prods!=null) {
			return prods.stream().map(ProdottoResponse::new).collect(Collectors.toList());
		}
		return null;
	}
}
