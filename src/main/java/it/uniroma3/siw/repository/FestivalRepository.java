package it.uniroma3.siw.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import it.uniroma3.siw.model.Festival;

public interface FestivalRepository extends CrudRepository<Festival, Long> {

	public List<Festival> findAllByOrderByDataInizioAsc();

	public boolean existsByNomeAndAnno(String nome, Integer anno);

	@Query("select f from Festival f "
			+ "left join fetch f.films fi "
			+ "left join fetch fi.regista "
			+ "where f.id = :id")
	public Optional<Festival> findByIdConFilm(@Param("id") Long id);
}