package br.com.adprofissionais.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import br.com.adprofissionais.domain.Funcao;
import br.com.adprofissionais.domain.Usuario;
import br.com.adprofissionais.factory.ConexaoFactory;

public class UsuarioDAO {

	public void salvar(Usuario u) throws SQLException {
		StringBuilder sql = new StringBuilder();
		sql.append("INSERT INTO usuario ");
		sql.append("(nome,login,senha) ");
		sql.append("VALUES (?,?,?) ");

		Connection conexao = ConexaoFactory.conectar();

		PreparedStatement comando = conexao.prepareStatement(sql.toString());
		comando.setString(1, u.getNome());
		comando.setString(2, u.getLogin());
		comando.setString(3, u.getSenha());

		comando.executeUpdate();

	}

	public void excluir(Usuario u) throws SQLException {
		StringBuilder sql = new StringBuilder();
		sql.append("DELETE FROM usuario ");
		sql.append("WHERE codigo = ? ");

		Connection conexao = ConexaoFactory.conectar();

		PreparedStatement comando = conexao.prepareStatement(sql.toString());

		comando.setLong(1, u.getCodigo());

		comando.executeUpdate();

	}

	public void editar(Usuario u) throws SQLException {
		StringBuilder sql = new StringBuilder();
		sql.append("UPDATE usuario ");
		sql.append("SET nome = ?, login = ?, senha = ?  ");
		sql.append("WHERE codigo = ? ");

		Connection conexao = ConexaoFactory.conectar();

		PreparedStatement comando = conexao.prepareStatement(sql.toString());
		comando.setString(1, u.getNome());
		comando.setString(2, u.getLogin());
		comando.setString(3, u.getSenha());

		comando.setLong(4, u.getCodigo());

		comando.executeUpdate();

	}

	public Usuario buscarPorCodigo(Usuario u) throws SQLException {
		StringBuilder sql = new StringBuilder();
		sql.append("SELECT u.codigo, u.nome, u.login , u.senha ");
		sql.append("FROM usuario u  ");
		sql.append("where u.condigo = ? ");
		sql.append("ORDER BY u.nome ASC ");

		Connection conexao = ConexaoFactory.conectar();

		PreparedStatement comando = conexao.prepareStatement(sql.toString());
		comando.setLong(1, u.getCodigo());

		ResultSet resultado = comando.executeQuery();

		Usuario usuario = new Usuario();

		usuario.setCodigo(resultado.getLong("u.codigo"));
		usuario.setNome(resultado.getString("u.nome"));
		usuario.setLogin(resultado.getString("u.login"));
		usuario.setSenha(resultado.getString("u.senha"));

		return usuario;

	}

	public ArrayList<Usuario> listar() throws SQLException {
		StringBuilder sql = new StringBuilder();
		sql.append("SELECT u.codigo, u.nome, u.login , u.senha ");
		sql.append("FROM usuario u  ");
		sql.append("ORDER BY u.nome ASC ");

		Connection conexao = ConexaoFactory.conectar();

		PreparedStatement comando = conexao.prepareStatement(sql.toString());

		ResultSet resultado = comando.executeQuery();

		ArrayList<Usuario> lista = new ArrayList<Usuario>();

		while (resultado.next()) {
			Usuario usuario = new Usuario();

			usuario.setCodigo(resultado.getLong("u.codigo"));
			usuario.setNome(resultado.getString("u.nome"));
			usuario.setLogin(resultado.getString("u.login"));
			usuario.setSenha(resultado.getString("u.senha"));
			lista.add(usuario);

		}

		return lista;
	}

	public ArrayList<Usuario> buscarDescricao(Usuario u) throws SQLException {
		StringBuilder sql = new StringBuilder();
		sql.append("SELECT u.codigo, u.nome, u.login , u.senha ");
		sql.append("FROM usuario u  ");
		sql.append("where u.nome LIKE ? ");
		sql.append("ORDER BY u.nome ASC ");
		
		Connection conexao = ConexaoFactory.conectar();

		PreparedStatement comando = conexao.prepareStatement(sql.toString());
		comando.setString(1, "%" + u.getNome() + "%");

		ResultSet resultado = comando.executeQuery();

		ArrayList<Usuario> lista = new ArrayList<Usuario>();

		while (resultado.next()) {
			Usuario usuario = new Usuario();

			usuario.setCodigo(resultado.getLong("u.codigo"));
			usuario.setNome(resultado.getString("u.nome"));
			usuario.setLogin(resultado.getString("u.login"));
			usuario.setSenha(resultado.getString("u.senha"));
			lista.add(usuario);

		}

		return lista;
	}

	public static void main(String[] args) {
		Usuario u1 = new Usuario();
		Funcao  f1 = new Funcao();
		
		u1.setNome("Luis Antonio Espoladore");
		u1.setLogin("laespol34");
		u1.setSenha("lae123");
		
		f1.setCodigo(1L);
		
		UsuarioDAO udao = new UsuarioDAO();

		try {
			udao.salvar(u1);

			ArrayList<Usuario> lista = udao.listar();
			for (Usuario u : lista) {
				System.out.println(u);

			}

			System.out.println("Usuarios Salvos com sucesso");
		} catch (SQLException e) {
			System.out.println("Ocorreu um erro ao tentar salvar Usuarios");
			e.printStackTrace();
		}

	}

}
