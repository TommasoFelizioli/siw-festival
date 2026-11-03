package it.uniroma3.siw.dto;

import java.time.LocalDate;
import java.time.LocalTime;

public record ProiezioneDto(
		Long id,
		LocalDate data,
		LocalTime ora,
		LocalTime oraFine,
		String stato,
		Long filmId,
		String filmTitolo,
		String salaNome) {
}