package it.uniroma3.siw.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import it.uniroma3.siw.model.Sala;

public interface SalaRepository extends CrudRepository<Sala, Long> {

	public List<Sala> findAllByOrderByNomeAsc();

	public boolean existsByNomeAndIndirizzo(String nome, String indirizzo);
}