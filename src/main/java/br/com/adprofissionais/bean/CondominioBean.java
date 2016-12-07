package br.com.adprofissionais.bean;

import java.sql.SQLException;
import java.util.ArrayList;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import br.com.adprofissionais.dao.CondominioDAO;
import br.com.adprofissionais.dao.EstadoDAO;
import br.com.adprofissionais.domain.Condominio;
import br.com.adprofissionais.domain.Estado;
import br.com.adprofissionais.util.JSFUtil;

@ManagedBean(name = "MBCondominio")
@ViewScoped
public class CondominioBean {

	private Condominio condominio ;
	private ArrayList<Condominio> itens;
	private ArrayList<Condominio> itensFiltrados;
	
	
	
	private ArrayList<Estado> comboEstados;
	
	public ArrayList<Estado> getComboEstados() {
		return comboEstados;
	}

	public void setComboEstados(ArrayList<Estado> comboEstados) {
		this.comboEstados = comboEstados;
	}

	public Condominio getCondominio() {
		return condominio;
	}

	public void setCondominio(Condominio condominio) {
		this.condominio = condominio;
	}

	public ArrayList<Condominio> getItens() {
		return itens;
	}

	public void setItens(ArrayList<Condominio> itens) {
		this.itens = itens;
	}

	public ArrayList<Condominio> getItensFiltrados() {
		return itensFiltrados;
	}

	public void setItensFiltrados(ArrayList<Condominio> itensFiltrados) {
		this.itensFiltrados = itensFiltrados;
	}

	@PostConstruct
	public void prepararPesquisa() {
		try {
			CondominioDAO cdao = new CondominioDAO();
			itens = cdao.listar();
		} catch (SQLException ex) {
			ex.printStackTrace();
			JSFUtil.adcionarMensagemErro(ex.getMessage());
		}
	}

	public void novo() {
		CondominioDAO cdao = new CondominioDAO();
		try {
			cdao.salvar(condominio);
			itens = cdao.listar();
			JSFUtil.adcionarMensagemSucesso("Condominio Salvo com Sucesso");
		} catch (SQLException ex) {
			ex.printStackTrace();
			JSFUtil.adcionarMensagemErro(ex.getMessage());
		}
	}

	public void prepararNovo() {
		condominio = new Condominio();
		Estado estado = new Estado();
		
		condominio.setEstado(estado);
		
		EstadoDAO edao = new EstadoDAO();
		
		try {
			comboEstados = edao.listar();
		} catch (SQLException ex) {
			ex.printStackTrace();
			JSFUtil.adcionarMensagemErro(ex.getMessage());
			
		}
	}

	public void excluir() {
		CondominioDAO cdao = new CondominioDAO();
		try {
			cdao.excluir(condominio);
			itens = cdao.listar();
			JSFUtil.adcionarMensagemSucesso("Condominio Excluido com Sucesso");
		} catch (SQLException ex) {
			ex.printStackTrace();
			JSFUtil.adcionarMensagemErro(ex.getMessage());
		}

	}
	
	public void prepararEditar() {
		EstadoDAO edao = new EstadoDAO();
		try {
			comboEstados = edao.listar();
			
		} catch (SQLException ex) {
			ex.printStackTrace();
			JSFUtil.adcionarMensagemErro(ex.getMessage());
			
		}
	}

	public void editar() {
		CondominioDAO cdao = new CondominioDAO();

		try {
			cdao.editar(condominio);
			itens = cdao.listar();
			JSFUtil.adcionarMensagemSucesso("Condominio Alterado com Sucesso");
		} catch (SQLException ex) {
			ex.printStackTrace();
			JSFUtil.adcionarMensagemErro(ex.getMessage());
		}

	}

}
