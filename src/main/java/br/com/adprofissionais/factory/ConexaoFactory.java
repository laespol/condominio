package br.com.adprofissionais.factory;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConexaoFactory {
	
	private static final String USUARIO = "root";
	private static final String SENHA   = "lae123";
	private static final String URL     = "jdbc:mysql://localhost:3306/adcon?autoReconnect=true&useSSL=false";
	
	public static Connection conectar()throws SQLException {
		DriverManager.registerDriver(new com.mysql.jdbc.Driver());
		
		Connection conexao = DriverManager.getConnection(URL, USUARIO, SENHA);
		return conexao;
		
	}
	
	public static void main(String[] args) {
		try {
		Connection conexao = ConexaoFactory.conectar();
		System.out.println("conexao realizada com sucesso");
		} catch(SQLException ex) {
			ex.printStackTrace();
			System.out.println("Não foi possivel realizar a conexao");
		}
	}
	
	

}
