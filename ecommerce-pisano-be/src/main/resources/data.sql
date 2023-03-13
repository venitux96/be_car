INSERT INTO prodotto(id, nome, bar_code, descrizione, prezzo, quantita, version, categoria) VALUES 
	(1, 'Ferrari F8', 'bc1', 'Automobile di marca Ferrari', 280000, 100, 0, 'supercar'),
	(2, 'Fiat Panda', 'bc2', 'Automobile di marca Fiat', 8800, 100, 0, 'familiare'),
	(3, 'Fiat Multipla', 'bc3', 'Automobile di marca Fiat', 20000 ,100, 0,'familiare'),
	(4, 'Fiat Punto ELX', 'bc4', 'Automobile di marca Fiat', 10000, 100, 0, 'citycar'),
	(5, 'Charger', 'bc5', 'Automobile di marca Chevolet', 180000, 100, 0, 'supercar'),
	(6, 'Camaro', 'bc6', 'Automobile di marca Chevolet', 15000, 100, 0, 'supercar');

INSERT INTO cliente(id, nome, cognome, telefono, email, indirizzo, username) VALUES 
	(7,'Giuseppe','Verdi','3398305458','giuseppeverdi@gmail.com','via xx','giuseppeverdi'),
	(8,'Mario','Bianchi','3219685704','mariobianchi@gmail.com','via xyz','mariobianchi'),
	(9,'Salvaore','Pisano','1234567890','venitux96@gmail.com','via Prov.','salvatorepisano');

INSERT INTO carrello(id, cliente) VALUES 
	(10,7),
	(11,8),
	(12,9);
	