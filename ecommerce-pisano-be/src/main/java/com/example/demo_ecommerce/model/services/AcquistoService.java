package com.example.demo_ecommerce.model.services;

import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.LockModeType;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.example.demo_ecommerce.exceptions.QuantityProductUnavailableException;
import com.example.demo_ecommerce.model.dto.AcquistoResponse;
import com.example.demo_ecommerce.model.dto.ResponseMessage;
import com.example.demo_ecommerce.model.entities.Acquisto;
import com.example.demo_ecommerce.model.entities.Carrello;
import com.example.demo_ecommerce.model.entities.CarrelloProdotto;
import com.example.demo_ecommerce.model.entities.Cliente;
import com.example.demo_ecommerce.model.entities.Prodotto;
import com.example.demo_ecommerce.model.entities.ProdottoAcquisto;
import com.example.demo_ecommerce.model.repositories.AcquistoRepository;
import com.example.demo_ecommerce.model.repositories.CarrelloProdottoRepository;
import com.example.demo_ecommerce.model.repositories.CarrelloRepository;
import com.example.demo_ecommerce.model.repositories.ClienteRepository;
import com.example.demo_ecommerce.model.repositories.ProdottoAcquistoRepository;
import com.example.demo_ecommerce.model.repositories.ProdottoRepository;

@Service
public class AcquistoService {
	
	@Autowired
	private AcquistoRepository acquistoRepository;
	
	@Autowired
	private ProdottoAcquistoRepository prodottoAcquistoRepository;
	
	@Autowired
	private ClienteRepository clienteRepository;
	
	@Autowired
	private CarrelloRepository carrelloRepository;
	
	@Autowired
	private ProdottoRepository prodottoRepository;
	
	@Autowired
	private CarrelloProdottoRepository carrelloProdottoRepository;
	
	@Autowired
	private EntityManager entityManager;

