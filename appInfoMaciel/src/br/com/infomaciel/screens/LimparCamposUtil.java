package br.com.infomaciel.screens;

import javax.swing.JComboBox;
import javax.swing.JTextField;

/**
 * A classe LimparCamposUtil fornece métodos estáticos para limpar campos de texto e caixas de seleção.
 */

public class LimparCamposUtil {
	
	/**
	 * Limpa os campos de texto passados como argumento.
	 *
	 * @param camposTexto Os campos de texto a serem limpos.
	 */
	
	public static void limparCampos(JTextField... camposTexto) {
		for (JTextField campo : camposTexto) {
			campo.setText("");
		}
	}
	
	/**
	 * Limpa os campos de texto e a caixa de seleção de usuário passados como argumento.
	 *
	 * @param txtUsuNome O campo de texto do nome do usuário.
	 * @param txtUsuFone O campo de texto do telefone do usuário.
	 * @param txtUsuLogin O campo de texto do login do usuário.
	 * @param txtUsuSenha O campo de texto da senha do usuário.
	 * @param comboBox A caixa de seleção do perfil do usuário.
	 */

	public static void limparCamposUs(JTextField txtUsuNome, JTextField txtUsuFone, JTextField txtUsuLogin, JTextField txtUsuSenha, JComboBox<?> comboBox) {
        limparCampos(txtUsuNome, txtUsuFone, txtUsuLogin, txtUsuSenha);
        comboBox.setSelectedItem("");
	}

	/**
	 * Limpa os campos de texto, a caixa de seleção de usuário e o campo de ID do usuário passados como argumento.
	 *
	 * @param txtUsuId O campo de texto do ID do usuário.
	 * @param txtUsuNome O campo de texto do nome do usuário.
	 * @param txtUsuFone O campo de texto do telefone do usuário.
	 * @param txtUsuLogin O campo de texto do login do usuário.
	 * @param txtUsuSenha O campo de texto da senha do usuário.
	 * @param comboBox A caixa de seleção do perfil do usuário.
	 */
	
	public static void limparCamposUsId(JTextField txtUsuId, JTextField txtUsuNome, JTextField txtUsuFone,
			JTextField txtUsuLogin, JTextField txtUsuSenha, JComboBox<?> comboBox) {
		limparCampos(txtUsuId, txtUsuNome, txtUsuFone, txtUsuLogin, txtUsuSenha);
		comboBox.setSelectedItem("");
	}
	
	/**
	 * Limpa os campos de texto do cliente passados como argumento.
	 *
	 * @param txtCliNome O campo de texto do nome do cliente.
	 * @param txtCliEnd O campo de texto do endereço do cliente.
	 * @param txtCliFone O campo de texto do telefone do cliente.
	 * @param txtCliEmail O campo de texto do e-mail do cliente.
	 */
	
	public static void limparCamposCl(JTextField txtCliNome, JTextField txtCliEnd, JTextField txtCliFone,
			JTextField txtCliEmail) {
		limparCampos(txtCliNome, txtCliEnd, txtCliFone, txtCliEmail);
	}
		
	/**
	 * Limpa os campos de texto, a caixa de pesquisa e o campo de ID do cliente passados como argumento.
	 *
	 * @param txtCliPesquisar O campo de pesquisa do cliente.
	 * @param txtCliId O campo de texto do ID do cliente.
	 * @param txtCliNome O campo de texto do nome do cliente.
	 * @param txtCliEnd O campo de texto do endereço do cliente.
	 * @param txtCliFone O campo de texto do telefone do cliente.
	 * @param txtCliEmail O campo de texto do e-mail do cliente.
	 */
	public static void limparCamposClId(JTextField txtCliPesquisar,JTextField txtCliId, JTextField txtCliNome, JTextField txtCliEnd, JTextField txtCliFone,
				JTextField txtCliEmail) {
			limparCampos(txtCliPesquisar, txtCliId, txtCliNome, txtCliEnd, txtCliFone, txtCliEmail);
	}
	
	
	/**
	 * Limpa os campos de texto, a caixa de ID do cliente e os campos de texto da ordem de serviço passados como argumento.
	 *
	 * @param txtCliId O campo de texto do ID do cliente.
	 * @param txtOsEquip O campo de texto do equipamento da ordem de serviço.
	 * @param txtOsDef O campo de texto da defeito da ordem de serviço.
	 * @param txtOsServ O campo de texto do serviço da ordem de serviço.
	 * @param txtOsTec O campo de texto do técnico responsável pela ordem de serviço.
	 * @param txtOsValor O campo de texto do valor da ordem de serviço.
	 * @param txtCliPesquisar O campo de pesquisa do cliente.
	 */
	
	public static void limparCamposOs(JTextField txtCliId,JTextField txtOsEquip, JTextField txtOsDef, JTextField txtOsServ, JTextField txtOsTec, JTextField txtOsValor, JTextField txtCliPesquisar) {
		limparCampos(txtCliId, txtOsEquip, txtOsDef, txtOsServ, txtOsTec, txtOsEquip, txtOsValor,txtCliPesquisar);
				
	}
	
}

