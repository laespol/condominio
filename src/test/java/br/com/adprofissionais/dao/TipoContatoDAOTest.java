package br.com.adprofissionais.dao;

import static org.junit.Assert.*;

import java.sql.SQLException;

import org.junit.Test;

import br.com.adprofissionais.domain.TipoContato;

public class TipoContatoDAOTest {

	@Test
	public void testSalvar() {
		
		TipoContato t = new TipoContato();
		
		t.setDescricao("Teste Junit");
		
		TipoContatoDAO dao = new TipoContatoDAO();
		
		try {
			dao.salvar(t);
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			fail("Not yet implemented");
		}
		
	}

}
