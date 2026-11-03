package it.uniroma3.siw.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import it.uniroma3.siw.model.Attore;
import it.uniroma3.siw.model.Interpretazione;
import it.uniroma3.siw.repository.AttoreRepository;
import it.uniroma3.siw.repository.InterpretazioneRepository;

@Service
public class AttoreService {

	private AttoreRepository attoreRepository;
	private InterpretazioneRepository interpretazioneRepository;

	public AttoreService(AttoreRepository attoreRepository, InterpretazioneRepository interpretazioneRepository) {
		this.attoreRepository = attoreRepository;
		this.interpretazioneRepository = interpretazioneRepository;
	}

	@Transactional(readOnly = true)
	public List<Attore> findAll() {
		return attoreRepository.findAllByOrderByCognomeAscNomeAsc();
	}

	@Transactional(readOnly = true)
	public Attore findById(Long id) {
		return attoreRepository.findById(id).orElse(null);
	}

	@Transactional(readOnly = true)
	public List<Interpretazione> findFilmografia(Long attoreId) {
		return interpretazioneRepository.findByAttoreConFilm(attoreId);
	}
}