package it.uniroma3.siw.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;

import it.uniroma3.siw.model.Credentials;

@ControllerAdvice
public class GlobalController {

	private String frontendUrl;

	public GlobalController(@Value("${frontend.url}") String frontendUrl) {
		this.frontendUrl = frontendUrl;
	}

	@ModelAttribute("frontendUrl")
	public String getFrontendUrl() {
		return frontendUrl;
	}

	@ModelAttribute("userDetails")
	public UserDetails getUser() {
		UserDetails user = null;
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		if (authentication != null && !(authentication instanceof AnonymousAuthenticationToken)) {
			user = (UserDetails) authentication.getPrincipal();
		}
		return user;
	}

	@ModelAttribute("isAdmin")
	public boolean isAdmin() {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		if (authentication == null) {
			return false;
		}
		for (GrantedAuthority authority : authentication.getAuthorities()) {
			if (authority.getAuthority().equals(Credentials.ADMIN_ROLE)) {
				return true;
			}
		}
		return false;
	}
}