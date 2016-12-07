package br.com.adprofissionais.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import br.com.adprofissionais.domain.Condominio;
import br.com.adprofissionais.domain.Estado;
import br.com.adprofissionais.domain.Funcao;
import br.com.adprofissionais.domain.Responsavel;
import br.com.adprofissionais.domain.Usuario;
import br.com.adprofissionais.factory.ConexaoFactory;

public class ResponsavelDAO {

	public void salvar(Responsavel r) throws SQLException {
		StringBuilder sql = new StringBuilder();
		sql.append("INSERT INTO responsavel ");
		sql.append("( telefonecelular, telefonefixo, email, funcao_codigo, ");
		sql.append(" condominio_codigo , usuario_codigo)");
		sql.append("VALUES (?,?,?,?,?,? ) ");

		Connection conexao = ConexaoFactory.conectar();

		PreparedStatement comando = conexao.prepareStatement(sql.toString());
		comando.setString(1, r.getTelefonecelular());
		comando.setString(2, r.getTelefonefixo());
		comando.setString(3, r.getEmail());
		comando.setLong(4, r.getFuncao().getCodigo());
		comando.setLong(5, r.getCondominio().getCodigo());
		comando.setLong(6, r.getUsuario().getCodigo());

		comando.executeUpdate();

	}

	public void editar(Responsavel r) throws SQLException {

		StringBuilder sql = new StringBuilder();
		sql.append("UPDATE responsavel ");
		sql.append("SET email = ? , ");
		sql.append("telefonefixo = ?, telefonecelular = ?, ");
		sql.append("funcao_codigo = ? , condominio_codigo = ?, usuario_codigo = ? ");
		sql.append("WHERE codigo = ? ");

		Connection conexao = ConexaoFactory.conectar();

		PreparedStatement comando = conexao.prepareStatement(sql.toString());

		comando.setString(1, r.getEmail());
		comando.setString(3, r.getTelefonecelular());
		comando.setString(2, r.getTelefonefixo());
		comando.setLong(4, r.getFuncao().getCodigo());
		comando.setLong(5, r.getCondominio().getCodigo());
		comando.setLong(6,r.getUsuario().getCodigo());
		comando.setLong(7, r.getCodigo());

		comando.executeUpdate();

	}

	public void excluir(Responsavel r) throws SQLException {
		StringBuilder sql = new StringBuilder();
		sql.append("DELETE FROM responsavel ");
		sql.append("WHERE codigo = ? ");

		Connection conexao = ConexaoFactory.conectar();

		PreparedStatement comando = conexao.prepareStatement(sql.toString());

		comando.setLong(1, r.getCodigo());

		comando.executeUpdate();

	}

