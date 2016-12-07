package br.com.adprofissionais.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import br.com.adprofissionais.domain.TipoContato;
import br.com.adprofissionais.factory.ConexaoFactory;

public class TipoContatoDAO {

	public void salvar(TipoContato f) throws SQLException{
		StringBuilder sql = new StringBuilder();
		sql.append("INSERT INTO tipocontato ");
		sql.append("(descricao) ");
		sql.append("VALUES (?) ");

		Connection conexao = ConexaoFactory.conectar();

		PreparedStatement comando = conexao.prepareStatement(sql.toString());
		comando.setString(1, f.getDescricao());
		
		comando.executeUpdate();
		
	}
	
	public void excluir (TipoContato f) throws SQLException{
		StringBuilder sql = new StringBuilder();
		sql.append("DELETE FROM tipocontato ");
		sql.append("WHERE codigo = ? ");

		Connection conexao = ConexaoFactory.conectar();
		
		PreparedStatement comando = conexao.prepareStatement(sql.toString());
		comando.setLong(1, f.getCodigo());
		
		comando.executeUpdate();		
		
	}
	
	public void editar (TipoContato f) throws SQLException{
		StringBuilder sql = new StringBuilder();
		sql.append("UPDATE tipocontato ");
		sql.append("SET descricao = ? "	);	
		sql.append("WHERE codigo = ? ");
		
		Connection conexao = ConexaoFactory.conectar();
		
		PreparedStatement comando = conexao.prepareStatement(sql.toString());
		comando.setString(1, f.getDescricao());
		comando.setLong(2, f.getCodigo());
		
		comando.executeUpdate();		
		
	}
	
	public TipoContato  buscarPorCodigo (TipoContato f) throws SQLException{
		StringBuilder sql = new StringBuilder();
		sql.append("SELECT codigo, descricao  ");
		sql.append("FROM tipocontato WHERE  codigo  = ? ");	
		
		Connection conexao = ConexaoFactory.conectar();
		
		PreparedStatement comando = conexao.prepareStatement(sql.toString());
		comando.setLong(1, f.getCodigo());
		
		ResultSet resultado = comando.executeQuery();
		TipoContato retorno = null;
		if(resultado.next()){
			retorno = new TipoContato();
			retorno.setCodigo(resultado.getLong("codigo"));
			retorno.setDescricao(resultado.getString("descricao"));
			
		}
		return retorno;

	}

	public ArrayList<TipoContato> listar() throws SQLException {
		StringBuilder sql = new StringBuilder();
		sql.append("SELECT codigo, descricao  ");
		sql.append("FROM tipocontato ORDER BY descricao ASC ");	
		
		Connection conexao = ConexaoFactory.conectar();
		
		PreparedStatement comando = conexao.prepareStatement(sql.toString());
	
		ResultSet resultado = comando.executeQuery();
		
		ArrayList<TipoContato> lista = new ArrayList<TipoContato>();
		
		while(resultado.next()) {
			TipoContato item = new TipoContato();
			item.setCodigo(resultado.getLong("codigo"));
			item.setDescricao(resultado.getString("descricao"));
			lista.add(item);
			
		}
		
		return lista;
	}
	
	public ArrayList<TipoContato> buscarDescricao(TipoContato f) throws SQLException {
		StringBuilder sql = new StringBuilder();
		sql.append("SELECT codigo, descricao  ");
		sql.append("FROM TipoContato " );
		sql.append(" WHERE descricao LIKE ? ");
		sql.append("ORDER BY descricao ASC ");	
		
		Connection conexao = ConexaoFactory.conectar();
		
		PreparedStatement comando = conexao.prepareStatement(sql.toString());
		comando.setString(1, "%" + f.getDescricao() + "%");
	
		ResultSet resultado = comando.executeQuery();
		
		ArrayList<TipoContato> lista = new ArrayList<TipoContato>();
		
		while(resultado.next()) {
			TipoContato item = new TipoContato();
			item.setCodigo(resultado.getLong("codigo"));
			item.setDescricao(resultado.getString("descricao"));
			lista.add(item);
			
		}
		
		return lista;
	}
	
	public static void main(String[] args) {
		TipoContato f1 = new TipoContato();
		TipoContato f2 = new TipoContato();
		

		f1.setDescricao("Telefone");
		f2.setDescricao("E-Mail");
		
		
		TipoContatoDAO fdao = new TipoContatoDAO();
		
		try {
			fdao.salvar(f1);
			fdao.salvar(f2);


	        ArrayList<TipoContato> lista = fdao.listar();
	        for (TipoContato f : lista) {
	        	System.out.println(f);
	        	
	        }
	        
		System.out.println("TipoContatos Salvos com sucesso");
		} catch (SQLException e) {
			System.out.println("Ocorreu um erro ao tentar salvar TipoContatos");
			e.printStackTrace();
		}
		
		
	}

}

