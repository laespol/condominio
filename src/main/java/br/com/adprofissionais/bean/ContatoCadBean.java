package br.com.adprofissionais.bean;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import br.com.adprofissionais.dao.CondominioDAO;
import br.com.adprofissionais.dao.ContatoDAO;
import br.com.adprofissionais.dao.ResponsavelDAO;
import br.com.adprofissionais.dao.TipoContatoDAO;
import br.com.adprofissionais.domain.Condominio;
import br.com.adprofissionais.domain.Contato;
import br.com.adprofissionais.domain.Responsavel;
import br.com.adprofissionais.domain.TipoContato;
import br.com.adprofissionais.util.JSFUtil;

@ManagedBean(name = "MBContatoCad")
@ViewScoped
public class ContatoCadBean {

	private Contato contato;
	private ArrayList<Contato> itens;
	private ArrayList<Contato> itensFiltrados;	
	
	private ArrayList<Condominio> comboCondominios;
	private ArrayList<Responsavel> comboResponsavels;
	private ArrayList<TipoContato> comboTipoContatos;
	
	private Date dataAtual = new Date();
	
	public Date getDataAtual() {
		return dataAtual;
	}
	public void setDataAtual(Date dataAtual) {
		this.dataAtual = dataAtual;
	}
	public Contato getContato() {
		return contato;
	}
	public void setContato(Contato contato) {
		this.contato = contato;
	}
	public ArrayList<Contato> getItens() {
		return itens;
	}
	public void setItens(ArrayList<Contato> itens) {
		this.itens = itens;
	}
	public ArrayList<Contato> getItensFiltrados() {
		return itensFiltrados;
	}
	public void setItensFiltrados(ArrayList<Contato> itensFiltrados) {
		this.itensFiltrados = itensFiltrados;
	}
	public ArrayList<Condominio> getComboCondominios() {
		return comboCondominios;
	}
	public void setComboCondominios(ArrayList<Condominio> comboCondominios) {
		this.comboCondominios = comboCondominios;
	}
	public ArrayList<Responsavel> getComboResponsavels() {
		return comboResponsavels;
	}
	public void setComboResponsavels(ArrayList<Responsavel> comboResponsavels) {
		this.comboResponsavels = comboResponsavels;
	}
	public ArrayList<TipoContato> getComboTipoContatos() {
		return comboTipoContatos;
	}
	public void setComboTipoContatos(ArrayList<TipoContato> comboTipoContatos) {
		this.comboTipoContatos = comboTipoContatos;
	}
	
	public void prepararPesquisa() {
		try {
			ContatoDAO cdao = new ContatoDAO();
			
			itens = cdao.listar();

		} catch (SQLException ex) {
			ex.printStackTrace();
			JSFUtil.adcionarMensagemErro(ex.getMessage());
		}
	}

	public void novo() {

		try {
			ContatoDAO  cdao = new ContatoDAO();
			
			cdao.salvar(contato);
			itens = cdao.listar();			
			JSFUtil.adcionarMensagemSucesso("Responsavel Salvo com Sucesso");
		} catch (SQLException ex) {
			ex.printStackTrace();
			JSFUtil.adcionarMensagemErro(ex.getMessage());
		}
	}

	@PostConstruct
	public void prepararNovo() {
		
		itens.add(1,contato);
		
		contato = new Contato();
		
		Condominio condominio = new Condominio();
		contato.setCondominio(condominio);
		
		TipoContato tipocontato = new TipoContato();
		contato.setTipocontato(tipocontato);
		
		Responsavel responsavel = new Responsavel();
		contato.setResponsavel(responsavel);
		
		CondominioDAO cdao = new CondominioDAO();
		TipoContatoDAO tdao = new TipoContatoDAO();
		ResponsavelDAO rdao = new ResponsavelDAO();
		
		try {
			comboCondominios  = cdao.listar();
			comboTipoContatos = tdao.listar();
			comboResponsavels = rdao.listarAdm();
		} catch (SQLException ex) {
			ex.printStackTrace();
			JSFUtil.adcionarMensagemErro(ex.getMessage());
			
		}
	}

	public void excluir() {
		ContatoDAO cdao = new ContatoDAO();
		
		try {
			cdao.excluir(contato);
			itens = cdao.listar();
			JSFUtil.adcionarMensagemSucesso("Responsavel Excluido com Sucesso");
		} catch (SQLException ex) {
			ex.printStackTrace();
			JSFUtil.adcionarMensagemErro(ex.getMessage());
		}

	}
	
	public void prepararEditar() {
		CondominioDAO cdao = new CondominioDAO();
		TipoContatoDAO tdao = new TipoContatoDAO();
		ResponsavelDAO rdao = new ResponsavelDAO();
		
		try {
			comboCondominios  = cdao.listar();
			comboTipoContatos = tdao.listar();
			comboResponsavels = rdao.listarAdm();
			
		} catch (SQLException ex) {
			ex.printStackTrace();
			JSFUtil.adcionarMensagemErro(ex.getMessage());
			
		}
	}

	public void editar() {
		ContatoDAO cdao = new ContatoDAO();

		try {
			cdao.editar(contato);
			itens = cdao.listar();
			JSFUtil.adcionarMensagemSucesso("Responsavel Alterado com Sucesso");
		} catch (SQLException ex) {
			ex.printStackTrace();
			JSFUtil.adcionarMensagemErro(ex.getMessage());
		}

	}

}

