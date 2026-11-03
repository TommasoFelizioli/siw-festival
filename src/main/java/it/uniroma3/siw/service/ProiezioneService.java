package it.uniroma3.siw.service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import it.uniroma3.siw.exception.ProiezioneNonValidaException;
import it.uniroma3.siw.model.Festival;
import it.uniroma3.siw.model.Film;
import it.uniroma3.siw.model.Proiezione;
import it.uniroma3.siw.model.Sala;
import it.uniroma3.siw.model.StatoProiezione;
import it.uniroma3.siw.repository.FestivalRepository;
import it.uniroma3.siw.repository.FilmRepository;
import it.uniroma3.siw.repository.ProiezioneRepository;
import it.uniroma3.siw.repository.SalaRepository;

@Service
public class ProiezioneService {

	private ProiezioneRepository proiezioneRepository;
	private FestivalRepository festivalRepository;
	private FilmRepository filmRepository;
	private SalaRepository salaRepository;

	public ProiezioneService(ProiezioneRepository proiezioneRepository, FestivalRepository festivalRepository,
			FilmRepository filmRepository, SalaRepository salaRepository) {
		this.proiezioneRepository = proiezioneRepository;
		this.festivalRepository = festivalRepository;
		this.filmRepository = filmRepository;
		this.salaRepository = salaRepository;
	}

	@Transactional(readOnly = true)
	public List<Proiezione> findProgrammaByFestival(Long festivalId) {
		return proiezioneRepository.findProgrammaByFestival(festivalId);
	}

	@Transactional(readOnly = true)
	public List<Proiezione> findByFilm(Long filmId) {
		return proiezioneRepository.findByFilm(filmId);
	}

	@Transactional(readOnly = true)
	public Proiezione findByIdConDettagli(Long id) {
		return proiezioneRepository.findByIdConDettagli(id).orElse(null);
	}

	@Transactional(isolation = Isolation.SERIALIZABLE)
	public Proiezione programma(Long festivalId, Long filmId, Long salaId, LocalDate data, LocalTime ora) {
		// 1. recupero del festival
		Festival festival = festivalRepository.findById(festivalId).orElse(null);
		if (festival == null) {
			throw new ProiezioneNonValidaException("Festival inesistente");
		}
		// 2. recupero del film e 3. recupero della sala
		Film film = cercaFilm(filmId);
		Sala sala = cercaSala(salaId);

		// 4. verifica delle regole, compresa la disponibilità della sala
		LocalTime oraFine = verifica(festival, film, sala, data, ora, -1L);

		// 5. creazione della proiezione e 6. impostazione delle associazioni
		Proiezione proiezione = new Proiezione();
		proiezione.setData(data);
		proiezione.setOra(ora);
		proiezione.setOraFine(oraFine);
		proiezione.setStato(StatoProiezione.SCHEDULED);
		proiezione.setFestival(festival);
		proiezione.setFilm(film);
		proiezione.setSala(sala);
		return proiezioneRepository.save(proiezione);
	}

	@Transactional(isolation = Isolation.SERIALIZABLE)
	public Proiezione modifica(Long proiezioneId, Long filmId, Long salaId, LocalDate data, LocalTime ora) {
		Proiezione proiezione = proiezioneRepository.findByIdConDettagli(proiezioneId).orElse(null);
		if (proiezione == null) {
			throw new ProiezioneNonValidaException("Proiezione inesistente");
		}
		if (proiezione.getStato() != StatoProiezione.SCHEDULED) {
			throw new ProiezioneNonValidaException("Si possono modificare solo le proiezioni programmate");
		}
		Film film = cercaFilm(filmId);
		Sala sala = cercaSala(salaId);

		LocalTime oraFine = verifica(proiezione.getFestival(), film, sala, data, ora, proiezioneId);

		proiezione.setFilm(film);
		proiezione.setSala(sala);
		proiezione.setData(data);
		proiezione.setOra(ora);
		proiezione.setOraFine(oraFine);
		return proiezione;
	}

	@Transactional
	public Long cancella(Long proiezioneId) {
		Proiezione proiezione = proiezioneRepository.findByIdConDettagli(proiezioneId).orElse(null);
		if (proiezione == null) {
			throw new ProiezioneNonValidaException("Proiezione inesistente");
		}
		if (proiezione.getStato() == StatoProiezione.SCHEDULED) {
			proiezione.setStato(StatoProiezione.CANCELLED);
		}
		return proiezione.getFestival().getId();
	}

	private LocalTime verifica(Festival festival, Film film, Sala sala, LocalDate data, LocalTime ora,
			Long escludiId) {
		if (!festival.getFilms().contains(film)) {
			throw new ProiezioneNonValidaException("Il film non partecipa a questo festival");
		}
		if (data.isBefore(festival.getDataInizio()) || data.isAfter(festival.getDataFine())) {
			throw new ProiezioneNonValidaException("La data deve essere compresa nei giorni del festival");
		}
		if (LocalDateTime.of(data, ora).isBefore(LocalDateTime.now())) {
			throw new ProiezioneNonValidaException("Non si può programmare una proiezione nel passato");
		}
		LocalTime oraFine = ora.plusMinutes(film.getDurata());
		if (!oraFine.isAfter(ora)) {
			throw new ProiezioneNonValidaException("La proiezione deve finire entro la mezzanotte");
		}
		List<Proiezione> sovrapposte = proiezioneRepository.findSovrapposte(sala.getId(), data, ora, oraFine,
				StatoProiezione.CANCELLED, escludiId);
		if (!sovrapposte.isEmpty()) {
			throw new ProiezioneNonValidaException("La sala è già occupata in quell'orario");
		}
		return oraFine;
	}

	private Film cercaFilm(Long filmId) {
		Film film = null;
		if (filmId != null) {
			film = filmRepository.findById(filmId).orElse(null);
		}
		if (film == null) {
			throw new ProiezioneNonValidaException("Scegli un film");
		}
		return film;
	}

	private Sala cercaSala(Long salaId) {
		Sala sala = null;
		if (salaId != null) {
			sala = salaRepository.findById(salaId).orElse(null);
		}
		if (sala == null) {
			throw new ProiezioneNonValidaException("Scegli una sala");
		}
		return sala;
	}
}