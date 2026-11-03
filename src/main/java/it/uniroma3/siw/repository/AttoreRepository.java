package it.uniroma3.siw.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import it.uniroma3.siw.model.Attore;

public interface AttoreRepository extends CrudRepository<Attore, Long> {

	public List<Attore> findAllByOrderByCognomeAscNomeAsc();
}