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

import br.com.infomaciel.dao.ConexaoDao;

/**
 *
 * A classe TelaUsuarios a uma janela interna que exibe a interface grafica para
 * gerenciamento de usuarios. Ela herda funcionalidades da classe JInternalFrame
 * e permite a interacaoo com o banco de dados para realizar operacoes
 * relacionadas aos usuarios, como criar, editar, pesquisar e excluir.
 */
public class TelaUsuarios extends JInternalFrame {

	/**
	 * @param criarUsuario     criar Usuario
	 *
	 * @param deletarUsuario   deletar Usuario
	 *
	 * @param pesquisarUsuario pesquisar Usuario
	 *
	 * @param atualizarUsuario atualizar clientes
	 *
	 */

	/**
	 *
	 * O objeto Connection representa a conexao com o banco de dados. Ele e
	 * responsavel por estabelecer a comunicação entre a aplicacao e o banco de
	 * dados, permitindo a execucao de consultas e atualizacoes.
	 */
	Connection conexao = null;

	/**
	 *
	 * O objeto PreparedStatement representa uma instrucao SQL pre compilada que
	 * pode ser executada várias vezes com diferentes parametros. Ele e usado para
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
	 * Numero de serie para a serializacao.
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * Campo de texto para exibicao/entrada do ID do usuario.
	 */
	private JTextField txtUsuId;

	/**
	 * Campo de texto para exibicao/entrada da senha do usuario.
	 */
	private JTextField txtUsuSenha;

	/**
	 * Campo de texto para exibicao/entrada do login do usuario.
	 */
	private JTextField txtUsuLogin;

	/**
	 * Campo de texto para exibicao/entrada do nome do usuario.
	 */
	private JTextField txtUsuNome;

	/**
	 * Campo de texto formatado para exibicao/entrada do telefone do usuario.
	 */
	private JFormattedTextField txtUsuFone;

	/**
	 * ComboBox para selecao do perfil do usuario.
	 */
	private JComboBox<String> cboUsuPerfil;

	/**
	Metodo principal responsavel por iniciar a aplicacao.
	@param args os argumentos de linha de comando
	*/
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			@Override
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
		setTitle("Usuários / Cadastros");
		setSize(608, 407);
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

		cboUsuPerfil = new JComboBox<>();
		cboUsuPerfil.setModel(new DefaultComboBoxModel<>(new String[] { "", "usuario", "tecnico", "admin" }));
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

		// metodo para adicionar usuarios
		JButton btnUsuCreate = new JButton("");
		btnUsuCreate.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				criarUsuario();
			}
		});
		btnUsuCreate.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		btnUsuCreate.setToolTipText("Criar");
		btnUsuCreate.setIcon(new ImageIcon(TelaUsuarios.class.getResource("/br/com/infomaciel/icons/create.png")));
		btnUsuCreate.setPreferredSize(new Dimension(65, 65));
		btnUsuCreate.setBounds(117, 242, 89, 73);
		getContentPane().add(btnUsuCreate);

		// metodo para remover usuario
		JButton btnUsuDelete = new JButton("");
		btnUsuDelete.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				deletarUsuario();
			}
		});
		btnUsuDelete.setToolTipText("Apagar");
		btnUsuDelete.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		btnUsuDelete.setIcon(new ImageIcon(TelaUsuarios.class.getResource("/br/com/infomaciel/icons/delete.png")));
		btnUsuDelete.setPreferredSize(new Dimension(65, 65));
		btnUsuDelete.setBounds(414, 242, 89, 73);
		getContentPane().add(btnUsuDelete);

		// metodo para consultar usuario
		JButton btnUsuRead = new JButton("");
		btnUsuRead.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				pesquisarUsuario();
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
			@Override
			public void actionPerformed(ActionEvent e) {
				atualizarUsuario();
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

	/**
	 * Metodo responsavel por criar um usuario.
	 */
	public void criarUsuario() {
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
			// validacaoo dos campos obrigatorios
			if ((txtUsuId.getText().isEmpty()) || (txtUsuNome.getText().isEmpty()) || (txtUsuLogin.getText().isEmpty())
					|| (txtUsuSenha.getText().isEmpty())) {
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

	/**
	 * Metodo responsavel por deletar um usuario.
	 */
	public void deletarUsuario() {
		// estrutura abaixo confirma a remoção do usuario.
		int remover = JOptionPane.showConfirmDialog(null, "tem certeza que deseja remover este usuário?", "Atenção",
				JOptionPane.YES_NO_OPTION);
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

	/**
	 * Metodo responsavel por pesquisar um usuario.
	 */
	public void pesquisarUsuario() {
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

	/**
	 * Metodo responsavel por atualizar um usuario.
	 */
	public void atualizarUsuario() {
		conexao = ConexaoDao.getConnection();
		// sempre prestar muito atencao na seguencia para passagem dos parametros
		String sql = "update tbuser set user=?,login=?,password=?,perfil=?,phone=? where iduser=?";
		try {

			pst = conexao.prepareStatement(sql);
			pst.setString(1, txtUsuNome.getText());
			pst.setString(2, txtUsuLogin.getText());
			pst.setString(3, txtUsuSenha.getText());
			pst.setString(4, cboUsuPerfil.getSelectedItem().toString());
			pst.setString(5, txtUsuFone.getText());
			pst.setString(6, txtUsuId.getText());

			if ((txtUsuId.getText().isEmpty()) || (txtUsuNome.getText().isEmpty()) || (txtUsuLogin.getText().isEmpty())
					|| (txtUsuSenha.getText().isEmpty())) {
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
}