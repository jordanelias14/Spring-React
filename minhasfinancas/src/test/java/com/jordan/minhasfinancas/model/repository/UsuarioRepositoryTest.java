package com.jordan.minhasfinancas.model.repository;

import java.util.Optional;

import org.assertj.core.api.Assertions;
import org.junit.Test;
//import org.junit.jupiter.api.Test;
//import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
//import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.junit4.SpringRunner;

import com.jordan.minhasfinancas.model.entity.Usuario;

@AutoConfigureTestDatabase(replace = Replace.NONE)//garante que o banco não será sobrescrito
@DataJpaTest //cria um banco em memoria e ao finalizar o teste o remove
@RunWith(SpringRunner.class)
@ActiveProfiles("test") //explicita qual é o arquivo de teste de DB pra usar

public class UsuarioRepositoryTest {
	
	@Autowired //necessario para inserir as dependencias do repository
	UsuarioRepository repository;
	
	@Autowired
	TestEntityManager entityManager;//responsavel por operações na base(persistir, deletar,salvar..)
	
	@Test //todos os testes tem metodos sem retorno (void)
	public void verificaExistenciaEmail() {
		
		// Cenario: cria um usuario e salva na base de dados
		Usuario	 usuarioTest = criarUsuario();
		//repository.save(usuarioTest); //agora eu uso o gerenciador de entidades
		entityManager.persist(usuarioTest);
		
		// Ação/Execução: verifica se existe na base um usuario com esse email
		boolean result = repository.existsByEmail("usuario@email.com");
		
		// Resultado: veja se o retorna verdadeiro
		Assertions.assertThat(result).isTrue();
	}
	

	@Test
	public void retornaFalseSeNaoHouverUsuarioCadastrado() {
		
		//cenario
		//repository.deleteAll(); //não é mais preciso pois o DataJpaTest exclui apos terminado
		
		//acao
		boolean result = repository.existsByEmail("usuario@email.com");
		
		//Resultado
		Assertions.assertThat(result).isFalse();
	}
	
	@Test
	public void devePersistirUsuarioNaBaseDeDados() {
		
		//cenario
		Usuario usuario = criarUsuario();
		
		//ação
		Usuario usuarioSalvo = repository.save(usuario);
		
		//resultado: apos salvar gera um id
		Assertions.assertThat(usuarioSalvo.getId()).isNotNull();
	}
	
	public static Usuario criarUsuario() {
						return Usuario
									.builder()
									.nome("usuario")
									.email("usuario@email.com")
									.senha("senha")
									.build();
	}
	
	@Test
	public void deveBuscarUsuarioPorEmail() {
		//cenario
		Usuario usuario = criarUsuario();
		entityManager.persist(usuario);
		
		//ação
		Optional<Usuario> result = repository.findByEmail("usuario@email.com");
		
		//resultado
		Assertions.assertThat(result.isPresent()).isTrue();
	}
	
	@Test
	public void deveRetornarVazioAoBuscarUsuarioPorEmailQueNaoExisteNaBase() {
		
		//ação
		Optional<Usuario> result = repository.findByEmail("usuario@email.com");
		
		//resultado
		Assertions.assertThat(result.isPresent()).isFalse();
	}
	
}
