package com.jordan.minhasfinancas.model.repository;

import java.util.Optional;

//import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.jordan.minhasfinancas.model.entity.Usuario;

public interface UsuarioRepository extends JpaRepository<Usuario, Long>{

	//pode existir ou nao usuario com o tal email, por isso o Optional
	
	boolean existsByEmail(String email);//ve se existe um usuario com aquele email

	Optional<Usuario> findByEmail(String email);//procura pela prop email

}
