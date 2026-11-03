package it.uniroma3.siw.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import it.uniroma3.siw.exception.SalaNonValidaException;
import it.uniroma3.siw.model.Sala;
import it.uniroma3.siw.repository.SalaRepository;

@Service
public class SalaService {

	private SalaRepository salaRepository;

	public SalaService(SalaRepository salaRepository) {
		this.salaRepository = salaRepository;
	}

	@Transactional(readOnly = true)
	public List<Sala> findAll() {
		return salaRepository.findAllByOrderByNomeAsc();
	}

	@Transactional(readOnly = true)
	public Sala findById(Long id) {
		return salaRepository.findById(id).orElse(null);
	}

	@Transactional
	public Sala save(Sala sala) {
		if (salaRepository.existsByNomeAndIndirizzo(sala.getNome(), sala.getIndirizzo())) {
			throw new SalaNonValidaException("Esiste già una sala con questo nome a questo indirizzo");
		}
		return salaRepository.save(sala);
	}

	@Transactional
	public Sala update(Long id, Sala nuoviDati) {
		Sala sala = salaRepository.findById(id).orElse(null);
		if (sala == null) {
			return null;
		}
		boolean nomeOIndirizzoCambiati = !sala.getNome().equals(nuoviDati.getNome())
				|| !sala.getIndirizzo().equals(nuoviDati.getIndirizzo());
		if (nomeOIndirizzoCambiati
				&& salaRepository.existsByNomeAndIndirizzo(nuoviDati.getNome(), nuoviDati.getIndirizzo())) {
			throw new SalaNonValidaException("Esiste già una sala con questo nome a questo indirizzo");
		}
		sala.setNome(nuoviDati.getNome());
		sala.setIndirizzo(nuoviDati.getIndirizzo());
		sala.setCapienza(nuoviDati.getCapienza());
		return sala;
	}
}