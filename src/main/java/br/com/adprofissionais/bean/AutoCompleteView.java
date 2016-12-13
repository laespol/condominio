package br.com.adprofissionais.bean;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.faces.bean.ManagedBean;

import br.com.adprofissionais.dao.EstadoDAO;
import br.com.adprofissionais.domain.Estado;
import br.com.adprofissionais.util.JSFUtil;
 
@ManagedBean
public class AutoCompleteView {
     
    private String txt1;


     
    public List<String> completeText(String query) {
    	System.out.println(query);
    	Estado estado = new Estado();
    	
    	estado.setDescricao(query);
    	
    	List<String> results = new ArrayList<String>();
        ArrayList<Estado> lista  = new ArrayList<Estado>();
         
		try {
			EstadoDAO tdao = new EstadoDAO();
			lista = tdao.buscarDescricao(estado);
			System.out.println(lista.size());
			for ( int i=0 ; i< lista.size(); i++ ) {
				results.add(lista.get(i).getDescricao());
				System.out.println(lista.get(i).getDescricao());
			}
			
			
		} catch (SQLException ex) {
			ex.printStackTrace();
			JSFUtil.adcionarMensagemErro(ex.getMessage());
		}
         
        return results;
    }


	public String getTxt1() {
		return txt1;
	}


	public void setTxt1(String txt1) {
		this.txt1 = txt1;
	}
     
 
}
