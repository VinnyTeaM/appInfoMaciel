package br.com.infomaciel.screens;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JInternalFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

import br.com.infomaciel.dal.ConexaoDao;

public class TelaTrocaSenha extends JInternalFrame {

	// usando a variavel conexao do DAL
	Connection conexao = null;
	/*
	 * criando variaveis especiais para conexao com o banco PreparedStatement e
	 * ResultSet são frameworks do pacote java.sql* e servem para preparar e
	 * executar as intruções sql
	 */
	PreparedStatement pst = null;
	ResultSet rs = null;

	private static final long serialVersionUID = 3L;
	private JLabel lblSenhaUsu;
	private JLabel lblSenhaSenha;
	private JLabel lblSenhaNova;
	private JLabel lblSenhaRep;
	private JTextField txtSenhaUsu;
	private JPasswordField txtSenhaSenha;
	private JPasswordField txtSenhaNova;
	private JPasswordField txtSenhaRep;
	private JCheckBox chbSenha;
	private JButton btnSenhaConf;
	private JButton btnSenhaCan;

	String nomeUsuario = TelaPrincipal.getNomeUsuario();
	String perfil = TelaLogin.getPerfil();

	public TelaTrocaSenha() {
		// Defina o título, tamanho e layout do JInternalFrame
		super("Mudar Senha / Opções", false, true, true, true);
		setSize(400, 300);
		getContentPane().setLayout(new GridLayout(6, 2));
		// System.out.println(nomeUsuario);
		// System.out.println(perfil);
		// Inicialize os campos de texto, senhas, botões e checkbox
		lblSenhaUsu = new JLabel("Usuário:");
		lblSenhaSenha = new JLabel("Senha:");
		lblSenhaNova = new JLabel("Nova senha:");
		lblSenhaRep = new JLabel("Repetir nova senha:");
		txtSenhaUsu = new JTextField();
		txtSenhaUsu.setEditable(false);
		txtSenhaUsu.setEnabled(true);
		txtSenhaUsu.setText(nomeUsuario);
		txtSenhaSenha = new JPasswordField();
		txtSenhaNova = new JPasswordField();
		txtSenhaRep = new JPasswordField();
		chbSenha = new JCheckBox("Visualizar senha");
		btnSenhaConf = new JButton("Confirmar");
		btnSenhaCan = new JButton("Cancelar");

		// Adicione os componentes na tela
		getContentPane().add(lblSenhaUsu);
		getContentPane().add(txtSenhaUsu);
		getContentPane().add(lblSenhaSenha);
		getContentPane().add(txtSenhaSenha);
		getContentPane().add(lblSenhaNova);
		getContentPane().add(txtSenhaNova);
		getContentPane().add(lblSenhaRep);
		getContentPane().add(txtSenhaRep);
		getContentPane().add(chbSenha);
		getContentPane().add(new JLabel()); // espaço em branco
		getContentPane().add(btnSenhaConf);
		getContentPane().add(btnSenhaCan);
		getRootPane().setDefaultButton(btnSenhaConf);

		chbSenha.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				txtSenhaSenha.setEchoChar(chbSenha.isSelected() ? '\0' : '*');
				txtSenhaNova.setEchoChar(chbSenha.isSelected() ? '\0' : '*');
				txtSenhaRep.setEchoChar(chbSenha.isSelected() ? '\0' : '*');
			}
		});

		// metodo para botão de confirmar trocar senha
		btnSenhaConf.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				conexao = ConexaoDao.getConnection();

				String senhaAntiga = new String(txtSenhaSenha.getPassword());
				String novaSenha = new String(txtSenhaNova.getPassword());
				String repetirNovaSenha = new String(txtSenhaRep.getPassword());

				// Verifica se os campos foram preenchidos corretamente
				if (nomeUsuario.isEmpty() || senhaAntiga.isEmpty() || novaSenha.isEmpty()
						|| repetirNovaSenha.isEmpty()) {
					JOptionPane.showMessageDialog(null, "Preencha todos os campos!", "Atenção",
							JOptionPane.ERROR_MESSAGE);
					return;
				}
				if (!novaSenha.equals(repetirNovaSenha)) {
					JOptionPane.showMessageDialog(null, "As senhas não coincidem!", "Atenção",
							JOptionPane.ERROR_MESSAGE);
					return;
				}

				try {
					conexao = ConexaoDao.getConnection();
					PreparedStatement pst = conexao
							.prepareStatement("SELECT * FROM tbuser WHERE user = ? AND password = ?");

					pst.setString(1, nomeUsuario);
					pst.setString(2, senhaAntiga);
					ResultSet rs = pst.executeQuery();

					if (rs.next()) {
						// Atualiza a senha na tabela tbuser
						PreparedStatement updatepst = conexao
								.prepareStatement("UPDATE tbuser SET password = ? WHERE user = ?");
						updatepst.setString(1, novaSenha);
						updatepst.setString(2, nomeUsuario);
						updatepst.executeUpdate();

						JOptionPane.showMessageDialog(null, "Senha alterada com sucesso!", "Sucesso",
								JOptionPane.INFORMATION_MESSAGE);
						dispose(); // fecha o JInternalFrame
					} else {
						JOptionPane.showMessageDialog(null, "Usuário ou senha incorretos!", "Erro",
								JOptionPane.ERROR_MESSAGE);
					}

					rs.close();
					pst.close();
					conexao.close();
				} catch (SQLException ex) {
					JOptionPane.showMessageDialog(null, "Erro ao acessar o banco de dados:\n" + ex.getMessage(), "Erro",
							JOptionPane.ERROR_MESSAGE);
				}
			}
		});

	}
}
