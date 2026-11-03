package it.uniroma3.siw.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import it.uniroma3.siw.service.AnalisiFetchService;

@Controller
public class AnalisiFetchController {

	private AnalisiFetchService analisiFetchService;

	public AnalisiFetchController(AnalisiFetchService analisiFetchService) {
		this.analisiFetchService = analisiFetchService;
	}

	@GetMapping(value = "/admin/analisi-fetch", produces = "text/plain;charset=UTF-8")
	@ResponseBody
	public String esegui() {
		StringBuilder risultato = new StringBuilder();
		risultato.append("=== Test accesso ai film del festival, con i relativi registi ===\n\n");

		Long festivalId = analisiFetchService.preparaDati();
		try {
			risultato.append(analisiFetchService.misuraLazy(festivalId));
			risultato.append(analisiFetchService.misuraJoinFetch(festivalId));
			risultato.append(analisiFetchService.misuraEntityGraph(festivalId));
		} finally {
			analisiFetchService.eliminaDati(festivalId);
		}

		System.out.println(risultato);
		return risultato.toString();
	}
}