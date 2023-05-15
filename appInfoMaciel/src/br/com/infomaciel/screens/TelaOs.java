package br.com.infomaciel.screens;

import java.awt.Cursor;
import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import javax.swing.ButtonGroup;
import javax.swing.DefaultComboBoxModel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JInternalFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;


import com.mysql.cj.protocol.Resultset;

import br.com.infomaciel.dal.ConexaoDao;
import net.proteanit.sql.DbUtils;

public class TelaOs extends JInternalFrame {
	/**
	 * 
	 */
	private static final long serialVersionUID = -1223643524871083895L;
	Connection conexao = null;
	PreparedStatement pst = null;
	ResultSet rs = null;
	// variavel para o radion button selecionado
	private String type;
	private JTextField txtOs;
	private JTextField txtData;
	private JTextField txtCliPesquisar;
	private JTextField txtCliId;
	private JTable tblClientes;
	private JTextField txtOsEquip;
	private JTextField txtOsDef;
	private JTextField txtOsServ;
	private JTextField txtOsTec;
	private JTextField txtOsValor;
	private String numOs;

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

		conexao = ConexaoDao.getConnection();
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
		lblNewLabel_1.setBounds(20, 5, 46, 14);
		panel.add(lblNewLabel_1);

		JLabel lblNewLabel = new JLabel("DATA");
		lblNewLabel.setBounds(153, 5, 46, 14);
		panel.add(lblNewLabel);

		txtOs = new JTextField();
		txtOs.setEditable(false);
		txtOs.setBounds(0, 30, 79, 20);
		panel.add(txtOs);
		txtOs.setColumns(10);

		txtData = new JTextField();
		txtData.setEditable(false);
		txtData.setBounds(111, 30, 129, 20);
		panel.add(txtData);
		txtData.setColumns(10);

