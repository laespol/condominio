package br.com.adprofissionais.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import br.com.adprofissionais.domain.Condominio;
import br.com.adprofissionais.domain.Contato;
import br.com.adprofissionais.domain.Estado;
import br.com.adprofissionais.domain.Funcao;
import br.com.adprofissionais.domain.Responsavel;
import br.com.adprofissionais.domain.TipoContato;
import br.com.adprofissionais.domain.Usuario;
import br.com.adprofissionais.factory.ConexaoFactory;

public class ContatoDAO {
	
	public void salvar(Contato c) throws SQLException {
		StringBuilder sql = new StringBuilder();
		sql.append("INSERT INTO contato ");
		sql.append("(descricao,data, condominio_codigo , responsavel_codigo,  ");
		sql.append("tipocontato_codigo) ");
		sql.append("VALUES (?,?,?,?,?) ");

		Connection conexao = ConexaoFactory.conectar();
		
		java.sql.Date d = new java.sql.Date(c.getData().getTime());

		PreparedStatement comando = conexao.prepareStatement(sql.toString());
		comando.setString(1, c.getDescricao());
		comando.setDate(2, d);
		comando.setLong(3, c.getCondominio().getCodigo());
		comando.setLong(4, c.getResponsavel().getCodigo());
		comando.setLong(5,c.getTipocontato().getCodigo());

		comando.executeUpdate();

	}
	
	public void excluir(Contato c) throws SQLException {
		StringBuilder sql = new StringBuilder();
		sql.append("DELETE FROM contato ");
		sql.append("WHERE codigo = ? ");

		Connection conexao = ConexaoFactory.conectar();

		PreparedStatement comando = conexao.prepareStatement(sql.toString());

		comando.setLong(1, c.getCodigo());

		comando.executeUpdate();

	}
	
	public void editar(Contato c) throws SQLException {
		
		StringBuilder sql = new StringBuilder();
		sql.append("UPDATE contato ");
		sql.append("SET descricao = ?, data =? , ");
		sql.append("condominio_codigo, responsavel_codigo =? , ");
		sql.append("tipocontato_codigo = ? ");
		sql.append("WHERE codigo = ? ");

		Connection conexao = ConexaoFactory.conectar();

		PreparedStatement comando = conexao.prepareStatement(sql.toString());
		
		
		comando.setString(1, c.getDescricao());
		comando.setDate(2, (java.sql.Date) c.getData());
		comando.setLong(3, c.getCondominio().getCodigo());
		comando.setLong(4, c.getResponsavel().getCodigo());
		comando.setLong(5,c.getTipocontato().getCodigo());
		comando.setLong(6, c.getCodigo());

		comando.executeUpdate();

	}

	public ArrayList<Contato> buscarPorCondominio(Contato c) throws SQLException {
		StringBuilder sql = new StringBuilder();
		sql.append("SELECT c.codigo, c.data, c.descricao, c.condominio_codigo, c.responsavel_codigo, c.tipocontato_codigo, ");
		sql.append("co.codigo,co.descricao, co.endereco,co.cidade,co.bairro, co.cep, co.telefonefixo, co.telefonecelular, co.estado_codigo, ");
		sql.append("e.codigo, e.descricao, ");
		sql.append("t.codigo,t.descricao, ");
		sql.append("f.codigo,f.descricao,f.tipo, ");
		sql.append("r.codigo, r.funcao_codigo, r.email, r.condominio_codigo, r.telefonecelular, r.telefonefixo ");
		sql.append(" FROM contato c ");
		sql.append(" INNER JOIN condominio co ON co.codigo = c.condominio_codigo ");
		sql.append(" INNER JOIN responsavel r ON r.codigo  = c.responsavel_codigo ");
		sql.append(" INNER JOIN tipocontato t ON t.codigo = c.tipocontato_codigo ");
		sql.append(" INNER JOIN funcao  f     ON f.codigo = r.funcao_codigo ");
		sql.append(" INNER JOIN estado   e    ON e.codigo = co.estado_codigo ");
		sql.append(" WHERE co.codigo = ? ORDER BY c.data DESC ");

		Connection conexao = ConexaoFactory.conectar();

		PreparedStatement comando = conexao.prepareStatement(sql.toString());
		comando.setLong(1, c.getCondominio().getCodigo());

		ResultSet resultado = comando.executeQuery();
		
		ArrayList<Contato> lista = new ArrayList<Contato>();
		
		Contato contato = null;

		while (resultado.next()) {
			
			contato = new Contato();
			
			Responsavel  responsavel = new Responsavel();
			Condominio condominio = new Condominio();
			Funcao     funcao     = new Funcao();
			Estado     estado     = new Estado();
			TipoContato tipocontato = new TipoContato();
			
			contato.setCodigo(resultado.getLong("c.codigo"));
			contato.setData(resultado.getDate("c.data"));
			contato.setDescricao(resultado.getString("c.descricao"));
			
			tipocontato.setCodigo(resultado.getLong("t.codigo"));
			tipocontato.setDescricao(resultado.getString("t.descricao"));
			
			
			responsavel.setCodigo(resultado.getLong("r.codigo"));
			responsavel.setTelefonecelular(resultado.getString("r.telefonecelular"));
			responsavel.setTelefonefixo(resultado.getString("r.telefonefixo"));
			responsavel.setEmail(resultado.getString("r.email"));
			
			condominio.setCodigo(resultado.getLong("co.codigo"));
			condominio.setDescricao(resultado.getString("co.descricao"));
			condominio.setEndereco(resultado.getString("co.endereco"));
			condominio.setBairro(resultado.getString("co.bairro"));
			condominio.setCidade(resultado.getString("co.cidade"));
			condominio.setCep(resultado.getString("co.cep"));
			condominio.setTelefonecelular(resultado.getString("co.telefonecelular"));
			condominio.setTelefonefixo(resultado.getString("co.telefonefixo"));
			
			estado.setCodigo(resultado.getString("e.codigo"));
			estado.setDescricao(resultado.getString("e.descricao"));
			
			funcao.setCodigo(resultado.getLong("f.codigo"));
			funcao.setDescricao(resultado.getString("f.descricao"));
			funcao.setTipo(resultado.getInt("f.tipo"));
			
			
			
			condominio.setEstado(estado);
			responsavel.setCondominio(condominio);
			responsavel.setFuncao(funcao);
			
			contato.setCondominio(condominio);
			contato.setResponsavel(responsavel);
			contato.setTipocontato(tipocontato);

			lista.add(contato);

		}

		return lista;		
	}
	

