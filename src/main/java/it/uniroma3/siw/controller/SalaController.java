package it.uniroma3.siw.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import it.uniroma3.siw.exception.SalaNonValidaException;
import it.uniroma3.siw.model.Sala;
import it.uniroma3.siw.service.SalaService;
import jakarta.validation.Valid;

@Controller
public class SalaController {

	private SalaService salaService;

	public SalaController(SalaService salaService) {
		this.salaService = salaService;
	}

	@GetMapping("/admin/sale")
	public String mostraSale(Model model) {
		model.addAttribute("sale", salaService.findAll());
		return "sale";
	}

	@GetMapping("/admin/sale/nuova")
	public String formNuova(Model model) {
		model.addAttribute("sala", new Sala());
		return "formSala";
	}

	@PostMapping("/admin/sale")
	public String nuova(@Valid @ModelAttribute("sala") Sala sala, BindingResult bindingResult) {
		if (bindingResult.hasErrors()) {
			return "formSala";
		}
		try {
			salaService.save(sala);
		} catch (SalaNonValidaException e) {
			bindingResult.reject("sala.nonValida", e.getMessage());
			return "formSala";
		}
		return "redirect:/admin/sale";
	}

	@GetMapping("/admin/sale/{id}/modifica")
	public String formModifica(@PathVariable("id") Long id, Model model) {
		Sala sala = salaService.findById(id);
		if (sala == null) {
			return "redirect:/admin/sale";
		}
		model.addAttribute("sala", sala);
		return "formSala";
	}

	@PostMapping("/admin/sale/{id}")
	public String modifica(@PathVariable("id") Long id, @Valid @ModelAttribute("sala") Sala sala,
			BindingResult bindingResult) {
		sala.setId(id);
		if (bindingResult.hasErrors()) {
			return "formSala";
		}
		try {
			salaService.update(id, sala);
		} catch (SalaNonValidaException e) {
			bindingResult.reject("sala.nonValida", e.getMessage());
			return "formSala";
		}
		return "redirect:/admin/sale";
	}
}