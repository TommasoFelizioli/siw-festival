package it.uniroma3.siw.service;

import java.time.LocalDate;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import it.uniroma3.siw.exception.OperazioneNonConsentitaException;
import it.uniroma3.siw.exception.RecensioneDuplicataException;
import it.uniroma3.siw.model.Credentials;
import it.uniroma3.siw.model.Film;
import it.uniroma3.siw.model.Recensione;
import it.uniroma3.siw.model.User;
import it.uniroma3.siw.repository.CredentialsRepository;
import it.uniroma3.siw.repository.FilmRepository;
import it.uniroma3.siw.repository.RecensioneRepository;

@Service
public class RecensioneService {

	private RecensioneRepository recensioneRepository;
	private FilmRepository filmRepository;
	private CredentialsRepository credentialsRepository;

	public RecensioneService(RecensioneRepository recensioneRepository, FilmRepository filmRepository,
			CredentialsRepository credentialsRepository) {
		this.recensioneRepository = recensioneRepository;
		this.filmRepository = filmRepository;
		this.credentialsRepository = credentialsRepository;
	}

	@Transactional(readOnly = true)
	public List<Recensione> findByFilm(Long filmId) {
		return recensioneRepository.findByFilmConAutore(filmId);
	}

	@Transactional
	public Recensione inserisci(Long filmId, String username, Recensione recensione) {
		Film film = filmRepository.findById(filmId).orElse(null);
		Credentials credentials = credentialsRepository.findByUsername(username).orElse(null);
		if (film == null || credentials == null) {
			return null;
		}
		User autore = credentials.getUser();

		if (recensioneRepository.existsByAutoreIdAndFilmId(autore.getId(), filmId)) {
			throw new RecensioneDuplicataException("Hai già recensito questo film");
		}

		recensione.setData(LocalDate.now());
		recensione.setFilm(film);
		recensione.setAutore(autore);
		return recensioneRepository.save(recensione);
	}

	@Transactional(readOnly = true)
	public Recensione findPropria(Long recensioneId, String username) {
		return caricaPropria(recensioneId, username);
	}

	@Transactional
	public Long modifica(Long recensioneId, String username, Recensione nuoviDati) {
		Recensione recensione = caricaPropria(recensioneId, username);
		recensione.setVoto(nuoviDati.getVoto());
		recensione.setTesto(nuoviDati.getTesto());
		return recensione.getFilm().getId();
	}

	@Transactional
	public Long elimina(Long recensioneId, String username) {
		Recensione recensione = caricaPropria(recensioneId, username);
		Long filmId = recensione.getFilm().getId();
		recensioneRepository.delete(recensione);
		return filmId;
	}

	private Recensione caricaPropria(Long recensioneId, String username) {
		Recensione recensione = recensioneRepository.findByIdConFilmEAutore(recensioneId).orElse(null);
		Credentials credentials = credentialsRepository.findByUsername(username).orElse(null);
		if (recensione == null || credentials == null) {
			throw new OperazioneNonConsentitaException("Recensione inesistente");
		}
		if (!recensione.getAutore().getId().equals(credentials.getUser().getId())) {
			throw new OperazioneNonConsentitaException("Puoi modificare o eliminare solo le tue recensioni");
		}
		return recensione;
	}
}