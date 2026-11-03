-- Dati di esempio, caricati da DatiInizialiLoader solo se il database è vuoto.
-- Ogni istruzione deve stare su una sola riga.

-- Registi
insert into regista (id, nome, cognome, data_nascita, nazionalita) values (nextval('regista_seq'), 'Francis Ford', 'Coppola', '1939-04-07', 'Stati Uniti');
insert into regista (id, nome, cognome, data_nascita, nazionalita) values (nextval('regista_seq'), 'Martin', 'Scorsese', '1942-11-17', 'Stati Uniti');
insert into regista (id, nome, cognome, data_nascita, nazionalita) values (nextval('regista_seq'), 'Quentin', 'Tarantino', '1963-03-27', 'Stati Uniti');
insert into regista (id, nome, cognome, data_nascita, nazionalita) values (nextval('regista_seq'), 'Federico', 'Fellini', '1920-01-20', 'Italia');
insert into regista (id, nome, cognome, data_nascita, nazionalita) values (nextval('regista_seq'), 'Sergio', 'Leone', '1929-01-03', 'Italia');
insert into regista (id, nome, cognome, data_nascita, nazionalita) values (nextval('regista_seq'), 'Giuseppe', 'Tornatore', '1956-05-27', 'Italia');
insert into regista (id, nome, cognome, data_nascita, nazionalita) values (nextval('regista_seq'), 'Paolo', 'Sorrentino', '1970-05-31', 'Italia');
insert into regista (id, nome, cognome, data_nascita, nazionalita) values (nextval('regista_seq'), 'Christopher', 'Nolan', '1970-07-30', 'Regno Unito');
insert into regista (id, nome, cognome, data_nascita, nazionalita) values (nextval('regista_seq'), 'Bong', 'Joon-ho', '1969-09-14', 'Corea del Sud');

-- Attori
insert into attore (id, nome, cognome, data_nascita, nazionalita) values (nextval('attore_seq'), 'Marlon', 'Brando', '1924-04-03', 'Stati Uniti');
insert into attore (id, nome, cognome, data_nascita, nazionalita) values (nextval('attore_seq'), 'Al', 'Pacino', '1940-04-25', 'Stati Uniti');
insert into attore (id, nome, cognome, data_nascita, nazionalita) values (nextval('attore_seq'), 'James', 'Caan', '1940-03-26', 'Stati Uniti');
insert into attore (id, nome, cognome, data_nascita, nazionalita) values (nextval('attore_seq'), 'Diane', 'Keaton', '1946-01-05', 'Stati Uniti');
insert into attore (id, nome, cognome, data_nascita, nazionalita) values (nextval('attore_seq'), 'Robert', 'De Niro', '1943-08-17', 'Stati Uniti');
insert into attore (id, nome, cognome, data_nascita, nazionalita) values (nextval('attore_seq'), 'Jodie', 'Foster', '1962-11-19', 'Stati Uniti');
insert into attore (id, nome, cognome, data_nascita, nazionalita) values (nextval('attore_seq'), 'John', 'Travolta', '1954-02-18', 'Stati Uniti');
insert into attore (id, nome, cognome, data_nascita, nazionalita) values (nextval('attore_seq'), 'Samuel L.', 'Jackson', '1948-12-21', 'Stati Uniti');
insert into attore (id, nome, cognome, data_nascita, nazionalita) values (nextval('attore_seq'), 'Uma', 'Thurman', '1970-04-29', 'Stati Uniti');
insert into attore (id, nome, cognome, data_nascita, nazionalita) values (nextval('attore_seq'), 'Marcello', 'Mastroianni', '1924-09-28', 'Italia');
insert into attore (id, nome, cognome, data_nascita, nazionalita) values (nextval('attore_seq'), 'Anita', 'Ekberg', '1931-09-29', 'Svezia');
insert into attore (id, nome, cognome, data_nascita, nazionalita) values (nextval('attore_seq'), 'Claudia', 'Cardinale', '1938-04-15', 'Italia');
insert into attore (id, nome, cognome, data_nascita, nazionalita) values (nextval('attore_seq'), 'Clint', 'Eastwood', '1930-05-31', 'Stati Uniti');
insert into attore (id, nome, cognome, data_nascita, nazionalita) values (nextval('attore_seq'), 'Eli', 'Wallach', '1915-12-07', 'Stati Uniti');
insert into attore (id, nome, cognome, data_nascita, nazionalita) values (nextval('attore_seq'), 'Lee', 'Van Cleef', '1925-01-09', 'Stati Uniti');
insert into attore (id, nome, cognome, data_nascita, nazionalita) values (nextval('attore_seq'), 'James', 'Woods', '1947-04-18', 'Stati Uniti');
insert into attore (id, nome, cognome, data_nascita, nazionalita) values (nextval('attore_seq'), 'Philippe', 'Noiret', '1930-10-01', 'Francia');
insert into attore (id, nome, cognome, data_nascita, nazionalita) values (nextval('attore_seq'), 'Salvatore', 'Cascio', '1979-11-07', 'Italia');
insert into attore (id, nome, cognome, data_nascita, nazionalita) values (nextval('attore_seq'), 'Toni', 'Servillo', '1959-01-25', 'Italia');
insert into attore (id, nome, cognome, data_nascita, nazionalita) values (nextval('attore_seq'), 'Leonardo', 'DiCaprio', '1974-11-11', 'Stati Uniti');
insert into attore (id, nome, cognome, data_nascita, nazionalita) values (nextval('attore_seq'), 'Joseph', 'Gordon-Levitt', '1981-02-17', 'Stati Uniti');
insert into attore (id, nome, cognome, data_nascita, nazionalita) values (nextval('attore_seq'), 'Margot', 'Robbie', '1990-07-02', 'Australia');
insert into attore (id, nome, cognome, data_nascita, nazionalita) values (nextval('attore_seq'), 'Song', 'Kang-ho', '1967-01-17', 'Corea del Sud');
insert into attore (id, nome, cognome, data_nascita, nazionalita) values (nextval('attore_seq'), 'Choi', 'Woo-shik', '1990-03-26', 'Corea del Sud');

