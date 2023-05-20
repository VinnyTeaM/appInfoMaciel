package br.com.infomaciel.screens;

import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.PlainDocument;

/**
 * A classe OnlyNumbersDocument e uma implementacao de PlainDocument que permite
 * apenas a insercao de numeros e caracteres permitidos.
 */
public class OnlyNumbersDocument extends PlainDocument {
	/**
	 * Numero de serie para a serializacao.
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * Vetor de caracteres permitidos para o documento.
	 */
	private char[] allowedChars;

	/**
	 * Construtor padrao que inicializa o vetor de caracteres permitidos com um
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
	 * Sobrescrita do metodo insertString, que e chamado sempre que um novo texto e
	 * inserido no componente.
	 *
	 * @param offs A posicao de offset para insercao do texto.
	 * @param str  A string a ser inserida.
	 * @param a    Os atributos do texto.
	 * @throws BadLocationException Se a posicao de offset for invalida.
	 */
	@Override
	public void insertString(int offs, String str, AttributeSet a) throws BadLocationException {
		// Verifica se a string passada e nula
		if (str == null) {
			return;
		}
		// Converte a string em um vetor de caracteres
		char[] chars = str.toCharArray();
		// Percorre o vetor de caracteres verificando se cada um e um digito ou um
		// caracter permitido
		for (int i = 0; i < chars.length; i++) {
			if (!Character.isDigit(chars[i]) && !isAllowedChar(chars[i])) {
				return; // Se o caracter nao e um digito e nao esta na lista de permitidos, retorna sem
						// inserir
			}
		}
		// Insere o texto normalmente, ja que ele e composto apenas de dígitos ou
		// caracteres permitidos
		super.insertString(offs, new String(chars), a);
	}

	/**
	 * Verifica se um caracter esta na lista de permitidos.
	 *
	 * @param c O caracter a ser verificado.
	 * @return true se o caracter esta na lista de permitidos, false caso contrario.
	 */
	private boolean isAllowedChar(char c) {
		for (int i = 0; i < allowedChars.length; i++) {
			if (allowedChars[i] == c) {
				return true; // Se o caracter esta na lista de permitidos, retorna verdadeiro
			}
		}
		return false; // Se o caracter nao esta na lista de permitidos, retorna falso
	}
}
