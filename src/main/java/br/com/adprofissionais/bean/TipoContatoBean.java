package br.com.adprofissionais.bean;

import java.sql.SQLException;
import java.util.ArrayList;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import br.com.adprofissionais.dao.TipoContatoDAO;
import br.com.adprofissionais.domain.TipoContato;
import br.com.adprofissionais.util.JSFUtil;


@ManagedBean(name = "MBTipoContato")
@ViewScoped
public class TipoContatoBean {

	private TipoContato tipocontato;
	private ArrayList<TipoContato> itens;
	private ArrayList<TipoContato> itensFiltrados;

	public TipoContato getTipocontato() {
		return tipocontato;
	}

	public void setTipocontato(TipoContato tipocontato) {
		this.tipocontato = tipocontato;
	}

	public ArrayList<TipoContato> getItens() {
		return itens;
	}

	public void setItens(ArrayList<TipoContato> itens) {
		this.itens = itens;
	}

	public ArrayList<TipoContato> getItensFiltrados() {
		return itensFiltrados;
	}

	public void setItensFiltrados(ArrayList<TipoContato> itensFiltrados) {
		this.itensFiltrados = itensFiltrados;
	}
	
	

	@PostConstruct
	public void prepararPesquisa() {
		try {
			TipoContatoDAO tdao = new TipoContatoDAO();
			itens = tdao.listar();
		} catch (SQLException ex) {
			ex.printStackTrace();
			JSFUtil.adcionarMensagemErro(ex.getMessage());
		}
	}

	public void novo() {
		TipoContatoDAO tdao = new TipoContatoDAO();
		try {
			tdao.salvar(tipocontato);
			itens = tdao.listar();
			JSFUtil.adcionarMensagemSucesso("Tipo de Contato Salvo com Sucesso");
		} catch (SQLException ex) {
			ex.printStackTrace();
			JSFUtil.adcionarMensagemErro(ex.getMessage());
		}
	}

	public void prepararNovo() {
		tipocontato = new TipoContato();
	}

	public void excluir() {
		TipoContatoDAO tdao = new TipoContatoDAO();
		try {
			tdao.excluir(tipocontato);
			itens = tdao.listar();
			JSFUtil.adcionarMensagemSucesso("Tipo de Contato Excluido com Sucesso");
		} catch (SQLException ex) {
			ex.printStackTrace();
			JSFUtil.adcionarMensagemErro(ex.getMessage());
		}

	}

	public void editar() {
		TipoContatoDAO tdao = new TipoContatoDAO();
		try {
			tdao.editar(tipocontato);
			itens = tdao.listar();
			JSFUtil.adcionarMensagemSucesso("Tipo de Contato Alterado com Sucesso");
		} catch (SQLException ex) {
			ex.printStackTrace();
			JSFUtil.adcionarMensagemErro(ex.getMessage());
		}

	}

}