-- Film
insert into film (id, titolo, anno, durata, genere, paese_produzione, regista_id) values (nextval('film_seq'), 'Il padrino', 1972, 175, 'Drammatico', 'Stati Uniti', (select id from regista where cognome = 'Coppola'));
insert into film (id, titolo, anno, durata, genere, paese_produzione, regista_id) values (nextval('film_seq'), 'Il padrino - Parte II', 1974, 202, 'Drammatico', 'Stati Uniti', (select id from regista where cognome = 'Coppola'));
insert into film (id, titolo, anno, durata, genere, paese_produzione, regista_id) values (nextval('film_seq'), 'Taxi Driver', 1976, 114, 'Drammatico', 'Stati Uniti', (select id from regista where cognome = 'Scorsese'));
insert into film (id, titolo, anno, durata, genere, paese_produzione, regista_id) values (nextval('film_seq'), 'Pulp Fiction', 1994, 154, 'Gangster', 'Stati Uniti', (select id from regista where cognome = 'Tarantino'));
insert into film (id, titolo, anno, durata, genere, paese_produzione, regista_id) values (nextval('film_seq'), 'La dolce vita', 1960, 174, 'Drammatico', 'Italia', (select id from regista where cognome = 'Fellini'));
insert into film (id, titolo, anno, durata, genere, paese_produzione, regista_id) values (nextval('film_seq'), '8½', 1963, 138, 'Drammatico', 'Italia', (select id from regista where cognome = 'Fellini'));
insert into film (id, titolo, anno, durata, genere, paese_produzione, regista_id) values (nextval('film_seq'), 'Il buono, il brutto, il cattivo', 1966, 178, 'Western', 'Italia', (select id from regista where cognome = 'Leone'));
insert into film (id, titolo, anno, durata, genere, paese_produzione, regista_id) values (nextval('film_seq'), 'C''era una volta in America', 1984, 229, 'Gangster', 'Italia', (select id from regista where cognome = 'Leone'));
insert into film (id, titolo, anno, durata, genere, paese_produzione, regista_id) values (nextval('film_seq'), 'Nuovo Cinema Paradiso', 1988, 155, 'Drammatico', 'Italia', (select id from regista where cognome = 'Tornatore'));
insert into film (id, titolo, anno, durata, genere, paese_produzione, regista_id) values (nextval('film_seq'), 'La grande bellezza', 2013, 141, 'Drammatico', 'Italia', (select id from regista where cognome = 'Sorrentino'));
insert into film (id, titolo, anno, durata, genere, paese_produzione, regista_id) values (nextval('film_seq'), 'Inception', 2010, 148, 'Fantascienza', 'Stati Uniti', (select id from regista where cognome = 'Nolan'));
insert into film (id, titolo, anno, durata, genere, paese_produzione, regista_id) values (nextval('film_seq'), 'The Wolf of Wall Street', 2013, 180, 'Commedia', 'Stati Uniti', (select id from regista where cognome = 'Scorsese'));
insert into film (id, titolo, anno, durata, genere, paese_produzione, regista_id) values (nextval('film_seq'), 'Parasite', 2019, 132, 'Thriller', 'Corea del Sud', (select id from regista where cognome = 'Joon-ho'));

