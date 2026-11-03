package it.uniroma3.siw.dto;

public record FilmDto(
		Long id,
		String titolo,
		Integer anno,
		Integer durata,
		String genere,
		String paeseProduzione,
		String regista) {
}