	public Contato buscarPorCodigo(Contato c) throws SQLException {
		StringBuilder sql = new StringBuilder();
		sql.append("SELECT c.codigo, c.data, c.descricao, c.condominio_codigo, c.responsavel_codigo, c.tipocontato_codigo, ");
		sql.append("co.codigo,co.descricao, co.endereco,co.cidade,co.bairro, co.cep, co.telefonefixo, co.telefonecelular, co.estado_codigo, ");
		sql.append("e.codigo, e.descricao, ");
		sql.append("t.codigo,t.descricao, ");
		sql.append("f.codigo,f.descricao,f.tipo, ");
		sql.append("r.codigo, r.funcao_codigo, r.email, r.condominio_codigo, r.telefonecelular, r.telefonefixo, ");
		sql.append("u.codigo, u.nome, u.dataativ " );
		sql.append(" FROM contato c ");
		sql.append(" INNER JOIN condominio co ON co.codigo = c.condominio_codigo ");
		sql.append(" INNER JOIN responsavel r ON r.codigo  = c.responsavel_codigo ");
		sql.append(" INNER JOIN tipocontato t ON t.codigo = c.tipocontato_codigo ");
		sql.append(" INNER JOIN funcao  f     ON f.codigo = r.funcao_codigo ");
		sql.append(" INNER JOIN estado   e    ON e.codigo = co.estado_codigo ");
		sql.append(" INNER JOIN usuario  u    ON u.codigo = r.usuario_codigo  ");
		sql.append(" WHERE c.codigo = ? ORDER BY c.data DESC ");

		Connection conexao = ConexaoFactory.conectar();

		PreparedStatement comando = conexao.prepareStatement(sql.toString());
		comando.setLong(1, c.getCodigo());

		ResultSet resultado = comando.executeQuery();
		Contato contato = null;
		
		if (resultado.next()) {
			contato = new Contato();
			
			Responsavel  responsavel = new Responsavel();
			Condominio condominio = new Condominio();
			Funcao     funcao     = new Funcao();
			Estado     estado     = new Estado();
			Usuario    usuario    = new Usuario();
			TipoContato tipocontato = new TipoContato();
			
			contato.setCodigo(resultado.getLong("c.codigo"));
			contato.setData(resultado.getDate("c.data"));
			contato.setDescricao(resultado.getString("c.descricao"));
			
			tipocontato.setCodigo(resultado.getLong("t.codigo"));
			tipocontato.setDescricao(resultado.getString("t.descricao"));
			
			
			responsavel.setCodigo(resultado.getLong("r.codigo"));
			responsavel.setTelefonecelular(resultado.getString("r.telefonecelular"));
			responsavel.setTelefonefixo(resultado.getString("r.telefonefixo"));
			responsavel.setEmail(resultado.getString("r.email"));
			
			condominio.setCodigo(resultado.getLong("co.codigo"));
			condominio.setDescricao(resultado.getString("co.descricao"));
			condominio.setEndereco(resultado.getString("co.endereco"));
			condominio.setBairro(resultado.getString("co.bairro"));
			condominio.setCidade(resultado.getString("co.cidade"));
			condominio.setCep(resultado.getString("co.cep"));
			condominio.setTelefonecelular(resultado.getString("co.telefonecelular"));
			condominio.setTelefonefixo(resultado.getString("co.telefonefixo"));
			
			estado.setCodigo(resultado.getString("e.codigo"));
			estado.setDescricao(resultado.getString("e.descricao"));
			
			funcao.setCodigo(resultado.getLong("f.codigo"));
			funcao.setDescricao(resultado.getString("f.descricao"));
			funcao.setTipo(resultado.getInt("f.tipo"));
			
			usuario.setCodigo(resultado.getLong("u.codigo"));
			usuario.setNome(resultado.getString("u.nome"));
			usuario.setDataativ(resultado.getDate("u.dataativ"));
			
			
			
			condominio.setEstado(estado);
			responsavel.setCondominio(condominio);
			responsavel.setFuncao(funcao);
			responsavel.setUsuario(usuario);
			
			contato.setCondominio(condominio);
			contato.setResponsavel(responsavel);
			contato.setTipocontato(tipocontato);


		}
		return contato;

	}
	
