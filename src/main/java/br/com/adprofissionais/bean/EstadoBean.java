package br.com.adprofissionais.bean;

import java.sql.SQLException;
import java.util.ArrayList;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import br.com.adprofissionais.dao.EstadoDAO;
import br.com.adprofissionais.domain.Estado;
import br.com.adprofissionais.util.JSFUtil;

@ManagedBean(name = "MBEstado")
@ViewScoped
public class EstadoBean {

	private Estado estado;
	private ArrayList<Estado> itens;
	private ArrayList<Estado> itensFiltrados;


	public Estado getEstado() {
		return estado;
	}

	public void setEstado(Estado estado) {
		this.estado = estado;
	}

	public ArrayList<Estado> getItens() {
		return itens;
	}

	public void setItens(ArrayList<Estado> itens) {
		this.itens = itens;
	}

	public ArrayList<Estado> getItensFiltrados() {
		return itensFiltrados;
	}

	public void setItensFiltrados(ArrayList<Estado> itensFiltrados) {
		this.itensFiltrados = itensFiltrados;
	}	

	@PostConstruct
	public void prepararPesquisa() {
		try {
			EstadoDAO tdao = new EstadoDAO();
			itens = tdao.listar();
		} catch (SQLException ex) {
			ex.printStackTrace();
			JSFUtil.adcionarMensagemErro(ex.getMessage());
		}
	}

	public void novo() {
		EstadoDAO tdao = new EstadoDAO();
		try {
			tdao.salvar(estado);
			itens = tdao.listar();
			JSFUtil.adcionarMensagemSucesso("Estado Salvo com Sucesso");
		} catch (SQLException ex) {
			ex.printStackTrace();
			JSFUtil.adcionarMensagemErro(ex.getMessage());
		}
	}

	public void prepararNovo() {
		estado = new Estado();
	}

	public void excluir() {
		EstadoDAO tdao = new EstadoDAO();
		try {
			tdao.excluir(estado);
			itens = tdao.listar();
			JSFUtil.adcionarMensagemSucesso("Estado Excluido com Sucesso");
		} catch (SQLException ex) {
			ex.printStackTrace();
			JSFUtil.adcionarMensagemErro(ex.getMessage());
		}

	}

	public void editar() {
		EstadoDAO tdao = new EstadoDAO();
		try {
			tdao.editar(estado);
			itens = tdao.listar();
			JSFUtil.adcionarMensagemSucesso("Estado Alterado com Sucesso");
		} catch (SQLException ex) {
			ex.printStackTrace();
			JSFUtil.adcionarMensagemErro(ex.getMessage());
		}

	}

}
