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
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.Map;

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

import br.com.infomaciel.dal.ConexaoDao;
import net.proteanit.sql.DbUtils;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.view.JasperViewer;

/**
 * A classe TelaOs é responsável por exibir uma interface gráfica para o usuário
 * interagir com as ordens de serviço. Ela herda funcionalidades da classe
 * JInternalFrame e implementa a lógica relacionada às ordens de serviço.
 */
public class TelaOs extends JInternalFrame {
	/**
	 * Número de série para a serialização.
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * 
	 * O objeto Connection representa a conexão com o banco de dados. Ele é
	 * responsável por estabelecer a comunicação entre a aplicação e o banco de
	 * dados, permitindo a execução de consultas e atualizações.
	 */
	Connection conexao = null;

	/**
	 * 
	 * O objeto PreparedStatement representa uma instrução SQL pré-compilada que
	 * pode ser executada várias vezes com diferentes parâmetros. Ele é usado para
	 * melhorar o desempenho e a segurança, prevenindo ataques de injeção de SQL.
	 */
	PreparedStatement pst = null;
	/**
	 * 
	 * O objeto ResultSet representa um conjunto de resultados de uma consulta ao
	 * banco de dados. Ele fornece métodos para iterar sobre as linhas do conjunto
	 * de resultados e acessar os dados armazenados em cada coluna.
	 */
	ResultSet rs = null;

	/**
	 * O tipo de usuário logado.
	 */
	private String type;

	/**
	 * O perfil do usuário logado obtido a partir da classe TelaLogin.
	 */
	String perfil = TelaLogin.getPerfil();

	/**
	 * Campo de texto para inserção do número da ordem de serviço.
	 */
	private JTextField txtOs;

	/**
	 * Campo de texto para inserção da data da ordem de serviço.
	 */
	private JTextField txtData;

	/**
	 * Campo de texto para pesquisa de clientes.
	 */
	private JTextField txtCliPesquisar;

	/**
	 * Campo de texto para exibição do ID do cliente selecionado.
	 */
	private JTextField txtCliId;

	/**
	 * Tabela que exibe os resultados da pesquisa de clientes.
	 */
	private JTable tblClientes;

	/**
	 * Campo de texto para inserção do equipamento relacionado à ordem de serviço.
	 */
	private JTextField txtOsEquip;

	/**
	 * Campo de texto para inserção da definição do problema relacionado à ordem de
	 * serviço.
	 */
	private JTextField txtOsDef;

	/**
	 * Campo de texto para inserção do serviço a ser realizado na ordem de serviço.
	 */
	private JTextField txtOsServ;

	/**
	 * Campo de texto para inserção do técnico responsável pela ordem de serviço.
	 */
	private JTextField txtOsTec;

	/**
	 * Campo de texto para inserção do valor do serviço da ordem de serviço.
	 */
	private JTextField txtOsValor;

	/**
	 * Número da ordem de serviço.
	 */
	private String numOs;

	/**
	 * Botão para atualizar a ordem de serviço.
	 */
	private JButton btnOsUpdate;

	/**
	 * Botão para excluir a ordem de serviço.
	 */
	private JButton btnOsDelete;

	/**
	 * Botão para imprimir a ordem de serviço.
	 */
	private JButton btnOsPrint;

	/**
	 * Botão para visualizar os detalhes da ordem de serviço.
	 */
	private JButton btnOsRead;

	/**
	 * Botão para criar uma nova ordem de serviço.
	 */
	private JButton btnOsCreate;

	/**
	 * ComboBox para seleção da situação da ordem de serviço.
	 */
	private JComboBox<String> cboOsSit;

	/**
	 * RadioButton para seleção de orçamento.
	 */
	private JRadioButton rbtOrc;

	/**
	 * RadioButton para seleção de ordem de serviço.
	 */
	private JRadioButton rbtOs;

