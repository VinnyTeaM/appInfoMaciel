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
		
	public static void limparCamposClId(JTextField txtCliPesquisar,JTextField txtCliId, JTextField txtCliNome, JTextField txtCliEnd, JTextField txtCliFone,
				JTextField txtCliEmail) {
			limparCampos(txtCliPesquisar, txtCliId, txtCliNome, txtCliEnd, txtCliFone, txtCliEmail);
	}
	
	public static void limparCamposOs(JTextField txtCliId,JTextField txtOsEquip, JTextField txtOsDef, JTextField txtOsServ, JTextField txtOsTec, JTextField txtOsValor, JTextField txtCliPesquisar) {
		limparCampos(txtCliId, txtOsEquip, txtOsDef, txtOsServ, txtOsTec, txtOsEquip, txtOsValor,txtCliPesquisar);
				
	}
	
}