	public ArrayList<Contato> listar() throws SQLException {
		StringBuilder sql = new StringBuilder();
		sql.append("SELECT c.codigo, c.data, c.descricao, c.condominio_codigo, c.responsavel_codigo, c.tipocontato_codigo, ");
		sql.append("co.codigo,co.descricao, co.endereco,co.cidade, co.cep, co.bairro,co.telefonefixo, co.telefonecelular, co.estado_codigo, ");
		sql.append("e.codigo, e.descricao, ");
		sql.append("t.codigo,t.descricao, ");
		sql.append("f.codigo,f.descricao,f.tipo, ");
		sql.append("r.codigo, r.funcao_codigo, r.email, r.condominio_codigo, r.telefonecelular, r.telefonefixo , ");
		sql.append("u.codigo, u.nome, u.dataativ " );
		sql.append(" FROM contato c ");		
		sql.append(" INNER JOIN condominio co ON co.codigo = c.condominio_codigo ");
		sql.append(" INNER JOIN responsavel r ON r.codigo  = c.responsavel_codigo ");
		sql.append(" INNER JOIN tipocontato t ON t.codigo = c.tipocontato_codigo ");
		sql.append(" INNER JOIN funcao  f     ON f.codigo = r.funcao_codigo ");
		sql.append(" INNER JOIN estado   e    ON e.codigo = co.estado_codigo ");
		sql.append(" INNER JOIN usuario  u    ON u.codigo = r.usuario_codigo  ");
		sql.append(" ORDER BY c.data DESC ");


		Connection conexao = ConexaoFactory.conectar();

		PreparedStatement comando = conexao.prepareStatement(sql.toString());

		ResultSet resultado = comando.executeQuery();

		ArrayList<Contato> lista = new ArrayList<Contato>();
		
		Contato contato = null;

		while (resultado.next()) {
			
			contato = new Contato();			
			
			Responsavel  responsavel = new Responsavel();
			Condominio condominio = new Condominio();
			Funcao     funcao     = new Funcao();
			Estado     estado     = new Estado();
			Usuario    usuario    = new Usuario();
			TipoContato tipocontato = new TipoContato();
			
			contato.setCodigo(resultado.getLong("c.codigo"));
			contato.setData(resultado.getDate("c.data"));
			contato.setDescricao(resultado.getString("c.descricao"));
			
			tipocontato.setCodigo(resultado.getLong("t.codigo"));
			tipocontato.setDescricao(resultado.getString("t.descricao"));
			
			
			responsavel.setCodigo(resultado.getLong("r.codigo"));
			responsavel.setTelefonecelular(resultado.getString("r.telefonecelular"));
			responsavel.setTelefonefixo(resultado.getString("r.telefonefixo"));
			responsavel.setEmail(resultado.getString("r.email"));
			
			condominio.setCodigo(resultado.getLong("co.codigo"));
			condominio.setDescricao(resultado.getString("co.descricao"));
			condominio.setEndereco(resultado.getString("co.endereco"));
			condominio.setBairro(resultado.getString("co.bairro"));
			condominio.setCidade(resultado.getString("co.cidade"));
			condominio.setCep(resultado.getString("co.cep"));
			condominio.setTelefonecelular(resultado.getString("co.telefonecelular"));
			condominio.setTelefonefixo(resultado.getString("co.telefonefixo"));
			
			estado.setCodigo(resultado.getString("e.codigo"));
			estado.setDescricao(resultado.getString("e.descricao"));
			
			funcao.setCodigo(resultado.getLong("f.codigo"));
			funcao.setDescricao(resultado.getString("f.descricao"));
			funcao.setTipo(resultado.getInt("f.tipo"));			responsavel.setUsuario(usuario);
			
			usuario.setCodigo(resultado.getLong("u.codigo"));
			usuario.setNome(resultado.getString("u.nome"));
			usuario.setDataativ(resultado.getDate("u.dataativ"));
			
			
			condominio.setEstado(estado);
			responsavel.setCondominio(condominio);
			responsavel.setFuncao(funcao);
			responsavel.setUsuario(usuario);
			
			contato.setCondominio(condominio);
			contato.setResponsavel(responsavel);
			contato.setTipocontato(tipocontato);
			
			lista.add(contato);

		}

		return lista;
	}

