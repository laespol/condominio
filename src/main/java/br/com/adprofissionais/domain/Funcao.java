package br.com.adprofissionais.domain;

public class Funcao {
	
	private Long codigo;
	private String descricao;
	private Integer tipo;

	public Long getCodigo() {
		return codigo;
	}

	public void setCodigo(Long codigo) {
		this.codigo = codigo;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public Integer getTipo() {
		return tipo;
	}

	public void setTipo(Integer tipo) {
		this.tipo = tipo;
	}
	@Override
	public String toString() {
		String saida = codigo + " " + descricao + " " + tipo;
		return saida;
	}

}