	/**
	 * 
	 * Método de entrada do programa.
	 * 
	 * @param args argumentos de linha de comando (não são utilizados)
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
		rbtOrc = new JRadioButton("Orçamento");
		rbtOrc.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				type = "Orçamento";
			}
		});
		rbtOrc.setBounds(0, 57, 109, 23);
		panel.add(rbtOrc);
		rbtOrc.setSelected(true);
		type = "Orçamento";

		rbtOs = new JRadioButton("Ordem de Serviço");
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

		cboOsSit = new JComboBox<>();
		cboOsSit.setModel(new DefaultComboBoxModel<>(
				new String[] { " ", "Na bancada", "Entrega OK", "Orçamento REPROVADO", "Orçamento APROVADO",
						"Aguardando aprovação", "Aguardando peças", "Abandonado pelo cliente", "Retornou" }));
		cboOsSit.setBounds(66, 114, 185, 22);
		getContentPane().add(cboOsSit);

		JPanel panel_1 = new JPanel();
		panel_1.setBorder(new TitledBorder(null, "Cliente", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		panel_1.setBounds(250, 0, 324, 142);
		getContentPane().add(panel_1);
		panel_1.setLayout(null);

		txtCliPesquisar = new JTextField();
		txtCliPesquisar.addKeyListener(new KeyAdapter() {
			@Override
			public void keyTyped(KeyEvent e) {
				cliPesquisar();
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

		btnOsCreate = new JButton("");
		btnOsCreate.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				criarOs();
			}
		});
		btnOsCreate.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		btnOsCreate.setIcon(new ImageIcon(TelaOs.class.getResource("/br/com/infomaciel/icons/create.png")));
		btnOsCreate.setBounds(48, 297, 89, 75);
		getContentPane().add(btnOsCreate);

		btnOsRead = new JButton("");
		btnOsRead.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				pesquisarOs();
			}
		});
		btnOsRead.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		btnOsRead.setIcon(new ImageIcon(TelaOs.class.getResource("/br/com/infomaciel/icons/read.png")));
		btnOsRead.setBounds(147, 297, 89, 75);
		getContentPane().add(btnOsRead);

		btnOsUpdate = new JButton("");
		btnOsUpdate.setEnabled(false);
		btnOsUpdate.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				atualizarOs();
			}
		});
		btnOsUpdate.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		btnOsUpdate.setIcon(new ImageIcon(TelaOs.class.getResource("/br/com/infomaciel/icons/update.png")));
		btnOsUpdate.setBounds(251, 297, 89, 75);
		getContentPane().add(btnOsUpdate);

		btnOsDelete = new JButton();
		btnOsDelete.setEnabled(false);
		btnOsDelete.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				deletarOs();
			}
		});
		btnOsDelete.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		btnOsDelete.setIcon(new ImageIcon(TelaOs.class.getResource("/br/com/infomaciel/icons/delete.png")));
		btnOsDelete.setBounds(357, 297, 89, 75);
		getContentPane().add(btnOsDelete);

		btnOsPrint = new JButton("");
		btnOsPrint.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				imprimirOs();
			}

		});
		btnOsPrint.setEnabled(false);
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

	/**
	 * Realiza a pesquisa de clientes com base no nome.
	 */
	public void cliPesquisar() {
		conexao = ConexaoDao.getConnection();
		String sql = "SELECT idcli AS ID, namecli AS NOME, phonecli AS FONE FROM tbclient WHERE namecli LIKE ?";
		try {
			pst = conexao.prepareStatement(sql);
			// passando o conteudo de pesquisa para o ?
			// atenção ao "%" continuação da string sql
			pst.setString(1, txtCliPesquisar.getText() + "%");
			rs = pst.executeQuery();
			// a linha abaixo usa a biblioteca rs2xml.jar para preencher a tabela.
			tblClientes.setModel(DbUtils.resultSetToTableModel(rs));
			txtOsValor.setText("0");
		} catch (Exception e2) {
			JOptionPane.showMessageDialog(null, e2);
		}
	}

