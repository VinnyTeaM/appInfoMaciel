package br.com.infomaciel.screens;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
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
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumn;
import javax.swing.table.TableColumnModel;
import javax.swing.text.MaskFormatter;

import br.com.infomaciel.dal.ConexaoDao;
import net.proteanit.sql.DbUtils;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class TelaClientes extends JInternalFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = -5485932035589945770L;
	Connection conexao = null;
	PreparedStatement pst = null;
	ResultSet rs = null;

	private JTextField txtCliNome;
	private JTextField txtCliEnd;
	private JTextField txtCliFone;
	private JTextField txtCliEmail;
	private JTextField txtCliPesquisar;
	private JTable tblClientes;
	private JTextField txtCliId;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
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
		setTitle("\t\t\t\t\t                                                                    \t\t\tClientes / Cadastros");
		getContentPane().setPreferredSize(new Dimension(80, 80));
		setMaximizable(true);
		setIconifiable(true);
		setClosable(true);
		setSize(608, 430);
		getContentPane().setLayout(null);

		JLabel lblNewLabel = new JLabel("* NOME");
		lblNewLabel.setBounds(10, 145, 46, 14);
		getContentPane().add(lblNewLabel);

		JLabel lblNewLabel_1 = new JLabel("  ENDEREÇO");
		lblNewLabel_1.setBounds(10, 179, 68, 14);
		getContentPane().add(lblNewLabel_1);

		JLabel lblNewLabel_2 = new JLabel("* FONE");
		lblNewLabel_2.setBounds(10, 216, 46, 14);
		getContentPane().add(lblNewLabel_2);

		JLabel lblNewLabel_3 = new JLabel("  EMAIL");
		lblNewLabel_3.setBounds(10, 255, 55, 14);
		getContentPane().add(lblNewLabel_3);

		JLabel lblNewLabel_4 = new JLabel("* Campos obrigatorios!");
		lblNewLabel_4.setBounds(450, 14, 132, 14);
		getContentPane().add(lblNewLabel_4);

		txtCliNome = new JTextField();
		txtCliNome.setBounds(80, 142, 502, 20);
		getContentPane().add(txtCliNome);
		txtCliNome.setColumns(10);

		txtCliEnd = new JTextField();
		txtCliEnd.setBounds(80, 176, 502, 20);
		getContentPane().add(txtCliEnd);
		txtCliEnd.setColumns(10);

		txtCliFone = new JFormattedTextField();
		txtCliFone.setDocument(new OnlyNumbersDocument());

		MaskFormatter mascarafone = null;
		try {
			mascarafone = new MaskFormatter("(##)#####-####");
		} catch (ParseException e) {
			e.printStackTrace();
		}

		JFormattedTextField txtCliFone = new JFormattedTextField(mascarafone);
		getContentPane().add(txtCliFone);
		txtCliFone.setBounds(80, 213, 145, 20);


		txtCliEmail = new JTextField();
		txtCliEmail.setBounds(80, 252, 378, 20);
		getContentPane().add(txtCliEmail);
		txtCliEmail.setColumns(10);

		// metodo para adicionar clientes
		JButton btnCliCreate = new JButton("");
		btnCliCreate.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				conexao = ConexaoDao.getConnection();
				String sql = "insert into tbclient(namecli,addcli,phonecli,emailcli) values(?,?,?,?)";
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
					 */
					String texto = txtCliFone.getText().trim();
					String textoVazio = "(  )     -";
					if ((txtCliNome.getText().isEmpty()) || (texto.equals(textoVazio))) {
						JOptionPane.showMessageDialog(null, "Preecher todos os campos obrigatorios!");
					} else {
						pst.executeUpdate();
						LimparCamposUtil.limparCamposCl(txtCliNome, txtCliEnd, txtCliFone, txtCliEmail);
						JOptionPane.showMessageDialog(null, "Cliente adicionado com sucesso!");
					}

				} catch (Exception e2) {
					JOptionPane.showMessageDialog(null, e2);
				}
			}
		});
		btnCliCreate.setIcon(new ImageIcon(TelaClientes.class.getResource("/br/com/infomaciel/icons/create.png")));
		btnCliCreate.setBounds(141, 296, 78, 66);
		getContentPane().add(btnCliCreate);

		// metodo para alterar cliente
		JButton btnCliUpdate = new JButton("");
		btnCliUpdate.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				conexao = ConexaoDao.getConnection();
				int setar = tblClientes.getSelectedRow();
				// sempre prestar muito atenção na seguencia para passagem dos paramentros
				String sql = "UPDATE tbclient SET namecli=?,addcli=?,phonecli=?,emailcli=? WHERE idcli=?";
				try {

					pst = conexao.prepareStatement(sql);
					pst.setString(1, txtCliNome.getText());
					pst.setString(2, txtCliEnd.getText());
					pst.setString(3, txtCliFone.getText());
					pst.setString(4, txtCliEmail.getText());
					pst.setString(5, txtCliId.getText());

					String texto = txtCliFone.getText().trim();
					String textoVazio = "(  )     -";
					if ((txtCliNome.getText().isEmpty()) || (texto.equals(textoVazio))) {
						JOptionPane.showMessageDialog(null, "Preecher todos os campos obrigatorios!");
					} else {
						pst.executeUpdate();
						LimparCamposUtil.limparCamposCl(txtCliNome, txtCliEnd, txtCliFone, txtCliEmail);
						JOptionPane.showMessageDialog(null, "Cliente alterado com sucesso!");
					}

				} catch (Exception erro) {
					JOptionPane.showMessageDialog(null, erro);
					// System.out.println(erro);
				}
			}
		});
		btnCliUpdate.setIcon(new ImageIcon(TelaClientes.class.getResource("/br/com/infomaciel/icons/update.png")));
		btnCliUpdate.setBounds(260, 296, 89, 66);
		getContentPane().add(btnCliUpdate);

		JButton btnCliDelete = new JButton("");
		btnCliDelete.setIcon(new ImageIcon(TelaClientes.class.getResource("/br/com/infomaciel/icons/delete.png")));
		btnCliDelete.setBounds(383, 296, 89, 66);
		getContentPane().add(btnCliDelete);

		JLabel lblNewLabel_5 = new JLabel("ADICIONAR");
		lblNewLabel_5.setBounds(151, 363, 68, 14);
		getContentPane().add(lblNewLabel_5);

		JLabel lblNewLabel_6 = new JLabel("EDITAR");
		lblNewLabel_6.setBounds(282, 363, 46, 14);
		getContentPane().add(lblNewLabel_6);

		JLabel lblNewLabel_7 = new JLabel("REMOVER");
		lblNewLabel_7.setBounds(405, 363, 67, 14);
		getContentPane().add(lblNewLabel_7);

		txtCliPesquisar = new JTextField();
		txtCliPesquisar.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent e) {
				conexao = ConexaoDao.getConnection();
				String sql = "SELECT * FROM `tbclient` WHERE namecli LIKE  ?";
				try {
					pst = conexao.prepareStatement(sql);
					// passando o conteudo de pesquisa para o ?
					// atenção ao "%" continuação da string sql
					pst.setString(1, txtCliPesquisar.getText() + "%");
					rs = pst.executeQuery();
					// a linha abaixo usa a biblioteca rs2xml.jar para preencher a tabela.
					tblClientes.setModel(DbUtils.resultSetToTableModel(rs));
				} catch (Exception e2) {
					JOptionPane.showMessageDialog(null, e2);
				}

			}
		});
		txtCliPesquisar.setBounds(10, 11, 311, 20);
		getContentPane().add(txtCliPesquisar);
		txtCliPesquisar.setColumns(10);

		JLabel lblNewLabel_8 = new JLabel("");
		lblNewLabel_8.setIcon(new ImageIcon(TelaClientes.class.getResource("/br/com/infomaciel/icons/find.png")));
		lblNewLabel_8.setBounds(322, 0, 32, 37);
		getContentPane().add(lblNewLabel_8);

		// evento usado para setar os campos na tabela (com click do mouse)
		tblClientes = new JTable();
		tblClientes.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				int setar = tblClientes.getSelectedRow();
				txtCliId.setText(tblClientes.getModel().getValueAt(setar, 0).toString());
				txtCliNome.setText(tblClientes.getModel().getValueAt(setar, 1).toString());
				txtCliEnd.setText(tblClientes.getModel().getValueAt(setar, 2).toString());
				txtCliFone.setText(tblClientes.getModel().getValueAt(setar, 3).toString());
				txtCliEmail.setText(tblClientes.getModel().getValueAt(setar, 4).toString());
			}
		});
		tblClientes.setAutoCreateRowSorter(true);
		tblClientes.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
		tblClientes.setFillsViewportHeight(true);
		tblClientes.setBounds(10, 39, 572, 53);
		getContentPane().add(tblClientes);

		// Criar um JScrollPane e adicionar a tabela a ele
		JScrollPane scrollPane = new JScrollPane(tblClientes);

		// Definir as dimensões e a posição do JScrollPane
		scrollPane.setBounds(10, 39, 572, 53);

		// Adicionar o JScrollPane ao conteúdo do contêiner ou painel adequado
		getContentPane().add(scrollPane);
		
		txtCliId = new JTextField();
		txtCliId.setEnabled(false);
		txtCliId.setBounds(79, 111, 86, 20);
		getContentPane().add(txtCliId);
		txtCliId.setColumns(10);
		
		JLabel lblNewLabel_9 = new JLabel("  Id Cliente");
		lblNewLabel_9.setBounds(10, 114, 68, 14);
		getContentPane().add(lblNewLabel_9);

	}
}
