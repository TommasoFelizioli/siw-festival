package it.uniroma3.siw.service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.hibernate.SessionFactory;
import org.hibernate.stat.Statistics;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import it.uniroma3.siw.model.Festival;
import it.uniroma3.siw.model.Film;
import it.uniroma3.siw.model.Regista;
import it.uniroma3.siw.repository.FestivalRepository;
import it.uniroma3.siw.repository.FilmRepository;
import it.uniroma3.siw.repository.RegistaRepository;
import jakarta.persistence.EntityManagerFactory;

@Service
public class AnalisiFetchService {

	private static final int NUMERO_FILM = 100;

	private FestivalRepository festivalRepository;
	private FilmRepository filmRepository;
	private RegistaRepository registaRepository;
	private EntityManagerFactory entityManagerFactory;

	public AnalisiFetchService(FestivalRepository festivalRepository, FilmRepository filmRepository,
			RegistaRepository registaRepository, EntityManagerFactory entityManagerFactory) {
		this.festivalRepository = festivalRepository;
		this.filmRepository = filmRepository;
		this.registaRepository = registaRepository;
		this.entityManagerFactory = entityManagerFactory;
	}

	@Transactional
	public Long preparaDati() {
		Festival festival = new Festival();
		festival.setNome("Festival di prova N+1");
		festival.setAnno(2099);
		festival.setCitta("Test");
		festival.setDataInizio(LocalDate.of(2099, 1, 1));
		festival.setDataFine(LocalDate.of(2099, 1, 2));

		for (int i = 1; i <= NUMERO_FILM; i++) {
			Regista regista = new Regista();
			regista.setNome("Regista");
			regista.setCognome("di prova " + i);
			registaRepository.save(regista);

			Film film = new Film();
			film.setTitolo("Film di prova " + i);
			film.setAnno(2099);
			film.setDurata(90);
			film.setGenere("Test");
			film.setRegista(regista);
			filmRepository.save(film);

			festival.getFilms().add(film);
		}

		festivalRepository.save(festival);
		return festival.getId();
	}

	@Transactional(readOnly = true)
	public String misuraLazy(Long festivalId) {
		Statistics statistiche = statistiche();
		statistiche.clear();
		long inizio = System.nanoTime();

		List<Film> films = filmRepository.findByFestivalLazy(festivalId);
		for (Film film : films) {
			film.getRegista().getCognome();
		}

		long millisecondi = (System.nanoTime() - inizio) / 1_000_000;
		return rapporto("Strategia 1: LAZY", films.size(), statistiche.getPrepareStatementCount(), millisecondi);
	}

	@Transactional(readOnly = true)
	public String misuraJoinFetch(Long festivalId) {
		Statistics statistiche = statistiche();
		statistiche.clear();
		long inizio = System.nanoTime();

		List<Film> films = filmRepository.findByFestivalJoinFetch(festivalId);
		for (Film film : films) {
			film.getRegista().getCognome();
		}

		long millisecondi = (System.nanoTime() - inizio) / 1_000_000;
		return rapporto("Strategia 2: JOIN FETCH", films.size(), statistiche.getPrepareStatementCount(),
				millisecondi);
	}

	@Transactional(readOnly = true)
	public String misuraEntityGraph(Long festivalId) {
		Statistics statistiche = statistiche();
		statistiche.clear();
		long inizio = System.nanoTime();

		List<Film> films = filmRepository.findByFestivalEntityGraph(festivalId);
		for (Film film : films) {
			film.getRegista().getCognome();
		}

		long millisecondi = (System.nanoTime() - inizio) / 1_000_000;
		return rapporto("Strategia 3: ENTITY GRAPH", films.size(), statistiche.getPrepareStatementCount(),
				millisecondi);
	}

	@Transactional
	public void eliminaDati(Long festivalId) {
		Festival festival = festivalRepository.findById(festivalId).orElse(null);
		if (festival == null) {
			return;
		}
		List<Film> films = new ArrayList<>(festival.getFilms());
		List<Regista> registi = new ArrayList<>();
		for (Film film : films) {
			registi.add(film.getRegista());
		}

		festival.getFilms().clear();
		festivalRepository.delete(festival);
		filmRepository.deleteAll(films);
		registaRepository.deleteAll(registi);
	}

	private Statistics statistiche() {
		Statistics statistiche = entityManagerFactory.unwrap(SessionFactory.class).getStatistics();
		statistiche.setStatisticsEnabled(true);
		return statistiche;
	}

	private String rapporto(String strategia, int caricati, long query, long millisecondi) {
		return strategia + "\n"
				+ "Film caricati: " + caricati + "\n"
				+ "Query SQL: " + query + "\n"
				+ "Tempo: " + millisecondi + " ms\n\n";
	}
}