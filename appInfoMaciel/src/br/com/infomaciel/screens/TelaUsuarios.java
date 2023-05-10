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

import javax.swing.ComboBoxModel;
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

	private static final long serialVersionUID = 2L;
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
		setClosable(true);
		setMaximizable(true);
		setIconifiable(true);
		getContentPane().setPreferredSize(new Dimension(80, 80));
		getContentPane().setLayout(null);

		JLabel lblNewLabel = new JLabel("* ID");
		lblNewLabel.setBounds(10, 83, 54, 14);
		getContentPane().add(lblNewLabel);

		JLabel lblNewLabel_5 = new JLabel("* NOME");
		lblNewLabel_5.setBounds(10, 108, 54, 14);
		getContentPane().add(lblNewLabel_5);

		JLabel lblNewLabel_4 = new JLabel("* LOGIN");
		lblNewLabel_4.setBounds(10, 164, 46, 14);
		getContentPane().add(lblNewLabel_4);

		JLabel lblNewLabel_3 = new JLabel("* SENHA");
		lblNewLabel_3.setBounds(247, 164, 50, 14);
		getContentPane().add(lblNewLabel_3);

		JLabel lblNewLabel_2 = new JLabel("* PERFIL");
		lblNewLabel_2.setBounds(10, 54, 54, 14);
		getContentPane().add(lblNewLabel_2);

		JLabel lblNewLabel_1 = new JLabel("   FONE");
		lblNewLabel_1.setBounds(10, 136, 46, 14);
		getContentPane().add(lblNewLabel_1);

		txtUsuId = new JTextField();
		txtUsuId.setBounds(74, 80, 77, 20);
		getContentPane().add(txtUsuId);
		txtUsuId.setColumns(10);

		txtUsuNome = new JTextField();
		txtUsuNome.setColumns(10);
		txtUsuNome.setBounds(74, 105, 378, 20);
		getContentPane().add(txtUsuNome);

		txtUsuLogin = new JTextField();
		txtUsuLogin.setColumns(10);
		txtUsuLogin.setBounds(74, 161, 145, 20);
		getContentPane().add(txtUsuLogin);

		txtUsuSenha = new JTextField();
		txtUsuSenha.setColumns(10);
		txtUsuSenha.setBounds(307, 161, 145, 20);
		getContentPane().add(txtUsuSenha);

		JComboBox<String> cboUsuPerfil = new JComboBox<>();
		cboUsuPerfil.setModel(new DefaultComboBoxModel(new String[] {"", "usuario", "tecnico", "admin"}));
		cboUsuPerfil.setBounds(74, 50, 77, 22);
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
		txtUsuFone.setBounds(74, 133, 145, 20);
		setVisible(true);

		// metodo para adicionar usuários
		JButton btnUsoCreate = new JButton("");
		btnUsoCreate.addActionListener(new ActionListener() {
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
					if ((txtUsuId.getText().isEmpty()) || (txtUsuNome.getText().isEmpty()) || (txtUsuLogin.getText().isEmpty())||(txtUsuSenha.getText().isEmpty())) {
						JOptionPane.showMessageDialog(null, "Preencher todos os campos obrigatorios!");
					} else {

						// a linha abaixo atualiza a tabela usuario com os dados do formulario
						pst.executeUpdate();
						JOptionPane.showMessageDialog(null, "Usuario adicionado com sucesso!", "Sucesso",
								JOptionPane.INFORMATION_MESSAGE);
						LimparCamposUtil.limparCamposId(txtUsuId, txtUsuNome, txtUsuFone, txtUsuLogin, txtUsuSenha,
								cboUsuPerfil);
					}
				} catch (Exception erro) {
					JOptionPane.showMessageDialog(null, erro);
				}
			}
		});
		btnUsoCreate.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		btnUsoCreate.setToolTipText("Criar");
		btnUsoCreate.setIcon(new ImageIcon(TelaUsuarios.class.getResource("/br/com/infomaciel/icons/create.png")));
		btnUsoCreate.setPreferredSize(new Dimension(65, 65));
		btnUsoCreate.setBounds(49, 236, 89, 73);
		getContentPane().add(btnUsoCreate);

		JButton btnUsoDelete = new JButton("");
		btnUsoDelete.setToolTipText("Apagar");
		btnUsoDelete.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		btnUsoDelete.setIcon(new ImageIcon(TelaUsuarios.class.getResource("/br/com/infomaciel/icons/delete.png")));
		btnUsoDelete.setPreferredSize(new Dimension(65, 65));
		btnUsoDelete.setBounds(346, 236, 89, 73);
		getContentPane().add(btnUsoDelete);

		// metodo para consultar usuário
		JButton btnUsoRead = new JButton("");
		btnUsoRead.addActionListener(new ActionListener() {
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
							JOptionPane.showMessageDialog(null, "Usuario não cadastrado!");
							// as linhas a baixo "limpa" os campos
							LimparCamposUtil.limparCampos(txtUsuNome, txtUsuFone, txtUsuLogin, txtUsuSenha);

						}
					}
				} catch (Exception erro) {
					JOptionPane.showMessageDialog(null, erro);

				}
			}
		});

		btnUsoRead.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		btnUsoRead.setToolTipText("Consultar");
		btnUsoRead.setIcon(new ImageIcon(TelaUsuarios.class.getResource("/br/com/infomaciel/icons/read.png")));
		btnUsoRead.setPreferredSize(new Dimension(65, 65));
		btnUsoRead.setBounds(148, 236, 89, 73);
		getContentPane().add(btnUsoRead);

		//criando o metodo para alterar dados do usuario
		JButton btnUsoUpdate = new JButton("");
		btnUsoUpdate.addActionListener(new ActionListener() {
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
					
					if ((txtUsuId.getText().isEmpty()) || (txtUsuNome.getText().isEmpty()) || (txtUsuLogin.getText().isEmpty())||(txtUsuSenha.getText().isEmpty())) {
						JOptionPane.showMessageDialog(null, "Preencher todos os campos obrigatorios!");
						
					} else {
						// a linha abaixo altera a tabela usuario com os dados do formulario
							pst.executeUpdate();
							JOptionPane.showMessageDialog(null, "Usuario alterado com sucesso!", "Sucesso",
							JOptionPane.INFORMATION_MESSAGE);
					}
					
				} catch (Exception erro) {
						JOptionPane.showMessageDialog(null, erro);
						//System.out.println(erro);
				}
				
			}
		});
		btnUsoUpdate.setToolTipText("Atualizar");
		btnUsoUpdate.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		btnUsoUpdate.setIcon(new ImageIcon(TelaUsuarios.class.getResource("/br/com/infomaciel/icons/update.png")));
		btnUsoUpdate.setPreferredSize(new Dimension(65, 65));
		btnUsoUpdate.setBounds(247, 236, 89, 73);
		getContentPane().add(btnUsoUpdate);
		setTitle("Cadastros / Usuários");
		setSize(500, 430);

		try {
			MaskFormatter formatter = new MaskFormatter("(##)#####-####");
			formatter.install(txtUsuFone);

			JLabel lblNewLabel_6 = new JLabel("ADICIONAR");
			lblNewLabel_6.setBounds(59, 307, 70, 14);
			getContentPane().add(lblNewLabel_6);

			JLabel lblNewLabel_7 = new JLabel("BUSCAR");
			lblNewLabel_7.setBounds(173, 307, 64, 14);
			getContentPane().add(lblNewLabel_7);

			JLabel lblNewLabel_8 = new JLabel("EDITAR");
			lblNewLabel_8.setBounds(274, 307, 46, 14);
			getContentPane().add(lblNewLabel_8);

			JLabel lblNewLabel_9 = new JLabel("DELETAR");
			lblNewLabel_9.setBounds(370, 307, 65, 14);
			getContentPane().add(lblNewLabel_9);

			JLabel lblNewLabel_10 = new JLabel("* Campos obrigatorios!");
			lblNewLabel_10.setBounds(318, 58, 134, 14);
			getContentPane().add(lblNewLabel_10);
		} catch (ParseException e) {
			e.printStackTrace();
		}
	}
}
