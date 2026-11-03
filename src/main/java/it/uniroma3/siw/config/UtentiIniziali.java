package it.uniroma3.siw.config;

import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import it.uniroma3.siw.model.Credentials;
import it.uniroma3.siw.model.User;
import it.uniroma3.siw.repository.CredentialsRepository;
import it.uniroma3.siw.service.CredentialsService;

@Component
@Order(1)
public class UtentiIniziali implements CommandLineRunner {

	private CredentialsRepository credentialsRepository;
	private CredentialsService credentialsService;
	private PasswordEncoder passwordEncoder;

	public UtentiIniziali(CredentialsRepository credentialsRepository, CredentialsService credentialsService,
			PasswordEncoder passwordEncoder) {
		this.credentialsRepository = credentialsRepository;
		this.credentialsService = credentialsService;
		this.passwordEncoder = passwordEncoder;
	}

	@Override
	public void run(String... args) {
		if (!credentialsRepository.existsByUsername("admin")) {
			Credentials credentials = new Credentials();
			credentials.setUsername("admin");
			credentials.setPassword(passwordEncoder.encode("admin"));
			credentials.setRole(Credentials.ADMIN_ROLE);
			credentials.setUser(nuovoUser("Admin", "Admin", "admin@example.com"));
			credentialsRepository.save(credentials);
		}

		creaUtente("mario", "Mario", "Rossi");
		creaUtente("giulia", "Giulia", "Bianchi");
		creaUtente("luca", "Luca", "Esposito");
		creaUtente("sara", "Sara", "Romano");
	}

	private void creaUtente(String username, String nome, String cognome) {
		if (credentialsRepository.existsByUsername(username)) {
			return;
		}
		Credentials credentials = new Credentials();
		credentials.setUsername(username);
		credentials.setPassword(username);
		credentials.setUser(nuovoUser(nome, cognome, username + "@example.com"));
		credentialsService.saveCredentials(credentials);
	}

	private User nuovoUser(String nome, String cognome, String email) {
		User user = new User();
		user.setName(nome);
		user.setSurname(cognome);
		user.setEmail(email);
		return user;
	}
}