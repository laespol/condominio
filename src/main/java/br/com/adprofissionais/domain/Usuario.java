package br.com.adprofissionais.domain;

public class Usuario {
	
	private Long codigo;
	private String nome;
	private String login;
	private String senha;

	@Override
	public String toString() {
		String saida = codigo + " " + nome + " " + login;
		return saida;
	}

	public Long getCodigo() {
		return codigo;
	}

	public void setCodigo(Long codigo) {
		this.codigo = codigo;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getLogin() {
		return login;
	}

	public void setLogin(String login) {
		this.login = login;
	}

	public String getSenha() {
		return senha;
	}

	public void setSenha(String senha) {
		this.senha = senha;
	}

}
