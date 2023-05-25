package br.com.infomaciel.screens;

import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFormattedTextField;
import javax.swing.JInternalFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import javax.swing.text.MaskFormatter;

import br.com.infomaciel.dao.ConexaoDao;
import net.proteanit.sql.DbUtils;

/**
 * A classe TelaClientes e responsavel por exibir uma interface grafica para o
 * usuario interagir com os clientes. Ela herda funcionalidades da classe
 * JInternalFrame e implementa a logica relacionada aos clientes.
 */
public class TelaClientes extends JInternalFrame {

	/**
	 * Numero de serie para a serializacao.
	 */
	private static final long serialVersionUID = 1;

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
	 *
	 * JTextField utilizado para inserir ou exibir o nome do cliente.
	 */
	private JTextField txtCliNome;
	/**
	 *
	 * JTextField utilizado para inserir ou exibir o endereco do cliente.
	 */
	private JTextField txtCliEnd;
	/**
	 *
	 * JFormattedTextField utilizado para inserir ou exibir o telefone do cliente.
	 */
	private JFormattedTextField txtCliFone;
	/**
	 *
	 * JTextField utilizado para inserir ou exibir o email do cliente.
	 */
	private JTextField txtCliEmail;
	/**
	 *
	 * JTextField utilizado para pesquisar clientes pelo nome.
	 */
	private JTextField txtCliPesquisar;
	/**
	 *
	 * JTable utilizado para exibir a lista de clientes.
	 */
	private JTable tblClientes;
	/**
	 *
	 * JTextField utilizado para exibir o ID do cliente selecionado.
	 */
	private JTextField txtCliId;
	/**
	 *
	 * JButton utilizado para criar um novo cliente.
	 */
	private JButton btnCliCreate;
	/**
	 *
	 * JButton utilizado para atualizar um cliente.
	 */
	private JButton btnCliUpdate;
	/**
	 *
	 * JButton utilizado para deletar um cliente.
	 */
	private JButton btnCliDelete;

