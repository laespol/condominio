package br.com.adprofissionais.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import br.com.adprofissionais.domain.Condominio;
import br.com.adprofissionais.domain.Estado;
import br.com.adprofissionais.factory.ConexaoFactory;

public class CondominioDAO {

	public void salvar(Condominio c) throws SQLException {
		StringBuilder sql = new StringBuilder();
		sql.append("INSERT INTO condominio ");
		sql.append("(descricao,endereco, bairro, cidade, cep,  ");
		sql.append("telefonefixo, telefonecelular , estado_codigo, email) ");
		sql.append("VALUES (?,?,?,?,?,?,?,?) ");

		Connection conexao = ConexaoFactory.conectar();

		PreparedStatement comando = conexao.prepareStatement(sql.toString());
		comando.setString(1, c.getDescricao());
		comando.setString(2, c.getEndereco());
		comando.setString(3, c.getBairro());
		comando.setString(4, c.getCidade());
		comando.setString(5, c.getCep());
		comando.setString(6, c.getTelefonefixo());
		comando.setString(7, c.getTelefonecelular());
		comando.setString(8, c.getEstado().getCodigo());
		comando.setString(9, c.getEmail());

		comando.executeUpdate();

	}

	public void excluir(Condominio c) throws SQLException {
		StringBuilder sql = new StringBuilder();
		sql.append("DELETE FROM condominio ");
		sql.append("WHERE codigo = ? ");

		Connection conexao = ConexaoFactory.conectar();

		PreparedStatement comando = conexao.prepareStatement(sql.toString());

		comando.setLong(1, c.getCodigo());

		comando.executeUpdate();

	}

	public void editar(Condominio c) throws SQLException {
		
		StringBuilder sql = new StringBuilder();
		sql.append("UPDATE condominio ");
		sql.append("SET descricao = ?, endereco =? , ");
		sql.append("bairro = ? , cidade = ?, cep =? , ");
		sql.append("telefonefixo = ?, telefonecelular = ?, estado_codigo = ?, email = ? ");
		sql.append("WHERE codigo = ? ");

		Connection conexao = ConexaoFactory.conectar();

		PreparedStatement comando = conexao.prepareStatement(sql.toString());
		comando.setString(1, c.getDescricao());
		comando.setString(2, c.getEndereco());
		comando.setString(3, c.getBairro());
		comando.setString(4, c.getCidade());
		comando.setString(5, c.getCep());
		comando.setString(6, c.getTelefonefixo());
		comando.setString(7, c.getTelefonecelular());
		comando.setString(8,c.getEstado().getCodigo());
		comando.setString(9, c.getEmail());
		comando.setLong(10, c.getCodigo());

		comando.executeUpdate();

	}

	public Condominio buscarPorCodigo(Condominio c) throws SQLException {
		StringBuilder sql = new StringBuilder();
		sql.append("SELECT c.codigo, c.descricao , c.endereco, c.bairro, c.cidade, c.cep, c.telefonefixo, c.telefonecelular, e.codigo, e.descricao, ");
		sql.append(" c.email " );
		sql.append("FROM condominio c INNER JOIN estado e ON e.codigo = c.estado_codigo ");
		sql.append("WHERE  c.codigo  = ?  ORDER BY c.descricao ASC ");

		Connection conexao = ConexaoFactory.conectar();

		PreparedStatement comando = conexao.prepareStatement(sql.toString());
		comando.setLong(1, c.getCodigo());

		ResultSet resultado = comando.executeQuery();
		Condominio condominio = null;
		if (resultado.next()) {
			condominio = new Condominio();
			Estado estado = new Estado();
			
			condominio.setCodigo(resultado.getLong("c.codigo"));
			condominio.setDescricao(resultado.getString("c.descricao"));
			condominio.setEndereco(resultado.getString("c.endereco"));
			condominio.setBairro(resultado.getString("c.bairro"));
			condominio.setCidade(resultado.getString("c.cidade"));
			condominio.setCep(resultado.getString("c.cep"));
			condominio.setTelefonefixo(resultado.getString("c.telefonefixo"));
			condominio.setTelefonecelular(resultado.getString("c.telefonecelular"));
			condominio.setEmail(resultado.getString("email"));
			
			estado.setCodigo(resultado.getString("e.codigo"));
			estado.setDescricao(resultado.getString("e.descricao"));
			
			condominio.setEstado(estado);

		}
		return condominio;

	}

