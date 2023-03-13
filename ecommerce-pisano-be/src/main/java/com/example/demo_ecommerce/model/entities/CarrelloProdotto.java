package com.example.demo_ecommerce.model.entities;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
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
@Table(name = "carrello_prodotto")

public class CarrelloProdotto {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private int id;

	@Basic
	@Column(name = "quantita")
	private int quantita;

	@ManyToOne /*
				 * CarrelloProdotto possiede una relazione molti-a-uno con prodotto poichè
				 * molteplici istanze di CarrelloProdotto possono fare riferimento ad una
				 * singola istanza di prodotto. Nel DB è presenta la chiave esterna
				 * carrello_prodotto(prodotto)->prodotto(id)
				 */
	@JoinColumn(name = "prodotto") /*
									 * tramite il campo prodotto della tabella carrello_prodotto memeorizziamo il
									 * prodotto associato nella variabile privata prodotto
									 */
	private Prodotto prodotto;

	@ManyToOne /*
				 * relazione molti-a-uno con carrello: più entità di CarrelloProdotto possono
				 * fare riferimento ad una singola istanza di Carrello. Nel DB è presente la
				 * chiave esterna carrello_prodotto(carrello)->carrello(id)
				 */
	@JoinColumn(name = "carrello") // Mappiamo la varibile Carrello sul campo carrello di carrello_prodotto
	@ToString.Exclude
	private Carrello carrello;
}
