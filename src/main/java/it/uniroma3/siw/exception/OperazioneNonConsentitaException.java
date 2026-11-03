package it.uniroma3.siw.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.FORBIDDEN)
public class OperazioneNonConsentitaException extends RuntimeException {

	public OperazioneNonConsentitaException(String message) {
		super(message);
	}
}