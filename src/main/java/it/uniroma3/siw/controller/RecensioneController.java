package it.uniroma3.siw.controller;

import java.security.Principal;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import it.uniroma3.siw.model.Recensione;
import it.uniroma3.siw.service.RecensioneService;
import jakarta.validation.Valid;

@Controller
public class RecensioneController {

	private RecensioneService recensioneService;

	public RecensioneController(RecensioneService recensioneService) {
		this.recensioneService = recensioneService;
	}

	@GetMapping("/recensioni/{id}/modifica")
	public String formModifica(@PathVariable("id") Long id, Principal principal, Model model) {
		Recensione recensione = recensioneService.findPropria(id, principal.getName());
		model.addAttribute("recensione", recensione);
		model.addAttribute("filmId", recensione.getFilm().getId());
		model.addAttribute("titoloFilm", recensione.getFilm().getTitolo());
		return "formModificaRecensione";
	}

	@PostMapping("/recensioni/{id}")
	public String modifica(@PathVariable("id") Long id, @Valid @ModelAttribute("recensione") Recensione recensione,
			BindingResult bindingResult, Principal principal, Model model) {
		if (bindingResult.hasErrors()) {
			Recensione originale = recensioneService.findPropria(id, principal.getName());
			model.addAttribute("filmId", originale.getFilm().getId());
			model.addAttribute("titoloFilm", originale.getFilm().getTitolo());
			return "formModificaRecensione";
		}
		Long filmId = recensioneService.modifica(id, principal.getName(), recensione);
		return "redirect:/film/" + filmId;
	}

	@PostMapping("/recensioni/{id}/elimina")
	public String elimina(@PathVariable("id") Long id, Principal principal) {
		Long filmId = recensioneService.elimina(id, principal.getName());
		return "redirect:/film/" + filmId;
	}
}