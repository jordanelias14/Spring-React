package com.jordan.minhasfinancas.service.impl;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jordan.minhasfinancas.exception.ErroAutenticacao;
import com.jordan.minhasfinancas.exception.RegraNegocioException;
import com.jordan.minhasfinancas.model.entity.Usuario;
import com.jordan.minhasfinancas.model.repository.UsuarioRepository;
import com.jordan.minhasfinancas.service.UsuarioService;

@Service
public class UsuarioServiceImpl implements UsuarioService {

	//O service acessa a base de dados atraves do model repository 
	private UsuarioRepository repository; 
	//metodo construtor obrigatório gerado no SOURCE
	
	@Autowired
	public UsuarioServiceImpl(UsuarioRepository repository) {
		super();
		this.repository = repository;
	}

	@Override
	public Usuario autenticar(String email, String senha) {
		
		Optional<Usuario> usuario = repository.findByEmail(email);
		
		if(!usuario.isPresent()) {
			throw new ErroAutenticacao("Usuario não encontrado pelo email.");
		}
		
		if(!usuario.get().getSenha().equals(senha)) {
			throw new ErroAutenticacao("Senha inválida");
		}
		
		return usuario.get();
	}


	@Override
	@Transactional //cria uma transação, executa o salvar e commita
	public Usuario salvarUsuario(Usuario usuario) {
		validarEmail(usuario.getEmail());
		return repository.save(usuario);
	}

	@Override
	public void validarEmail(String email) {
		boolean existe = repository.existsByEmail(email);
		if(existe) {
			throw new RegraNegocioException("Já existe um usuário cadastrado com este email.");
		}
		
	}
	
}
	
