package br.com.infomaciel.screens;

import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.PlainDocument;

public class OnlyNumbersDocument extends PlainDocument {

	private static final long serialVersionUID = 2L;
	private char[] allowedChars;

	// Construtor padrão que inicializa o vetor de caracteres permitidos com um
	// vetor vazio
	public OnlyNumbersDocument() {
		super();
		this.allowedChars = new char[0];
	}

	// Construtor que recebe um vetor de caracteres permitidos
	public OnlyNumbersDocument(char[] allowedChars) {
		super();
		this.allowedChars = allowedChars;
	}

	// Sobrescrita do método insertString, que é chamado sempre que um novo texto é
	// inserido no componente
	@Override
	public void insertString(int offs, String str, AttributeSet a) throws BadLocationException {
		// Verifica se a string passada é nula
		if (str == null) {
			return;
		}
		// Converte a string em um vetor de caracteres
		char[] chars = str.toCharArray();
		// Percorre o vetor de caracteres verificando se cada um é um dígito ou um
		// caracter permitido
		for (int i = 0; i < chars.length; i++) {
			if (!Character.isDigit(chars[i]) && !isAllowedChar(chars[i])) {
				return; // Se o caracter não é um dígito e não está na lista de permitidos, retorna sem
						// inserir
			}
		}
		// Insere o texto normalmente, já que ele é composto apenas de dígitos ou
		// caracteres permitidos
		super.insertString(offs, new String(chars), a);
	}

	// Método privado que verifica se um caracter está na lista de permitidos
	private boolean isAllowedChar(char c) {
		for (int i = 0; i < allowedChars.length; i++) {
			if (allowedChars[i] == c) {
				return true; // Se o caracter está na lista de permitidos, retorna verdadeiro
			}
		}
		return false; // Se o caracter não está na lista de permitidos, retorna falso
	}
}
