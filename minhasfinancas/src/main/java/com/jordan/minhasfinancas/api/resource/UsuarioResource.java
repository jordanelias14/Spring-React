package com.jordan.minhasfinancas.api.resource;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UsuarioResource {
	
	@GetMapping("/") //URL
	public String helloWorld() {
		return "Olá Mundo!";
	}
}
