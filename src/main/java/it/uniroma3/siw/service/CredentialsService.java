package it.uniroma3.siw.service;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import it.uniroma3.siw.exception.RegistrazioneException;
import it.uniroma3.siw.model.Credentials;
import it.uniroma3.siw.repository.CredentialsRepository;
import it.uniroma3.siw.repository.UserRepository;

@Service
public class CredentialsService {

	private CredentialsRepository credentialsRepository;
	private UserRepository userRepository;
	private PasswordEncoder passwordEncoder;

	public CredentialsService(CredentialsRepository credentialsRepository, UserRepository userRepository,
			PasswordEncoder passwordEncoder) {
		this.credentialsRepository = credentialsRepository;
		this.userRepository = userRepository;
		this.passwordEncoder = passwordEncoder;
	}

	@Transactional(readOnly = true)
	public Credentials getCredentials(String username) {
		return credentialsRepository.findByUsername(username).orElse(null);
	}

	@Transactional
	public Credentials saveCredentials(Credentials credentials) {
		if (credentialsRepository.existsByUsername(credentials.getUsername())) {
			throw new RegistrazioneException("Username già in uso");
		}
		if (userRepository.existsByEmail(credentials.getUser().getEmail())) {
			throw new RegistrazioneException("Esiste già un utente con questa email");
		}
		credentials.setRole(Credentials.USER_ROLE);
		credentials.setPassword(passwordEncoder.encode(credentials.getPassword()));
		return credentialsRepository.save(credentials);
	}
}