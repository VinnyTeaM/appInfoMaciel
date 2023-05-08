package br.com.infomaciel.screens;

import java.awt.Dimension;
import java.awt.EventQueue;

import javax.swing.JInternalFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.JComboBox;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.ImageIcon;
import java.awt.Cursor;


public class TelaUsuarios extends JInternalFrame {
	private static final long serialVersionUID = 2L;
	private JTextField txtUsuId;
	private JTextField txtUsoSenha;
	private JTextField txtUsuLogin;
	private JTextField txtUsuNome;
	private JTextField txtUsuFone;
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
		
		JLabel lblNewLabel = new JLabel("ID");
		lblNewLabel.setBounds(33, 83, 46, 14);
		getContentPane().add(lblNewLabel);
		
		JLabel lblNewLabel_5 = new JLabel("NOME");
		lblNewLabel_5.setBounds(33, 108, 46, 14);
		getContentPane().add(lblNewLabel_5);
		
		JLabel lblNewLabel_4 = new JLabel("LOGIN");
		lblNewLabel_4.setBounds(33, 164, 46, 14);
		getContentPane().add(lblNewLabel_4);
		
		JLabel lblNewLabel_3 = new JLabel("SENHA");
		lblNewLabel_3.setBounds(263, 164, 46, 14);
		getContentPane().add(lblNewLabel_3);
		
		JLabel lblNewLabel_2 = new JLabel("PERFIL");
		lblNewLabel_2.setBounds(33, 54, 46, 14);
		getContentPane().add(lblNewLabel_2);
		
		JLabel lblNewLabel_1 = new JLabel("FONE");
		lblNewLabel_1.setBounds(33, 136, 46, 14);
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
		
		txtUsoSenha = new JTextField();
		txtUsoSenha.setColumns(10);
		txtUsoSenha.setBounds(307, 161, 145, 20);
		getContentPane().add(txtUsoSenha);
		
		JComboBox cboUsuPerfil = new JComboBox();
		cboUsuPerfil.setModel(new DefaultComboBoxModel(new String[] {"USUARIO", "TECNICO", "ADMIN"}));
		cboUsuPerfil.setBounds(74, 50, 77, 22);
		getContentPane().add(cboUsuPerfil);
		
		txtUsuFone = new JTextField();
		txtUsuFone.setBounds(74, 133, 145, 20);
		getContentPane().add(txtUsuFone);
		txtUsuFone.setColumns(10);
		
		JButton btnUsoCreate = new JButton("");
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
		
		JButton btnUsoRead = new JButton("");
		btnUsoRead.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		btnUsoRead.setToolTipText("Consultar");
		btnUsoRead.setIcon(new ImageIcon(TelaUsuarios.class.getResource("/br/com/infomaciel/icons/read.png")));
		btnUsoRead.setPreferredSize(new Dimension(65, 65));
		btnUsoRead.setBounds(148, 236, 89, 73);
		getContentPane().add(btnUsoRead);
		
		JButton btnUsoUpdate = new JButton("");
		btnUsoUpdate.setToolTipText("Atualizar");
		btnUsoUpdate.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		btnUsoUpdate.setIcon(new ImageIcon(TelaUsuarios.class.getResource("/br/com/infomaciel/icons/update.png")));
		btnUsoUpdate.setPreferredSize(new Dimension(65, 65));
		btnUsoUpdate.setBounds(247, 236, 89, 73);
		getContentPane().add(btnUsoUpdate);
		setTitle("Cadastros / Usuários");
		setSize(500, 430);

	}
}
