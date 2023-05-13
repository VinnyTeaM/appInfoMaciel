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

public class TelaLogin extends JFrame implements ActionListener {
	
	//usando a variavel conexao do DAL
	Connection conexao = null;
	/*criando variaveis especiais para conexao com o banco
	PreparedStatement e ResultSet são frameworks do pacote java.sql*
	e servem para preparar e executar as intruções sql */
	
	PreparedStatement pst = null;
	ResultSet rs = null;

	private static final long serialVersionUID = 2L;
	private JLabel labelUsuario, labelSenha;
	private JTextField campoUsuario;
	private JPasswordField campoSenha;
	private JButton botaoLogin;
	private JLabel lblStatus;
	private String perfil;

	public static String getPerfil() {
		return null;
	}

	public TelaLogin() {
		super("Tela de Login");
		setTitle("-*Informática Maciel*- LOGIN");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(400, 200);
		setResizable(false);
		setLocationRelativeTo(null);
		ImageIcon icone = new ImageIcon(getClass().getResource("/br/com/infomaciel/icons/login.png"));
		setIconImage(icone.getImage());
		labelUsuario = new JLabel("Usuário:");
		labelUsuario.setForeground(Color.BLUE);
		campoUsuario = new JTextField(20);
		campoUsuario.setForeground(Color.BLUE);
		labelSenha = new JLabel("Senha:");
		labelSenha.setForeground(Color.BLUE);
		campoSenha = new JPasswordField(20);
		campoSenha.setForeground(Color.BLUE);
		botaoLogin = new JButton("Login");
		botaoLogin.setForeground(Color.BLUE);
		botaoLogin.addActionListener(this);

		JPanel painelPrincipal = new JPanel(new GridLayout(3, 2, 5, 5));
		painelPrincipal.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
		painelPrincipal.add(labelUsuario);
		painelPrincipal.add(campoUsuario);
		painelPrincipal.add(labelSenha);
		painelPrincipal.add(campoSenha);

		lblStatus = new JLabel("");
		painelPrincipal.add(lblStatus);
		painelPrincipal.add(botaoLogin);

		getContentPane().add(painelPrincipal);

		setVisible(true);
	}
	/*
	 * aqui ve condição assim que abrir tela de login try (Connection conexao =
	 * ConexaoMySQL.getConnection()) { if (conexao != null) { lblStatus.setIcon( new
	 * javax.swing.ImageIcon(getClass().getResource(
	 * "/br/com/infomaciel/icons/dbon.png"))); } else { lblStatus.setIcon( new
	 * javax.swing.ImageIcon(getClass().getResource(
	 * "/br/com/infomaciel/icons/dboff.png"))); }
	 * 
	 * } catch (Exception erro) { erro.printStackTrace(); } }
	 */

	
	
	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == botaoLogin) {
			String usuario = campoUsuario.getText();
			String senha = new String(campoSenha.getPassword());

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

						// System.out.println(perfil);
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
						campoSenha.setText("");
						campoSenha.requestFocus();
					}
				} else {
					JOptionPane.showMessageDialog(this, "Usuário não encontrado!", "Erro", JOptionPane.ERROR_MESSAGE);
					campoUsuario.setText("");
					campoSenha.setText("");
					campoUsuario.requestFocus();
				}
			} catch (SQLException ex) {
				JOptionPane.showMessageDialog(this, "Erro ao conectar ao banco de dados.", "Erro",
						JOptionPane.ERROR_MESSAGE);
				ex.printStackTrace();
			}

		}
		

	}

	public static void main(String[] args) {
		new TelaLogin();
	}
	
	
}
