package br.com.adprofissionais.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import br.com.adprofissionais.domain.Estado;
import br.com.adprofissionais.factory.ConexaoFactory;

public class EstadoDAO {

	public void salvar(Estado f) throws SQLException{
		StringBuilder sql = new StringBuilder();
		sql.append("INSERT INTO estado ");
		sql.append("(codigo,descricao) ");
		sql.append("VALUES (?,?) ");

		Connection conexao = ConexaoFactory.conectar();

		PreparedStatement comando = conexao.prepareStatement(sql.toString());
		comando.setString(1, f.getCodigo());
		comando.setString(2, f.getDescricao());
		
		comando.executeUpdate();
		
	}
	
	public void excluir (Estado f) throws SQLException{
		StringBuilder sql = new StringBuilder();
		sql.append("DELETE FROM estado ");
		sql.append("WHERE codigo = ? ");

		Connection conexao = ConexaoFactory.conectar();
		
		PreparedStatement comando = conexao.prepareStatement(sql.toString());
		comando.setString(1, f.getCodigo());
		
		comando.executeUpdate();		
		
	}
	
	public void editar (Estado f) throws SQLException{
		StringBuilder sql = new StringBuilder();
		sql.append("UPDATE estado ");
		sql.append("SET descricao = ? "	);	
		sql.append("WHERE codigo = ? ");
		
		Connection conexao = ConexaoFactory.conectar();
		
		PreparedStatement comando = conexao.prepareStatement(sql.toString());
		comando.setString(1, f.getDescricao());
		comando.setString(2, f.getCodigo());
		
		comando.executeUpdate();		
		
	}
	
	public Estado buscarPorCodigo (Estado f) throws SQLException{
		StringBuilder sql = new StringBuilder();
		sql.append("SELECT codigo, descricao  ");
		sql.append("FROM estado WHERE  codigo  = ? ");	
		System.out.println(sql);		
		System.out.println(" dao codigo = " + f.getCodigo());
		
		Connection conexao = ConexaoFactory.conectar();
		
		PreparedStatement comando = conexao.prepareStatement(sql.toString());
		comando.setString(1, f.getCodigo());
		
		ResultSet resultado = comando.executeQuery();
		Estado retorno = new Estado();
		if(resultado.next()){
//			retorno = new Estado();
			retorno.setCodigo(resultado.getString("codigo"));
			retorno.setDescricao(resultado.getString("descricao"));		
		}
		return retorno;

	}

	public ArrayList<Estado> listar() throws SQLException {
		StringBuilder sql = new StringBuilder();
		sql.append("SELECT e.codigo, e.descricao  ");
		sql.append("FROM estado e ORDER BY e.descricao ASC ");	
		
		Connection conexao = ConexaoFactory.conectar();
		
		PreparedStatement comando = conexao.prepareStatement(sql.toString());
	
		ResultSet resultado = comando.executeQuery();
		
		ArrayList<Estado> lista = new ArrayList<Estado>();
		
		while(resultado.next()) {
			Estado item = new Estado();
			item.setCodigo(resultado.getString("e.codigo"));
			item.setDescricao(resultado.getString("e.descricao"));
			lista.add(item);

			
		}
		
		return lista;
	}
	
	public ArrayList<Estado> buscarDescricao(Estado f) throws SQLException {
		StringBuilder sql = new StringBuilder();
		sql.append("SELECT codigo, descricao  ");
		sql.append("FROM estado " );
		sql.append(" WHERE descricao LIKE ? ");
		sql.append("ORDER BY descricao ASC ");	
	System.out.println(sql);	
		Connection conexao = ConexaoFactory.conectar();
		
		PreparedStatement comando = conexao.prepareStatement(sql.toString());
		comando.setString(1, "%" + f.getDescricao() + "%");
	
		ResultSet resultado = comando.executeQuery();
		
		ArrayList<Estado> lista = new ArrayList<Estado>();
		
		while(resultado.next()) {
			Estado item = new Estado();
			item.setCodigo(resultado.getString("codigo"));
			item.setDescricao(resultado.getString("descricao"));
			lista.add(item);
			
		}
		
		return lista;
	}
	
	public ArrayList<Estado> buscarCodigolista(Estado f) throws SQLException {
		StringBuilder sql = new StringBuilder();
		sql.append("SELECT codigo, descricao  ");
		sql.append("FROM estado " );
		sql.append(" WHERE codigo LIKE ? ");
		sql.append("ORDER BY descricao ASC ");	
	System.out.println(sql);	
		Connection conexao = ConexaoFactory.conectar();
		
		PreparedStatement comando = conexao.prepareStatement(sql.toString());
		comando.setString(1, "%" + f.getCodigo() + "%");
	
		ResultSet resultado = comando.executeQuery();
		
		ArrayList<Estado> lista = new ArrayList<Estado>();
		
		while(resultado.next()) {
			Estado item = new Estado();
			item.setCodigo(resultado.getString("codigo"));
			item.setDescricao(resultado.getString("descricao"));
			lista.add(item);
			
		}
		
		return lista;
	}
	public static void main(String[] args) {
		Estado f1 = new Estado();
		Estado f2 = new Estado();
		

		f1.setDescricao("Telefone");
		f2.setDescricao("E-Mail");
		
		
		EstadoDAO fdao = new EstadoDAO();
		
		try {
			fdao.salvar(f1);
			fdao.salvar(f2);


	        ArrayList<Estado> lista = fdao.listar();
	        for (Estado f : lista) {
	        	System.out.println(f);
	        	
	        }
	        
		System.out.println("Estados Salvos com sucesso");
		} catch (SQLException e) {
			System.out.println("Ocorreu um erro ao tentar salvar Estados");
			e.printStackTrace();
		}
		
		
	}

}


