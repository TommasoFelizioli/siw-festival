package it.uniroma3.siw.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import it.uniroma3.siw.model.Recensione;

public interface RecensioneRepository extends CrudRepository<Recensione, Long> {

	public boolean existsByAutoreIdAndFilmId(Long autoreId, Long filmId);

	@Query("select r from Recensione r "
			+ "join fetch r.autore "
			+ "where r.film.id = :filmId "
			+ "order by r.data desc")
	public List<Recensione> findByFilmConAutore(@Param("filmId") Long filmId);

	@Query("select r from Recensione r "
			+ "join fetch r.film "
			+ "join fetch r.autore "
			+ "where r.id = :id")
	public Optional<Recensione> findByIdConFilmEAutore(@Param("id") Long id);
}