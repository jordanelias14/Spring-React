package com.jordan.minhasfinancas.model.entity;

import java.util.Objects;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Builder;
import lombok.Data;
import lombok.experimental.SuperBuilder;

@Entity						//Informo que Usuário é uma entidade da tabela
@Table(name = "usuario" , schema = "financas")	//Informo a tabela e seu schema caso haja
@Data
@SuperBuilder

// Usando Lombok
public class Usuario {
	
	@Id						//Pra dizer que ID é chave primária
	@Column(name = "id")	//Informo as colunas da tabela
	@GeneratedValue( strategy = GenerationType.IDENTITY ) //Informa que é auto incremento serial
	private Long id;
	
	@Column(name = "nome")
	private String nome;
	
	@Column(name = "email")
	private String email;
	
	@Column(name = "senha")
	private String senha;
	
	//Não uso nada disso pois uso o lombok que automatiza
	
//	@Override			//Hash usado para comparar o que é passado com o elemento interno
//	public int hashCode() {
//		return Objects.hash(email, id, nome, senha);
//	}
//
//	@Override
//	public boolean equals(Object obj) {
//		if (this == obj)
//			return true;
//		if (obj == null)
//			return false;
//		if (getClass() != obj.getClass())
//			return false;
//		Usuario other = (Usuario) obj;
//		return Objects.equals(email, other.email) && Objects.equals(id, other.id) && Objects.equals(nome, other.nome)
//				&& Objects.equals(senha, other.senha);
//	}
//
//	public Long getId() {
//		return id;
//	}
//
//	public void setId(Long id) {
//		this.id = id;
//	}
//
//	public String getNome() {
//		return nome;
//	}
//
//	public void setNome(String nome) {
//		this.nome = nome;
//	}
//
//	public String getEmail() {
//		return email;
//	}
//
//	public void setEmail(String email) {
//		this.email = email;
//	}
//
//	public String getSenha() {
//		return senha;
//	}
//
//	public void setSenha(String senha) {
//		this.senha = senha;
//	}


//	@Override		//Facilita o debug ao passar o mouse sobre o objeto
//	public String toString() {
//		return "Usuario [id=" + id + ", nome=" + nome + ", email=" + email + ", senha=" + senha + "]";
//	}
	

}
