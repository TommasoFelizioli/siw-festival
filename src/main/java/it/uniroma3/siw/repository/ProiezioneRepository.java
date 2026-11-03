package it.uniroma3.siw.repository;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import it.uniroma3.siw.model.Proiezione;
import it.uniroma3.siw.model.StatoProiezione;

public interface ProiezioneRepository extends CrudRepository<Proiezione, Long> {

	@Query("select p from Proiezione p "
			+ "join fetch p.film "
			+ "join fetch p.sala "
			+ "where p.festival.id = :festivalId "
			+ "order by p.data, p.ora")
	public List<Proiezione> findProgrammaByFestival(@Param("festivalId") Long festivalId);

	@Query("select p from Proiezione p "
			+ "join fetch p.festival "
			+ "join fetch p.sala "
			+ "where p.film.id = :filmId "
			+ "order by p.data, p.ora")
	public List<Proiezione> findByFilm(@Param("filmId") Long filmId);

	@Query("select p from Proiezione p "
			+ "join fetch p.festival "
			+ "join fetch p.film "
			+ "join fetch p.sala "
			+ "where p.id = :id")
	public Optional<Proiezione> findByIdConDettagli(@Param("id") Long id);

	@Query("select p from Proiezione p "
			+ "where p.sala.id = :salaId "
			+ "and p.data = :data "
			+ "and p.stato <> :statoEscluso "
			+ "and p.id <> :escludiId "
			+ "and p.ora < :oraFine and p.oraFine > :ora")
	public List<Proiezione> findSovrapposte(@Param("salaId") Long salaId, @Param("data") LocalDate data,
			@Param("ora") LocalTime ora, @Param("oraFine") LocalTime oraFine,
			@Param("statoEscluso") StatoProiezione statoEscluso, @Param("escludiId") Long escludiId);

	public boolean existsByFestivalIdAndFilmIdAndStato(Long festivalId, Long filmId, StatoProiezione stato);

	public boolean existsByFilmIdAndStato(Long filmId, StatoProiezione stato);

	@Query("select count(p) from Proiezione p "
			+ "where p.festival.id = :festivalId "
			+ "and p.stato = :stato "
			+ "and (p.data < :inizio or p.data > :fine)")
	public long countFuoriIntervallo(@Param("festivalId") Long festivalId, @Param("stato") StatoProiezione stato,
			@Param("inizio") LocalDate inizio, @Param("fine") LocalDate fine);
}