package it.uniroma3.siw.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import it.uniroma3.siw.model.Regista;

public interface RegistaRepository extends CrudRepository<Regista, Long> {

	public List<Regista> findAllByOrderByCognomeAscNomeAsc();
}