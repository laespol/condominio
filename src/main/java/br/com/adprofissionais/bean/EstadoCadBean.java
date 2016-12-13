package br.com.adprofissionais.bean;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import org.primefaces.model.chart.Axis;
import org.primefaces.model.chart.AxisType;
import org.primefaces.model.chart.BarChartModel;
import org.primefaces.model.chart.BarChartSeries;
import org.primefaces.model.chart.CartesianChartModel;
import org.primefaces.model.chart.LineChartSeries;
import org.primefaces.model.chart.PieChartModel;

import br.com.adprofissionais.dao.EstadoDAO;
import br.com.adprofissionais.domain.Estado;
import br.com.adprofissionais.util.JSFUtil;

@ManagedBean(name = "MBEstadoCad")
@ViewScoped
public class EstadoCadBean {

	private Estado estado;
	private ArrayList<Estado> itens;
	private ArrayList<Estado> itensFiltrados;
    private PieChartModel pieModel2;
    private CartesianChartModel combinedModel;

	public PieChartModel getPieModel2() {
		return pieModel2;
	}

	public void setPieModel2(PieChartModel pieModel2) {
		this.pieModel2 = pieModel2;
	}

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
		 createPieModel2();
		 createCombinedModel();
		System.out.println("prepara novo");
		estado = new Estado();
	}

	public void novo() {
		System.out.println(itens);
		System.out.println("novo");
		System.out.println(estado.getCodigo() + " " + estado.getDescricao());
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

	public void excluir() {
		System.out.println("Entrei no Escluir");
		System.out.println(estado.getCodigo() + "  " + estado.getDescricao());
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
	
	public void prepararPesquisa() {
		try {
			System.out.println("prepararPesquisa");
			System.out.println(estado.getCodigo() + " " + estado.getDescricao());
			Estado e = new Estado();
			EstadoDAO edao = new EstadoDAO();
			e = edao.buscarPorCodigo(estado);
			System.out.println(e.getCodigo() + " " + e.getDescricao());
		} catch (SQLException ex) {
			ex.printStackTrace();
			JSFUtil.adcionarMensagemErro(ex.getMessage());
		}
	}
	
    private void  createPieModel2() {
        pieModel2 = new PieChartModel();
         
        pieModel2.set("Brand 1", 540);
        pieModel2.set("Brand 2", 325);
        pieModel2.set("Brand 3", 702);
        pieModel2.set("Brand 4", 421);
         
        pieModel2.setTitle("Custom Pie");
        pieModel2.setLegendPosition("e");
        pieModel2.setFill(false);
        pieModel2.setShowDataLabels(true);
        pieModel2.setDiameter(150);
    }
    
    private void createCombinedModel() {
        combinedModel = new BarChartModel();
 
        BarChartSeries boys = new BarChartSeries();
        boys.setLabel("Boys");
 
        boys.set("2004", 120);
        boys.set("2005", 100);
        boys.set("2006", 44);
        boys.set("2007", 150);
        boys.set("2008", 25);
 
        LineChartSeries girls = new LineChartSeries();
        girls.setLabel("Girls");
 
        girls.set("2004", 52);
        girls.set("2005", 60);
        girls.set("2006", 110);
        girls.set("2007", 135);
        girls.set("2008", 120);
 
        combinedModel.addSeries(boys);
        combinedModel.addSeries(girls);
         
        combinedModel.setTitle("Bar and Line");
        combinedModel.setLegendPosition("ne");
        combinedModel.setMouseoverHighlight(false);
        combinedModel.setShowDatatip(false);
        combinedModel.setShowPointLabels(true);
        Axis yAxis = combinedModel.getAxis(AxisType.Y);
        yAxis.setMin(0);
        yAxis.setMax(200);
    }

	public CartesianChartModel getCombinedModel() {
		return combinedModel;
	}

	public void setCombinedModel(CartesianChartModel combinedModel) {
		this.combinedModel = combinedModel;
	}
}