-- Interpretazioni: quale attore interpreta quale personaggio in quale film
insert into interpretazione (id, personaggio, film_id, attore_id) values (nextval('interpretazione_seq'), 'Vito Corleone', (select id from film where titolo = 'Il padrino'), (select id from attore where cognome = 'Brando'));
insert into interpretazione (id, personaggio, film_id, attore_id) values (nextval('interpretazione_seq'), 'Michael Corleone', (select id from film where titolo = 'Il padrino'), (select id from attore where cognome = 'Pacino'));
insert into interpretazione (id, personaggio, film_id, attore_id) values (nextval('interpretazione_seq'), 'Sonny Corleone', (select id from film where titolo = 'Il padrino'), (select id from attore where cognome = 'Caan'));
insert into interpretazione (id, personaggio, film_id, attore_id) values (nextval('interpretazione_seq'), 'Kay Adams', (select id from film where titolo = 'Il padrino'), (select id from attore where cognome = 'Keaton'));
insert into interpretazione (id, personaggio, film_id, attore_id) values (nextval('interpretazione_seq'), 'Michael Corleone', (select id from film where titolo = 'Il padrino - Parte II'), (select id from attore where cognome = 'Pacino'));
insert into interpretazione (id, personaggio, film_id, attore_id) values (nextval('interpretazione_seq'), 'Vito Corleone da giovane', (select id from film where titolo = 'Il padrino - Parte II'), (select id from attore where cognome = 'De Niro'));
insert into interpretazione (id, personaggio, film_id, attore_id) values (nextval('interpretazione_seq'), 'Kay Adams', (select id from film where titolo = 'Il padrino - Parte II'), (select id from attore where cognome = 'Keaton'));
insert into interpretazione (id, personaggio, film_id, attore_id) values (nextval('interpretazione_seq'), 'Travis Bickle', (select id from film where titolo = 'Taxi Driver'), (select id from attore where cognome = 'De Niro'));
insert into interpretazione (id, personaggio, film_id, attore_id) values (nextval('interpretazione_seq'), 'Iris', (select id from film where titolo = 'Taxi Driver'), (select id from attore where cognome = 'Foster'));
insert into interpretazione (id, personaggio, film_id, attore_id) values (nextval('interpretazione_seq'), 'Vincent Vega', (select id from film where titolo = 'Pulp Fiction'), (select id from attore where cognome = 'Travolta'));
insert into interpretazione (id, personaggio, film_id, attore_id) values (nextval('interpretazione_seq'), 'Jules Winnfield', (select id from film where titolo = 'Pulp Fiction'), (select id from attore where cognome = 'Jackson'));
insert into interpretazione (id, personaggio, film_id, attore_id) values (nextval('interpretazione_seq'), 'Mia Wallace', (select id from film where titolo = 'Pulp Fiction'), (select id from attore where cognome = 'Thurman'));
insert into interpretazione (id, personaggio, film_id, attore_id) values (nextval('interpretazione_seq'), 'Marcello Rubini', (select id from film where titolo = 'La dolce vita'), (select id from attore where cognome = 'Mastroianni'));
insert into interpretazione (id, personaggio, film_id, attore_id) values (nextval('interpretazione_seq'), 'Sylvia', (select id from film where titolo = 'La dolce vita'), (select id from attore where cognome = 'Ekberg'));
insert into interpretazione (id, personaggio, film_id, attore_id) values (nextval('interpretazione_seq'), 'Guido Anselmi', (select id from film where titolo = '8½'), (select id from attore where cognome = 'Mastroianni'));
insert into interpretazione (id, personaggio, film_id, attore_id) values (nextval('interpretazione_seq'), 'Claudia', (select id from film where titolo = '8½'), (select id from attore where cognome = 'Cardinale'));
insert into interpretazione (id, personaggio, film_id, attore_id) values (nextval('interpretazione_seq'), 'il Biondo', (select id from film where titolo = 'Il buono, il brutto, il cattivo'), (select id from attore where cognome = 'Eastwood'));
insert into interpretazione (id, personaggio, film_id, attore_id) values (nextval('interpretazione_seq'), 'Tuco', (select id from film where titolo = 'Il buono, il brutto, il cattivo'), (select id from attore where cognome = 'Wallach'));
insert into interpretazione (id, personaggio, film_id, attore_id) values (nextval('interpretazione_seq'), 'Sentenza', (select id from film where titolo = 'Il buono, il brutto, il cattivo'), (select id from attore where cognome = 'Van Cleef'));
insert into interpretazione (id, personaggio, film_id, attore_id) values (nextval('interpretazione_seq'), 'David "Noodles" Aaronson', (select id from film where titolo = 'C''era una volta in America'), (select id from attore where cognome = 'De Niro'));
insert into interpretazione (id, personaggio, film_id, attore_id) values (nextval('interpretazione_seq'), 'Max', (select id from film where titolo = 'C''era una volta in America'), (select id from attore where cognome = 'Woods'));
insert into interpretazione (id, personaggio, film_id, attore_id) values (nextval('interpretazione_seq'), 'Alfredo', (select id from film where titolo = 'Nuovo Cinema Paradiso'), (select id from attore where cognome = 'Noiret'));
insert into interpretazione (id, personaggio, film_id, attore_id) values (nextval('interpretazione_seq'), 'Salvatore da bambino', (select id from film where titolo = 'Nuovo Cinema Paradiso'), (select id from attore where cognome = 'Cascio'));
insert into interpretazione (id, personaggio, film_id, attore_id) values (nextval('interpretazione_seq'), 'Jep Gambardella', (select id from film where titolo = 'La grande bellezza'), (select id from attore where cognome = 'Servillo'));
insert into interpretazione (id, personaggio, film_id, attore_id) values (nextval('interpretazione_seq'), 'Dom Cobb', (select id from film where titolo = 'Inception'), (select id from attore where cognome = 'DiCaprio'));
insert into interpretazione (id, personaggio, film_id, attore_id) values (nextval('interpretazione_seq'), 'Arthur', (select id from film where titolo = 'Inception'), (select id from attore where cognome = 'Gordon-Levitt'));
insert into interpretazione (id, personaggio, film_id, attore_id) values (nextval('interpretazione_seq'), 'Jordan Belfort', (select id from film where titolo = 'The Wolf of Wall Street'), (select id from attore where cognome = 'DiCaprio'));
insert into interpretazione (id, personaggio, film_id, attore_id) values (nextval('interpretazione_seq'), 'Naomi Lapaglia', (select id from film where titolo = 'The Wolf of Wall Street'), (select id from attore where cognome = 'Robbie'));
insert into interpretazione (id, personaggio, film_id, attore_id) values (nextval('interpretazione_seq'), 'Kim Ki-taek', (select id from film where titolo = 'Parasite'), (select id from attore where cognome = 'Kang-ho'));
insert into interpretazione (id, personaggio, film_id, attore_id) values (nextval('interpretazione_seq'), 'Kim Ki-woo', (select id from film where titolo = 'Parasite'), (select id from attore where cognome = 'Woo-shik'));