	public Responsavel buscarPorCodigo(Responsavel r) throws SQLException {
		StringBuilder sql = new StringBuilder();
		sql.append("SELECT r.codigo, r.telefonecelular, r.telefonefixo, r.email, r.funcao_codigo, r.condominio_codigo,");
		sql.append(" c.codigo, c.descricao, c.endereco, c.bairro, c.cidade, c.cep, c.telefonecelular, c.telefonefixo, c.estado_codigo,");
		sql.append("e.codigo, e.descricao, f.codigo , f.descricao, f.tipo, ");
		sql.append(" u.codigo, u.nome, u.login , u.senha ");
		sql.append("FROM responsavel r ");
		sql.append("INNER JOIN condominio c ON c.codigo = r.condominio_codigo ");
		sql.append("INNER JOIN estado     e ON e.codigo = c.estado_codigo ");
		sql.append("INNER JOIN funcao     f ON f.codigo = r.funcao_codigo ");
		sql.append("INNER JOIN usuario    u ON u.codigo = r.usuario_codigo ");
		sql.append("WHERE r.codigo = ? ORDER BY u.nome ");

		Connection conexao = ConexaoFactory.conectar();

		PreparedStatement comando = conexao.prepareStatement(sql.toString());
		comando.setLong(1, r.getCodigo());

		ResultSet resultado = comando.executeQuery();
		Responsavel responsavel = null;
		if (resultado.next()) {
			responsavel = new Responsavel();
			Condominio condominio = new Condominio();
			Funcao     funcao     = new Funcao();
			Estado     estado     = new Estado();
			Usuario    usuario    = new Usuario();
			
			responsavel.setCodigo(resultado.getLong("r.codigo"));
			responsavel.setTelefonecelular(resultado.getString("r.telefonecelular"));
			responsavel.setTelefonefixo(resultado.getString("r.telefonefixo"));
			responsavel.setEmail(resultado.getString("r.email"));
			
			condominio.setCodigo(resultado.getLong("c.codigo"));
			condominio.setDescricao(resultado.getString("c.descricao"));
			condominio.setEndereco(resultado.getString("c.endereco"));
			condominio.setBairro(resultado.getString("c.bairro"));
			condominio.setCidade(resultado.getString("c.cidade"));
			condominio.setCep(resultado.getString("c.cep"));
			condominio.setTelefonecelular(resultado.getString("c.telefonecelular"));
			condominio.setTelefonefixo(resultado.getString("c.telefonefixo"));
			
			estado.setCodigo(resultado.getString("e.estado"));
			estado.setDescricao(resultado.getString("e.descricao"));
			
			funcao.setCodigo(resultado.getLong("f.codigo"));
			funcao.setDescricao(resultado.getString("f.descricao"));
			funcao.setTipo(resultado.getInt("f.tipo"));
			
			usuario.setCodigo(resultado.getLong("u.codigo"));
			usuario.setNome(resultado.getString("u.nome"));
			usuario.setLogin(resultado.getString("u.login"));
			usuario.setSenha(resultado.getString("u.senha"));
			
			condominio.setEstado(estado);
			responsavel.setCondominio(condominio);
			responsavel.setFuncao(funcao);
			responsavel.setUsuario(usuario);

		}
		return responsavel;

	}
	
	public ArrayList<Responsavel> listar() throws SQLException {
		StringBuilder sql = new StringBuilder();
		sql.append("SELECT r.codigo, r.telefonecelular, r.telefonefixo, r.email, r.funcao_codigo, r.condominio_codigo,");
		sql.append(" c.codigo, c.descricao, c.endereco, c.bairro, c.cidade, c.cep, c.telefonecelular, c.telefonefixo, c.estado_codigo,");
		sql.append("e.codigo, e.descricao, f.codigo , f.descricao, f.tipo, ");
		sql.append(" u.codigo, u.nome, u.login , u.senha ");
		sql.append("FROM responsavel r ");
		sql.append("INNER JOIN condominio c ON c.codigo = r.condominio_codigo ");
		sql.append("INNER JOIN estado     e ON e.codigo = c.estado_codigo ");
		sql.append("INNER JOIN funcao     f ON f.codigo = r.funcao_codigo ");
		sql.append("INNER JOIN usuario    u ON u.codigo = r.usuario_codigo ");
		sql.append(" ORDER BY u.nome ");

		Connection conexao = ConexaoFactory.conectar();

		PreparedStatement comando = conexao.prepareStatement(sql.toString());

		ResultSet resultado = comando.executeQuery();

		ArrayList<Responsavel> lista = new ArrayList<Responsavel>();
		
		Responsavel responsavel = null;

		while (resultado.next()) {
			responsavel = new Responsavel();
			Condominio condominio = new Condominio();
			Funcao     funcao     = new Funcao();
			Estado     estado     = new Estado();
			Usuario    usuario    = new Usuario();
			
			responsavel.setCodigo(resultado.getLong("r.codigo"));
			responsavel.setTelefonecelular(resultado.getString("r.telefonecelular"));
			responsavel.setTelefonefixo(resultado.getString("r.telefonefixo"));
			responsavel.setEmail(resultado.getString("r.email"));
			
			condominio.setCodigo(resultado.getLong("c.codigo"));
			condominio.setDescricao(resultado.getString("c.descricao"));
			condominio.setEndereco(resultado.getString("c.endereco"));
			condominio.setBairro(resultado.getString("c.bairro"));
			condominio.setCidade(resultado.getString("c.cidade"));
			condominio.setCep(resultado.getString("c.cep"));
			condominio.setTelefonecelular(resultado.getString("c.telefonecelular"));
			condominio.setTelefonefixo(resultado.getString("c.telefonefixo"));
			
			estado.setCodigo(resultado.getString("e.codigo"));
			estado.setDescricao(resultado.getString("e.descricao"));
			
			funcao.setCodigo(resultado.getLong("f.codigo"));
			funcao.setDescricao(resultado.getString("f.descricao"));
			funcao.setTipo(resultado.getInt("f.tipo"));
			
			usuario.setCodigo(resultado.getLong("u.codigo"));
			usuario.setNome(resultado.getString("u.nome"));
			usuario.setLogin(resultado.getString("u.login"));
			usuario.setSenha(resultado.getString("u.senha"));
			
			condominio.setEstado(estado);
			responsavel.setCondominio(condominio);
			responsavel.setFuncao(funcao);
			responsavel.setUsuario(usuario);
			
			lista.add(responsavel);

		}

		return lista;
	}
	
