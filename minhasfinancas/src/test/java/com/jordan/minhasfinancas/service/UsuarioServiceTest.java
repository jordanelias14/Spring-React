package com.jordan.minhasfinancas.service;

import static org.mockito.Mockito.when;

import java.util.Optional;

import org.assertj.core.api.Assertions;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import com.jordan.minhasfinancas.exception.ErroAutenticacao;
import com.jordan.minhasfinancas.exception.RegraNegocioException;
import com.jordan.minhasfinancas.model.entity.Usuario;
import com.jordan.minhasfinancas.model.repository.UsuarioRepository;
import com.jordan.minhasfinancas.service.impl.UsuarioServiceImpl;

//@SpringBootTest // não é preciso quando usar mocks
@RunWith(SpringRunner.class)
@ActiveProfiles("test")

//Camada Service = somente TESTES UNITÁRIOS
public class UsuarioServiceTest {

	//@Autowired testes unitarios não precisam de injeção
	//UsuarioService service;
	
	@SpyBean// spring para spy. Reparar que é o serviceIMPL
	UsuarioServiceImpl service;
	
	//@Autowired
	@MockBean// cria o mockito para todo repository de UsuarioRepository
	UsuarioRepository repository;//garante nenhum usuario cadastrado
	
	//@Before //executado antes de cada um dos testes
	//public void setUp() {
		
		//mocka apenas um spy do service. FORMA PADRAO. Teria que excluir a criacao do metodo real ali em baixo
		//service = Mockito.spy(UsuarioServiceImpl.class);
		
		//repository = Mockito.mock(UsuarioRepository.class);//cria o fake
	//	service = new UsuarioServiceImpl(repository);//metodo real
		
	//}
	
	@Test(expected = Test.None.class)
	public void deveSalvarUsuario() {
		//cenario
		//Se eu mockar o SERVICE vai passar a dar erro nos outros testes pois ele mocka tudo de vez.
		//Preciso criar um SPY para o mock service e fazer a implemntação abaixo
		//mock do validarEmail
		Mockito.doNothing().when(service).validarEmail(Mockito.anyString());//impede que erros sejam lancados 
		Usuario usuario = Usuario.builder()
				.id(1l)
				.nome("nome")
				.email("email@email.com")
				.senha("senha").build();
		//mock do salvar
		Mockito.when(repository.save(Mockito.any(Usuario.class))).thenReturn(usuario);
		
		//ação
		Usuario usuarioSalvo = service.salvarUsuario(new Usuario());
		
		//verificação
		Assertions.assertThat(usuarioSalvo).isNotNull();
		Assertions.assertThat(usuarioSalvo.getId()).isEqualTo(1l);
		Assertions.assertThat(usuarioSalvo.getNome()).isEqualTo("nome");
		Assertions.assertThat(usuarioSalvo.getEmail()).isEqualTo("email@email.com");
		Assertions.assertThat(usuarioSalvo.getSenha()).isEqualTo("senha");
		
	}
	@Test(expected = RegraNegocioException.class)
	public void naoDeveSalvarUsuarioComEmailJaCriado() {
		
		//cenario
		String email = "email@email.com";
		Usuario usuario = Usuario.builder().email(email).build();
		Mockito.doThrow(RegraNegocioException.class).when(service).validarEmail(email);
		
		//ação
		service.salvarUsuario(usuario);
		
		//verificação: espero que ele nunca tenha chamado o método de salvar usuario
		Mockito.verify(repository, Mockito.never()).save(usuario);
		
	}
	
	
	@Test(expected = Test.None.class) //garante que não lançe exceção
	public void deveAutenticarUmUsuarioComSucesso() {
		
		//cenario
		String email = "email@email.com";
		String senha = "senha";
		
		Usuario usuario = Usuario.builder().email(email).senha(senha).id(1l).build();
		Mockito.when(repository.findByEmail(email)).thenReturn(Optional.of(usuario));
		
		//ação
		Usuario result = service.autenticar(email, senha);
		
		//verificação
		Assertions.assertThat(result).isNotNull();
		
	}
	
	@Test(expected = ErroAutenticacao.class)//esse erro não explicita qual a mensagem(se é de senha ou email errado)
	public void deveLancarErroQuandoNaoEncontrarUsuarioComEmailInformado() {
		
		//cenario
		Mockito.when(repository.findByEmail(Mockito.anyString())).thenReturn(Optional.empty());
		
		//acao
		service.autenticar("email@email.com", "senha");
		
	}
	
	@Test //(expected = ErroAutenticacao.class) //Esse teste explicita onde está o erro na verificação (nesse caso, na senha)
	public void deveLancarErroQuandoSenhaNaoBateu() {
		
		//cenario
		String senha = "senha";
		Usuario usuario = Usuario.builder().email("email@email.com").senha(senha).build();
		Mockito.when(repository.findByEmail(Mockito.anyString())).thenReturn(Optional.of(usuario));
		
		//ação simular senha incorreta
		Throwable exception = Assertions.catchThrowable( () -> service.autenticar("email@email.com", "123")); //Deve lancar a msg clara de senha invalida
		
		//verificação		
		Assertions.assertThat(exception).isInstanceOf(ErroAutenticacao.class).hasMessage("Senha inválida"); //Ele compara se a msg é a mesma 
		
	}	
	
	@Test(expected = Test.None.class)// o teste espera que não lançe exceçoes
	public void ValidaEmail() {
		//cenario
		Mockito.when(repository.existsByEmail(Mockito.anyString())).thenReturn(false);
		
		//repository.deleteAll(); //não precisa no mocks
		
		//ação
		service.validarEmail("email@email.com");
	}
	
	@Test(expected = RegraNegocioException.class)//espera que lance a exceção
	public void deveLancarErroAoValidarEmailQuandoExistirEmailCadastrado() {
		
		//cenario
		
		Mockito.when(repository.existsByEmail(Mockito.anyString())).thenReturn(true);
		//Usuario usuario = Usuario.builder().nome("usuario").email("email@email.com").build();
		//repository.save(usuario);
		
		//ação
		service.validarEmail("email@email.com");
		
	}
	
	
	
}
