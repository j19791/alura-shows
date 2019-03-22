package br.com.alura.owasp.model;

//Para tratar Mass Assignemnt: criado DTO (Data Transfer Object) UsuarioDTO exatamente com os atributos que esperamos ser recebidos pelo formul�rio
public class UsuarioDTO {

	private String email;
	private String senha;
	private String nome;
	private String nomeImagem;

	public void setEmail(String email) {
		this.email = email;
	}

	public void setSenha(String senha) {
		this.senha = senha;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public void setNomeImagem(String nomeImagem) {
		this.nomeImagem = nomeImagem;
	}

	public Usuario montaUsuario() {
		Usuario usuario = new Usuario(email, senha, nome, nomeImagem);
		return usuario;
	}
}
