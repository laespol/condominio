package br.com.adprofissionais.domain;

import java.sql.Date;

public class Usuario {
	
	private Long codigo;
	private String nome;
	private String login;
	private String senha;
	private String email;
	private Date dataativ;
	private Date datadesativ;
	private String status;
	private String numcpf;

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

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public Date getDataativ() {
		return dataativ;
	}

	public void setDataativ(Date dataativ) {
		this.dataativ = dataativ;
	}

	public Date getDatadesativ() {
		return datadesativ;
	}

	public void setDatadesativ(Date datadesativ) {
		this.datadesativ = datadesativ;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getNumcpf() {
		return numcpf;
	}

	public void setNumcpf(String numcpf) {
		this.numcpf = numcpf;
	}
	

}
