package br.com.infomaciel.screens;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.SwingUtilities;

public class TrocarSenhaFrame extends JFrame {
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JPasswordField txtSenhaNova;
    private JPasswordField txtRepetirSenha;
    private JCheckBox chkVisualizarSenha;

    public TrocarSenhaFrame() {
        setTitle("Trocar Senha");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(300, 200);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        JPanel panel = new JPanel(new GridLayout(3, 2, 10, 10));

        JLabel lblSenhaNova = new JLabel("Senha Nova:");
        JLabel lblRepetirSenha = new JLabel("Repetir Senha:");
        txtSenhaNova = new JPasswordField();
        txtRepetirSenha = new JPasswordField();
        chkVisualizarSenha = new JCheckBox("Visualizar Senha");

        // Configurar a opção de visualização de senha
        chkVisualizarSenha.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JCheckBox checkBox = (JCheckBox) e.getSource();
                if (checkBox.isSelected()) {
                    txtSenhaNova.setEchoChar((char) 0);
                    txtRepetirSenha.setEchoChar((char) 0);
                } else {
                    txtSenhaNova.setEchoChar('\u2022');
                    txtRepetirSenha.setEchoChar('\u2022');
                }
            }
        });

        panel.add(lblSenhaNova);
        panel.add(txtSenhaNova);
        panel.add(lblRepetirSenha);
        panel.add(txtRepetirSenha);
        panel.add(chkVisualizarSenha);

        JButton btnTrocarSenha = new JButton("Trocar Senha");
        btnTrocarSenha.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Lógica para trocar a senha
                String senhaNova = new String(txtSenhaNova.getPassword());
                String repetirSenha = new String(txtRepetirSenha.getPassword());

                if (senhaNova.equals(repetirSenha)) {
                    // Senhas coincidem, realizar a troca de senha
                    JOptionPane.showMessageDialog(null, "Senha alterada com sucesso!");
                    // Lógica para realizar a troca de senha
                } else {
                    // Senhas não coincidem, exibir mensagem de erro
                    JOptionPane.showMessageDialog(null, "As senhas digitadas não coincidem. Tente novamente.");
                    txtSenhaNova.setText("");
                    txtRepetirSenha.setText("");
                }
            }
        });

        add(panel, BorderLayout.CENTER);
        add(btnTrocarSenha, BorderLayout.SOUTH);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                TrocarSenhaFrame frame = new TrocarSenhaFrame();
                frame.setVisible(true);
            }
        });
    }
}
