package br.com.infomaciel.screens;

import java.awt.Color;
import java.awt.Dialog.ModalExclusionType;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;
import java.sql.Connection;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.swing.DebugGraphics;
import javax.swing.ImageIcon;
import javax.swing.JDesktopPane;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.KeyStroke;
import javax.swing.Timer;
import javax.swing.border.EmptyBorder;

import br.com.infomaciel.dal.ConexaoDao;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.view.JasperViewer;

public class TelaPrincipal extends JFrame {

	Connection conexao = null;

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	public static JMenuItem menCadUsu;
	public static JMenu menRel;
	public static JLabel lblUsuario;
	public static JDesktopPane desktop;

	/**
	 * Launch the application.
	 */

	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					TelaPrincipal frame = new TelaPrincipal();
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
	public TelaPrincipal() {
		conexao = ConexaoDao.getConnection();

		setModalExclusionType(ModalExclusionType.APPLICATION_EXCLUDE);
		setResizable(false);
		setTitle("-*Informática Maciel*- TELA PRINCIPAL");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 845, 506);
		contentPane = new JPanel();
		contentPane.setFont(new Font("Tahoma", Font.BOLD, 11));
		contentPane.setForeground(Color.YELLOW);
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setLocationRelativeTo(null);
		ImageIcon icone = new ImageIcon(getClass().getResource("/br/com/infomaciel/icons/login.png"));
		setIconImage(icone.getImage());

		setContentPane(contentPane);
		contentPane.setLayout(null);

		JMenuBar menu = new JMenuBar();
		menu.setForeground(Color.BLUE);
		menu.setBounds(0, 0, 829, 22);
		contentPane.add(menu);

		JMenu menCad = new JMenu("Cadastros");
		menCad.setFont(new Font("Segoe UI", Font.BOLD, 12));
		menu.add(menCad);