-- Festival
insert into festival (id, nome, anno, citta, data_inizio, data_fine, descrizione) values (nextval('festival_seq'), 'Ciak Roma Festival', 2026, 'Roma', '2026-04-15', '2026-04-18', 'Quattro giorni di grandi classici restaurati nel centro di Roma.');
insert into festival (id, nome, anno, citta, data_inizio, data_fine, descrizione) values (nextval('festival_seq'), 'Milano Film Week', 2026, 'Milano', '2026-11-19', '2026-11-22', 'Una settimana di cinema d''autore italiano e internazionale.');
insert into festival (id, nome, anno, citta, data_inizio, data_fine, descrizione) values (nextval('festival_seq'), 'Torino Classic Days', 2026, 'Torino', '2026-12-03', '2026-12-06', 'Il festival dei grandi registi, tra western e gangster movie.');
insert into festival (id, nome, anno, citta, data_inizio, data_fine, descrizione) values (nextval('festival_seq'), 'Napoli Cinema Mare', 2027, 'Napoli', '2027-03-11', '2027-03-14', 'Proiezioni sul lungomare dedicate al cinema italiano.');
insert into festival (id, nome, anno, citta, data_inizio, data_fine, descrizione) values (nextval('festival_seq'), 'Bologna Film Restaurati', 2027, 'Bologna', '2027-06-24', '2027-06-27', 'Le copie restaurate dei capolavori del Novecento.');

