package it.uniroma3.siw.controller;

import java.security.Principal;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import it.uniroma3.siw.exception.RecensioneDuplicataException;
import it.uniroma3.siw.model.Credentials;
import it.uniroma3.siw.model.Film;
import it.uniroma3.siw.model.Recensione;
import it.uniroma3.siw.service.AttoreService;
import it.uniroma3.siw.service.CredentialsService;
import it.uniroma3.siw.service.FilmService;
import it.uniroma3.siw.service.ProiezioneService;
import it.uniroma3.siw.service.RecensioneService;
import jakarta.validation.Valid;

@Controller
public class FilmController {

	private FilmService filmService;
	private ProiezioneService proiezioneService;
	private RecensioneService recensioneService;
	private CredentialsService credentialsService;
	private AttoreService attoreService;

	public FilmController(FilmService filmService, ProiezioneService proiezioneService,
			RecensioneService recensioneService, CredentialsService credentialsService,
			AttoreService attoreService) {
		this.filmService = filmService;
		this.proiezioneService = proiezioneService;
		this.recensioneService = recensioneService;
		this.credentialsService = credentialsService;
		this.attoreService = attoreService;
	}

	@GetMapping("/film")
	public String mostraFilm(Model model) {
		model.addAttribute("elencoFilm", filmService.findAll());
		return "elencoFilm";
	}

	@GetMapping("/film/{id}")
	public String mostraDettaglio(@PathVariable("id") Long id, Principal principal, Model model) {
		model.addAttribute("recensione", new Recensione());
		return preparaPagina(id, principal, model);
	}

	@PostMapping("/film/{id}/recensioni")
	public String nuovaRecensione(@PathVariable("id") Long id,
			@Valid @ModelAttribute("recensione") Recensione recensione, BindingResult bindingResult,
			Principal principal, Model model) {
		if (!bindingResult.hasErrors()) {
			try {
				recensioneService.inserisci(id, principal.getName(), recensione);
				return "redirect:/film/" + id;
			} catch (RecensioneDuplicataException e) {
				bindingResult.reject("recensione.duplicata", e.getMessage());
			}
		}
		return preparaPagina(id, principal, model);
	}

	private String preparaPagina(Long id, Principal principal, Model model) {
		Film film = filmService.findByIdConRegistaEFestival(id);
		if (film == null) {
			return "redirect:/film";
		}

		Long utenteId = null;
		if (principal != null) {
			Credentials credentials = credentialsService.getCredentials(principal.getName());
			if (credentials != null && credentials.getUser() != null) {
				utenteId = credentials.getUser().getId();
			}
		}

		model.addAttribute("film", film);
		model.addAttribute("interpretazioni", filmService.findInterpretazioni(id));
		model.addAttribute("proiezioni", proiezioneService.findByFilm(id));
		model.addAttribute("recensioni", recensioneService.findByFilm(id));
		model.addAttribute("utenteId", utenteId);
		model.addAttribute("attori", attoreService.findAll());
		return "film";
	}
}