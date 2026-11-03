package it.uniroma3.siw.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.jpa.repository.EntityGraph;
import it.uniroma3.siw.model.Film;

public interface FilmRepository extends CrudRepository<Film, Long> {

	@Query("select f from Film f left join fetch f.regista order by f.titolo")
	public List<Film> findAllConRegista();

	@Query("select f from Film f "
			+ "left join fetch f.regista "
			+ "left join fetch f.festivals "
			+ "where f.id = :id")
	public Optional<Film> findByIdConRegistaEFestival(@Param("id") Long id);

	@Query("select f from Film f "
			+ "where f.id not in (select fi.id from Festival fe join fe.films fi where fe.id = :festivalId) "
			+ "order by f.titolo")
	public List<Film> findNonPartecipanti(@Param("festivalId") Long festivalId);

	public boolean existsByTitoloAndAnno(String titolo, Integer anno);

	// Strategia 1 - LAZY: carica solo i film; il regista viene letto dopo, uno alla volta
	@Query("select f from Film f join f.festivals fe where fe.id = :festivalId")
	public List<Film> findByFestivalLazy(@Param("festivalId") Long festivalId);

	// Strategia 2 - JOIN FETCH: film e registi con una sola query
	@Query("select f from Film f join fetch f.regista join f.festivals fe where fe.id = :festivalId")
	public List<Film> findByFestivalJoinFetch(@Param("festivalId") Long festivalId);

	// Strategia 3 - EntityGraph: la stessa query della strategia 1, ma chiediamo di caricare anche il regista
	@EntityGraph(attributePaths = { "regista" })
	@Query("select f from Film f join f.festivals fe where fe.id = :festivalId")
	public List<Film> findByFestivalEntityGraph(@Param("festivalId") Long festivalId);
}