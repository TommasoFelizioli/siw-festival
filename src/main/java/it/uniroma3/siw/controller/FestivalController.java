package it.uniroma3.siw.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import it.uniroma3.siw.exception.FestivalNonValidoException;
import it.uniroma3.siw.exception.RimozioneNonConsentitaException;
import it.uniroma3.siw.model.Festival;
import it.uniroma3.siw.service.FestivalService;
import it.uniroma3.siw.service.FilmService;
import it.uniroma3.siw.service.ProiezioneService;
import jakarta.validation.Valid;

@Controller
public class FestivalController {

	private FestivalService festivalService;
	private ProiezioneService proiezioneService;
	private FilmService filmService;

	public FestivalController(FestivalService festivalService, ProiezioneService proiezioneService,
			FilmService filmService) {
		this.festivalService = festivalService;
		this.proiezioneService = proiezioneService;
		this.filmService = filmService;
	}

	@GetMapping({ "/", "/festival" })
	public String mostraFestival(Model model) {
		model.addAttribute("elencoFestival", festivalService.findAll());
		return "elencoFestival";
	}

	@GetMapping("/festival/{id}")
	public String mostraDettaglio(@PathVariable("id") Long id, Model model) {
		Festival festival = festivalService.findByIdConFilm(id);
		if (festival == null) {
			return "redirect:/festival";
		}
		model.addAttribute("festival", festival);
		model.addAttribute("proiezioni", proiezioneService.findProgrammaByFestival(id));
		model.addAttribute("filmDisponibili", filmService.findNonPartecipanti(id));
		return "festival";
	}

	@GetMapping("/admin/festival/nuovo")
	public String formNuovo(Model model) {
		model.addAttribute("festival", new Festival());
		return "formFestival";
	}

	@PostMapping("/admin/festival")
	public String nuovo(@Valid @ModelAttribute("festival") Festival festival, BindingResult bindingResult) {
		if (bindingResult.hasErrors()) {
			return "formFestival";
		}
		try {
			festivalService.save(festival);
		} catch (FestivalNonValidoException e) {
			bindingResult.reject("festival.nonValido", e.getMessage());
			return "formFestival";
		}
		return "redirect:/festival/" + festival.getId();
	}

	@GetMapping("/admin/festival/{id}/modifica")
	public String formModifica(@PathVariable("id") Long id, Model model) {
		Festival festival = festivalService.findById(id);
		if (festival == null) {
			return "redirect:/festival";
		}
		model.addAttribute("festival", festival);
		return "formFestival";
	}

	@PostMapping("/admin/festival/{id}")
	public String modifica(@PathVariable("id") Long id, @Valid @ModelAttribute("festival") Festival festival,
			BindingResult bindingResult) {
		festival.setId(id);
		if (bindingResult.hasErrors()) {
			return "formFestival";
		}
		try {
			festivalService.update(id, festival);
		} catch (FestivalNonValidoException e) {
			bindingResult.reject("festival.nonValido", e.getMessage());
			return "formFestival";
		}
		return "redirect:/festival/" + id;
	}

	@PostMapping("/admin/festival/{id}/film")
	public String aggiungiFilm(@PathVariable("id") Long id, @RequestParam("filmId") Long filmId) {
		festivalService.aggiungiFilm(id, filmId);
		return "redirect:/festival/" + id;
	}

	@PostMapping("/admin/festival/{id}/film/{filmId}/rimuovi")
	public String rimuoviFilm(@PathVariable("id") Long id, @PathVariable("filmId") Long filmId,
			RedirectAttributes redirectAttributes) {
		try {
			festivalService.rimuoviFilm(id, filmId);
		} catch (RimozioneNonConsentitaException e) {
			redirectAttributes.addFlashAttribute("errore", e.getMessage());
		}
		return "redirect:/festival/" + id;
	}
}