	public ArrayList<Contato> buscarDescricao(Contato c) throws SQLException {
		StringBuilder sql = new StringBuilder();
		sql.append("SELECT c.codigo, c.data, c.descricao, c.condominio_codigo, c.responsavel_codigo, c.tipocontato_codigo, ");
		sql.append("co.codigo,co.descricao, co.endereco,co.cidade,co.bairo, co.cep, co.telefonefixo, co.telefonecelular, co.estado_codigo, ");
		sql.append("e.codigo, e.descricao, ");
		sql.append("t.codigo,t.descricao, ");
		sql.append("f.codigo,f.descricao,f.tipo, ");
		sql.append("r.codigo, r.funcao_codigo, r.email, r.condominio_codigo, r.telefonecelular, r.telefonefixo ");
		sql.append(" FROM contato c ");
		sql.append(" INNER JOIN condominio co ON co.codigo = c.condominio_codigo ");
		sql.append(" INNER JOIN responsavel r ON r.codigo  = c.responsavel_codigo ");
		sql.append(" INNER JOIN tipocontato t ON t.codigo = c.tipocontato_codigo ");
		sql.append(" INNER JOIN funcao  f     ON f.codigo = r.funcao_codigo ");
		sql.append(" INNER JOIN estado   e    ON e.codigo = co.estado_codigo ");
		sql.append(" WHERE co.descricao LIKE ? ORDER BY c.data DESC ");

		Connection conexao = ConexaoFactory.conectar();

		PreparedStatement comando = conexao.prepareStatement(sql.toString());
		comando.setString(1, "%" + c.getDescricao() + "%");

		ResultSet resultado = comando.executeQuery();

		ArrayList<Contato> lista = new ArrayList<Contato>();
		
		Contato contato = null;

		while (resultado.next()) {
			
			contato = new Contato();
			
			Responsavel  responsavel = new Responsavel();
			Condominio condominio = new Condominio();
			Funcao     funcao     = new Funcao();
			Estado     estado     = new Estado();
			TipoContato tipocontato = new TipoContato();
			
			contato.setCodigo(resultado.getLong("c.codigo"));
			contato.setData(resultado.getDate("c.data"));
			contato.setDescricao(resultado.getString("c.descricao"));
			
			tipocontato.setCodigo(resultado.getLong("t.codigo"));
			tipocontato.setDescricao(resultado.getString("t.descricao"));
			
			
			responsavel.setCodigo(resultado.getLong("r.codigo"));
			responsavel.setTelefonecelular(resultado.getString("r.telefonecelular"));
			responsavel.setTelefonefixo(resultado.getString("r.telefonefixo"));
			responsavel.setEmail(resultado.getString("r.email"));
			
			condominio.setCodigo(resultado.getLong("co.codigo"));
			condominio.setDescricao(resultado.getString("co.descricao"));
			condominio.setEndereco(resultado.getString("co.endereco"));
			condominio.setBairro(resultado.getString("co.bairro"));
			condominio.setCidade(resultado.getString("co.cidade"));
			condominio.setCep(resultado.getString("co.cep"));
			condominio.setTelefonecelular(resultado.getString("co.telefonecelular"));
			condominio.setTelefonefixo(resultado.getString("co.telefonefixo"));
			
			estado.setCodigo(resultado.getString("e.codigo"));
			estado.setDescricao(resultado.getString("e.descricao"));
			
			funcao.setCodigo(resultado.getLong("f.codigo"));
			funcao.setDescricao(resultado.getString("f.descricao"));
			funcao.setTipo(resultado.getInt("f.tipo"));
			
			
			
			condominio.setEstado(estado);
			responsavel.setCondominio(condominio);
			responsavel.setFuncao(funcao);
			
			contato.setCondominio(condominio);
			contato.setResponsavel(responsavel);
			contato.setTipocontato(tipocontato);
			
			lista.add(contato);

		}

		return lista;
	}

}