	/**
	 * Cria uma nova ordem de serviço.
	 */
	public void criarOs() {
		String sql = "INSERT INTO tbos (type,situation,equipment,defect,service,tech,price,idcli) VALUES (?,?,?,?,?,?,?,?)";
		String sqlos = "SELECT max(os) FROM tbos";
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

			if ((txtCliId.getText().isEmpty()) || (txtOsEquip.getText().isEmpty()) || (txtOsDef.getText().isEmpty())
					|| (cboOsSit.getSelectedItem().equals(" "))) {
				JOptionPane.showMessageDialog(null, "Preencha todos os campos obrigatorios!!");

			} else {
				int adicionado = pst.executeUpdate();
				if (adicionado > 0) {
					JOptionPane.showMessageDialog(null, "OS adicionada com sucesso!!");
					btnOsCreate.setEnabled(false);
					btnOsRead.setEnabled(false);
					btnOsPrint.setEnabled(true);
					pst = conexao.prepareStatement(sqlos);
					rs = pst.executeQuery();
					if (rs.next()) {
						txtOs.setText(rs.getString(1));
					}

					LimparCamposUtil.limparCamposOs(txtCliId, txtOsEquip, txtOsDef, txtOsServ, txtOsTec, txtOsValor,
							txtCliPesquisar);
					((DefaultTableModel) tblClientes.getModel()).setRowCount(0);
					cboOsSit.setSelectedItem(" ");

				}
			}

		} catch (Exception e2) {
			JOptionPane.showMessageDialog(null, e2);
		}
		;
	}

	/**
	 * Pesquisa uma ordem de serviço no banco de dados com base no número da OS.
	 */
	public void pesquisarOs() {
		numOs = JOptionPane.showInputDialog("Número da OS");
		String sql = "SELECT * FROM tbos WHERE os = " + numOs;
		// formata a data para o padrão brasileiro, exemplo: 10/07/2016
		DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");

		try {
			pst = conexao.prepareStatement(sql);
			rs = (ResultSet) pst.executeQuery();
			if (rs.next()) {
				txtOs.setText(rs.getString(1));
				// recebe a data do SQL
				txtData.setText(dateFormat.format(rs.getTimestamp(2)));
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
				// evitando problemas
				btnOsCreate.setEnabled(false);
				btnOsRead.setEnabled(false);
				txtCliPesquisar.setEnabled(false);
				tblClientes.setVisible(false);
				// botoes habilitados
				btnOsUpdate.setEnabled(true);
				btnOsDelete.setEnabled(true);
				btnOsPrint.setEnabled(true);

			} else {
				JOptionPane.showMessageDialog(null, "Os não cadastrada");
			}

		} catch (java.sql.SQLSyntaxErrorException e2) {
			JOptionPane.showMessageDialog(null, "OS inválida");

		} catch (Exception e3) {
			JOptionPane.showMessageDialog(null, e3);
		}

	}

	/**
	 * Atualiza uma ordem de serviço no banco de dados com base nos dados
	 * preenchidos.
	 */
	public void atualizarOs() {
		String sql = "UPDATE tbos SET type = ?, situation = ?, equipment = ?, defect = ?, service = ?, tech = ?, price = ? WHERE os = ?";
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
			pst.setString(8, txtOs.getText());

			if ((txtCliId.getText().isEmpty()) || (txtOsEquip.getText().isEmpty()) || (txtOsDef.getText().isEmpty())
					|| (cboOsSit.getSelectedItem().equals(" "))) {
				JOptionPane.showMessageDialog(null, "Preencha todos os campos obrigatorios!!");

			} else {
				int adicionado = pst.executeUpdate();
				if (adicionado > 0) {
					JOptionPane.showMessageDialog(null, "OS alterada com sucesso!!");
					LimparCamposUtil.limparCamposOs(txtCliId, txtOsEquip, txtOsDef, txtOsServ, txtOsTec, txtOsValor,
							txtCliPesquisar);
					cboOsSit.setSelectedItem(" ");
					txtOs.setText(null);
					txtData.setText(null);
					// gerenciando botoes
					btnOsCreate.setEnabled(true);
					btnOsRead.setEnabled(true);
					txtCliPesquisar.setEnabled(true);
					tblClientes.setVisible(true);
					btnOsUpdate.setEnabled(false);
					btnOsDelete.setEnabled(false);
					btnOsPrint.setEnabled(false);

				}
			}

		} catch (Exception e2) {
			JOptionPane.showMessageDialog(null, e2);
		}
		;
	}

	/**
	 * 
	 * Deleta uma OS do banco de dados, antes verifica se o perfil do usuário logado
	 * é "admin" Se for, executa a instrução SQL
	 */

	public void deletarOs() {
		int confirma = JOptionPane.showConfirmDialog(null, "Tem certeza que deseja remover esta OS?", "Atenção",
				JOptionPane.YES_NO_OPTION);
		if (confirma == JOptionPane.YES_OPTION) {
			String sql = "SELECT FROM tbuser WHERE perfil = ?";
			try {
				pst = conexao.prepareStatement(sql);
				pst.setString(1, TelaLogin.getPerfil());

				if (TelaLogin.getPerfil().equals("admin")) {
					String sqldelete = "DELETE FROM tbos WHERE os = ?";
					pst = conexao.prepareStatement(sqldelete);
					pst.setString(1, txtOs.getText());
				}
				int apagado = pst.executeUpdate();
				if (apagado > 0) {

					JOptionPane.showMessageDialog(null, "OS removida com sucesso!!");
					LimparCamposUtil.limparCamposOs(txtCliId, txtOsEquip, txtOsDef, txtOsServ, txtOsTec, txtOsValor,
							txtCliPesquisar);
					cboOsSit.setSelectedItem(" ");
					txtOs.setText(null);
					txtData.setText(null);
					// habilitar os objetos
					btnOsCreate.setEnabled(true);
					btnOsRead.setEnabled(true);
					txtCliPesquisar.setEnabled(true);
					tblClientes.setVisible(true);
					btnOsUpdate.setEnabled(false);
					btnOsDelete.setEnabled(false);
					btnOsPrint.setEnabled(false);
				}

			} catch (java.sql.SQLException e2) {
				JOptionPane.showMessageDialog(null, "APENAS ADMINISTRADORES PODEM EXCLUIR OS!!");
			}
		}
	}

	/**
	 * Imprime a ordem de serviço em um relatório utilizando o JasperReports.
	 */

	public void imprimirOs() {
		int confirma = JOptionPane.showConfirmDialog(null, "Confirma a impressão dessa OS?", "Atenção",
				JOptionPane.YES_NO_OPTION);
		if (confirma == JOptionPane.YES_OPTION) {
			try {
				// Criar um filtro com parâmetros
				Map<String, Object> parametros = new HashMap<>();
				parametros.put("os", Integer.parseInt(txtOs.getText()));

				// Obter a conexão com o banco de dados
				Connection conexao = ConexaoDao.getConnection();

				// Preencher o relatório utilizando o JasperReport
				JasperPrint print = JasperFillManager.fillReport(
						"C:\\Users\\avinn\\OneDrive\\sistema os\\relatorios\\os.jasper", parametros, conexao);

				// Exibir o relatório utilizando o JasperViewer
				JasperViewer.viewReport(print, false);

				// Fechar a conexão com o banco de dados
				conexao.close();
				// habilitar os objetos
				btnOsRead.setEnabled(true);

				LimparCamposUtil.limparCamposOs(txtCliId, txtOsEquip, txtOsDef, txtOsServ, txtOsTec, txtOsValor,
						txtCliPesquisar);
			} catch (Exception e2) {
				JOptionPane.showMessageDialog(null, "Erro ao imprimir relatório: " + e2.getMessage());
				e2.printStackTrace();
			}
		}
	}

	/**
	 * Obtém o botão de atualização de ordem de serviço.
	 *
	 * @return O botão de atualização de ordem de serviço.
	 */
	public JButton getBtnOsUpdate() {
		return btnOsUpdate;
	}

	/**
	 * Obtém o botão de exclusão de ordem de serviço.
	 *
	 * @return O botão de exclusão de ordem de serviço.
	 */
	public JButton getBtnOsDelete() {
		return btnOsDelete;
	}

	/**
	 * Obtém o botão de impressão de ordem de serviço.
	 *
	 * @return O botão de impressão de ordem de serviço.
	 */
	public JButton getBtnOsPrint() {
		return btnOsPrint;
	}

	/**
	 * Obtém o botão de criação de ordem de serviço.
	 *
	 * @return O botão de criação de ordem de serviço.
	 */
	public JButton getBtnOsCreate() {
		return btnOsCreate;
	}

	/**
	 * Obtém o botão de leitura de ordem de serviço.
	 *
	 * @return O botão de leitura de ordem de serviço.
	 */
	public JButton getBtnOsRead() {
		return btnOsRead;
	}

}
