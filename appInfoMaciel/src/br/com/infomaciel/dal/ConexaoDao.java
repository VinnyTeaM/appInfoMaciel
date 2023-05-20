/*
 * Copyright (c) [2023] [VinnyTeaM]
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */

package br.com.infomaciel.dal;

import java.sql.Connection;

import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Conexao com o banco de dados
 * @author VinnyTeaM
 * @version 1.0
 */

public class ConexaoDao {

	/**
	 * Metodo responsavel pela conexao com banco
	 * 
	 * @return conexao
	 */

	public static Connection getConnection() {
		try {
			String url = "jdbc:mysql://localhost:3306/dbinfomaciel?characterEnconding=utf-8";
			String usuario = "dba";
			String senha = "infomaciel123456";
			Connection conexao = DriverManager.getConnection(url, usuario, senha);
			return conexao;
		} catch (SQLException e) {
			System.out.println("Erro ao conectar ao banco de dados");
			e.printStackTrace();
			return null;
		}
	}
}