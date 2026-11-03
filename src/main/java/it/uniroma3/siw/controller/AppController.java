package it.uniroma3.siw.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class AppController {

	@GetMapping({ "/app", "/app/", "/app/festival/{id}" })
	public String app() {
		return "forward:/app/index.html";
	}
}