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
   
	// usando a variavel conexao do DAL
		Connection conexao = null;
		/*
		 * criando variaveis especiais para conexao com o banco PreparedStatement e
		 * ResultSet são frameworks do pacote java.sql* e servem para preparar e
		 * executar as intruções sql
		 */

		PreparedStatement pst = null;
		ResultSet rs = null;
	
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
    
    String nomeUsuario = TelaPrincipal.getNomeUsuario();
    String perfil = TelaLogin.getPerfil();
    

    public TelaTrocaSenha() {
        // Defina o título, tamanho e layout do JInternalFrame
        super("Mudar Senha / Opções", false, true, true, true);
        setSize(400, 300);
        getContentPane().setLayout(new GridLayout(6, 2));
       
       System.out.println(perfil);
        // Inicialize os campos de texto, senhas, botões e checkbox
        usuarioLabel = new JLabel("Usuário:");
        senhaLabel = new JLabel("Senha:");
        novaSenhaLabel = new JLabel("Nova senha:");
        repetirNovaSenhaLabel = new JLabel("Repetir nova senha:");
        usuarioField = new JTextField();
        usuarioField.setEditable(true);
        usuarioField.setEnabled(true);
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

        // metodo para botão de confirmar trocar senha
        confirmarButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
            	conexao = ConexaoDao.getConnection();
                
            	String usuario = usuarioField.getText();
                String senhaAntiga = new String(senhaField.getPassword());
                String novaSenha = new String(novaSenhaField.getPassword());
                String repetirNovaSenha = new String(repetirNovaSenhaField.getPassword());

                // Verifica se os campos foram preenchidos corretamente
                if (usuario.isEmpty() || senhaAntiga.isEmpty() ||
                        novaSenha.isEmpty() || repetirNovaSenha.isEmpty()) {
                    JOptionPane.showMessageDialog(null,
                        "Preencha todos os campos!", "Atenção", JOptionPane.ERROR_MESSAGE);
                    return;
                }
                if (!novaSenha.equals(repetirNovaSenha)) {
                    JOptionPane.showMessageDialog(null,
                        "As senhas não coincidem!", "Atenção", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                try {
                     conexao = ConexaoDao.getConnection();
                    PreparedStatement pst = conexao.prepareStatement(
                        "SELECT * FROM tbuser WHERE login = ? AND password = ?");
                    
                    pst.setString(1, usuario);
                    pst.setString(2, senhaAntiga);
                    ResultSet rs = pst.executeQuery();

                    if (rs.next()) {
                        // Atualiza a senha na tabela tbuser
                        PreparedStatement updatepst = conexao.prepareStatement(
                            "UPDATE tbuser SET password = ? WHERE login = ?");
                        updatepst.setString(1, novaSenha);
                        updatepst.setString(2, usuario);
                        updatepst.executeUpdate();

                        JOptionPane.showMessageDialog(null,
                            "Senha alterada com sucesso!", "Sucesso", JOptionPane.INFORMATION_MESSAGE);
                        dispose(); // fecha o JInternalFrame
                    } else {
                        JOptionPane.showMessageDialog(null,
                            "Usuário ou senha incorretos!", "Erro", JOptionPane.ERROR_MESSAGE);
                    }

                    rs.close();
                    pst.close();
                    conexao.close();
                } catch (SQLException ex) {
                    JOptionPane.showMessageDialog(null,
                        "Erro ao acessar o banco de dados:\n" + ex.getMessage(),
                        "Erro", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

    }
} 