	/**
	 * Launch the application.
	 *
	 * @param args the command-line arguments
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			@Override
			public void run() {
				try {
					TelaClientes frame = new TelaClientes();
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
	public TelaClientes() {
		setTitle("Clientes / Cadastros");
		getContentPane().setPreferredSize(new Dimension(80, 80));
		setMaximizable(true);
		setIconifiable(true);
		setClosable(true);
		setSize(608, 407);
		getContentPane().setLayout(null);

		JLabel lblNewLabel = new JLabel("* NOME");
		lblNewLabel.setBounds(10, 158, 46, 14);
		getContentPane().add(lblNewLabel);

		JLabel lblNewLabel_1 = new JLabel("  ENDEREÇO");
		lblNewLabel_1.setBounds(10, 192, 68, 14);
		getContentPane().add(lblNewLabel_1);

		JLabel lblNewLabel_2 = new JLabel("* FONE");
		lblNewLabel_2.setBounds(10, 220, 46, 14);
		getContentPane().add(lblNewLabel_2);

		JLabel lblNewLabel_3 = new JLabel("  EMAIL");
		lblNewLabel_3.setBounds(10, 257, 55, 14);
		getContentPane().add(lblNewLabel_3);

		JLabel lblNewLabel_4 = new JLabel("* Campos obrigatorios!");
		lblNewLabel_4.setBounds(450, 14, 132, 14);
		getContentPane().add(lblNewLabel_4);

		txtCliNome = new JTextField();
		txtCliNome.setBounds(80, 155, 502, 20);
		getContentPane().add(txtCliNome);
		txtCliNome.setColumns(10);

		txtCliEnd = new JTextField();
		txtCliEnd.setBounds(80, 189, 502, 20);
		getContentPane().add(txtCliEnd);
		txtCliEnd.setColumns(10);

		try {
			MaskFormatter mask = new MaskFormatter("(##)#####-####");
			txtCliFone = new JFormattedTextField(mask);
		} catch (ParseException e) {
			e.printStackTrace();
		}

		txtCliFone.setBounds(80, 217, 145, 20);
		getContentPane().add(txtCliFone);

		txtCliEmail = new JTextField();
		txtCliEmail.setBounds(80, 254, 378, 20);
		getContentPane().add(txtCliEmail);
		txtCliEmail.setColumns(10);

		btnCliCreate = new JButton("");
		btnCliCreate.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		btnCliCreate.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				adicionarCliente();
			}
		});
		btnCliCreate.setIcon(new ImageIcon(TelaClientes.class.getResource("/br/com/infomaciel/icons/create.png")));
		btnCliCreate.setBounds(141, 286, 78, 66);
		getContentPane().add(btnCliCreate);

		btnCliUpdate = new JButton("");
		btnCliUpdate.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		btnCliUpdate.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				atualizarCliente();
			}
		});
		btnCliUpdate.setIcon(new ImageIcon(TelaClientes.class.getResource("/br/com/infomaciel/icons/update.png")));
		btnCliUpdate.setBounds(260, 286, 89, 66);
		getContentPane().add(btnCliUpdate);

		btnCliDelete = new JButton("");
		btnCliDelete.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		btnCliDelete.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				deletarCliente();
				}
		});
		btnCliDelete.setIcon(new ImageIcon(TelaClientes.class.getResource("/br/com/infomaciel/icons/delete.png")));
		btnCliDelete.setBounds(383, 286, 89, 66);
		getContentPane().add(btnCliDelete);

		JLabel lblNewLabel_5 = new JLabel("ADICIONAR");
		lblNewLabel_5.setBounds(151, 352, 68, 14);
		getContentPane().add(lblNewLabel_5);

		JLabel lblNewLabel_6 = new JLabel("EDITAR");
		lblNewLabel_6.setBounds(282, 352, 46, 14);
		getContentPane().add(lblNewLabel_6);

		JLabel lblNewLabel_7 = new JLabel("REMOVER");
		lblNewLabel_7.setBounds(405, 352, 67, 14);
		getContentPane().add(lblNewLabel_7);

		txtCliPesquisar = new JTextField();
		txtCliPesquisar.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent e) {
				pesquisarCliente();
			}
		});
		txtCliPesquisar.setBounds(10, 11, 311, 20);
		getContentPane().add(txtCliPesquisar);
		txtCliPesquisar.setColumns(10);

		JLabel lblNewLabel_8 = new JLabel("");
		lblNewLabel_8.setIcon(new ImageIcon(TelaClientes.class.getResource("/br/com/infomaciel/icons/find.png")));
		lblNewLabel_8.setBounds(322, 11, 32, 20);
		getContentPane().add(lblNewLabel_8);

		DefaultTableModel model = new DefaultTableModel() {
			/**
			 *
			 */
			private static final long serialVersionUID = 1L;