-- Film partecipanti a ogni festival
insert into festival_films (festivals_id, films_id) values ((select id from festival where nome = 'Ciak Roma Festival'), (select id from film where titolo = 'Il padrino'));
insert into festival_films (festivals_id, films_id) values ((select id from festival where nome = 'Ciak Roma Festival'), (select id from film where titolo = 'La dolce vita'));
insert into festival_films (festivals_id, films_id) values ((select id from festival where nome = 'Ciak Roma Festival'), (select id from film where titolo = '8½'));
insert into festival_films (festivals_id, films_id) values ((select id from festival where nome = 'Ciak Roma Festival'), (select id from film where titolo = 'Nuovo Cinema Paradiso'));
insert into festival_films (festivals_id, films_id) values ((select id from festival where nome = 'Ciak Roma Festival'), (select id from film where titolo = 'Pulp Fiction'));
insert into festival_films (festivals_id, films_id) values ((select id from festival where nome = 'Milano Film Week'), (select id from film where titolo = 'Il padrino'));
insert into festival_films (festivals_id, films_id) values ((select id from festival where nome = 'Milano Film Week'), (select id from film where titolo = 'Il padrino - Parte II'));
insert into festival_films (festivals_id, films_id) values ((select id from festival where nome = 'Milano Film Week'), (select id from film where titolo = 'Taxi Driver'));
insert into festival_films (festivals_id, films_id) values ((select id from festival where nome = 'Milano Film Week'), (select id from film where titolo = 'La grande bellezza'));
insert into festival_films (festivals_id, films_id) values ((select id from festival where nome = 'Milano Film Week'), (select id from film where titolo = 'Inception'));
insert into festival_films (festivals_id, films_id) values ((select id from festival where nome = 'Milano Film Week'), (select id from film where titolo = 'Parasite'));
insert into festival_films (festivals_id, films_id) values ((select id from festival where nome = 'Torino Classic Days'), (select id from film where titolo = 'Il buono, il brutto, il cattivo'));
insert into festival_films (festivals_id, films_id) values ((select id from festival where nome = 'Torino Classic Days'), (select id from film where titolo = 'C''era una volta in America'));
insert into festival_films (festivals_id, films_id) values ((select id from festival where nome = 'Torino Classic Days'), (select id from film where titolo = 'Taxi Driver'));
insert into festival_films (festivals_id, films_id) values ((select id from festival where nome = 'Torino Classic Days'), (select id from film where titolo = 'Pulp Fiction'));
insert into festival_films (festivals_id, films_id) values ((select id from festival where nome = 'Torino Classic Days'), (select id from film where titolo = 'The Wolf of Wall Street'));
insert into festival_films (festivals_id, films_id) values ((select id from festival where nome = 'Napoli Cinema Mare'), (select id from film where titolo = 'La dolce vita'));
insert into festival_films (festivals_id, films_id) values ((select id from festival where nome = 'Napoli Cinema Mare'), (select id from film where titolo = 'La grande bellezza'));
insert into festival_films (festivals_id, films_id) values ((select id from festival where nome = 'Napoli Cinema Mare'), (select id from film where titolo = 'Nuovo Cinema Paradiso'));
insert into festival_films (festivals_id, films_id) values ((select id from festival where nome = 'Napoli Cinema Mare'), (select id from film where titolo = '8½'));
insert into festival_films (festivals_id, films_id) values ((select id from festival where nome = 'Napoli Cinema Mare'), (select id from film where titolo = 'Il buono, il brutto, il cattivo'));
insert into festival_films (festivals_id, films_id) values ((select id from festival where nome = 'Bologna Film Restaurati'), (select id from film where titolo = 'Il padrino'));
insert into festival_films (festivals_id, films_id) values ((select id from festival where nome = 'Bologna Film Restaurati'), (select id from film where titolo = 'C''era una volta in America'));
insert into festival_films (festivals_id, films_id) values ((select id from festival where nome = 'Bologna Film Restaurati'), (select id from film where titolo = 'Parasite'));
insert into festival_films (festivals_id, films_id) values ((select id from festival where nome = 'Bologna Film Restaurati'), (select id from film where titolo = 'Inception'));
insert into festival_films (festivals_id, films_id) values ((select id from festival where nome = 'Bologna Film Restaurati'), (select id from film where titolo = 'La dolce vita'));

