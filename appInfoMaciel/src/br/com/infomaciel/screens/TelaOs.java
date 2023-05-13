package br.com.infomaciel.screens;

import java.awt.EventQueue;

import javax.swing.ButtonGroup;
import javax.swing.JInternalFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTextField;
import javax.swing.JComboBox;
import javax.swing.DefaultComboBoxModel;
import javax.swing.border.TitledBorder;
import javax.swing.ImageIcon;
import java.awt.ScrollPane;
import javax.swing.JTable;
import javax.swing.JScrollPane;
import javax.swing.table.DefaultTableModel;
import javax.swing.JButton;

public class TelaOs extends JInternalFrame {
	private JTextField textField;
	private JTextField textField_1;
	private JTextField textField_2;
	private JTextField textField_3;
	private JTable table;
	private JTextField textField_4;
	private JTextField textField_5;
	private JTextField textField_6;
	private JTextField textField_7;
	private JTextField textField_8;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					TelaOs frame = new TelaOs();
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
	public TelaOs() {
		setTitle("OS / Cadastros");
		setMaximizable(true);
		setIconifiable(true);
		setClosable(true);
		setSize(600, 430);
		getContentPane().setLayout(null);
		
		JPanel panel = new JPanel();
		panel.setBounds(10, 11, 241, 87);
		getContentPane().add(panel);
		panel.setLayout(null);
		
		JLabel lblNewLabel_1 = new JLabel("Nº OS");
		lblNewLabel_1.setBounds(0, 5, 46, 14);
		panel.add(lblNewLabel_1);
		
		JLabel lblNewLabel = new JLabel("DATA");
		lblNewLabel.setBounds(125, 5, 46, 14);
		panel.add(lblNewLabel);
		
		textField = new JTextField();
		textField.setEditable(false);
		textField.setBounds(0, 30, 115, 20);
		panel.add(textField);
		textField.setColumns(10);
		
		textField_1 = new JTextField();
		textField_1.setEditable(false);
		textField_1.setBounds(125, 30, 115, 20);
		panel.add(textField_1);
		textField_1.setColumns(10);
		
		JRadioButton rdbtnNewRadioButton = new JRadioButton("Orçamento");
		rdbtnNewRadioButton.setBounds(0, 57, 109, 23);
		panel.add(rdbtnNewRadioButton);
		
		JRadioButton rdbtnNewRadioButton_1 = new JRadioButton("Ordem de Serviço");
		rdbtnNewRadioButton_1.setBounds(111, 57, 129, 23);
		panel.add(rdbtnNewRadioButton_1);
		
		//Adicione os botões de opção ao ButtonGroup:
		ButtonGroup buttonGroup = new ButtonGroup();
		buttonGroup.add(rdbtnNewRadioButton);
		buttonGroup.add(rdbtnNewRadioButton_1);
		
		JLabel lblNewLabel_2 = new JLabel("Situação");
		lblNewLabel_2.setBounds(10, 118, 46, 14);
		getContentPane().add(lblNewLabel_2);
		
		JComboBox comboBox = new JComboBox();
		comboBox.setModel(new DefaultComboBoxModel(new String[] {"Entrega OK", "Orçamento REPROVADO", "Aguardando aprovação", "Aguardando peças", "Abandonado pelo cliente", "Na bancada", "Retornou"}));
		comboBox.setBounds(66, 114, 185, 22);
		getContentPane().add(comboBox);
		
		JPanel panel_1 = new JPanel();
		panel_1.setBorder(new TitledBorder(null, "Cliente", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		panel_1.setBounds(250, 0, 324, 142);
		getContentPane().add(panel_1);
		panel_1.setLayout(null);
		
		textField_2 = new JTextField();
		textField_2.setBounds(10, 17, 128, 20);
		panel_1.add(textField_2);
		textField_2.setColumns(10);
		
		JLabel lblNewLabel_3 = new JLabel("");
		lblNewLabel_3.setIcon(new ImageIcon(TelaOs.class.getResource("/br/com/infomaciel/icons/find.png")));
		lblNewLabel_3.setBounds(135, 11, 32, 26);
		panel_1.add(lblNewLabel_3);
		
		JLabel lblNewLabel_4 = new JLabel("* ID");
		lblNewLabel_4.setBounds(191, 20, 32, 14);
		panel_1.add(lblNewLabel_4);
		
		textField_3 = new JTextField();
		textField_3.setBounds(233, 17, 65, 20);
		panel_1.add(textField_3);
		textField_3.setColumns(10);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(10, 48, 304, 65);
		panel_1.add(scrollPane);
		
		table = new JTable();
		table.setModel(new DefaultTableModel(
			new Object[][] {
			},
			new String[] {
				"ID", "NOME", "FONE"
			}
		));
		table.getColumnModel().getColumn(0).setPreferredWidth(68);
		table.getColumnModel().getColumn(1).setPreferredWidth(279);
		table.getColumnModel().getColumn(2).setPreferredWidth(258);
		scrollPane.setViewportView(table);
		
		JLabel lblNewLabel_5 = new JLabel("* Equipamento");
		lblNewLabel_5.setBounds(10, 171, 74, 14);
		getContentPane().add(lblNewLabel_5);
		
		JLabel lblNewLabel_5_1 = new JLabel("* Defeito");
		lblNewLabel_5_1.setBounds(10, 199, 46, 14);
		getContentPane().add(lblNewLabel_5_1);
		
		JLabel lblNewLabel_5_2 = new JLabel("Serviço");
		lblNewLabel_5_2.setBounds(20, 224, 46, 14);
		getContentPane().add(lblNewLabel_5_2);
		
		JLabel lblNewLabel_5_3 = new JLabel("Técnico");
		lblNewLabel_5_3.setBounds(20, 248, 46, 14);
		getContentPane().add(lblNewLabel_5_3);
		
		textField_4 = new JTextField();
		textField_4.setBounds(94, 168, 480, 20);
		getContentPane().add(textField_4);
		textField_4.setColumns(10);
		
		textField_5 = new JTextField();
		textField_5.setColumns(10);
		textField_5.setBounds(94, 196, 480, 20);
		getContentPane().add(textField_5);
		
		textField_6 = new JTextField();
		textField_6.setColumns(10);
		textField_6.setBounds(94, 221, 480, 20);
		getContentPane().add(textField_6);
		
		textField_7 = new JTextField();
		textField_7.setColumns(10);
		textField_7.setBounds(94, 245, 200, 20);
		getContentPane().add(textField_7);
		
		JLabel lblNewLabel_6 = new JLabel("Valor total");
		lblNewLabel_6.setBounds(316, 248, 58, 14);
		getContentPane().add(lblNewLabel_6);
		
		textField_8 = new JTextField();
		textField_8.setColumns(10);
		textField_8.setBounds(374, 245, 200, 20);
		getContentPane().add(textField_8);
		
		JButton btnNewButton = new JButton("");
		btnNewButton.setIcon(new ImageIcon(TelaOs.class.getResource("/br/com/infomaciel/icons/create.png")));
		btnNewButton.setBounds(48, 297, 89, 75);
		getContentPane().add(btnNewButton);
		
		JButton btnNewButton_1 = new JButton("");
		btnNewButton_1.setIcon(new ImageIcon(TelaOs.class.getResource("/br/com/infomaciel/icons/read.png")));
		btnNewButton_1.setBounds(147, 297, 89, 75);
		getContentPane().add(btnNewButton_1);
		
		JButton btnNewButton_2 = new JButton("");
		btnNewButton_2.setIcon(new ImageIcon(TelaOs.class.getResource("/br/com/infomaciel/icons/update.png")));
		btnNewButton_2.setBounds(251, 297, 89, 75);
		getContentPane().add(btnNewButton_2);
		
		JButton btnNewButton_3 = new JButton("");
		btnNewButton_3.setIcon(new ImageIcon(TelaOs.class.getResource("/br/com/infomaciel/icons/delete.png")));
		btnNewButton_3.setBounds(357, 297, 89, 75);
		getContentPane().add(btnNewButton_3);
		
		JButton btnNewButton_4 = new JButton("");
		btnNewButton_4.setIcon(new ImageIcon(TelaOs.class.getResource("/br/com/infomaciel/icons/print.png")));
		btnNewButton_4.setBounds(456, 297, 89, 75);
		getContentPane().add(btnNewButton_4);
		
		JLabel lblNewLabel_7 = new JLabel("ADICIONAR");
		lblNewLabel_7.setBounds(66, 375, 71, 14);
		getContentPane().add(lblNewLabel_7);
		
		JLabel lblNewLabel_8 = new JLabel("BUSCAR");
		lblNewLabel_8.setBounds(174, 375, 62, 14);
		getContentPane().add(lblNewLabel_8);
		
		JLabel lblNewLabel_9 = new JLabel("EDITAR");
		lblNewLabel_9.setBounds(277, 375, 46, 14);
		getContentPane().add(lblNewLabel_9);
		
		JLabel lblNewLabel_10 = new JLabel("REMOVER");
		lblNewLabel_10.setBounds(374, 375, 58, 14);
		getContentPane().add(lblNewLabel_10);
		
		JLabel lblNewLabel_11 = new JLabel("IMPRIMIR");
		lblNewLabel_11.setBounds(476, 375, 58, 14);
		getContentPane().add(lblNewLabel_11);
		

	}
}
