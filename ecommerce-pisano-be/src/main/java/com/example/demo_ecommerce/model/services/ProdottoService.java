package com.example.demo_ecommerce.model.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.example.demo_ecommerce.model.dto.ProdottoResponse;
import com.example.demo_ecommerce.model.dto.ResponseMessage;
import com.example.demo_ecommerce.model.entities.Prodotto;
import com.example.demo_ecommerce.model.repositories.ProdottoRepository;

@Service
public class ProdottoService {

	@Autowired
	private ProdottoRepository prodottoRepository;

	@Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
	public ResponseEntity<Object> mostraProdottiNomeDescrizione(String ricerca, int numeroPagina, int dimensionePagina,
			String sortBy) {
		
		Pageable paging = PageRequest.of(numeroPagina, dimensionePagina, Sort.by(sortBy));
		Page<Prodotto> pagedResult = prodottoRepository.findBySearch(ricerca, paging);
		if (pagedResult.hasContent()) {
			return new ResponseEntity<>(ProdottoResponse.toResponses(pagedResult.getContent()), HttpStatus.OK);
		} else {
			return new ResponseEntity<>(new ResponseMessage("Nessun risultato!"), HttpStatus.OK);
		}
	}
	
}
