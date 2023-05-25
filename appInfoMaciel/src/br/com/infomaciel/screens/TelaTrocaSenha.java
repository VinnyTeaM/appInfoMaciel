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

import br.com.infomaciel.dao.ConexaoDao;

/**
 * A classe TelaTrocaSenha e responsavel por exibir uma interface grafica para o
 * usuario alterar sua senha. Ela herda funcionalidades da classe JInternalFrame
 * e implementa a logica de troca de senha no banco de dados conforme o usuario
 * logado.
 */
public class TelaTrocaSenha extends JInternalFrame {

	/**
	 *
	 * O objeto Connection representa a conexao com o banco de dados. Ele e
	 * responsavel por estabelecer a comunicacao entre a aplicacao e o banco de
	 * dados, permitindo a execucao de consultas e atualizacoes.
	 */
	Connection conexao = null;

	/**
	 *
	 * O objeto PreparedStatement representa uma instrucao SQL pre compilada que
	 * pode ser executada varias vezes com diferentes parametros. Ele e usado para
	 * melhorar o desempenho e a seguranca, prevenindo ataques de injecao de SQL.
	 */
	PreparedStatement pst = null;
	/**
	 *
	 * O objeto ResultSet representa um conjunto de resultados de uma consulta ao
	 * banco de dados. Ele fornece metodos para iterar sobre as linhas do conjunto
	 * de resultados e acessar os dados armazenados em cada coluna.
	 */
	ResultSet rs = null;

	/**
	 * Numero de serie para a serializacao.
	 */
	private static final long serialVersionUID = 1L;

	/**
	 *
	 * Campo para o campo "Usuario".
	 */
	private JLabel lblSenhaUsu;
	/**
	 *
	 * Campo para o campo "Senha".
	 */
	private JLabel lblSenhaSenha;
	/**
	 *
	 * Campo para o campo "Nova senha".
	 */
	private JLabel lblSenhaNova;
	/**
	 *
	 * Campo para o campo "Repetir nova senha".
	 */
	private JLabel lblSenhaRep;
	/**
	 * Campo de texto e campo de senha utilizado na interface de alteracao de senha.
	 */
	private JTextField txtSenhaUsu;

	/**
	 *
	 * Campo de senha utilizado para digitar a senha atual.
	 */
	private JPasswordField txtSenhaSenha;
	/**
	 *
	 * Campo de senha utilizado para digitar a nova senha.
	 */
	private JPasswordField txtSenhaNova;
	/**
	 *
	 * Campo de senha utilizado para repetir a nova senha.
	 */
	private JPasswordField txtSenhaRep;

	/**
	 * Checkbox utilizado para exibir/ocultar a senha.
	 */
	private JCheckBox chbSenha;

	/**
	 *
	 * Botao de confirmacao para a alteracao de senha.
	 */
	private JButton btnSenhaConf;
	/**
	 *
	 * Botao de cancelamento da alteracao de senha.
	 */
	private JButton btnSenhaCan;

	/**
	 *
	 * Variavel que armazena o nome de usuario do usuario logado.
	 */
	String nomeUsuario = TelaPrincipal.getNomeUsuario();
	/**
	 *
	 * Variavel que armazena o perfil do usuario logado.
	 */
	String perfil = TelaLogin.getPerfil();

	/**
	 * Construtor da classe TelaTrocaSenha.
	 */
	public TelaTrocaSenha() {
		// Define o título, tamanho e layout do JInternalFrame
		super("Mudar Senha / Opções", false, true, true, true);
		setSize(400, 300);

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

		btnSenhaCan.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				dispose();
			}
		});

		// Adiciona os componentes na tela
		getContentPane().setLayout(new GridLayout(6, 2));
		getContentPane().add(lblSenhaUsu);
		getContentPane().add(txtSenhaUsu);
		getContentPane().add(lblSenhaSenha);
		getContentPane().add(txtSenhaSenha);
		getContentPane().add(lblSenhaNova);
		getContentPane().add(txtSenhaNova);
		getContentPane().add(lblSenhaRep);
		getContentPane().add(txtSenhaRep);
		getContentPane().add(chbSenha);
		// espaco em branco
		getContentPane().add(new JLabel());
		getContentPane().add(btnSenhaConf);
		getContentPane().add(btnSenhaCan);
		getRootPane().setDefaultButton(btnSenhaConf);

		chbSenha.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				txtSenhaSenha.setEchoChar(chbSenha.isSelected() ? '\0' : '*');
				txtSenhaNova.setEchoChar(chbSenha.isSelected() ? '\0' : '*');
				txtSenhaRep.setEchoChar(chbSenha.isSelected() ? '\0' : '*');
			}
		});

		btnSenhaConf.addActionListener(new ActionListener() {
			@Override
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
