package it.uniroma3.siw.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import it.uniroma3.siw.exception.RegistrazioneException;
import it.uniroma3.siw.model.Credentials;
import it.uniroma3.siw.model.User;
import it.uniroma3.siw.service.CredentialsService;
import jakarta.validation.Valid;

@Controller
public class AuthenticationController {

	private CredentialsService credentialsService;

	public AuthenticationController(CredentialsService credentialsService) {
		this.credentialsService = credentialsService;
	}

	@GetMapping("/login")
	public String formLogin() {
		return "login";
	}

	@GetMapping("/register")
	public String formRegistrazione(Model model) {
		model.addAttribute("user", new User());
		model.addAttribute("credentials", new Credentials());
		return "formRegister";
	}

	@PostMapping("/register")
	public String registraUtente(@Valid @ModelAttribute("user") User user, BindingResult userBindingResult,
			@Valid @ModelAttribute("credentials") Credentials credentials, BindingResult credentialsBindingResult,
			Model model) {
		if (userBindingResult.hasErrors() || credentialsBindingResult.hasErrors()) {
			return "formRegister";
		}
		credentials.setUser(user);
		try {
			credentialsService.saveCredentials(credentials);
		} catch (RegistrazioneException e) {
			model.addAttribute("erroreRegistrazione", e.getMessage());
			return "formRegister";
		}
		return "redirect:/login";
	}
}