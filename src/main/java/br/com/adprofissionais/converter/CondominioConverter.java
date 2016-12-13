package br.com.adprofissionais.converter;

import java.sql.SQLException;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

import br.com.adprofissionais.dao.CondominioDAO;
import br.com.adprofissionais.domain.Condominio;
import br.com.adprofissionais.util.JSFUtil;

@FacesConverter(value = "condominioConverter")
public class CondominioConverter implements Converter {

	@Override
	public Object getAsObject(FacesContext fc, UIComponent uic, String string) {
		Condominio condominio = new Condominio();
		condominio.setDescricao(string);
		CondominioDAO cdao = new CondominioDAO();
		try {
			condominio = cdao.buscarDescricaoUm(condominio);
		} catch (SQLException ex) {
			ex.printStackTrace();
			JSFUtil.adcionarMensagemErro(ex.getMessage());
		}
		return condominio;
	}

	@Override
	public String getAsString(FacesContext fc, UIComponent uic, Object o) {
		Condominio condominio = new Condominio();
		condominio = (Condominio) o;
		return condominio.getDescricao();
	}

}