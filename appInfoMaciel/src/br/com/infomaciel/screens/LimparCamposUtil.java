package br.com.infomaciel.screens;

import javax.swing.JComboBox;
import javax.swing.JTextField;

public class LimparCamposUtil {

	public static void limparCampos(JTextField... camposTexto) {
		for (JTextField campo : camposTexto) {
			campo.setText("");
		}
	}

	public static void limparCamposUs(JTextField txtUsuNome, JTextField txtUsuFone, JTextField txtUsuLogin, JTextField txtUsuSenha, JComboBox<?> comboBox) {
        limparCampos(txtUsuNome, txtUsuFone, txtUsuLogin, txtUsuSenha);
        comboBox.setSelectedItem("");
	}

	public static void limparCamposUsId(JTextField txtUsuId, JTextField txtUsuNome, JTextField txtUsuFone,
			JTextField txtUsuLogin, JTextField txtUsuSenha, JComboBox<?> comboBox) {
		limparCampos(txtUsuId, txtUsuNome, txtUsuFone, txtUsuLogin, txtUsuSenha);
		comboBox.setSelectedItem("");
	}
	
	public static void limparCamposCl(JTextField txtCliNome, JTextField txtCliEnd, JTextField txtCliFone,
			JTextField txtCliEmail) {
		limparCampos(txtCliNome, txtCliEnd, txtCliFone, txtCliEmail);
	}
}
