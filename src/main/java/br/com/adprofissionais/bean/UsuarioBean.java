package br.com.adprofissionais.bean;

import java.sql.SQLException;
import java.util.ArrayList;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import br.com.adprofissionais.dao.UsuarioDAO;
import br.com.adprofissionais.domain.Usuario;
import br.com.adprofissionais.util.JSFUtil;

@ManagedBean(name = "MBUsuario")
@ViewScoped
public class UsuarioBean {

	private Usuario usuario;
	private ArrayList<Usuario> itens;
	private ArrayList<Usuario> itensFiltrados;

	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

	public ArrayList<Usuario> getItens() {
		return itens;
	}

	public void setItens(ArrayList<Usuario> itens) {
		this.itens = itens;
	}

	public ArrayList<Usuario> getItensFiltrados() {
		return itensFiltrados;
	}

	public void setItensFiltrados(ArrayList<Usuario> itensFiltrados) {
		this.itensFiltrados = itensFiltrados;
	}

	public void prepararPesquisa() {
		try {
			UsuarioDAO udao = new UsuarioDAO();
			itens = udao.listar();
		} catch (SQLException ex) {
			ex.printStackTrace();
			JSFUtil.adcionarMensagemErro(ex.getMessage());
		}
	}

	public void novo() {
		UsuarioDAO udao = new UsuarioDAO();
		try {
			udao.salvar(usuario);
			itens = udao.listar();
			JSFUtil.adcionarMensagemSucesso("Usuario Salvo com Sucesso");
		} catch (SQLException ex) {
			ex.printStackTrace();
			JSFUtil.adcionarMensagemErro(ex.getMessage());
		}
	}

	public void prepararNovo() {
		usuario = new Usuario();
	}

	public void excluir() {
		UsuarioDAO udao = new UsuarioDAO();
		try {
			udao.excluir(usuario);
			itens = udao.listar();
			JSFUtil.adcionarMensagemSucesso("Usuario Excluido com Sucesso");
		} catch (SQLException ex) {
			ex.printStackTrace();
			JSFUtil.adcionarMensagemErro(ex.getMessage());
		}

	}

	public void editar() {
		UsuarioDAO udao = new UsuarioDAO();
		try {
			udao.editar(usuario);
			itens = udao.listar();
			JSFUtil.adcionarMensagemSucesso("Usuario Alterado com Sucesso");
		} catch (SQLException ex) {
			ex.printStackTrace();
			JSFUtil.adcionarMensagemErro(ex.getMessage());
		}

	}

}
