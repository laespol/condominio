package br.com.adprofissionais.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;

import br.com.adprofissionais.domain.Funcao;
import br.com.adprofissionais.domain.LogAcesso;
import br.com.adprofissionais.domain.Usuario;
import br.com.adprofissionais.factory.ConexaoFactory;

public class UsuarioDAO {

	public void salvar(Usuario u) throws SQLException {
		System.out.println("entrei no dao");
		StringBuilder sql = new StringBuilder();
		sql.append("INSERT INTO usuario ");
		sql.append("(nome,login,senha,email, dataativ, status, numcpf) ");
		sql.append("VALUES (?,?,?,?,?,?,?) ");

		System.out.println(sql);
		Date date = new Date();

		Connection conexao = ConexaoFactory.conectar();
		PreparedStatement comando = conexao.prepareStatement(sql.toString());
		comando.setString(1, u.getNome());
		comando.setString(2, u.getLogin());
		comando.setString(3, u.getSenha());
		comando.setString(4, u.getEmail());
		comando.setTimestamp(5, new java.sql.Timestamp(date.getTime()));
		comando.setString(6, "A");
		comando.setString(7, u.getNumcpf());
		comando.executeUpdate();

	}

	public void excluir(Usuario u) throws SQLException {
		StringBuilder sql = new StringBuilder();
		sql.append("UPDATE usuario ");
		sql.append("SET datadesativ = ?, status = ? ");
		sql.append("WHERE codigo = ? ");

		Date date = new Date();
		Connection conexao = ConexaoFactory.conectar();

		PreparedStatement comando = conexao.prepareStatement(sql.toString());
		comando.setTimestamp(1, new java.sql.Timestamp(date.getTime()));
		comando.setString(2, "I");

		comando.setLong(3, u.getCodigo());

		comando.executeUpdate();

	}

	public void editar(Usuario u) throws SQLException {
		StringBuilder sql = new StringBuilder();
		sql.append("UPDATE usuario ");
		sql.append("SET nome = ?, login = ?, senha = ? , ");
		sql.append(" email = ? , numcpf = ? ");
		sql.append("WHERE codigo = ? ");

		Connection conexao = ConexaoFactory.conectar();

		PreparedStatement comando = conexao.prepareStatement(sql.toString());
		comando.setString(1, u.getNome());
		comando.setString(2, u.getLogin());
		comando.setString(3, u.getSenha());
		comando.setString(4, u.getEmail());
		comando.setString(5, u.getNumcpf());

		comando.setLong(6, u.getCodigo());

		comando.executeUpdate();

	}

	public Usuario buscarPorCodigo(Usuario u) throws SQLException {
		StringBuilder sql = new StringBuilder();
		sql.append("SELECT u.codigo, u.nome, u.login , u.senha, ");
		sql.append(" u.email, u.dataativ, u.datadesativ, u.status, u.numcpf ");
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
		usuario.setEmail(resultado.getString("u.email"));
		usuario.setDataativ(resultado.getDate("u.dataativ"));
		usuario.setDatadesativ(resultado.getDate("u.datadesativ"));
		usuario.setStatus(resultado.getString("u.status"));
		usuario.setNumcpf(resultado.getString("u.numcpf"));
		return usuario;

	}

	public ArrayList<Usuario> listar() throws SQLException {
		StringBuilder sql = new StringBuilder();
		sql.append("SELECT u.codigo, u.nome, u.login , u.senha, ");
		sql.append(" u.email, u.dataativ, u.datadesativ, u.status, u.numcpf ");
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
			usuario.setEmail(resultado.getString("u.email"));
			usuario.setDataativ(resultado.getDate("u.dataativ"));
			usuario.setDatadesativ(resultado.getDate("u.datadesativ"));
			usuario.setStatus(resultado.getString("u.status"));
			usuario.setNumcpf(resultado.getString("u.numcpf"));
			lista.add(usuario);

		}

		return lista;
	}

	public ArrayList<Usuario> buscarDescricao(Usuario u) throws SQLException {
		StringBuilder sql = new StringBuilder();
		sql.append("SELECT u.codigo, u.nome, u.login , u.senha, ");
		sql.append(" u.email, u.dataativ, u.datadesativ, u.status, u.numcpf ");
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
			usuario.setEmail(resultado.getString("u.email"));
			usuario.setDataativ(resultado.getDate("u.dataativ"));
			usuario.setDatadesativ(resultado.getDate("u.datadesativ"));
			usuario.setStatus(resultado.getString("u.status"));
			usuario.setNumcpf(resultado.getString("u.numcpf"));
			lista.add(usuario);

		}
		return lista;
	}

	public String nomeLogin(String username) throws SQLException {
		StringBuilder sql = new StringBuilder();
		sql.append("SELECT u.nome ");
		sql.append("FROM usuario u  ");
		sql.append("where u.login = ? ");

		String nome = null;

		Connection conexao = ConexaoFactory.conectar();

		PreparedStatement comando = conexao.prepareStatement(sql.toString());
		comando.setString(1, username);

		ResultSet resultado = comando.executeQuery();

		if (resultado.next()) {

			nome = resultado.getString("u.nome");
			nome = nome + " ";
			System.out.println(nome);
		}
		return nome;
	}

	public boolean login(String user, String password) throws SQLException {
		StringBuilder sql = new StringBuilder();
		sql.append("SELECT u.login , u.senha ");
		sql.append("FROM usuario u  ");
		sql.append("where u.login =  ? and u.senha = ? ");
		
		System.out.println(sql);
		System.out.println(user);
		System.out.println(password);

		Connection conexao = ConexaoFactory.conectar();

		PreparedStatement comando = conexao.prepareStatement(sql.toString());

		comando.setString(1, user);
		comando.setString(2, password);

		ResultSet resultado = comando.executeQuery();

		LogAcesso l = new LogAcesso();
		boolean permite = false;
		l.setLogin(user);

		if (resultado.next()) // found
		{
			System.out.println(resultado.getString("u.login"));
			l.setSucesso("Permitido");
			permite = true;
		} else {
			l.setSucesso("Negado");
			permite = false;
		}
		LogAcessoDAO ldao = new LogAcessoDAO();

		ldao.salvar(l);

		return permite;
	}

	public static void main(String[] args) {
		Usuario u1 = new Usuario();
		Funcao f1 = new Funcao();

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
