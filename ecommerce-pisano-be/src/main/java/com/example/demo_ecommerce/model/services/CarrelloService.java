package com.example.demo_ecommerce.model.services;

import java.util.LinkedList;
import java.util.List;

import javax.persistence.EntityManager;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.example.demo_ecommerce.exceptions.AggiornamentoFallitoException;
import com.example.demo_ecommerce.model.dto.ResponseMessage;
import com.example.demo_ecommerce.model.entities.Carrello;
import com.example.demo_ecommerce.model.entities.CarrelloProdotto;
import com.example.demo_ecommerce.model.entities.Cliente;
import com.example.demo_ecommerce.model.entities.Prodotto;
import com.example.demo_ecommerce.model.repositories.CarrelloProdottoRepository;
import com.example.demo_ecommerce.model.repositories.CarrelloRepository;
import com.example.demo_ecommerce.model.repositories.ClienteRepository;
import com.example.demo_ecommerce.model.repositories.ProdottoRepository;

@Service
public class CarrelloService {
	
	@Autowired
	private CarrelloRepository carrelloRepository;
	
	@Autowired
	private CarrelloProdottoRepository carrelloProdottoRepository;
	
	@Autowired
	private EntityManager entityManager;
	
	@Autowired
	private ClienteRepository clienteRepository;
	
	@Autowired
	private ProdottoRepository prodottoRepository;

	@Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
	private List<CarrelloProdotto> getProdotti(Cliente cliente) {
		
		Carrello car = carrelloRepository.findByCliente(cliente); // carrello associato al cliente
		return car.getProdotti();
	}

	@Transactional(readOnly = false, propagation = Propagation.REQUIRED)
	private void aggiorna(Cliente c, Prodotto p, int q) throws AggiornamentoFallitoException {
		/*
		 * Usiamo questo metodo per aggiungere un prodotto al carrello, modificarne la
		 * quantità oppure eliminarlo (se la quantità q è 0).
		 */
		try {
			Carrello car = carrelloRepository.findByCliente(c); // Predno il carrello del cliente c
			CarrelloProdotto carProd = carrelloProdottoRepository.findByCarrelloAndProdotto(car,
					p); /*
						 * prendo il carrelloProdotto associato al cliente c e al prodotto p qual'ora
						 * questo ci sia. Se non c'è significa che il prodotto non è presente nel
						 * carrello e dobbiamo aggiungerlo
						 */
			if (carProd == null) { // non è presente nel carrello, lo aggiungiamo da zero con lq quantità
									// specificata
				CarrelloProdotto cp = new CarrelloProdotto(); // creo il carrelloProdotto e lo popolo.
				cp.setCarrello(car);
				cp.setQuantita(q);
				cp.setProdotto(p);
				car.getProdotti().add(cp); // aggiungo il carrello prodotto al carrello del cliente
				CarrelloProdotto ok = carrelloProdottoRepository.save(cp); // rendo cp persistente
				entityManager.refresh(ok);
			} else { /*
						 * se carProd non è nullo significa che lo vogliamo eliminare oppure che ne
						 * vogliamo modificare la quantità
						 */
				if (q == 0) { // lo eliminiamo
					car.getProdotti().remove(carProd); // lo rimuovo dall lista di CarrelloPrdodotto del carrello
					carrelloProdottoRepository.delete(carProd); // lo rimuovo dalla tabella carrello_prodotto

				} else { // lo aggiorniamo
					/*
					 * la quantità viene incrementata di quanto specificato quando si aggiunge al
					 * carrello
					 */
					carProd.setQuantita(q);
					CarrelloProdotto ok = carrelloProdottoRepository.saveAndFlush(carProd);
					entityManager.refresh(ok);
				}
			}
			entityManager.refresh(car);
		} catch (Exception e) {
			throw new AggiornamentoFallitoException();
		}
	}
	
	@Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
	public ResponseEntity<Object> getProdottiCarrello(String username) {
		
		Cliente c = clienteRepository.findByUsername(username);
		if (c == null) {
			c = clienteRepository.findByEmail(username);
		}
		List<CarrelloProdotto> list = this.getProdotti(c);
		List<Prodotto> prodotti = new LinkedList<>();
		for (CarrelloProdotto cp : list) {
			prodotti.add(cp.getProdotto());
		}
		if (prodotti.size() == 0) {
			return new ResponseEntity<>(prodotti, HttpStatus.NO_CONTENT);
		}
		return new ResponseEntity<>(prodotti, HttpStatus.OK);
	}
	
	@Transactional(propagation = Propagation.SUPPORTS)
	public ResponseEntity<Object> aggiornaPrdodottoCarrello(String emailCliente, String prodottoId, String quantita) {
		
		try {
			Prodotto prodotto = prodottoRepository.findById(Integer.parseInt(prodottoId)).orElse(null);
			Cliente cliente = clienteRepository.findByEmail(emailCliente);
			if (cliente == null) {
				cliente = clienteRepository.findByUsername(emailCliente);
			}
			this.aggiorna(cliente, prodotto, Integer.parseInt(quantita));
		} catch (AggiornamentoFallitoException e) {
			return new ResponseEntity<>(new ResponseMessage("Aggiornamento fallito"), HttpStatus.BAD_REQUEST);
		}
		return new ResponseEntity<>(new ResponseMessage("Aggiornamento avvenuto con successo"), HttpStatus.OK);
	}
	
	@Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
	public ResponseEntity<Object> getQuantitaProdotto(String username, String prodottoId) {
		
		Cliente c = clienteRepository.findByUsername(username);
		if (c == null) {
			c = clienteRepository.findByEmail(username);
		}
		List<CarrelloProdotto> list = this.getProdotti(c);
		int quantita = 0;
		for (CarrelloProdotto cp : list) {
			if (cp.getProdotto().getId() == Integer.parseInt(prodottoId)) {
				quantita = cp.getQuantita();
				break;
			}
		}
		if (quantita == 0) {
			return new ResponseEntity<>(new ResponseMessage("Il prodotto non è presente nel carrello"), HttpStatus.OK);
		}
		return new ResponseEntity<>(String.valueOf(quantita), HttpStatus.OK);
	}
}
