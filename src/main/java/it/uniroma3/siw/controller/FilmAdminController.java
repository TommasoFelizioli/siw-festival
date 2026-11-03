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

import it.uniroma3.siw.exception.FilmNonValidoException;
import it.uniroma3.siw.model.Film;
import it.uniroma3.siw.service.FilmService;
import jakarta.validation.Valid;

@Controller
public class FilmAdminController {

	private FilmService filmService;

	public FilmAdminController(FilmService filmService) {
		this.filmService = filmService;
	}

	@GetMapping("/admin/film/nuovo")
	public String formNuovo(Model model) {
		model.addAttribute("film", new Film());
		model.addAttribute("registi", filmService.findRegisti());
		return "formFilm";
	}

	@PostMapping("/admin/film")
	public String nuovo(@Valid @ModelAttribute("film") Film film, BindingResult bindingResult,
			@RequestParam(name = "registaId", required = false) Long registaId, Model model) {
		if (!bindingResult.hasErrors()) {
			try {
				filmService.save(film, registaId);
				return "redirect:/film/" + film.getId();
			} catch (FilmNonValidoException e) {
				bindingResult.reject("film.nonValido", e.getMessage());
			}
		}
		model.addAttribute("registi", filmService.findRegisti());
		model.addAttribute("registaIdScelto", registaId);
		return "formFilm";
	}

	@GetMapping("/admin/film/{id}/modifica")
	public String formModifica(@PathVariable("id") Long id, Model model) {
		Film film = filmService.findByIdConRegistaEFestival(id);
		if (film == null) {
			return "redirect:/film";
		}
		model.addAttribute("film", film);
		model.addAttribute("registi", filmService.findRegisti());
		if (film.getRegista() != null) {
			model.addAttribute("registaIdScelto", film.getRegista().getId());
		}
		return "formFilm";
	}

	@PostMapping("/admin/film/{id}")
	public String modifica(@PathVariable("id") Long id, @Valid @ModelAttribute("film") Film film,
			BindingResult bindingResult, @RequestParam(name = "registaId", required = false) Long registaId,
			Model model) {
		film.setId(id);
		if (!bindingResult.hasErrors()) {
			try {
				filmService.update(id, film, registaId);
				return "redirect:/film/" + id;
			} catch (FilmNonValidoException e) {
				bindingResult.reject("film.nonValido", e.getMessage());
			}
		}
		model.addAttribute("registi", filmService.findRegisti());
		model.addAttribute("registaIdScelto", registaId);
		return "formFilm";
	}

	@PostMapping("/admin/film/{id}/cast")
	public String aggiungiAlCast(@PathVariable("id") Long id,
			@RequestParam(name = "attoreId", required = false) Long attoreId,
			@RequestParam(name = "personaggio", required = false) String personaggio,
			RedirectAttributes redirectAttributes) {
		try {
			filmService.aggiungiInterpretazione(id, attoreId, personaggio);
		} catch (FilmNonValidoException e) {
			redirectAttributes.addFlashAttribute("erroreCast", e.getMessage());
		}
		return "redirect:/film/" + id;
	}

	@PostMapping("/admin/film/{id}/cast/{interpretazioneId}/rimuovi")
	public String rimuoviDalCast(@PathVariable("id") Long id,
			@PathVariable("interpretazioneId") Long interpretazioneId) {
		filmService.rimuoviInterpretazione(interpretazioneId);
		return "redirect:/film/" + id;
	}
}