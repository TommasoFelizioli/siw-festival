package it.uniroma3.siw.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import it.uniroma3.siw.exception.FestivalNonValidoException;
import it.uniroma3.siw.exception.RimozioneNonConsentitaException;
import it.uniroma3.siw.model.Festival;
import it.uniroma3.siw.model.Film;
import it.uniroma3.siw.model.StatoProiezione;
import it.uniroma3.siw.repository.FestivalRepository;
import it.uniroma3.siw.repository.FilmRepository;
import it.uniroma3.siw.repository.ProiezioneRepository;

@Service
public class FestivalService {

	private FestivalRepository festivalRepository;
	private FilmRepository filmRepository;
	private ProiezioneRepository proiezioneRepository;

	public FestivalService(FestivalRepository festivalRepository, FilmRepository filmRepository,
			ProiezioneRepository proiezioneRepository) {
		this.festivalRepository = festivalRepository;
		this.filmRepository = filmRepository;
		this.proiezioneRepository = proiezioneRepository;
	}

	@Transactional(readOnly = true)
	public List<Festival> findAll() {
		return festivalRepository.findAllByOrderByDataInizioAsc();
	}

	@Transactional(readOnly = true)
	public Festival findById(Long id) {
		return festivalRepository.findById(id).orElse(null);
	}

	@Transactional(readOnly = true)
	public Festival findByIdConFilm(Long id) {
		return festivalRepository.findByIdConFilm(id).orElse(null);
	}

	@Transactional
	public Festival save(Festival festival) {
		verificaDate(festival);
		if (festivalRepository.existsByNomeAndAnno(festival.getNome(), festival.getAnno())) {
			throw new FestivalNonValidoException("Esiste già un festival con lo stesso nome nello stesso anno");
		}
		return festivalRepository.save(festival);
	}

	@Transactional
	public Festival update(Long id, Festival nuoviDati) {
		Festival festival = festivalRepository.findById(id).orElse(null);
		if (festival == null) {
			return null;
		}

		verificaDate(nuoviDati);

		boolean nomeOAnnoCambiati = !festival.getNome().equals(nuoviDati.getNome())
				|| !festival.getAnno().equals(nuoviDati.getAnno());
		if (nomeOAnnoCambiati && festivalRepository.existsByNomeAndAnno(nuoviDati.getNome(), nuoviDati.getAnno())) {
			throw new FestivalNonValidoException("Esiste già un festival con lo stesso nome nello stesso anno");
		}

		long escluse = proiezioneRepository.countFuoriIntervallo(id, StatoProiezione.SCHEDULED,
				nuoviDati.getDataInizio(), nuoviDati.getDataFine());
		if (escluse > 0) {
			throw new FestivalNonValidoException("Ci sono " + escluse
					+ " proiezioni programmate fuori dalle nuove date: cancellale prima di cambiare le date");
		}

		festival.setNome(nuoviDati.getNome());
		festival.setAnno(nuoviDati.getAnno());
		festival.setCitta(nuoviDati.getCitta());
		festival.setDataInizio(nuoviDati.getDataInizio());
		festival.setDataFine(nuoviDati.getDataFine());
		festival.setDescrizione(nuoviDati.getDescrizione());
		return festival;
	}

	@Transactional
	public void aggiungiFilm(Long festivalId, Long filmId) {
		Festival festival = festivalRepository.findById(festivalId).orElse(null);
		Film film = filmRepository.findById(filmId).orElse(null);
		if (festival == null || film == null) {
			return;
		}
		festival.getFilms().add(film);
	}

	@Transactional
	public void rimuoviFilm(Long festivalId, Long filmId) {
		Festival festival = festivalRepository.findById(festivalId).orElse(null);
		Film film = filmRepository.findById(filmId).orElse(null);
		if (festival == null || film == null) {
			return;
		}
		if (proiezioneRepository.existsByFestivalIdAndFilmIdAndStato(festivalId, filmId, StatoProiezione.SCHEDULED)) {
			throw new RimozioneNonConsentitaException(
					"Il film ha proiezioni programmate in questo festival: cancellale prima di rimuoverlo");
		}
		festival.getFilms().remove(film);
	}

	private void verificaDate(Festival festival) {
		if (festival.getDataFine().isBefore(festival.getDataInizio())) {
			throw new FestivalNonValidoException("La data di fine non può precedere la data di inizio");
		}
		if (festival.getDataInizio().getYear() != festival.getAnno()
				|| festival.getDataFine().getYear() != festival.getAnno()) {
			throw new FestivalNonValidoException(
					"Le date devono cadere nell'anno del festival (" + festival.getAnno() + ")");
		}
	}
}