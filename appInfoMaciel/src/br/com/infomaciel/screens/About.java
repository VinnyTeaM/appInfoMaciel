package br.com.infomaciel.screens;

import java.awt.EventQueue;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;

/**
 * A classe About representa a janela de sobre do sistema.
 */
public class About extends JFrame {

	private static final long serialVersionUID = 2L;
	private JPanel Sobre;

	/**
	 * Método principal que inicia a aplicação.
	 *
	 * @param args Os argumentos da linha de comando.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					About frame = new About();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Cria a janela de ajuda/sobre.
	 */
	public About() {
		setTitle("AJUDA / SOBRE");
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 460, 244);
		setResizable(false);
		setLocationRelativeTo(null);
		ImageIcon icone = new ImageIcon(getClass().getResource("/br/com/infomaciel/icons/login.png"));
		setIconImage(icone.getImage());
		Sobre = new JPanel();
		Sobre.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(Sobre);
		Sobre.setLayout(null);

		JLabel lblNewLabel = new JLabel("Sistema para controle de ordem de serviços");
		lblNewLabel.setBounds(10, 11, 255, 14);
		Sobre.add(lblNewLabel);

		JLabel lblNewLabel_1 = new JLabel("Desenvolvido por @VinnyTeaM");
		lblNewLabel_1.setBounds(10, 36, 200, 14);
		Sobre.add(lblNewLabel_1);

		JLabel lblNewLabel_3 = new JLabel("Por fim, acredita-se que o presente trabalho cumpriu o objetivo proposto");
		lblNewLabel_3.setBounds(10, 71, 414, 14);
		Sobre.add(lblNewLabel_3);

		JLabel lblNewLabel_4 = new JLabel("de desenvolver uma aplicação que viabilize o controle ordens de serviço.");
		lblNewLabel_4.setBounds(10, 85, 414, 14);
		Sobre.add(lblNewLabel_4);
		
		JLabel lblNewLabel_2 = new JLabel("");
		lblNewLabel_2.setIcon(new ImageIcon(About.class.getResource("/br/com/infomaciel/icons/login.png")));
		lblNewLabel_2.setBounds(178, 122, 87, 64);
		Sobre.add(lblNewLabel_2);
	}
}