-- Sale
insert into sala (id, nome, indirizzo, capienza) values (nextval('sala_seq'), 'Sala Fellini', 'Via Veneto 120, Roma', 300);
insert into sala (id, nome, indirizzo, capienza) values (nextval('sala_seq'), 'Sala Magnani', 'Via Veneto 120, Roma', 120);
insert into sala (id, nome, indirizzo, capienza) values (nextval('sala_seq'), 'Cinema Odeon', 'Via Santa Radegonda 8, Milano', 450);
insert into sala (id, nome, indirizzo, capienza) values (nextval('sala_seq'), 'Sala Visconti', 'Via Santa Radegonda 8, Milano', 150);
insert into sala (id, nome, indirizzo, capienza) values (nextval('sala_seq'), 'Sala Mole', 'Via Montebello 20, Torino', 280);
insert into sala (id, nome, indirizzo, capienza) values (nextval('sala_seq'), 'Sala Vesuvio', 'Via Caracciolo 10, Napoli', 220);
insert into sala (id, nome, indirizzo, capienza) values (nextval('sala_seq'), 'Sala Lumière', 'Piazzetta Pasolini 2, Bologna', 200);

-- Proiezioni
insert into proiezione (id, data, ora, ora_fine, stato, festival_id, film_id, sala_id) values (nextval('proiezione_seq'), '2026-04-15', '18:00:00', '20:55:00', 'COMPLETED', (select id from festival where nome = 'Ciak Roma Festival'), (select id from film where titolo = 'Il padrino'), (select id from sala where nome = 'Sala Fellini'));
insert into proiezione (id, data, ora, ora_fine, stato, festival_id, film_id, sala_id) values (nextval('proiezione_seq'), '2026-04-15', '20:45:00', '23:19:00', 'COMPLETED', (select id from festival where nome = 'Ciak Roma Festival'), (select id from film where titolo = 'Pulp Fiction'), (select id from sala where nome = 'Sala Magnani'));
insert into proiezione (id, data, ora, ora_fine, stato, festival_id, film_id, sala_id) values (nextval('proiezione_seq'), '2026-04-16', '20:00:00', '22:54:00', 'COMPLETED', (select id from festival where nome = 'Ciak Roma Festival'), (select id from film where titolo = 'La dolce vita'), (select id from sala where nome = 'Sala Fellini'));
insert into proiezione (id, data, ora, ora_fine, stato, festival_id, film_id, sala_id) values (nextval('proiezione_seq'), '2026-04-17', '17:00:00', '19:18:00', 'CANCELLED', (select id from festival where nome = 'Ciak Roma Festival'), (select id from film where titolo = '8½'), (select id from sala where nome = 'Sala Magnani'));
insert into proiezione (id, data, ora, ora_fine, stato, festival_id, film_id, sala_id) values (nextval('proiezione_seq'), '2026-04-18', '21:00:00', '23:35:00', 'COMPLETED', (select id from festival where nome = 'Ciak Roma Festival'), (select id from film where titolo = 'Nuovo Cinema Paradiso'), (select id from sala where nome = 'Sala Fellini'));
insert into proiezione (id, data, ora, ora_fine, stato, festival_id, film_id, sala_id) values (nextval('proiezione_seq'), '2026-11-19', '16:00:00', '18:55:00', 'SCHEDULED', (select id from festival where nome = 'Milano Film Week'), (select id from film where titolo = 'Il padrino'), (select id from sala where nome = 'Cinema Odeon'));
insert into proiezione (id, data, ora, ora_fine, stato, festival_id, film_id, sala_id) values (nextval('proiezione_seq'), '2026-11-19', '21:00:00', '23:28:00', 'SCHEDULED', (select id from festival where nome = 'Milano Film Week'), (select id from film where titolo = 'Inception'), (select id from sala where nome = 'Cinema Odeon'));
insert into proiezione (id, data, ora, ora_fine, stato, festival_id, film_id, sala_id) values (nextval('proiezione_seq'), '2026-11-19', '21:00:00', '22:54:00', 'SCHEDULED', (select id from festival where nome = 'Milano Film Week'), (select id from film where titolo = 'Taxi Driver'), (select id from sala where nome = 'Sala Visconti'));
insert into proiezione (id, data, ora, ora_fine, stato, festival_id, film_id, sala_id) values (nextval('proiezione_seq'), '2026-11-20', '15:30:00', '18:52:00', 'SCHEDULED', (select id from festival where nome = 'Milano Film Week'), (select id from film where titolo = 'Il padrino - Parte II'), (select id from sala where nome = 'Cinema Odeon'));
insert into proiezione (id, data, ora, ora_fine, stato, festival_id, film_id, sala_id) values (nextval('proiezione_seq'), '2026-11-21', '18:00:00', '20:21:00', 'SCHEDULED', (select id from festival where nome = 'Milano Film Week'), (select id from film where titolo = 'La grande bellezza'), (select id from sala where nome = 'Sala Visconti'));
insert into proiezione (id, data, ora, ora_fine, stato, festival_id, film_id, sala_id) values (nextval('proiezione_seq'), '2026-11-22', '20:30:00', '22:42:00', 'SCHEDULED', (select id from festival where nome = 'Milano Film Week'), (select id from film where titolo = 'Parasite'), (select id from sala where nome = 'Cinema Odeon'));
insert into proiezione (id, data, ora, ora_fine, stato, festival_id, film_id, sala_id) values (nextval('proiezione_seq'), '2026-12-03', '17:00:00', '19:58:00', 'SCHEDULED', (select id from festival where nome = 'Torino Classic Days'), (select id from film where titolo = 'Il buono, il brutto, il cattivo'), (select id from sala where nome = 'Sala Mole'));
insert into proiezione (id, data, ora, ora_fine, stato, festival_id, film_id, sala_id) values (nextval('proiezione_seq'), '2026-12-04', '16:00:00', '19:49:00', 'SCHEDULED', (select id from festival where nome = 'Torino Classic Days'), (select id from film where titolo = 'C''era una volta in America'), (select id from sala where nome = 'Sala Mole'));
insert into proiezione (id, data, ora, ora_fine, stato, festival_id, film_id, sala_id) values (nextval('proiezione_seq'), '2026-12-05', '18:00:00', '19:54:00', 'SCHEDULED', (select id from festival where nome = 'Torino Classic Days'), (select id from film where titolo = 'Taxi Driver'), (select id from sala where nome = 'Sala Mole'));
insert into proiezione (id, data, ora, ora_fine, stato, festival_id, film_id, sala_id) values (nextval('proiezione_seq'), '2026-12-05', '21:00:00', '23:34:00', 'SCHEDULED', (select id from festival where nome = 'Torino Classic Days'), (select id from film where titolo = 'Pulp Fiction'), (select id from sala where nome = 'Sala Mole'));
insert into proiezione (id, data, ora, ora_fine, stato, festival_id, film_id, sala_id) values (nextval('proiezione_seq'), '2026-12-06', '20:00:00', '23:00:00', 'SCHEDULED', (select id from festival where nome = 'Torino Classic Days'), (select id from film where titolo = 'The Wolf of Wall Street'), (select id from sala where nome = 'Sala Mole'));
insert into proiezione (id, data, ora, ora_fine, stato, festival_id, film_id, sala_id) values (nextval('proiezione_seq'), '2027-03-11', '20:30:00', '22:51:00', 'SCHEDULED', (select id from festival where nome = 'Napoli Cinema Mare'), (select id from film where titolo = 'La grande bellezza'), (select id from sala where nome = 'Sala Vesuvio'));
insert into proiezione (id, data, ora, ora_fine, stato, festival_id, film_id, sala_id) values (nextval('proiezione_seq'), '2027-03-12', '18:00:00', '20:35:00', 'SCHEDULED', (select id from festival where nome = 'Napoli Cinema Mare'), (select id from film where titolo = 'Nuovo Cinema Paradiso'), (select id from sala where nome = 'Sala Vesuvio'));
insert into proiezione (id, data, ora, ora_fine, stato, festival_id, film_id, sala_id) values (nextval('proiezione_seq'), '2027-03-13', '17:00:00', '19:54:00', 'SCHEDULED', (select id from festival where nome = 'Napoli Cinema Mare'), (select id from film where titolo = 'La dolce vita'), (select id from sala where nome = 'Sala Vesuvio'));
insert into proiezione (id, data, ora, ora_fine, stato, festival_id, film_id, sala_id) values (nextval('proiezione_seq'), '2027-03-14', '16:00:00', '18:58:00', 'SCHEDULED', (select id from festival where nome = 'Napoli Cinema Mare'), (select id from film where titolo = 'Il buono, il brutto, il cattivo'), (select id from sala where nome = 'Sala Vesuvio'));
insert into proiezione (id, data, ora, ora_fine, stato, festival_id, film_id, sala_id) values (nextval('proiezione_seq'), '2027-06-24', '21:00:00', '23:55:00', 'SCHEDULED', (select id from festival where nome = 'Bologna Film Restaurati'), (select id from film where titolo = 'Il padrino'), (select id from sala where nome = 'Sala Lumière'));
insert into proiezione (id, data, ora, ora_fine, stato, festival_id, film_id, sala_id) values (nextval('proiezione_seq'), '2027-06-25', '18:30:00', '20:42:00', 'SCHEDULED', (select id from festival where nome = 'Bologna Film Restaurati'), (select id from film where titolo = 'Parasite'), (select id from sala where nome = 'Sala Lumière'));
insert into proiezione (id, data, ora, ora_fine, stato, festival_id, film_id, sala_id) values (nextval('proiezione_seq'), '2027-06-26', '17:00:00', '20:49:00', 'SCHEDULED', (select id from festival where nome = 'Bologna Film Restaurati'), (select id from film where titolo = 'C''era una volta in America'), (select id from sala where nome = 'Sala Lumière'));
insert into proiezione (id, data, ora, ora_fine, stato, festival_id, film_id, sala_id) values (nextval('proiezione_seq'), '2027-06-27', '21:00:00', '23:28:00', 'SCHEDULED', (select id from festival where nome = 'Bologna Film Restaurati'), (select id from film where titolo = 'Inception'), (select id from sala where nome = 'Sala Lumière'));

