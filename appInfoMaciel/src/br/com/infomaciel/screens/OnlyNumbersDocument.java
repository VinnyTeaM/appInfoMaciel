package br.com.infomaciel.screens;

import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.PlainDocument;

/**
 * A classe OnlyNumbersDocument é uma implementação de PlainDocument que permite
 * apenas a inserção de números e caracteres permitidos.
 */
public class OnlyNumbersDocument extends PlainDocument {
	/**
	 * Número de série para a serialização.
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * Vetor de caracteres permitidos para o documento.
	 */
	private char[] allowedChars;

	/**
	 * Construtor padrão que inicializa o vetor de caracteres permitidos com um
	 * vetor vazio.
	 */
	public OnlyNumbersDocument() {
		super();
		this.allowedChars = new char[0];
	}

	/**
	 * Construtor que recebe um vetor de caracteres permitidos.
	 *
	 * @param allowedChars O vetor de caracteres permitidos.
	 */
	public OnlyNumbersDocument(char[] allowedChars) {
		super();
		this.allowedChars = allowedChars;
	}

	/**
	 * Sobrescrita do método insertString, que é chamado sempre que um novo texto é
	 * inserido no componente.
	 *
	 * @param offs A posição de offset para inserção do texto.
	 * @param str  A string a ser inserida.
	 * @param a    Os atributos do texto.
	 * @throws BadLocationException Se a posição de offset for inválida.
	 */
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

	/**
	 * Verifica se um caracter está na lista de permitidos.
	 *
	 * @param c O caracter a ser verificado.
	 * @return true se o caracter está na lista de permitidos, false caso contrário.
	 */
	private boolean isAllowedChar(char c) {
		for (int i = 0; i < allowedChars.length; i++) {
			if (allowedChars[i] == c) {
				return true; // Se o caracter está na lista de permitidos, retorna verdadeiro
			}
		}
		return false; // Se o caracter não está na lista de permitidos, retorna falso
	}
}
