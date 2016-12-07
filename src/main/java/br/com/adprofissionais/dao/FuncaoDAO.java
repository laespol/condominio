package br.com.adprofissionais.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import br.com.adprofissionais.domain.Funcao;
import br.com.adprofissionais.factory.ConexaoFactory;


public class FuncaoDAO {

	public void salvar(Funcao f) throws SQLException{
		StringBuilder sql = new StringBuilder();
		sql.append("INSERT INTO funcao ");
		sql.append("(descricao,tipo) ");
		sql.append("VALUES (?,?) ");

		Connection conexao = ConexaoFactory.conectar();

		PreparedStatement comando = conexao.prepareStatement(sql.toString());
		comando.setString(1, f.getDescricao());
		comando.setInt(2, f.getTipo());
		
		comando.executeUpdate();
		
	}
	
	public void excluir (Funcao f) throws SQLException{
		StringBuilder sql = new StringBuilder();
		sql.append("DELETE FROM funcao ");
		sql.append("WHERE codigo = ? ");

		Connection conexao = ConexaoFactory.conectar();
		
		PreparedStatement comando = conexao.prepareStatement(sql.toString());

		comando.setLong(1, f.getCodigo());

		comando.executeUpdate();		
		
	}
	
	public void editar (Funcao f) throws SQLException{
		StringBuilder sql = new StringBuilder();
		sql.append("UPDATE funcao ");
		sql.append("SET descricao = ?, tipo =?  "	);	
		sql.append("WHERE codigo = ? ");
		
		Connection conexao = ConexaoFactory.conectar();
		
		PreparedStatement comando = conexao.prepareStatement(sql.toString());
		comando.setString(1, f.getDescricao());
		comando.setInt(2, f.getTipo());
		comando.setLong(3, f.getCodigo());
		
		comando.executeUpdate();		
		
	}
	
	public Funcao  buscarPorCodigo (Funcao f) throws SQLException{
		StringBuilder sql = new StringBuilder();
		sql.append("SELECT codigo, descricao  ");
		sql.append("FROM funcao WHERE  codigo  = ? ");	
		
		Connection conexao = ConexaoFactory.conectar();
		
		PreparedStatement comando = conexao.prepareStatement(sql.toString());
		comando.setLong(1, f.getCodigo());
		
		ResultSet resultado = comando.executeQuery();
		Funcao retorno = null;
		if(resultado.next()){
			retorno = new Funcao();
			retorno.setCodigo(resultado.getLong("codigo"));
			retorno.setDescricao(resultado.getString("descricao"));
			
		}
		return retorno;

	}

	public ArrayList<Funcao> listar() throws SQLException {
		StringBuilder sql = new StringBuilder();
		sql.append("SELECT codigo, descricao , tipo ");
		sql.append("FROM funcao ORDER BY descricao ASC ");	
		
		Connection conexao = ConexaoFactory.conectar();
		
		PreparedStatement comando = conexao.prepareStatement(sql.toString());
	
		ResultSet resultado = comando.executeQuery();
		
		ArrayList<Funcao> lista = new ArrayList<Funcao>();
		
		while(resultado.next()) {
			Funcao item = new Funcao();
			item.setCodigo(resultado.getLong("codigo"));
			item.setDescricao(resultado.getString("descricao"));
			item.setTipo(resultado.getInt("tipo"));
			lista.add(item);
			
		}
		
		return lista;
	}
	
	public ArrayList<Funcao> buscarDescricao(Funcao f) throws SQLException {
		StringBuilder sql = new StringBuilder();
		sql.append("SELECT codigo, descricao , tipo ");
		sql.append("FROM funcao " );
		sql.append(" WHERE descricao LIKE ? ");
		sql.append("ORDER BY descricao ASC ");	
		
		Connection conexao = ConexaoFactory.conectar();
		
		PreparedStatement comando = conexao.prepareStatement(sql.toString());
		comando.setString(1, "%" + f.getDescricao() + "%");
	
		ResultSet resultado = comando.executeQuery();
		
		ArrayList<Funcao> lista = new ArrayList<Funcao>();
		
		while(resultado.next()) {
			Funcao item = new Funcao();
			item.setCodigo(resultado.getLong("codigo"));
			item.setDescricao(resultado.getString("descricao"));
			item.setTipo(resultado.getInt("tipo"));
			lista.add(item);
			
		}
		
		return lista;
	}
	
	public static void main(String[] args) {
		Funcao f1 = new Funcao();
		Funcao f2 = new Funcao();
		
		f1.setDescricao("Sindicao");
		f1.setTipo(1);
		f2.setDescricao("Zelador");
		f2.setTipo(2);
			
		FuncaoDAO fdao = new FuncaoDAO();
		
		try {
			fdao.salvar(f1);
			fdao.salvar(f2);

	        ArrayList<Funcao> lista = fdao.listar();
	        for (Funcao f : lista) {
	        	System.out.println(f);
	        	
	        }
	        
			
			System.out.println("Funcaos Salvos com sucesso");
		} catch (SQLException e) {
			System.out.println("Ocorreu um erro ao tentar salvar Funcaos");
			e.printStackTrace();
		}
		
		
	}

}

