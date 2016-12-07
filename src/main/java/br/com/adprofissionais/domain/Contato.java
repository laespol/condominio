package br.com.adprofissionais.domain;

import java.util.Date;

public class Contato {

	private Long codigo;
	private Date data;
	private String descricao;
	private Condominio condominio;
	private Responsavel responsavel ;
	private TipoContato tipocontato;

	public Long getCodigo() {
		return codigo;
	}

	public void setCodigo(Long codigo) {
		this.codigo = codigo;
	}

	public Date getData() {
		return data;
	}

	public void setData(Date data) {
		this.data = data;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public Condominio getCondominio() {
		return condominio;
	}

	public void setCondominio(Condominio condominio) {
		this.condominio = condominio;
	}


	public TipoContato getTipocontato() {
		return tipocontato;
	}

	public void setTipocontato(TipoContato tipocontato) {
		this.tipocontato = tipocontato;
	}

	public Responsavel getResponsavel() {
		return responsavel;
	}

	public void setResponsavel(Responsavel responsavel) {
		this.responsavel = responsavel;
	}

	@Override
	public String toString() {
		String saida = codigo + " " + descricao ;
		return saida;
	}

	

}
