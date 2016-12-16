package br.com.adprofissionais.bean;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.servlet.http.HttpSession;

import br.com.adprofissionais.dao.EstadoDAO;
import br.com.adprofissionais.domain.Estado;
import br.com.adprofissionais.util.JSFUtil;
import br.com.adprofissionais.util.Util;

@ManagedBean(name = "MBEstadoCad")
@ViewScoped
public class EstadoCadBean {

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

	public List<String> completeText(String query) {
		System.out.println(query);
		Estado estado = new Estado();

		estado.setDescricao(query);

		List<String> results = new ArrayList<String>();
		ArrayList<Estado> lista = new ArrayList<Estado>();

		try {
			EstadoDAO tdao = new EstadoDAO();
			lista = tdao.buscarDescricao(estado);
			System.out.println(lista.size());
			for (int i = 0; i < lista.size(); i++) {
				results.add(lista.get(i).getDescricao());
				System.out.println(lista.get(i).getDescricao());
			}

		} catch (SQLException ex) {
			ex.printStackTrace();
			JSFUtil.adcionarMensagemErro(ex.getMessage());
		}

		return results;
	}

	public List<String> completeTextCodigo(String query) {
		System.out.println(query);
		Estado estado = new Estado();

		estado.setCodigo(query);

		List<String> results = new ArrayList<String>();

		try {
			EstadoDAO tdao = new EstadoDAO();
			itens = tdao.buscarCodigolista(estado);
			System.out.println(itens.size());
			for (int i = 0; i < itens.size(); i++) {
				results.add(itens.get(i).getCodigo());
				System.out.println(itens.get(i).getCodigo());
			}

		} catch (SQLException ex) {
			ex.printStackTrace();
			JSFUtil.adcionarMensagemErro(ex.getMessage());
		}

		return results;
	}

	@PostConstruct
	public void prepararNovo() {
		System.out.println("prepara novo");
		estado = new Estado();
		HttpSession session = Util.getSession();
		System.out.println(session.getAttribute("username") + " request");
	}

	public void novo() {
		System.out.println(itens);
		System.out.println("novo");
		System.out.println(estado.getCodigo() + " " + estado.getDescricao());
		EstadoDAO tdao = new EstadoDAO();
		try {
			tdao.salvar(estado);
			JSFUtil.adcionarMensagemSucesso("Estado Salvo com Sucesso");
		} catch (SQLException ex) {
			ex.printStackTrace();
			JSFUtil.adcionarMensagemErro(ex.getMessage());
		}
	}

	public void excluir() {
		System.out.println("Entrei no Escluir");
		System.out.println(estado.getCodigo() + "  " + estado.getDescricao());
		EstadoDAO tdao = new EstadoDAO();
		try {
			estado = tdao.buscarPorCodigo(estado);
			if (estado.getCodigo().equals(null)) {
				JSFUtil.adcionarMensagemErro("Estado não econtrado");
			} else {
				System.out.println("Entrei no Escluir");
				System.out.println(estado.getCodigo() + "  " + estado.getDescricao());
				tdao.excluir(estado);
				JSFUtil.adcionarMensagemSucesso("Estado Excluido com Sucesso");
			}
		} catch (SQLException ex) {
			ex.printStackTrace();
			JSFUtil.adcionarMensagemErro(ex.getMessage());
		}

	}

	public void editar() {
		EstadoDAO tdao = new EstadoDAO();
		try {
			estado = tdao.buscarPorCodigo(estado);
			if (estado.getCodigo().equals(null)) {
				JSFUtil.adcionarMensagemErro("Estado não econtrado");
			} else {
				tdao.editar(estado);
				JSFUtil.adcionarMensagemSucesso("Estado Alterado com Sucesso");
			}
		} catch (SQLException ex) {
			ex.printStackTrace();
			JSFUtil.adcionarMensagemErro(ex.getMessage());
		}

	}

	public void prepararPesquisa() {
		System.out.println("prepararPesquisa");
		EstadoDAO edao = new EstadoDAO();
		try {
			String codigo = estado.getCodigo();
			estado = edao.buscarPorCodigo(estado);
			estado.setCodigo(codigo);
			System.out.println(estado.getCodigo() + " " + estado.getDescricao());
		} catch (SQLException ex) {
			ex.printStackTrace();
			JSFUtil.adcionarMensagemErro(ex.getMessage());
		}
	}
}
