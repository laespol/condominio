package br.com.adprofissionais.bean;

import java.sql.SQLException;
import java.util.ArrayList;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import br.com.adprofissionais.dao.CondominioDAO;
import br.com.adprofissionais.dao.FuncaoDAO;
import br.com.adprofissionais.dao.ResponsavelDAO;
import br.com.adprofissionais.dao.UsuarioDAO;
import br.com.adprofissionais.domain.Condominio;
import br.com.adprofissionais.domain.Funcao;
import br.com.adprofissionais.domain.Responsavel;
import br.com.adprofissionais.domain.Usuario;
import br.com.adprofissionais.util.JSFUtil;

@ManagedBean(name = "MBResponsavel")
@ViewScoped
public class ResponsavelBean {

	private Responsavel responsavel;
	private ArrayList<Responsavel> itens;
	private ArrayList<Responsavel> itensFiltrados;

	private ArrayList<Condominio> comboCondominios;
	private ArrayList<Funcao> comboFuncaos;
	private ArrayList<Usuario> comboUsuarios;

	public Responsavel getResponsavel() {
		return responsavel;
	}

	public void setResponsavel(Responsavel responsavel) {
		this.responsavel = responsavel;
	}

	public ArrayList<Responsavel> getItens() {
		return itens;
	}

	public void setItens(ArrayList<Responsavel> itens) {
		this.itens = itens;
	}

	public ArrayList<Responsavel> getItensFiltrados() {
		return itensFiltrados;
	}

	public void setItensFiltrados(ArrayList<Responsavel> itensFiltrados) {
		this.itensFiltrados = itensFiltrados;
	}

	public ArrayList<Condominio> getComboCondominios() {
		return comboCondominios;
	}

	public void setComboCondominios(ArrayList<Condominio> comboCondominios) {
		this.comboCondominios = comboCondominios;
	}

	public ArrayList<Funcao> getComboFuncaos() {
		return comboFuncaos;
	}

	public void setComboFuncaos(ArrayList<Funcao> comboFuncaos) {
		this.comboFuncaos = comboFuncaos;
	}

	public ArrayList<Usuario> getComboUsuarios() {
		return comboUsuarios;
	}

	public void setComboUsuarios(ArrayList<Usuario> comboUsuarios) {
		this.comboUsuarios = comboUsuarios;
	}

	@PostConstruct
	public void prepararPesquisa() {
		try {
			ResponsavelDAO rdao = new ResponsavelDAO();
			itens = rdao.listar();
		} catch (SQLException ex) {
			ex.printStackTrace();
			JSFUtil.adcionarMensagemErro(ex.getMessage());
		}
	}

	public void novo() {

		try {
			ResponsavelDAO rdao = new ResponsavelDAO();
			rdao.salvar(responsavel);
			itens = rdao.listar();
			JSFUtil.adcionarMensagemSucesso("Responsavel Salvo com Sucesso");
		} catch (SQLException ex) {
			ex.printStackTrace();
			JSFUtil.adcionarMensagemErro(ex.getMessage());
		}
	}

	public void prepararNovo() {
		responsavel = new Responsavel();

		Condominio condominio = new Condominio();
		responsavel.setCondominio(condominio);

		Usuario usuario = new Usuario();
		responsavel.setUsuario(usuario);

		Funcao funcao = new Funcao();
		responsavel.setFuncao(funcao);

		CondominioDAO cdao = new CondominioDAO();
		FuncaoDAO fdao = new FuncaoDAO();
		UsuarioDAO udao = new UsuarioDAO();

		try {
			comboCondominios = cdao.listar();
			comboFuncaos = fdao.listar();
			comboUsuarios = udao.listar();
			System.out.println(comboCondominios);
		} catch (SQLException ex) {
			ex.printStackTrace();
			JSFUtil.adcionarMensagemErro(ex.getMessage());

		}
	}

	public void excluir() {
		ResponsavelDAO rdao = new ResponsavelDAO();

		try {
			rdao.excluir(responsavel);
			itens = rdao.listar();
			JSFUtil.adcionarMensagemSucesso("Responsavel Excluido com Sucesso");
		} catch (SQLException ex) {
			ex.printStackTrace();
			JSFUtil.adcionarMensagemErro(ex.getMessage());
		}

	}

	public void prepararEditar() {
		CondominioDAO cdao = new CondominioDAO();
		FuncaoDAO fdao = new FuncaoDAO();
		UsuarioDAO udao = new UsuarioDAO();

		try {
			comboCondominios = cdao.listar();
			comboFuncaos = fdao.listar();
			comboUsuarios = udao.listar();

		} catch (SQLException ex) {
			ex.printStackTrace();
			JSFUtil.adcionarMensagemErro(ex.getMessage());

		}
	}

	public void editar() {
		ResponsavelDAO rdao = new ResponsavelDAO();

		try {
			rdao.editar(responsavel);
			itens = rdao.listar();
			JSFUtil.adcionarMensagemSucesso("Responsavel Alterado com Sucesso");
		} catch (SQLException ex) {
			ex.printStackTrace();
			JSFUtil.adcionarMensagemErro(ex.getMessage());
		}

	}

}
