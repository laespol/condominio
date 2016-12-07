package br.com.adprofissionais.domain;

public class Responsavel {

	private Long codigo;
	private String telefonecelular;
	private String telefonefixo;
	private String email;
	private Funcao funcao;
	private Condominio condominio;
	private Usuario usuario ;
	

	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

	public Funcao getFuncao() {
		return funcao;
	}

	public void setFuncao(Funcao funcao) {
		this.funcao = funcao;
	}

	public Condominio getCondominio() {
		return condominio;
	}

	public void setCondominio(Condominio condominio) {
		this.condominio = condominio;
	}

	public Long getCodigo() {
		return codigo;
	}

	public void setCodigo(Long codigo) {
		this.codigo = codigo;
	}

	public String getTelefonecelular() {
		return telefonecelular;
	}

	public void setTelefonecelular(String telefonecelular) {
		this.telefonecelular = telefonecelular;
	}

	public String getTelefonefixo() {
		return telefonefixo;
	}

	public void setTelefonefixo(String telefonefixo) {
		this.telefonefixo = telefonefixo;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

}
