package br.com.adprofissionais.dao;

import static org.junit.Assert.fail;

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
			fail("Erro no momento de Salvar");
		}
	}

	@Test
	public void testSalvarNull() {

		TipoContato t = new TipoContato();

		TipoContatoDAO dao = new TipoContatoDAO();

		t.setDescricao(null);

		try {
			dao.salvar(t);
			fail("Gravando registro nulo");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	@Test
	public void testSalvarTamanhoSuperiorMaximo() {

		TipoContato t = new TipoContato();

		TipoContatoDAO dao = new TipoContatoDAO();

		t.setDescricao("12345678901234567890123456789012345678901234567890");

		try {
			dao.salvar(t);
			fail("Gravando registro com tamanho superior ao campo");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	@Test
	public void testSalvarTamanhoMaximo() {

		TipoContato t = new TipoContato();

		TipoContatoDAO dao = new TipoContatoDAO();

		t.setDescricao("123456789012345678901234567890123456789012345");

		try {
			dao.salvar(t);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			fail("Gravando registro com tamanho superior ao campo");
		}

	}

}
