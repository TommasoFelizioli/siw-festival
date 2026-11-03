package it.uniroma3.siw.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import it.uniroma3.siw.model.Attore;
import it.uniroma3.siw.service.AttoreService;

@Controller
public class AttoreController {

	private AttoreService attoreService;

	public AttoreController(AttoreService attoreService) {
		this.attoreService = attoreService;
	}

	@GetMapping("/attori")
	public String mostraAttori(Model model) {
		model.addAttribute("attori", attoreService.findAll());
		return "attori";
	}

	@GetMapping("/attori/{id}")
	public String mostraAttore(@PathVariable("id") Long id, Model model) {
		Attore attore = attoreService.findById(id);
		if (attore == null) {
			return "redirect:/attori";
		}
		model.addAttribute("attore", attore);
		model.addAttribute("filmografia", attoreService.findFilmografia(id));
		return "attore";
	}
}