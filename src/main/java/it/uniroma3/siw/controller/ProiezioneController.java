package it.uniroma3.siw.controller;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeParseException;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import it.uniroma3.siw.exception.ProiezioneNonValidaException;
import it.uniroma3.siw.model.Festival;
import it.uniroma3.siw.model.Proiezione;
import it.uniroma3.siw.service.FestivalService;
import it.uniroma3.siw.service.ProiezioneService;
import it.uniroma3.siw.service.SalaService;

@Controller
public class ProiezioneController {

	private ProiezioneService proiezioneService;
	private FestivalService festivalService;
	private SalaService salaService;

	public ProiezioneController(ProiezioneService proiezioneService, FestivalService festivalService,
			SalaService salaService) {
		this.proiezioneService = proiezioneService;
		this.festivalService = festivalService;
		this.salaService = salaService;
	}

	@GetMapping("/admin/festival/{festivalId}/proiezioni/nuova")
	public String formNuova(@PathVariable("festivalId") Long festivalId, Model model) {
		return preparaForm(festivalId, null, null, null, null, null, model);
	}

	@PostMapping("/admin/festival/{festivalId}/proiezioni")
	public String nuova(@PathVariable("festivalId") Long festivalId,
			@RequestParam(name = "filmId", required = false) Long filmId,
			@RequestParam(name = "salaId", required = false) Long salaId,
			@RequestParam(name = "data", required = false) String data,
			@RequestParam(name = "ora", required = false) String ora, Model model) {
		try {
			proiezioneService.programma(festivalId, filmId, salaId, LocalDate.parse(data), LocalTime.parse(ora));
			return "redirect:/festival/" + festivalId;
		} catch (DateTimeParseException | NullPointerException e) {
			model.addAttribute("errore", "Data e ora sono obbligatorie");
		} catch (ProiezioneNonValidaException e) {
			model.addAttribute("errore", e.getMessage());
		}
		return preparaForm(festivalId, null, filmId, salaId, data, ora, model);
	}

	@GetMapping("/admin/proiezioni/{id}/modifica")
	public String formModifica(@PathVariable("id") Long id, Model model) {
		Proiezione p = proiezioneService.findByIdConDettagli(id);
		if (p == null) {
			return "redirect:/festival";
		}
		return preparaForm(p.getFestival().getId(), id, p.getFilm().getId(), p.getSala().getId(),
				p.getData().toString(), p.getOra().toString(), model);
	}

	@PostMapping("/admin/proiezioni/{id}")
	public String modifica(@PathVariable("id") Long id,
			@RequestParam(name = "filmId", required = false) Long filmId,
			@RequestParam(name = "salaId", required = false) Long salaId,
			@RequestParam(name = "data", required = false) String data,
			@RequestParam(name = "ora", required = false) String ora, Model model) {
		Proiezione originale = proiezioneService.findByIdConDettagli(id);
		if (originale == null) {
			return "redirect:/festival";
		}
		Long festivalId = originale.getFestival().getId();
		try {
			proiezioneService.modifica(id, filmId, salaId, LocalDate.parse(data), LocalTime.parse(ora));
			return "redirect:/festival/" + festivalId;
		} catch (DateTimeParseException | NullPointerException e) {
			model.addAttribute("errore", "Data e ora sono obbligatorie");
		} catch (ProiezioneNonValidaException e) {
			model.addAttribute("errore", e.getMessage());
		}
		return preparaForm(festivalId, id, filmId, salaId, data, ora, model);
	}

	@PostMapping("/admin/proiezioni/{id}/cancella")
	public String cancella(@PathVariable("id") Long id, RedirectAttributes redirectAttributes) {
		try {
			Long festivalId = proiezioneService.cancella(id);
			return "redirect:/festival/" + festivalId;
		} catch (ProiezioneNonValidaException e) {
			return "redirect:/festival";
		}
	}

	private String preparaForm(Long festivalId, Long proiezioneId, Long filmId, Long salaId, String data,
			String ora, Model model) {
		Festival festival = festivalService.findByIdConFilm(festivalId);
		if (festival == null) {
			return "redirect:/festival";
		}
		model.addAttribute("festival", festival);
		model.addAttribute("sale", salaService.findAll());
		model.addAttribute("proiezioneId", proiezioneId);
		model.addAttribute("filmIdScelto", filmId);
		model.addAttribute("salaIdScelta", salaId);
		model.addAttribute("dataScelta", data);
		model.addAttribute("oraScelta", ora);
		return "formProiezione";
	}
}