package com.jordan.minhasfinancas.service;

import com.jordan.minhasfinancas.model.entity.Usuario;

public interface UsuarioService {
	
	//metodos que serao implementados na ServiceImpl
	Usuario autenticar(String email, String senha);
	
	Usuario salvarUsuario(Usuario usuario);
	
	void validarEmail(String email);
	
}