	public ArrayList<Condominio> listar() throws SQLException {
		StringBuilder sql = new StringBuilder();
		sql.append("SELECT c.codigo, c.descricao , c.endereco, c.bairro, c.cidade, c.cep, c.telefonefixo, c.telefonecelular, e.codigo, e.descricao ,");
		sql.append(" c.email " );
		sql.append("FROM condominio c INNER JOIN estado e ON e.codigo = c.estado_codigo ");
		sql.append("ORDER BY c.descricao ASC ");

		Connection conexao = ConexaoFactory.conectar();

		PreparedStatement comando = conexao.prepareStatement(sql.toString());

		ResultSet resultado = comando.executeQuery();

		ArrayList<Condominio> lista = new ArrayList<Condominio>();

		Condominio condominio = null;
		while (resultado.next()) {
			condominio = new Condominio();
			Estado estado = new Estado();
			
			condominio.setCodigo(resultado.getLong("c.codigo"));
			condominio.setDescricao(resultado.getString("c.descricao"));
			condominio.setEndereco(resultado.getString("c.endereco"));
			condominio.setBairro(resultado.getString("c.bairro"));
			condominio.setCidade(resultado.getString("c.cidade"));
			condominio.setCep(resultado.getString("c.cep"));
			condominio.setTelefonefixo(resultado.getString("c.telefonefixo"));
			condominio.setTelefonecelular(resultado.getString("c.telefonecelular"));
			condominio.setEmail(resultado.getString("email"));
			
			estado.setCodigo(resultado.getString("e.codigo"));
			estado.setDescricao(resultado.getString("e.descricao"));
			
			condominio.setEstado(estado);
			lista.add(condominio);

		}

		return lista;
	}