		// atribuindo um texto a variavel type se selecionado o radion button
		JRadioButton rbtOrc = new JRadioButton("Orçamento");
		rbtOrc.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				type = "Orçamento";
			}
		});
		rbtOrc.setBounds(0, 57, 109, 23);
		panel.add(rbtOrc);
		rbtOrc.setSelected(true);
		type = "Orçamento";

		JRadioButton rbtOs = new JRadioButton("Ordem de Serviço");
		rbtOs.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				type = "Ordem de Serviço";
			}
		});
		rbtOs.setBounds(111, 57, 129, 23);
		panel.add(rbtOs);

		// Adicione os botões de opção ao ButtonGroup:
		ButtonGroup buttonGroup = new ButtonGroup();
		buttonGroup.add(rbtOrc);
		buttonGroup.add(rbtOs);

		JLabel lblNewLabel_2 = new JLabel("Situação");
		lblNewLabel_2.setBounds(10, 118, 56, 14);
		getContentPane().add(lblNewLabel_2);

		JComboBox<String> cboOsSit = new JComboBox<>();
		cboOsSit.setModel(
				new DefaultComboBoxModel<String>(new String[] { "Na bancada", "Entrega OK", "Orçamento REPROVADO",
						"Aguardando aprovação", "Aguardando peças", "Abandonado pelo cliente", "Retornou" }));
		cboOsSit.setBounds(66, 114, 185, 22);
		getContentPane().add(cboOsSit);

		JPanel panel_1 = new JPanel();
		panel_1.setBorder(new TitledBorder(null, "Cliente", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		panel_1.setBounds(250, 0, 324, 142);
		getContentPane().add(panel_1);
		panel_1.setLayout(null);

		txtCliPesquisar = new JTextField();

		// chamando o metodo pesquisar clientes
		txtCliPesquisar = new JTextField();
		txtCliPesquisar.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent e) {
				conexao = ConexaoDao.getConnection();
				String sql = "SELECT idcli AS ID,namecli AS NOME,phonecli AS FONE FROM tbclient WHERE namecli LIKE ?";
				try {
					pst = conexao.prepareStatement(sql);
					// passando o conteudo de pesquisa para o ?
					// atenção ao "%" continuação da string sql
					pst.setString(1, txtCliPesquisar.getText() + "%");
					rs = (Resultset) pst.executeQuery();
					// a linha abaixo usa a biblioteca rs2xml.jar para preencher a tabela.
					tblClientes.setModel(DbUtils.resultSetToTableModel((ResultSet) rs));
				} catch (Exception e2) {
					JOptionPane.showMessageDialog(null, e2);
				}

			}
		});

		txtCliPesquisar.setBounds(10, 17, 175, 20);
		panel_1.add(txtCliPesquisar);
		txtCliPesquisar.setColumns(10);

		JLabel lblNewLabel_3 = new JLabel("");
		lblNewLabel_3.setIcon(new ImageIcon(TelaOs.class.getResource("/br/com/infomaciel/icons/find.png")));
		lblNewLabel_3.setBounds(184, 11, 32, 26);
		panel_1.add(lblNewLabel_3);

		JLabel lblNewLabel_4 = new JLabel("* ID");
		lblNewLabel_4.setBounds(217, 20, 32, 14);
		panel_1.add(lblNewLabel_4);

		txtCliId = new JTextField();
		txtCliId.setEditable(false);
		txtCliId.setBounds(249, 17, 65, 20);
		panel_1.add(txtCliId);
		txtCliId.setColumns(10);

		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(10, 45, 304, 86);
		panel_1.add(scrollPane);

		DefaultTableModel model = new DefaultTableModel() {
			/**
			 * 
			 */
			private static final long serialVersionUID = 1L;

			@Override
			public boolean isCellEditable(int rowIndex, int columnIndex) {
				return false; // Torna todas as células não editáveis
			}
		};

		tblClientes = new JTable();
		tblClientes = new JTable(model) {
			/**
			 * 
			 */
			private static final long serialVersionUID = 1L;

			@Override
			public boolean isCellEditable(int row, int column) {
				return false; // Torna todas as células não editáveis
			}
		};
		tblClientes.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				int setar = tblClientes.getSelectedRow();
				TableModel model = tblClientes.getModel();
				txtCliId.setText(model.getValueAt(setar, 0).toString());
			}
		});
		tblClientes.setFocusable(false);
		tblClientes.setModel(new DefaultTableModel(new Object[][] {}, new String[] { "ID", "NOME", "FONE" }));
		tblClientes.getColumnModel().getColumn(0).setPreferredWidth(68);
		tblClientes.getColumnModel().getColumn(1).setPreferredWidth(279);
		tblClientes.getColumnModel().getColumn(2).setPreferredWidth(258);
		scrollPane.setViewportView(tblClientes);

		JLabel lblNewLabel_5 = new JLabel("* Equipamento");
		lblNewLabel_5.setBounds(10, 171, 89, 14);
		getContentPane().add(lblNewLabel_5);

		JLabel lblNewLabel_5_1 = new JLabel("* Defeito");
		lblNewLabel_5_1.setBounds(10, 199, 74, 14);
		getContentPane().add(lblNewLabel_5_1);

		JLabel lblNewLabel_5_2 = new JLabel("Serviço");
		lblNewLabel_5_2.setBounds(20, 224, 46, 14);
		getContentPane().add(lblNewLabel_5_2);

		JLabel lblNewLabel_5_3 = new JLabel("Técnico");
		lblNewLabel_5_3.setBounds(20, 248, 46, 14);
		getContentPane().add(lblNewLabel_5_3);

		txtOsEquip = new JTextField();
		txtOsEquip.setBounds(94, 168, 480, 20);
		getContentPane().add(txtOsEquip);
		txtOsEquip.setColumns(10);

		txtOsDef = new JTextField();
		txtOsDef.setColumns(10);
		txtOsDef.setBounds(94, 196, 480, 20);
		getContentPane().add(txtOsDef);

		txtOsServ = new JTextField();
		txtOsServ.setColumns(10);
		txtOsServ.setBounds(94, 221, 480, 20);
		getContentPane().add(txtOsServ);

		txtOsTec = new JTextField();
		txtOsTec.setColumns(10);
		txtOsTec.setBounds(94, 245, 200, 20);
		getContentPane().add(txtOsTec);

		JLabel lblNewLabel_6 = new JLabel("Valor total");
		lblNewLabel_6.setBounds(306, 248, 58, 14);
		getContentPane().add(lblNewLabel_6);

		txtOsValor = new JTextField();
		txtOsValor.setText("0");
		txtOsValor.setColumns(10);
		txtOsValor.setBounds(374, 245, 200, 20);
		getContentPane().add(txtOsValor);

		JButton btnOsCreate = new JButton("");
		btnOsCreate.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String sql = "INSERT INTO tbos (type,situation,equipment,defect,service,tech,price,idcli) VALUES (?,?,?,?,?,?,?,?)";
				try {
					pst = conexao.prepareStatement(sql);
					pst.setString(1, type);
					pst.setString(2, cboOsSit.getSelectedItem().toString());
					pst.setString(3, txtOsEquip.getText());
					pst.setString(4, txtOsDef.getText());
					pst.setString(5, txtOsServ.getText());
					pst.setString(6, txtOsTec.getText());
					// substitui a "," por "."
					pst.setString(7, txtOsValor.getText().replace(",", "."));
					pst.setString(8, txtCliId.getText());

					if ((txtCliId.getText().isEmpty()) || (txtOsEquip.getText().isEmpty())
							|| (txtOsDef.getText().isEmpty())) {
						JOptionPane.showMessageDialog(null, "Preencha todos os campos obrigatorios!!");

					} else {
						int adicionado = pst.executeUpdate();
						if (adicionado > 0) {
							JOptionPane.showMessageDialog(null, "OS adicionada com sucesso!!");
							LimparCamposUtil.limparCamposOs(txtCliId, txtOsEquip, txtOsDef, txtOsServ, txtOsTec,
									txtOsValor);
						}
					}

				} catch (Exception e2) {
					JOptionPane.showMessageDialog(null, e2);
				}
				;
			}
		});
		btnOsCreate.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		btnOsCreate.setIcon(new ImageIcon(TelaOs.class.getResource("/br/com/infomaciel/icons/create.png")));
		btnOsCreate.setBounds(48, 297, 89, 75);
		getContentPane().add(btnOsCreate);
		
		//Metodo pesquisar OS
		JButton btnOsRead = new JButton("");
		btnOsRead.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				numOs = JOptionPane.showInputDialog("Número da OS");
				String sql = "SELECT * FROM tbos WHERE os = " + numOs;
				try {
					pst = conexao.prepareStatement(sql);
					rs = (ResultSet) pst.executeQuery();
					if (rs.next()) {
					    txtOs.setText(rs.getString(1));
					    txtData.setText(rs.getString(2));
					    // setando os radios buttons
					    String rbtType = rs.getString(3);
					    if (rbtType.equals("Ordem de Serviço")) {
					        rbtOs.setSelected(true);
					        type = "Ordem de Serviço";
					    } else {
					        rbtOrc.setSelected(true);
					        type = "Orçamento";
					    }
					    cboOsSit.setSelectedItem(rs.getString(4));
					    txtOsEquip.setText(rs.getString(5));
					    txtOsDef.setText(rs.getString(6));
					    txtOsServ.setText(rs.getString(7));
					    txtOsTec.setText(rs.getString(8));
					    txtOsValor.setText(rs.getString(9));
					    txtCliId.setText(rs.getString(10));
					    //evitando problemas 
					    btnOsCreate.setEnabled(false);
					    txtCliPesquisar.setEnabled(false);
					    tblClientes.setVisible(false);
					} else {
					    JOptionPane.showMessageDialog(null, "Os não cadastrada");
					}


				} catch (java.sql.SQLSyntaxErrorException e2) {
					JOptionPane.showMessageDialog(null, "OS inválida");
					
				}catch (Exception e3) {
					JOptionPane.showMessageDialog(null, e3);
				}


			}
		});
		btnOsRead.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		btnOsRead.setIcon(new ImageIcon(TelaOs.class.getResource("/br/com/infomaciel/icons/read.png")));
		btnOsRead.setBounds(147, 297, 89, 75);
		getContentPane().add(btnOsRead);

		JButton btnOsUpdate = new JButton("");
		btnOsUpdate.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		btnOsUpdate.setIcon(new ImageIcon(TelaOs.class.getResource("/br/com/infomaciel/icons/update.png")));
		btnOsUpdate.setBounds(251, 297, 89, 75);
		getContentPane().add(btnOsUpdate);

		JButton btnOsDelete = new JButton("");
		btnOsDelete.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		btnOsDelete.setIcon(new ImageIcon(TelaOs.class.getResource("/br/com/infomaciel/icons/delete.png")));
		btnOsDelete.setBounds(357, 297, 89, 75);
		getContentPane().add(btnOsDelete);

		JButton btnOsPrint = new JButton("");
		btnOsPrint.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		btnOsPrint.setIcon(new ImageIcon(TelaOs.class.getResource("/br/com/infomaciel/icons/print.png")));
		btnOsPrint.setBounds(456, 297, 89, 75);
		getContentPane().add(btnOsPrint);

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
		lblNewLabel_10.setBounds(375, 375, 59, 14);
		getContentPane().add(lblNewLabel_10);

		JLabel lblNewLabel_11 = new JLabel("IMPRIMIR");
		lblNewLabel_11.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		lblNewLabel_11.setBounds(476, 375, 58, 14);
		getContentPane().add(lblNewLabel_11);

	}

}