-- Recensioni (gli utenti sono creati da UtentiIniziali, che parte prima)
insert into recensione (id, testo, voto, data, film_id, autore_id) values (nextval('recensione_seq'), 'Un capolavoro senza tempo, rivisto in sala è un''altra cosa.', 5, '2026-04-15', (select id from film where titolo = 'Il padrino'), (select id from users where email = 'mario@example.com'));
insert into recensione (id, testo, voto, data, film_id, autore_id) values (nextval('recensione_seq'), 'Brando è impressionante, ma il film è lungo.', 4, '2026-04-15', (select id from film where titolo = 'Il padrino'), (select id from users where email = 'giulia@example.com'));
insert into recensione (id, testo, voto, data, film_id, autore_id) values (nextval('recensione_seq'), 'Il miglior film di sempre, non ho altro da aggiungere.', 5, '2026-04-16', (select id from film where titolo = 'Il padrino'), (select id from users where email = 'luca@example.com'));
insert into recensione (id, testo, voto, data, film_id, autore_id) values (nextval('recensione_seq'), 'Dialoghi indimenticabili e una colonna sonora perfetta.', 5, '2026-04-15', (select id from film where titolo = 'Pulp Fiction'), (select id from users where email = 'mario@example.com'));
insert into recensione (id, testo, voto, data, film_id, autore_id) values (nextval('recensione_seq'), 'Troppo violento per i miei gusti, però girato benissimo.', 3, '2026-04-16', (select id from film where titolo = 'Pulp Fiction'), (select id from users where email = 'sara@example.com'));
insert into recensione (id, testo, voto, data, film_id, autore_id) values (nextval('recensione_seq'), 'La scena della Fontana di Trevi vale da sola il biglietto.', 5, '2026-04-16', (select id from film where titolo = 'La dolce vita'), (select id from users where email = 'giulia@example.com'));
insert into recensione (id, testo, voto, data, film_id, autore_id) values (nextval('recensione_seq'), 'Affascinante, anche se a tratti lento.', 4, '2026-04-17', (select id from film where titolo = 'La dolce vita'), (select id from users where email = 'luca@example.com'));
insert into recensione (id, testo, voto, data, film_id, autore_id) values (nextval('recensione_seq'), 'Il finale con i baci tagliati mi ha fatto piangere.', 5, '2026-04-18', (select id from film where titolo = 'Nuovo Cinema Paradiso'), (select id from users where email = 'sara@example.com'));
insert into recensione (id, testo, voto, data, film_id, autore_id) values (nextval('recensione_seq'), 'Una lettera d''amore al cinema.', 5, '2026-04-18', (select id from film where titolo = 'Nuovo Cinema Paradiso'), (select id from users where email = 'mario@example.com'));
insert into recensione (id, testo, voto, data, film_id, autore_id) values (nextval('recensione_seq'), 'Ancora oggi uno dei film più originali che abbia visto.', 4, '2026-05-02', (select id from film where titolo = 'Inception'), (select id from users where email = 'luca@example.com'));