	public ArrayList<Responsavel> listarAdm() throws SQLException {
		StringBuilder sql = new StringBuilder();
		sql.append("SELECT r.codigo, r.telefonecelular, r.telefonefixo, r.email, r.funcao_codigo, r.condominio_codigo,");
		sql.append(" c.codigo, c.descricao, c.endereco, c.bairro, c.cidade, c.cep, c.telefonecelular, c.telefonefixo, c.estado_codigo,");
		sql.append("e.codigo, e.descricao, f.codigo , f.descricao, f.tipo, ");
		sql.append(" u.codigo, u.nome, u.login , u.senha ");
		sql.append("FROM responsavel r ");
		sql.append("INNER JOIN condominio c ON c.codigo = r.condominio_codigo ");
		sql.append("INNER JOIN estado     e ON e.codigo = c.estado_codigo ");
		sql.append("INNER JOIN funcao     f ON f.codigo = r.funcao_codigo ");
		sql.append("INNER JOIN usuario    u ON u.codigo = r.usuario_codigo ");
		sql.append(" WHERE f.tipo = 2 ORDER BY u.nome ");

		Connection conexao = ConexaoFactory.conectar();

		PreparedStatement comando = conexao.prepareStatement(sql.toString());

		ResultSet resultado = comando.executeQuery();

		ArrayList<Responsavel> lista = new ArrayList<Responsavel>();
		
		Responsavel responsavel = null;

		while (resultado.next()) {
			responsavel = new Responsavel();
			Condominio condominio = new Condominio();
			Funcao     funcao     = new Funcao();
			Estado     estado     = new Estado();
			Usuario    usuario    = new Usuario();
			
			responsavel.setCodigo(resultado.getLong("r.codigo"));
			responsavel.setTelefonecelular(resultado.getString("r.telefonecelular"));
			responsavel.setTelefonefixo(resultado.getString("r.telefonefixo"));
			responsavel.setEmail(resultado.getString("r.email"));
			
			condominio.setCodigo(resultado.getLong("c.codigo"));
			condominio.setDescricao(resultado.getString("c.descricao"));
			condominio.setEndereco(resultado.getString("c.endereco"));
			condominio.setBairro(resultado.getString("c.bairro"));
			condominio.setCidade(resultado.getString("c.cidade"));
			condominio.setCep(resultado.getString("c.cep"));
			condominio.setTelefonecelular(resultado.getString("c.telefonecelular"));
			condominio.setTelefonefixo(resultado.getString("c.telefonefixo"));
			
			estado.setCodigo(resultado.getString("e.codigo"));
			estado.setDescricao(resultado.getString("e.descricao"));
			
			funcao.setCodigo(resultado.getLong("f.codigo"));
			funcao.setDescricao(resultado.getString("f.descricao"));
			funcao.setTipo(resultado.getInt("f.tipo"));
			
			usuario.setCodigo(resultado.getLong("u.codigo"));
			usuario.setNome(resultado.getString("u.nome"));
			usuario.setLogin(resultado.getString("u.login"));
			usuario.setSenha(resultado.getString("u.senha"));
			
			condominio.setEstado(estado);
			responsavel.setCondominio(condominio);
			responsavel.setFuncao(funcao);
			responsavel.setUsuario(usuario);
			
			lista.add(responsavel);

		}

		return lista;
	}
	public ArrayList<Responsavel> buscarDescricao(Responsavel r) throws SQLException {
		StringBuilder sql = new StringBuilder();
		sql.append("SELECT r.codigo, r.telefonecelular, r.telefonefixo, r.email, r.funcao_codigo, r.condominio_codigo,");
		sql.append(" c.codigo, c.descricao, c.endereco, c.bairro, c.cidade, c.cep, c.telefonecelular, c.telefonefixo, c.estado_codigo,");
		sql.append("e.codigo, e.descricao, f.codigo , f.descricao, f.tipo, ");
		sql.append(" u.codigo, u.nome, u.login , u.senha ");
		sql.append("FROM responsavel r ");
		sql.append("INNER JOIN condominio c ON c.codigo = r.condominio_codigo ");
		sql.append("INNER JOIN estado     e ON e.codigo = c.estado_codigo ");
		sql.append("INNER JOIN funcao     f ON f.codigo = r.funcao_codigo ");
		sql.append("INNER JOIN usuario    u ON u.codigo = r.usuario_codigo ");
		sql.append(" WHERE u.nome LIKE ?  ORDER BY u.nome ");

		Connection conexao = ConexaoFactory.conectar();

		PreparedStatement comando = conexao.prepareStatement(sql.toString());
		comando.setString(1, "%" + r.getUsuario().getNome() + "%");

		ResultSet resultado = comando.executeQuery();

		ArrayList<Responsavel> lista = new ArrayList<Responsavel>();
		
		Responsavel responsavel = null;

		while (resultado.next()) {
			responsavel = new Responsavel();
			Condominio condominio = new Condominio();
			Funcao     funcao     = new Funcao();
			Estado     estado     = new Estado();
			Usuario    usuario    = new Usuario();
			
			responsavel.setCodigo(resultado.getLong("r.codigo"));
			responsavel.setTelefonecelular(resultado.getString("r.telefonecelular"));
			responsavel.setTelefonefixo(resultado.getString("r.telefonefixo"));
			responsavel.setEmail(resultado.getString("r.email"));
			
			condominio.setCodigo(resultado.getLong("c.codigo"));
			condominio.setDescricao(resultado.getString("c.descricao"));
			condominio.setEndereco(resultado.getString("c.endereco"));
			condominio.setBairro(resultado.getString("c.bairro"));
			condominio.setCidade(resultado.getString("c.cidade"));
			condominio.setCep(resultado.getString("c.cep"));
			condominio.setTelefonecelular(resultado.getString("c.telefonecelular"));
			condominio.setTelefonefixo(resultado.getString("c.telefonefixo"));
			
			estado.setCodigo(resultado.getString("e.codigo"));
			estado.setDescricao(resultado.getString("e.descricao"));
			
			funcao.setCodigo(resultado.getLong("f.codigo"));
			funcao.setDescricao(resultado.getString("f.descricao"));
			funcao.setTipo(resultado.getInt("f.tipo"));
			
			usuario.setCodigo(resultado.getLong("u.codigo"));
			usuario.setNome(resultado.getString("u.nome"));
			usuario.setLogin(resultado.getString("u.login"));
			usuario.setSenha(resultado.getString("u.senha"));
			
			condominio.setEstado(estado);
			responsavel.setCondominio(condominio);
			responsavel.setFuncao(funcao);
			responsavel.setUsuario(usuario);
			
			lista.add(responsavel);

		}

		return lista;
	}


}
