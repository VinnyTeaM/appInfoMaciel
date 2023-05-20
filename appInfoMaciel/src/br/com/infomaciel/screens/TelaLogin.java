package br.com.infomaciel.screens;

import java.awt.Color;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

import br.com.infomaciel.dal.ConexaoDao;

/**
 * A classe TelaLogin representa a tela de login do sistema.
 */
public class TelaLogin extends JFrame implements ActionListener {

	/**
	 * Numero de serie para a serializacao.
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * 
	 * JLabel privado para o rotulo do usuario.
	 */
	private JLabel labelUsuario;
	/**
	 * 
	 * JLabel privado para o rotulo da senha.
	 */
	private JLabel labelSenha;
	/**
	 * 
	 * JTextField privado para o campo de entrada do usuario.
	 */
	private JTextField txtUsuario;
	/**
	 * 
	 * JPasswordField privado para o campo de entrada da senha.
	 */
	private JPasswordField txtSenha;

	/**
	 * Botao utilizado para fazer login.
	 */
	private JButton btnLogin;

	/**
	 * Label utilizado para exibir o status do login.
	 */
	private JLabel lblStatus;

	/**
	 * Perfil do usuario logado.
	 */
	private static String perfil;

	/**
	 * Obtem o perfil do usuario logado.
	 *
	 * @return O perfil do usuario.
	 */
	public static String getPerfil() {
		return perfil;
	}

	/**
	 * Construtor da classe TelaLogin.
	 */
	public TelaLogin() {
		super("Tela de Login");
		setTitle("-*Informática Maciel*- LOGIN");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(400, 200);
		setResizable(false);
		setLocationRelativeTo(null);
		getRootPane().setDefaultButton(btnLogin);
		ImageIcon icone = new ImageIcon(getClass().getResource("/br/com/infomaciel/icons/login.png"));
		setIconImage(icone.getImage());
		labelUsuario = new JLabel("Usuário:");
		labelUsuario.setForeground(Color.BLUE);
		txtUsuario = new JTextField(20);
		txtUsuario.setForeground(Color.BLUE);
		labelSenha = new JLabel("Senha:");
		labelSenha.setForeground(Color.BLUE);
		txtSenha = new JPasswordField(20);
		txtSenha.setForeground(Color.BLUE);
		btnLogin = new JButton("Login");
		btnLogin.setForeground(Color.BLUE);
		btnLogin.addActionListener(this);

		JPanel painelPrincipal = new JPanel(new GridLayout(3, 2, 5, 5));
		painelPrincipal.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
		painelPrincipal.add(labelUsuario);
		painelPrincipal.add(txtUsuario);
		painelPrincipal.add(labelSenha);
		painelPrincipal.add(txtSenha);

		lblStatus = new JLabel("");
		painelPrincipal.add(lblStatus);
		painelPrincipal.add(btnLogin);
		getContentPane().add(painelPrincipal);

		getRootPane().setDefaultButton(btnLogin);

		setVisible(true);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == btnLogin) {
			String usuario = txtUsuario.getText();
			String senha = new String(txtSenha.getPassword());

			try (Connection conexao = ConexaoDao.getConnection()) {

				// aqui ve a condição apos acionar botao login
				if (conexao != null) {
					lblStatus.setIcon(
							new javax.swing.ImageIcon(getClass().getResource("/br/com/infomaciel/icons/dbon.png")));
				} else {
					lblStatus.setIcon(
							new javax.swing.ImageIcon(getClass().getResource("/br/com/infomaciel/icons/dboff.png")));
				}

				PreparedStatement consulta = conexao.prepareStatement("SELECT * FROM tbuser WHERE login = ?");
				consulta.setString(1, usuario);
				ResultSet resultado = consulta.executeQuery();

				if (resultado.next()) {
					String senhaArmazenada = resultado.getString("password");
					if (senha.equals(senhaArmazenada)) {
						// a linha abaixo obtem o conteudo do campo perfil na tabela tbuser
						perfil = resultado.getString(5);

						// estrutura abaixo faz o tratamento do perfil do usuario
						if (perfil.equals("admin")) {
							resultado.getString(2);
							TelaPrincipal principal = new TelaPrincipal();
							principal.setVisible(true);
							TelaPrincipal.menCadUsu.setEnabled(true);
							TelaPrincipal.menRel.setEnabled(true);
							TelaPrincipal.lblUsuario.setText(resultado.getString(2));
							TelaPrincipal.lblUsuario.setForeground(Color.red);

							this.dispose();
							conexao.close();
						} else {
							resultado.getString(2);
							TelaPrincipal principal = new TelaPrincipal();
							principal.setVisible(true);
							TelaPrincipal.lblUsuario.setText(resultado.getString(2));

							this.dispose();
							conexao.close();
						}

					} else {
						JOptionPane.showMessageDialog(this, "Senha incorreta!", "Erro", JOptionPane.ERROR_MESSAGE);
						txtSenha.setText("");
						txtSenha.requestFocus();
					}
				} else {
					JOptionPane.showMessageDialog(this, "Usuário não encontrado!", "Erro", JOptionPane.ERROR_MESSAGE);
					txtUsuario.setText("");
					txtSenha.setText("");
					txtUsuario.requestFocus();
				}
			} catch (SQLException ex) {
				JOptionPane.showMessageDialog(this, "Erro ao conectar ao banco de dados.", "Erro",
						JOptionPane.ERROR_MESSAGE);
				ex.printStackTrace();
			}

		}

	}

	/**
	 * 
	 * Metodo de entrada do programa.
	 * 
	 * @param args argumentos de linha de comando (nao sao utilizados)
	 */
	public static void main(String[] args) {
		new TelaLogin();
	}
}
