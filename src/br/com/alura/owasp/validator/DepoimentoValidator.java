package br.com.alura.owasp.validator;

import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import br.com.alura.owasp.model.Depoimento;

/**
 * valida��o para evitar que os usu�rio ataque com XSS (Cross site scripting)
 * 
 * 
 * 
 *
 */

public class DepoimentoValidator implements Validator {

	@Override
	/**
	 * recebe a classe do objeto que est� querendo ser validado e retorna se o
	 * validador consegue lidar com os objetos dessa classe
	 */
	public boolean supports(Class<?> clazz) {
		return Depoimento.class.isAssignableFrom(clazz);
	}

	@Override
	/**
	 * configura��o da valida��o do que queremos fazer.
	 */
	public void validate(Object target, Errors errors) {
		Depoimento depoimento = (Depoimento) target;
		String titulo = depoimento.getTitulo();
		String mensagem = depoimento.getMensagem();

		if (titulo.contains("<") || titulo.contains(">")) {
			errors.rejectValue("titulo", "errors.titulo");
		}

		if (mensagem.contains("<") || mensagem.contains(">")) {
			errors.rejectValue("mensagem", "errors.mensagem");
		}

	}

}
