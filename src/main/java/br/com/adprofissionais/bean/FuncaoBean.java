package br.com.adprofissionais.bean;


import java.sql.SQLException;
import java.util.ArrayList;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import br.com.adprofissionais.dao.FuncaoDAO;
import br.com.adprofissionais.domain.Funcao;
import br.com.adprofissionais.util.JSFUtil;


@ManagedBean(name = "MBFuncao")
@ViewScoped
public class FuncaoBean {

	private Funcao funcao;
	private ArrayList<Funcao> itens;
	private ArrayList<Funcao> itensFiltrados;

	public Funcao getFuncao() {
		return funcao;
	}

	public void setFuncao(Funcao funcao) {
		this.funcao = funcao;
	}

	public ArrayList<Funcao> getItens() {
		return itens;
	}

	public void setItens(ArrayList<Funcao> itens) {
		this.itens = itens;
	}

	public ArrayList<Funcao> getItensFiltrados() {
		return itensFiltrados;
	}

	public void setItensFiltrados(ArrayList<Funcao> itensFiltrados) {
		this.itensFiltrados = itensFiltrados;
	}


	
	

	@PostConstruct
	public void prepararPesquisa() {
		try {
			FuncaoDAO tdao = new FuncaoDAO();
			itens = tdao.listar();
		} catch (SQLException ex) {
			ex.printStackTrace();
			JSFUtil.adcionarMensagemErro(ex.getMessage());
		}
	}

	public void novo() {
		FuncaoDAO tdao = new FuncaoDAO();
		try {
			tdao.salvar(funcao);
			itens = tdao.listar();
			JSFUtil.adcionarMensagemSucesso("Funcao Salvo com Sucesso");
		} catch (SQLException ex) {
			ex.printStackTrace();
			JSFUtil.adcionarMensagemErro(ex.getMessage());
		}
	}

	public void prepararNovo() {
		funcao = new Funcao();
	}

	public void excluir() {
		System.out.println("excluir");
		
		FuncaoDAO tdao = new FuncaoDAO();
		try {
			tdao.excluir(funcao);
			itens = tdao.listar();
			JSFUtil.adcionarMensagemSucesso("Funcao Excluido com Sucesso");
		} catch (SQLException ex) {
			ex.printStackTrace();
			JSFUtil.adcionarMensagemErro(ex.getMessage());
		}

	}

	public void editar() {
		FuncaoDAO tdao = new FuncaoDAO();
		try {
			tdao.editar(funcao);
			itens = tdao.listar();
			JSFUtil.adcionarMensagemSucesso("Funcao Alterado com Sucesso");
		} catch (SQLException ex) {
			ex.printStackTrace();
			JSFUtil.adcionarMensagemErro(ex.getMessage());
		}

	}

}
