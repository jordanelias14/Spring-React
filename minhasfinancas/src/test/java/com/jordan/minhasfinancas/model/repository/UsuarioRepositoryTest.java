package com.jordan.minhasfinancas.model.repository;

import org.assertj.core.api.Assertions;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.jordan.minhasfinancas.model.entity.Usuario;

@SpringBootTest
@RunWith(SpringRunner.class)


public class UsuarioRepositoryTest {
	
	@Autowired //necessario para iserir as dependencias do repository
	UsuarioRepository repository;
	
	@Test //todos os testes tem metodos sem retorno (void)
	public void verificaExistenciaEmail() {
		
		// Cenario: cria um usuario e salva na base de dados
		Usuario usuarioTest = Usuario.builder().nome("usuario").email("usuario@email.com").build();
		repository.save(usuarioTest);
		
		// Ação/Execução: verifica se existe na base um usuario com esse email
		boolean result = repository.existsByEmail("usuario@email.com");
		
		// Resultado: veja se o retorna verdadeiro
		Assertions.assertThat(result).isTrue();
		
	}
	
}
