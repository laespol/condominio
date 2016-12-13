package br.com.adprofissionais.domain;

import java.sql.Date;

public class LogAcesso {
	
	private Long codigo;
	private Date dataacesso;
	private String login;
	private String sucesso;
	public Long getCodigo() {
		return codigo;
	}
	public void setCodigo(Long codigo) {
		this.codigo = codigo;
	}
	public Date getDataacesso() {
		return dataacesso;
	}
	public void setDataacesso(Date dataacesso) {
		this.dataacesso = dataacesso;
	}
	public String getLogin() {
		return login;
	}
	public void setLogin(String login) {
		this.login = login;
	}
	public String getSucesso() {
		return sucesso;
	}
	public void setSucesso(String sucesso) {
		this.sucesso = sucesso;
	}
	

}
