package it.uniroma3.siw.dto;

import java.time.LocalDate;

public record RecensioneDto(
		Long id,
		String autore,
		Integer voto,
		String testo,
		LocalDate data) {
}