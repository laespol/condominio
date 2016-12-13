package br.com.adprofissionais.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Date;

import br.com.adprofissionais.domain.LogAcesso;
import br.com.adprofissionais.factory.ConexaoFactory;

public class LogAcessoDAO {

	public void salvar(LogAcesso l) throws SQLException{
		StringBuilder sql = new StringBuilder();
		sql.append("INSERT INTO logacesso ");
		sql.append("(dataacesso, login , sucesso) ");
		sql.append("VALUES (?,?,?) ");

		Date date = new Date();
		Connection conexao = ConexaoFactory.conectar();

		PreparedStatement comando = conexao.prepareStatement(sql.toString());
		comando.setTimestamp(1, new java.sql.Timestamp(date.getTime()) );
		comando.setString(2, l.getLogin());
		comando.setString(3, l.getSucesso());
		
		
		comando.executeUpdate();
		
	}
	
}