			@Override
			public boolean isCellEditable(int rowIndex, int columnIndex) {
				return false; // Torna todas as celulas nao editaveis
			}
		};
		// evento usado para setar os campos na tabela (com click do mouse)
		tblClientes = new JTable(model) {
			/**
			 *
			 */
			private static final long serialVersionUID = 1L;

			@Override
			public boolean isCellEditable(int row, int column) {
				return false; // Torna todas as celulas nao editaveis
			}
		};
		tblClientes.setFocusable(false);
		tblClientes.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
		tblClientes.setUpdateSelectionOnSort(false);
		tblClientes.setModel(new DefaultTableModel(new Object[][] {},
				new String[] { "ID", "NOME", "FONE", "EMAIL", "ENDERE\u00C7O" }));
		tblClientes.getColumnModel().getColumn(0).setPreferredWidth(41);
		tblClientes.getColumnModel().getColumn(1).setPreferredWidth(183);
		tblClientes.getColumnModel().getColumn(2).setPreferredWidth(123);
		tblClientes.getColumnModel().getColumn(3).setPreferredWidth(118);
		tblClientes.getColumnModel().getColumn(4).setPreferredWidth(158);

		tblClientes.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				int setar = tblClientes.getSelectedRow();
				try {
					if (setar >= 0) {
						TableModel model = tblClientes.getModel();
						txtCliId.setText(model.getValueAt(setar, 0).toString());
						txtCliNome.setText(model.getValueAt(setar, 1).toString());
						txtCliEnd.setText(model.getValueAt(setar, 2).toString());
						txtCliFone.setText(model.getValueAt(setar, 3).toString());
						txtCliEmail.setText(model.getValueAt(setar, 4).toString());
						btnCliCreate.setEnabled(false);

					} else {
						LimparCamposUtil.limparCamposClId(txtCliPesquisar, txtCliId, txtCliNome, txtCliFone, txtCliEnd,
								txtCliEmail);
					}
				} catch (java.lang.ArrayIndexOutOfBoundsException e1) {
					 e1.printStackTrace();

				}
			}
		});

		tblClientes.setFocusable(false);
		tblClientes.setAutoCreateRowSorter(true);
		tblClientes.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
		tblClientes.setFillsViewportHeight(true);
		tblClientes.setBounds(10, 39, 572, 53);
		getContentPane().add(tblClientes);
		// a linha abaixo desabilita o botao adicionar quando for cliente for setado

		// Criar um JScrollPane e adicionar a tabela a ele
		JScrollPane scrollPane = new JScrollPane(tblClientes);

		// Definir as dimensões e a posição do JScrollPane
		scrollPane.setBounds(10, 39, 572, 83);

		// Adicionar o JScrollPane ao conteudo do conteiner ou painel adequado
		getContentPane().add(scrollPane);

		txtCliId = new JTextField();
		txtCliId.setEnabled(false);
		txtCliId.setBounds(79, 124, 86, 20);
		getContentPane().add(txtCliId);
		txtCliId.setColumns(10);

		JLabel lblNewLabel_9 = new JLabel("  Id Cliente");
		lblNewLabel_9.setBounds(10, 127, 68, 14);
		getContentPane().add(lblNewLabel_9);

		JButton btnRefresh = new JButton("");
		btnRefresh.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				LimparCamposUtil.limparCamposClId(txtCliPesquisar, txtCliId, txtCliNome, txtCliEnd, txtCliFone, txtCliEmail);
				DefaultTableModel model = (DefaultTableModel) tblClientes.getModel();
				btnCliCreate.setEnabled(true);
				model.setRowCount(0);
			}
		});
		btnRefresh.setIcon(new ImageIcon(TelaClientes.class.getResource("/br/com/infomaciel/icons/refresh.png")));
		btnRefresh.setBounds(366, 11, 46, 20);
		getContentPane().add(btnRefresh);

	}

	/**
	 *
	 * Cria um novo cliente no banco de dados.
	 */
	public void adicionarCliente() {
		conexao = ConexaoDao.getConnection();
		String sql = "INSERT INTO tbclient(namecli, addcli, phonecli, emailcli) VALUES(?,?,?,?)";

		try {
			pst = conexao.prepareStatement(sql);
			pst.setString(1, txtCliNome.getText());
			pst.setString(2, txtCliEnd.getText());
			pst.setString(3, txtCliFone.getText());
			pst.setString(4, txtCliEmail.getText());

			/*
			 * .trim(): É um método da classe String que remove os espaços em branco no
			 * início e no final de uma string. Ele retorna uma nova string sem os espaços
			 * em branco adicionais.
			 * .replaceAll() é um método da classe String. Ele é usado
			 * para substituir todas as ocorrências de uma determinada sequência de
			 * caracteres por outra sequência especificada.
			 */

			if (txtCliNome.getText().isEmpty()) {
				JOptionPane.showMessageDialog(null, "Preencher todos os campos obrigatórios!");
			} else if (txtCliFone.getText().replaceAll("[^0-9]", "").isEmpty()) {
				JOptionPane.showMessageDialog(null, "Preencher o FONE corretamente!");
			} else {
				pst.executeUpdate();
				LimparCamposUtil.limparCamposCl(txtCliNome, txtCliEnd, txtCliFone, txtCliEmail);
				JOptionPane.showMessageDialog(null, "Cliente adicionado com sucesso!");
			}

		} catch (Exception e2) {
			JOptionPane.showMessageDialog(null, e2);
		} finally {
			if (conexao != null) {
				try {
					conexao.close();
				} catch (SQLException e2) {
					JOptionPane.showMessageDialog(null,
							"Erro ao fechar a conexão com o banco de dados: " + e2.getMessage());
				}
			}
		}
		// Adicionar o FocusListener para limpar o campo txtCliFone quando receber o
		// foco
		txtCliFone.addFocusListener(new FocusListener() {
			@Override
			public void focusGained(FocusEvent e) {
				txtCliFone.setText(null);
			}

			@Override
			public void focusLost(FocusEvent e) {
				// Não é necessário fazer nada quando o campo perde o foco
			}
		});
	}

	/**
	 *
	 * Atualiza as informacoes de um cliente existente.
	 */
	public void atualizarCliente() {
		conexao = ConexaoDao.getConnection();
		String sql = "UPDATE tbclient SET namecli=?, addcli=?, phonecli=?, emailcli=? WHERE idcli=?";
		try {
			pst = conexao.prepareStatement(sql);
			pst.setString(1, txtCliNome.getText());
			pst.setString(2, txtCliEnd.getText());
			pst.setString(3, txtCliFone.getText());
			pst.setString(4, txtCliEmail.getText());
			pst.setString(5, txtCliId.getText());

			// Verificar se todos os campos obrigatórios estão preenchidos
			if (txtCliNome.getText().isEmpty()) {
				JOptionPane.showMessageDialog(null, "Preencher todos os campos obrigatórios!");
			} else if (txtCliFone.getText().replaceAll("[^0-9]", "").isEmpty()) {
				JOptionPane.showMessageDialog(null, "Preencher o FONE corretamente!");
			} else {
				pst.executeUpdate();
				LimparCamposUtil.limparCamposCl(txtCliNome, txtCliEnd, txtCliFone, txtCliEmail);
				JOptionPane.showMessageDialog(null, "Cliente adicionado com sucesso!");
			}

		} catch (Exception e2) {
			JOptionPane.showMessageDialog(null, e2);
		} finally {
			if (conexao != null) {
				try {
					conexao.close();
				} catch (SQLException e2) {
					JOptionPane.showMessageDialog(null,
							"Erro ao fechar a conexão com o banco de dados: " + e2.getMessage());
				}
			}
		}
		// Adicionar o FocusListener para limpar o campo txtCliFone quando receber o
		// foco
		txtCliFone.addFocusListener(new FocusListener() {
			@Override
			public void focusGained(FocusEvent e) {
				txtCliFone.setText(null);
			}

			@Override
			public void focusLost(FocusEvent e) {
				// Não é necessário fazer nada quando o campo perde o foco
			}
		});
	}

	/**
	 * Deleta um cliente do banco de dados. Antes de realizar a exclusao, exibe uma
	 * mensagem de confirmacao para o usuario.
	 *
	 */
	public void deletarCliente() {
		conexao = ConexaoDao.getConnection();
		int remover = JOptionPane.showConfirmDialog(null, "Tem certeza que deseja remover este cliente?", "Atenção",
				JOptionPane.YES_NO_OPTION);
		if (remover == JOptionPane.YES_OPTION) {
			String sql = "DELETE FROM tbclient WHERE idcli=?";
			try {
				pst = conexao.prepareStatement(sql);
				pst.setString(1, txtCliId.getText());
				pst.executeUpdate();
				btnCliCreate.setEnabled(true);
				((DefaultTableModel) tblClientes.getModel()).setRowCount(0);
				LimparCamposUtil.limparCamposClId(txtCliPesquisar, txtCliId, txtCliNome, txtCliFone, txtCliEnd,
						txtCliEmail);
				JOptionPane.showMessageDialog(null, "Cliente removido com sucesso!", "Sucesso",
						JOptionPane.INFORMATION_MESSAGE);
			} catch (Exception erro) {
				JOptionPane.showMessageDialog(null, erro);
			}
		}
	}

	/**
	 * Realiza a pesquisa de clientes com base no nome do cliente.
	 *
	 */
	public void pesquisarCliente() {
		conexao = ConexaoDao.getConnection();
		String sql = "SELECT idcli AS ID, namecli AS NOME, addcli AS ENDEREÇO, phonecli AS FONE, emailcli AS EMAIL FROM tbclient WHERE namecli LIKE ?";
		try {
			pst = conexao.prepareStatement(sql);
			pst.setString(1, txtCliPesquisar.getText() + "%");
			rs = pst.executeQuery();
			tblClientes.setModel(DbUtils.resultSetToTableModel(rs));
		} catch (Exception e2) {
			JOptionPane.showMessageDialog(null, e2);
		}

		// Adicionar o FocusListener para limpar o campo txtCliFone quando receber o
		// foco
		txtCliFone.addFocusListener(new FocusListener() {
			@Override
			public void focusGained(FocusEvent e) {
				txtCliFone.setText(null);
			}

			@Override
			public void focusLost(FocusEvent e) {
				// Não é necessário fazer nada quando o campo perde o foco
			}
		});
	}
}
