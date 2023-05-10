package br.com.infomaciel.screens;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JInternalFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

import br.com.infomaciel.dal.ConexaoDao;

public class TelaTrocaSenha extends JInternalFrame {
    /**
	 * 
	 */
	private static final long serialVersionUID = 2L;
	private JLabel usuarioLabel;
    private JLabel senhaLabel;
    private JLabel novaSenhaLabel;
    private JLabel repetirNovaSenhaLabel;
    private JTextField usuarioField;
    private JPasswordField senhaField;
    private JPasswordField novaSenhaField;
    private JPasswordField repetirNovaSenhaField;
    private JCheckBox visualizarSenhaCheckbox;
    private JButton confirmarButton;
    private JButton cancelarButton;

    public TelaTrocaSenha() {
        // Defina o título, tamanho e layout do JInternalFrame
        super("Opções / Trocar Senha", false, true, true, true);
        setSize(400, 300);
        getContentPane().setLayout(new GridLayout(6, 2));
       

        // Inicialize os campos de texto, senhas, botões e checkbox
        usuarioLabel = new JLabel("Usuário:");
        senhaLabel = new JLabel("Senha:");
        novaSenhaLabel = new JLabel("Nova senha:");
        repetirNovaSenhaLabel = new JLabel("Repetir nova senha:");
        usuarioField = new JTextField();
        senhaField = new JPasswordField();
        novaSenhaField = new JPasswordField();
        repetirNovaSenhaField = new JPasswordField();
        visualizarSenhaCheckbox = new JCheckBox("Visualizar senha");
        confirmarButton = new JButton("Confirmar");
        cancelarButton = new JButton("Cancelar");
        
        // Adicione os componentes na tela
        getContentPane().add(usuarioLabel);
        getContentPane().add(usuarioField);
        getContentPane().add(senhaLabel);
        getContentPane().add(senhaField);
        getContentPane().add(novaSenhaLabel);
        getContentPane().add(novaSenhaField);
        getContentPane().add(repetirNovaSenhaLabel);
        getContentPane().add(repetirNovaSenhaField);
        getContentPane().add(visualizarSenhaCheckbox);
        getContentPane().add(new JLabel()); // espaço em branco
        getContentPane().add(confirmarButton);
        getContentPane().add(cancelarButton);
        
        visualizarSenhaCheckbox.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                senhaField.setEchoChar(
                    visualizarSenhaCheckbox.isSelected() ? '\0' : '*');
                novaSenhaField.setEchoChar(
                    visualizarSenhaCheckbox.isSelected() ? '\0' : '*');
                repetirNovaSenhaField.setEchoChar(
                    visualizarSenhaCheckbox.isSelected() ? '\0' : '*');
            }
        });

        // Adicione um listener ao botão de confirmar
        confirmarButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String usuario = usuarioField.getText();
                String senhaAntiga = new String(senhaField.getPassword());
                String novaSenha = new String(novaSenhaField.getPassword());
                String repetirNovaSenha = new String(repetirNovaSenhaField.getPassword());

                // Verifica se os campos foram preenchidos corretamente
                if (usuario.isEmpty() || senhaAntiga.isEmpty() ||
                        novaSenha.isEmpty() || repetirNovaSenha.isEmpty()) {
                    JOptionPane.showMessageDialog(null,
                        "Preencha todos os campos!", "Erro", JOptionPane.ERROR_MESSAGE);
                    return;
                }
                if (!novaSenha.equals(repetirNovaSenha)) {
                    JOptionPane.showMessageDialog(null,
                        "As senhas não coincidem!", "Erro", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                try {
                    Connection conn = ConexaoDao.getConnection();
                    PreparedStatement stmt = conn.prepareStatement(
                        "SELECT * FROM tbuser WHERE login = ? AND password = ?");
                    stmt.setString(1, usuario);
                    stmt.setString(2, senhaAntiga);
                    ResultSet rs = stmt.executeQuery();

                    if (rs.next()) {
                        // Atualiza a senha na tabela tbuser
                        PreparedStatement updateStmt = conn.prepareStatement(
                            "UPDATE tbuser SET password = ? WHERE login = ?");
                        updateStmt.setString(1, novaSenha);
                        updateStmt.setString(2, usuario);
                        updateStmt.executeUpdate();

                        JOptionPane.showMessageDialog(null,
                            "Senha alterada com sucesso!", "Sucesso", JOptionPane.INFORMATION_MESSAGE);
                        dispose(); // fecha o JInternalFrame
                    } else {
                        JOptionPane.showMessageDialog(null,
                            "Usuário ou senha incorretos!", "Erro", JOptionPane.ERROR_MESSAGE);
                    }

                    rs.close();
                    stmt.close();
                    conn.close();
                } catch (SQLException ex) {
                    JOptionPane.showMessageDialog(null,
                        "Erro ao acessar o banco de dados:\n" + ex.getMessage(),
                        "Erro", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        // ...
    }
} 
