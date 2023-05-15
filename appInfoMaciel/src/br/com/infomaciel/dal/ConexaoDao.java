package br.com.infomaciel.dal;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConexaoDao {
	
	public static Connection getConnection() {
		try {
			String url = "jdbc:mysql://localhost:3306/dbinfomaciel";
			String usuario = "root";
			String senha = "123Ibyte!";
			Connection conexao = DriverManager.getConnection(url, usuario, senha);
			return conexao;
		} catch (SQLException e) {
			System.out.println("Erro ao conectar ao banco de dados");
			e.printStackTrace();
			return null;
		}
	}
}