		JMenuItem menCadCli = new JMenuItem("Cliente");
		menCadCli.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				TelaClientes clientes = new TelaClientes();
				clientes.setVisible(true);
				desktop.add(clientes);
			}
		});
		menCadCli.setFont(new Font("Segoe UI", Font.BOLD, 12));
		menCadCli.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_C, InputEvent.ALT_DOWN_MASK));
		menCad.add(menCadCli);

		// botao chama tela OS
		JMenuItem menCadOs = new JMenuItem("OS");
		menCadOs.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				TelaOs os = new TelaOs();
				os.setVisible(true);
				desktop.add(os);
			}
		});
		menCadOs.setFont(new Font("Segoe UI", Font.BOLD, 12));
		menCadOs.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_O, InputEvent.ALT_DOWN_MASK));
		menCad.add(menCadOs);

		// botao chama tela Usuários
		menCadUsu = new JMenuItem("Usuários");
		menCadUsu.addActionListener(new ActionListener() {
			// abrir o form TelaUsuario dentro desktop pane
			public void actionPerformed(ActionEvent e) {
				TelaUsuarios usuarios = new TelaUsuarios();
				usuarios.setVisible(true);
				desktop.add(usuarios);
			}
		});
		menCadUsu.setFont(new Font("Segoe UI", Font.BOLD, 12));
		menCadUsu.setEnabled(false);
		menCadUsu.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_U, InputEvent.ALT_DOWN_MASK));
		menCad.add(menCadUsu);

		menRel = new JMenu("Relatórios");
		menRel.setFont(new Font("Segoe UI", Font.BOLD, 12));
		menRel.setDebugGraphicsOptions(DebugGraphics.NONE_OPTION);
		menRel.setEnabled(false);
		menu.add(menRel);

		// gerando um relatorio de clientes
		JMenuItem memRelCli = new JMenuItem("Clientes");
		memRelCli.addActionListener(new ActionListener() {
		    public void actionPerformed(ActionEvent e) {
		        int confirma = JOptionPane.showConfirmDialog(null, "Confirma a emissão desse relatório?", "Atenção",
		                JOptionPane.YES_NO_OPTION);
		        if (confirma == JOptionPane.YES_OPTION) {
		            // Imprimir relatório com o framework Jasper
		            try {
		                // Reestabelecer a conexão com o banco de dados
		                conexao = ConexaoDao.getConnection();

		                // Preencher o relatório
		                JasperPrint print = JasperFillManager.fillReport(
		                        "C:\\Users\\avinn\\OneDrive\\sistema os\\relatorios\\clientes.jasper", null, conexao);

		                // Exibir o relatório
		                JasperViewer.viewReport(print, false);
		                conexao.close();

		            } catch (Exception e2) {
		                JOptionPane.showMessageDialog(null, "Erro ao emitir relatório: " + e2.getMessage());
		                e2.printStackTrace();
		            }
		        }
		    }
		});


		memRelCli.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_R, InputEvent.ALT_DOWN_MASK));
		memRelCli.setFont(new Font("Segoe UI", Font.BOLD, 12));
		menRel.add(memRelCli);

		//GERA RELATORIO DE SERVIÇOS
		JMenuItem menRelSer = new JMenuItem("Serviços");
		menRelSer.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
		        int confirma = JOptionPane.showConfirmDialog(null, "Confirma a emissão desse relatório?", "Atenção",
		                JOptionPane.YES_NO_OPTION);
		        if (confirma == JOptionPane.YES_OPTION) {
		            // Imprimir relatório com o framework Jasper
		            try {
		                // Reestabelecer a conexão com o banco de dados
		                conexao = ConexaoDao.getConnection();

		                // Preencher o relatório
		                JasperPrint print = JasperFillManager.fillReport(
		                        "C:\\Users\\avinn\\JaspersoftWorkspace\\infomaciel\\servicos.jasper", null, conexao);

		                // Exibir o relatório
		                JasperViewer.viewReport(print, false);
		                conexao.close();

		            } catch (Exception e1) {
		                JOptionPane.showMessageDialog(null, "Erro ao emitir relatório: " + e1.getMessage());
		                e1.printStackTrace();
		            }
		        }
		    }
		});
	
		menRelSer.setFont(new Font("Segoe UI", Font.BOLD, 12));
		menRelSer.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_S, InputEvent.ALT_DOWN_MASK));
		menRel.add(menRelSer);

		JMenu menAju = new JMenu("Ajuda");
		menAju.setFont(new Font("Segoe UI", Font.BOLD, 12));
		menu.add(menAju);

		JMenuItem menAjuSob = new JMenuItem("Sobre");
		menAjuSob.setFont(new Font("Segoe UI", Font.BOLD, 12));
		menAjuSob.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// chamando a tela sobre
				About sobre = new About();
				sobre.setVisible(true);
			}
		});
		menAjuSob.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_F1, InputEvent.ALT_DOWN_MASK));
		menAju.add(menAjuSob);

		JMenu menOpc = new JMenu("Opções");
		menOpc.setFont(new Font("Segoe UI", Font.BOLD, 12));
		menu.add(menOpc);

		JMenuItem menOpcSai = new JMenuItem("Sair");
		menOpcSai.setFont(new Font("Segoe UI", Font.BOLD, 12));
		menOpcSai.addActionListener(new ActionListener() {
			// exibir uma caixa de dialogo sim ou não
			public void actionPerformed(ActionEvent e) {
				int sair = JOptionPane.showConfirmDialog(null, "tem certeza que deseja sair?", "Atenção",
						JOptionPane.YES_NO_OPTION);
				if (sair == JOptionPane.YES_OPTION) {
					System.exit(0);
				}
			}
		});
		menOpcSai.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_F4, InputEvent.ALT_DOWN_MASK));
		menOpc.add(menOpcSai);

		JMenuItem menOpcSenha = new JMenuItem("Alterar Senha");
		menOpcSenha.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				TelaTrocaSenha telaTrocaSenha = new TelaTrocaSenha();
				telaTrocaSenha.setVisible(true);
				desktop.add(telaTrocaSenha);

			}
		});
		menOpcSenha.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_P, InputEvent.ALT_DOWN_MASK));
		menOpcSenha.setFont(new Font("Segoe UI", Font.BOLD, 12));
		menOpc.add(menOpcSenha);

		desktop = new JDesktopPane();
		desktop.setLocation(0, 22);
		desktop.setBackground(new Color(102, 102, 255));
		desktop.setSize(600, 430);
		contentPane.add(desktop);
		desktop.setLayout(null);

		JLabel lblNewLabel = new JLabel("New label");
		lblNewLabel
				.setIcon(new ImageIcon(TelaPrincipal.class.getResource("/br/com/infomaciel/icons/telaprincipal.png")));
		lblNewLabel.setBounds(600, 208, 215, 232);
		contentPane.add(lblNewLabel);

		lblUsuario = new JLabel("\t\tUsuário");
		lblUsuario.setFont(new Font("Tahoma", Font.BOLD, 18));
		lblUsuario.setBounds(614, 94, 215, 22);
		contentPane.add(lblUsuario);

		JLabel lblData = new JLabel("");
		lblData.setFont(new Font("Tahoma", Font.BOLD, 18));
		lblData.setBounds(614, 138, 215, 22);
		contentPane.add(lblData);

		// atualiza a data e hora do sistema a cada 1 segundo
		Timer timer = new Timer(1000, new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
				Date date = new Date();
				lblData.setText(dateFormat.format(date));
			}
		});
		((Timer) timer).start();

	}

	public JMenuItem getMenCadUsu() {
		return menCadUsu;
	}

	public JMenu getMenRel() {
		return menRel;
	}

	public static String getNomeUsuario() {
		return lblUsuario.getText().toString();
	}
}