package it.uniroma3.siw.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import it.uniroma3.siw.model.Interpretazione;

public interface InterpretazioneRepository extends CrudRepository<Interpretazione, Long> {

	@Query("select i from Interpretazione i "
			+ "join fetch i.attore a "
			+ "where i.film.id = :filmId "
			+ "order by a.cognome")
	public List<Interpretazione> findByFilmConAttore(@Param("filmId") Long filmId);

	@Query("select i from Interpretazione i "
			+ "join fetch i.film f "
			+ "where i.attore.id = :attoreId "
			+ "order by f.anno")
	public List<Interpretazione> findByAttoreConFilm(@Param("attoreId") Long attoreId);

	public boolean existsByFilmIdAndAttoreIdAndPersonaggio(Long filmId, Long attoreId, String personaggio);
}