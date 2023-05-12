package br.com.infomaciel.screens;

import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.text.ParseException;

import javax.swing.DefaultComboBoxModel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFormattedTextField;
import javax.swing.JInternalFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.text.MaskFormatter;

import br.com.infomaciel.dal.ConexaoDao;

public class TelaUsuarios extends JInternalFrame {

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
	private JTextField txtUsuId;
	private JTextField txtUsuSenha;
	private JTextField txtUsuLogin;
	private JTextField txtUsuNome;
	private JFormattedTextField txtUsuFone;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					TelaUsuarios frame = new TelaUsuarios();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public TelaUsuarios() {
		setTitle(
				"\t\t\t\t\t                                                                    \t\t\t         Usuários / Cadastros");
		setSize(608, 430);
		setClosable(true);
		setMaximizable(true);
		setIconifiable(true);
		getContentPane().setPreferredSize(new Dimension(80, 80));
		getContentPane().setLayout(null);

		JLabel lblNewLabel = new JLabel("* ID");
		lblNewLabel.setBounds(78, 89, 54, 14);
		getContentPane().add(lblNewLabel);

		JLabel lblNewLabel_5 = new JLabel("* NOME");
		lblNewLabel_5.setBounds(78, 114, 54, 14);
		getContentPane().add(lblNewLabel_5);

		JLabel lblNewLabel_4 = new JLabel("* LOGIN");
		lblNewLabel_4.setBounds(78, 170, 46, 14);
		getContentPane().add(lblNewLabel_4);

		JLabel lblNewLabel_3 = new JLabel("* SENHA");
		lblNewLabel_3.setBounds(315, 170, 50, 14);
		getContentPane().add(lblNewLabel_3);

		JLabel lblNewLabel_2 = new JLabel("* PERFIL");
		lblNewLabel_2.setBounds(78, 60, 54, 14);
		getContentPane().add(lblNewLabel_2);

		JLabel lblNewLabel_1 = new JLabel("   FONE");
		lblNewLabel_1.setBounds(78, 142, 46, 14);
		getContentPane().add(lblNewLabel_1);

		txtUsuId = new JTextField();
		txtUsuId.setBounds(142, 86, 77, 20);
		getContentPane().add(txtUsuId);
		txtUsuId.setColumns(10);

		txtUsuNome = new JTextField();
		txtUsuNome.setColumns(10);
		txtUsuNome.setBounds(142, 111, 378, 20);
		getContentPane().add(txtUsuNome);

		txtUsuLogin = new JTextField();
		txtUsuLogin.setColumns(10);
		txtUsuLogin.setBounds(142, 167, 145, 20);
		getContentPane().add(txtUsuLogin);

		txtUsuSenha = new JTextField();
		txtUsuSenha.setColumns(10);
		txtUsuSenha.setBounds(375, 167, 145, 20);
		getContentPane().add(txtUsuSenha);

		JComboBox<String> cboUsuPerfil = new JComboBox<>();
		cboUsuPerfil.setModel(new DefaultComboBoxModel(new String[] { "", "usuario", "tecnico", "admin" }));
		cboUsuPerfil.setBounds(142, 56, 77, 22);
		getContentPane().add(cboUsuPerfil);

		txtUsuFone = new JFormattedTextField();
		txtUsuFone.setDocument(new OnlyNumbersDocument());

		MaskFormatter mascarafone = null;
		try {
			mascarafone = new MaskFormatter("(##)#####-####");
		} catch (ParseException e) {
			e.printStackTrace();
		}

		JFormattedTextField txtUsuFone = new JFormattedTextField(mascarafone);
		getContentPane().add(txtUsuFone);
		txtUsuFone.setBounds(142, 139, 145, 20);

		// metodo para adicionar usuários
		JButton btnUsuCreate = new JButton("");
		btnUsuCreate.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				conexao = ConexaoDao.getConnection();
				String sql = "insert into tbuser(iduser,user,login,password,perfil,phone) values(?,?,?,?,?,?)";
				try {
					pst = conexao.prepareStatement(sql);
					pst.setString(1, txtUsuId.getText());
					pst.setString(2, txtUsuNome.getText());
					pst.setString(3, txtUsuLogin.getText());
					pst.setString(4, txtUsuSenha.getText());
					pst.setString(5, cboUsuPerfil.getSelectedItem().toString());
					pst.setString(6, txtUsuFone.getText());
					// validação dos campos obrigatorios
					if ((txtUsuId.getText().isEmpty()) || (txtUsuNome.getText().isEmpty())
							|| (txtUsuLogin.getText().isEmpty()) || (txtUsuSenha.getText().isEmpty())) {
						JOptionPane.showMessageDialog(null, "Preencher todos os campos obrigatorios!");
					} else {

						// a linha abaixo atualiza a tabela usuario com os dados do formulario
						pst.executeUpdate();
						JOptionPane.showMessageDialog(null, "Usuário adicionado com sucesso!", "Sucesso",
								JOptionPane.INFORMATION_MESSAGE);
						LimparCamposUtil.limparCamposUsId(txtUsuId, txtUsuNome, txtUsuFone, txtUsuLogin, txtUsuSenha,
								cboUsuPerfil);
					}
				} catch (Exception erro) {
					JOptionPane.showMessageDialog(null, erro);
				}
			}
		});
		btnUsuCreate.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		btnUsuCreate.setToolTipText("Criar");
		btnUsuCreate.setIcon(new ImageIcon(TelaUsuarios.class.getResource("/br/com/infomaciel/icons/create.png")));
		btnUsuCreate.setPreferredSize(new Dimension(65, 65));
		btnUsuCreate.setBounds(117, 242, 89, 73);
		getContentPane().add(btnUsuCreate);

		// metodo para remover usuário
		JButton btnUsuDelete = new JButton("");
		btnUsuDelete.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// estrutura abaixo confirma a remoção do usuario.
				int remover = JOptionPane.showConfirmDialog(null, "tem certeza que deseja remover este usuário?",
						"Atenção", JOptionPane.YES_NO_OPTION);
				if (remover == JOptionPane.YES_OPTION) {
					String sql = "delete from tbuser where iduser=?";
					try {
						pst = conexao.prepareStatement(sql);
						pst.setString(1, txtUsuId.getText());
						pst.executeUpdate();
						LimparCamposUtil.limparCamposUsId(txtUsuId, txtUsuNome, txtUsuFone, txtUsuLogin, txtUsuSenha,
								cboUsuPerfil);
						JOptionPane.showMessageDialog(null, "Usuário removido com sucesso!", "Sucesso",
								JOptionPane.INFORMATION_MESSAGE);
					} catch (Exception erro) {
						JOptionPane.showMessageDialog(null, erro);
					}
				}
			}
		});
		btnUsuDelete.setToolTipText("Apagar");
		btnUsuDelete.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		btnUsuDelete.setIcon(new ImageIcon(TelaUsuarios.class.getResource("/br/com/infomaciel/icons/delete.png")));
		btnUsuDelete.setPreferredSize(new Dimension(65, 65));
		btnUsuDelete.setBounds(414, 242, 89, 73);
		getContentPane().add(btnUsuDelete);

		// metodo para consultar usuário
		JButton btnUsuRead = new JButton("");
		btnUsuRead.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				conexao = ConexaoDao.getConnection();
				String sql = "select * from tbuser where  iduser = ?";

				try {

					if (txtUsuId.getText().equals("")) {
						JOptionPane.showMessageDialog(null, "Digite um ID válido");
					} else {
						pst = conexao.prepareStatement(sql);
						pst.setString(1, txtUsuId.getText());
						rs = pst.executeQuery();
						if (rs.next()) {
							txtUsuNome.setText(rs.getString(2));
							txtUsuFone.setText(rs.getString(6));
							txtUsuLogin.setText(rs.getString(3));
							txtUsuSenha.setText(rs.getString(4));
							// a linha abaixo se refere ao combobox
							cboUsuPerfil.setSelectedItem(rs.getString(5));
						} else {
							JOptionPane.showMessageDialog(null, "Usuário não cadastrado!");
							// as linhas a baixo "limpa" os campos
							LimparCamposUtil.limparCampos(txtUsuNome, txtUsuFone, txtUsuLogin, txtUsuSenha);

						}
					}
				} catch (Exception erro) {
					JOptionPane.showMessageDialog(null, erro);

				}
			}
		});

		btnUsuRead.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		btnUsuRead.setToolTipText("Consultar");
		btnUsuRead.setIcon(new ImageIcon(TelaUsuarios.class.getResource("/br/com/infomaciel/icons/read.png")));
		btnUsuRead.setPreferredSize(new Dimension(65, 65));
		btnUsuRead.setBounds(216, 242, 89, 73);
		getContentPane().add(btnUsuRead);

		// criando o metodo para alterar dados do usuario
		JButton btnUsuUpdate = new JButton("");
		btnUsuUpdate.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				conexao = ConexaoDao.getConnection();
				// sempre prestar muito atenção na seguencia para passagem dos paramentros
				String sql = "update tbuser set user=?,login=?,password=?,perfil=?,phone=? where iduser=?";
				try {

					pst = conexao.prepareStatement(sql);
					pst.setString(1, txtUsuNome.getText());
					pst.setString(2, txtUsuLogin.getText());
					pst.setString(3, txtUsuSenha.getText());
					pst.setString(4, cboUsuPerfil.getSelectedItem().toString());
					pst.setString(5, txtUsuFone.getText());
					pst.setString(6, txtUsuId.getText());

					if ((txtUsuId.getText().isEmpty()) || (txtUsuNome.getText().isEmpty())
							|| (txtUsuLogin.getText().isEmpty()) || (txtUsuSenha.getText().isEmpty())) {
						JOptionPane.showMessageDialog(null, "Preencher todos os campos obrigatorios!");

					} else {
						// a linha abaixo altera a tabela usuario com os dados do formulario
						pst.executeUpdate();
						LimparCamposUtil.limparCamposUsId(txtUsuNome, txtUsuFone, txtUsuLogin, txtUsuSenha, txtUsuId,
								cboUsuPerfil);
						JOptionPane.showMessageDialog(null, "Usuário alterado com sucesso!", "Sucesso",
								JOptionPane.INFORMATION_MESSAGE);
					}

				} catch (Exception erro) {
					JOptionPane.showMessageDialog(null, erro);
					// System.out.println(erro);
				}

			}
		});
		btnUsuUpdate.setToolTipText("Atualizar");
		btnUsuUpdate.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		btnUsuUpdate.setIcon(new ImageIcon(TelaUsuarios.class.getResource("/br/com/infomaciel/icons/update.png")));
		btnUsuUpdate.setPreferredSize(new Dimension(65, 65));
		btnUsuUpdate.setBounds(315, 242, 89, 73);
		getContentPane().add(btnUsuUpdate);

		// colocando formato padrao no campo txtUsuFone
		try {
			MaskFormatter formatter = new MaskFormatter("(##)#####-####");
			formatter.install(txtUsuFone);

			JLabel lblNewLabel_6 = new JLabel("ADICIONAR");
			lblNewLabel_6.setBounds(127, 313, 70, 14);
			getContentPane().add(lblNewLabel_6);

			JLabel lblNewLabel_7 = new JLabel("BUSCAR");
			lblNewLabel_7.setBounds(241, 313, 64, 14);
			getContentPane().add(lblNewLabel_7);

			JLabel lblNewLabel_8 = new JLabel("EDITAR");
			lblNewLabel_8.setBounds(342, 313, 46, 14);
			getContentPane().add(lblNewLabel_8);

			JLabel lblNewLabel_9 = new JLabel("REMOVER");
			lblNewLabel_9.setBounds(438, 313, 65, 14);
			getContentPane().add(lblNewLabel_9);

			JLabel lblNewLabel_10 = new JLabel("* Campos obrigatorios!");
			lblNewLabel_10.setBounds(386, 64, 134, 14);
			getContentPane().add(lblNewLabel_10);
		} catch (ParseException e) {
			e.printStackTrace();
		}
	}
}