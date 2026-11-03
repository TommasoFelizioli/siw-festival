package it.uniroma3.siw.dto;

import java.time.LocalDate;

public record FestivalDto(
		Long id,
		String nome,
		Integer anno,
		String citta,
		LocalDate dataInizio,
		LocalDate dataFine,
		String descrizione) {
}