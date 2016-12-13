package br.com.adprofissionais.dao;

import static org.junit.Assert.*;

import java.sql.SQLException;
import java.util.ArrayList;

import org.junit.Test;

import br.com.adprofissionais.domain.Condominio;
import br.com.adprofissionais.domain.Contato;

public class ContatoDAOTest {

	@Test
	public void testBuscarPorCondominio() {
		
		Contato c = new Contato();
		Condominio co = new Condominio();
		ArrayList<Contato> lista = new ArrayList<Contato>();
		
		ContatoDAO dao = new ContatoDAO();
		
		co.setCodigo(4L);
		
		c.setCondominio(co);
		
		try {
			lista =  dao.buscarPorCondominio(c);
			
			System.out.println(lista.size());
			
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			fail("Not yet implemented");
		}
	}

}