	public ArrayList<Condominio> listarGeral() throws SQLException {
		StringBuilder sql = new StringBuilder();
		sql.append("SELECT c.codigo, c.descricao , c.endereco, c.bairro, c.cidade, c.cep, c.telefonefixo, c.telefonecelular, ");
		sql.append("b.data , b.descricao , e.codigo, e.descricao, " );
		sql.append(" c.email " );
		sql.append("FROM condominio c " );
		sql.append("INNER JOIN estado e ON e.codigo = c.estado_codigo ");
		sql.append("LEFT JOIN contato b ON c.codigo = b.condominio_codigo " );
		sql.append("ORDER BY c.descricao ASC ");

		Connection conexao = ConexaoFactory.conectar();

		PreparedStatement comando = conexao.prepareStatement(sql.toString());

		ResultSet resultado = comando.executeQuery();

		ArrayList<Condominio> lista = new ArrayList<Condominio>();

		Condominio condominio = null;
		while (resultado.next()) {
			condominio = new Condominio();
			Estado estado = new Estado();
			
			condominio.setCodigo(resultado.getLong("c.codigo"));
			condominio.setDescricao(resultado.getString("c.descricao"));
			condominio.setEndereco(resultado.getString("c.endereco"));
			condominio.setBairro(resultado.getString("c.bairro"));
			condominio.setCidade(resultado.getString("c.cidade"));
			condominio.setCep(resultado.getString("c.cep"));
			condominio.setTelefonefixo(resultado.getString("c.telefonefixo"));
			condominio.setTelefonecelular(resultado.getString("c.telefonecelular"));
			condominio.setEmail(resultado.getString("email"));
			
			estado.setCodigo(resultado.getString("e.codigo"));
			estado.setDescricao(resultado.getString("e.descricao"));
			
			condominio.setEstado(estado);
			lista.add(condominio);

		}

		return lista;
	}
	public ArrayList<Condominio> buscarDescricao(Condominio c) throws SQLException {
		StringBuilder sql = new StringBuilder();
		sql.append("SELECT c.codigo, c.descricao , c.endereco, c.bairro, c.cidade, c.cep, c.telefonefixo, c.telefonecelular, e.codigo, e.descricao ");
		sql.append(" c.email " );
		sql.append("FROM condominio c INNER JOIN estado e ON e.codigo = c.estado_codigo ");
		sql.append("WHERE c.descricao LIKE ? ");
		sql.append("ORDER BY c.descricao ASC ");

		Connection conexao = ConexaoFactory.conectar();

		PreparedStatement comando = conexao.prepareStatement(sql.toString());
		comando.setString(1, "%" + c.getDescricao() + "%");

		ResultSet resultado = comando.executeQuery();

		ArrayList<Condominio> lista = new ArrayList<Condominio>();
		Condominio condominio = null;
		while (resultado.next()) {
			condominio = new Condominio();
			Estado estado = new Estado();
			
			condominio.setCodigo(resultado.getLong("c.codigo"));
			condominio.setDescricao(resultado.getString("c.descricao"));
			condominio.setEndereco(resultado.getString("c.endereco"));
			condominio.setBairro(resultado.getString("c.bairro"));
			condominio.setCidade(resultado.getString("c.cidade"));
			condominio.setCep(resultado.getString("c.cep"));
			condominio.setTelefonefixo(resultado.getString("c.telefonefixo"));
			condominio.setTelefonecelular(resultado.getString("c.telefonecelular"));
			condominio.setEmail(resultado.getString("email"));
			
			estado.setCodigo(resultado.getString("e.codigo"));
			estado.setDescricao(resultado.getString("e.descricao"));
			
			condominio.setEstado(estado);
			lista.add(condominio);

		}

		return lista;
	}
	
	public Condominio buscarDescricaoUm(Condominio c) throws SQLException {
		StringBuilder sql = new StringBuilder();
		sql.append("SELECT c.codigo, c.descricao , c.endereco, c.bairro, c.cidade, c.cep, c.telefonefixo, c.telefonecelular, e.codigo, e.descricao ");
		sql.append(" c.email " );
		sql.append("FROM condominio c INNER JOIN estado e ON e.codigo = c.estado_codigo ");
		sql.append("WHERE c.descricao LIKE ? ");
		sql.append("ORDER BY c.descricao ASC ");

		Connection conexao = ConexaoFactory.conectar();

		PreparedStatement comando = conexao.prepareStatement(sql.toString());
		comando.setString(1, "%" + c.getDescricao() + "%");

		ResultSet resultado = comando.executeQuery();

		ArrayList<Condominio> lista = new ArrayList<Condominio>();
		Condominio condominio = null;
		if (resultado.next()) {
			condominio = new Condominio();
			Estado estado = new Estado();
			
			condominio.setCodigo(resultado.getLong("c.codigo"));
			condominio.setDescricao(resultado.getString("c.descricao"));
			condominio.setEndereco(resultado.getString("c.endereco"));
			condominio.setBairro(resultado.getString("c.bairro"));
			condominio.setCidade(resultado.getString("c.cidade"));
			condominio.setCep(resultado.getString("c.cep"));
			condominio.setTelefonefixo(resultado.getString("c.telefonefixo"));
			condominio.setTelefonecelular(resultado.getString("c.telefonecelular"));
			condominio.setEmail(resultado.getString("email"));
			
			estado.setCodigo(resultado.getString("e.codigo"));
			estado.setDescricao(resultado.getString("e.descricao"));
			
			condominio.setEstado(estado);

		}

		return condominio;
	}

}