	@SuppressWarnings("unlikely-arg-type")
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED)
	@Lock(LockModeType.OPTIMISTIC)
	/*
	 * Il lock ottimistico funziona mediante il campo indicato da @Version
	 * nell'entità critica. Ogni transazione che legge i dati prende il valore del
	 * campo version (che è un intero) e prima che questa transazione faccia
	 * un'aggiornamento sull'entità controlla la proprietà version nuovamente. Se il
	 * valore è cambiato signifca che un'altra transazione ha fatto commit
	 * nell'intervallo di tempo e ha modifcato il valore di version, si genera
	 * dunque un OptimisticLockException. Altrimenti, la transazione è finalizzata
	 * (commit) e il valore della proprietà version viene incrementato.
	 *
	 * NB: in contrapposizione al lock ottimistico troviamo il lock pessimistico che
	 * implica il blocco delle entità a livello di database. Ogni transazione può
	 * ottenere il lock sui dati. Finchè la transazione tiene il lock nessun'altra
	 * transazione può leggere, cancellare o fare alcun cambiamento sui dati
	 * bloccati (con il lock). Possiamo assumere che il lock pessimistico possa
	 * generare deadlock. Tuttavia garantisce maggiore integrità dei dati rispetto
	 * al lock ottimistico.
	 */
	private Acquisto aggiungiAcquisto(String emailUser, String idProdotto)
			throws Exception { /*
								 * Per correttezza ritorniamo l'acquisto anche se praticamente non lo usiamo
								 * perchè il controller degli acquisti ritorna una stringa
								 */
		Cliente cliente = clienteRepository.findByEmail(emailUser);
		if (cliente == null) {
			cliente = clienteRepository.findByUsername(emailUser);
		}
		Carrello carrello = carrelloRepository.findByCliente(cliente);
		Prodotto prodotto = prodottoRepository.findById(Integer.parseInt(idProdotto)).orElse(null);
		CarrelloProdotto carrelloProdotto = carrelloProdottoRepository.findByCarrelloAndProdotto(carrello, prodotto);

		int qta = carrelloProdotto.getQuantita();
		if (carrelloProdotto.getQuantita() > prodotto.getQuantita())
			throw new QuantityProductUnavailableException(prodotto); // se la quantità acquistabile non è disponibile
																		// nel DB

		/*
		 * noi possiamo inserire una quantità non disponibile di oggetti nel carrello
		 * nel caso volessimo fare un acquisto nel futuro
		 */

		// Creo un Acquisto, imposto cliente, data e lo salvo
		Acquisto nuovoAcquisto = new Acquisto();
		nuovoAcquisto.setCliente(cliente);
		nuovoAcquisto.setData(new Date());
		acquistoRepository.saveAndFlush(nuovoAcquisto);

		// Creo il prodottoAcquisto per contenere l'acquisto effettuato
		ProdottoAcquisto prodottoAcquisto = new ProdottoAcquisto();
		prodottoAcquisto.setAcquisto(nuovoAcquisto); // imposto l'acquisto
		prodottoAcquisto.setProdotto(prodotto); // imposto il prodotto
		prodottoAcquisto.setQuantita(carrelloProdotto.getQuantita()); // imposto la quantità acquistata
		if (nuovoAcquisto.getProdottoAcquistoList() == null)
			nuovoAcquisto.setProdottoAcquistoList(new LinkedList<>());
		nuovoAcquisto.getProdottoAcquistoList()
				.add(prodottoAcquisto); /*
										 * aggiungo il prodotto alla lista di prodottoAcquisto contenuta nell'acquisto
										 */
		prodottoAcquistoRepository.saveAndFlush(prodottoAcquisto);

		// eliminazione dal carrello
		List<CarrelloProdotto> listaCarrelloProdotto = carrello.getProdotti();
		listaCarrelloProdotto.remove(prodottoAcquisto); // elimino il prodotto acquistato dalla lista di
														// CarrelloProdotto
		carrelloRepository.save(carrello);
		carrelloProdottoRepository.delete(carrelloProdotto); // elimino il carrelloProdotto dal carrello

		// aggiorno la quantità
		prodotto.setQuantita(prodotto.getQuantita() - qta);
		prodottoRepository.save(prodotto);

		entityManager.refresh(nuovoAcquisto);
		entityManager.refresh(prodottoAcquisto);
		return nuovoAcquisto;
	}

	@Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
	public String getDettagliOrdine(int idOrdine) {
		/*
		 * Creiamo una stringa contenete il nome e la quantità del prodotto acquistato
		 * nell'ordine idOrdine. Tale stringa la visualizzaziamo nella textbox inerente
		 * ai dettagli dell'ordine nella OrderCard.
		 */
		try {
			Acquisto a = acquistoRepository.findById(idOrdine).orElse(null);
			List<ProdottoAcquisto> prodottoAcquistoList = prodottoAcquistoRepository.findByAcquisto(a);
			String s = "";
			for (ProdottoAcquisto pa : prodottoAcquistoList) {
				// System.out.println(pa.toString());
				s += pa.getProdotto().getNome() + ", qt: " + pa.getQuantita() + ", "
						+ pa.getProdotto().getPrezzo() * pa.getQuantita() + " €" + "\n";
			}
			return s;
		} catch (Exception e) {
			return "ERROR";
		}
	}
	
	@Transactional(propagation = Propagation.REQUIRED)
	public ResponseEntity<Object> acquista(String emailUser, String idProdotto) {
		try {
			this.aggiungiAcquisto(emailUser, idProdotto);
			return new ResponseEntity<>("Prododotto/i "
					+ prodottoRepository.findById(Integer.parseInt(idProdotto)).orElse(null).getNome() + " acquistato con successo",
					HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>("Prodotto/i "
					+ prodottoRepository.findById(Integer.parseInt(idProdotto)).orElse(null).getNome() + " non acquistato/i",
					HttpStatus.OK);
		}
	}
	
	@Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
	public ResponseEntity<Object> getAcquistiCliente(String userName) {
		
		Cliente c = clienteRepository.findByEmail(userName);
		if (c == null)
			c = clienteRepository.findByUsername(userName);
		List<AcquistoResponse> listaOrdini = new LinkedList<>();
		for (Acquisto a : acquistoRepository.findByCliente(c)) {
			listaOrdini.add(new AcquistoResponse(a.getId(), a.getData().toString()));
		}
		if (listaOrdini.size() <= 0) {
			return new ResponseEntity<>(new ResponseMessage("Nessun risultato!"), HttpStatus.OK);
		}
		return new ResponseEntity<>(listaOrdini, HttpStatus.OK);
	}
}
