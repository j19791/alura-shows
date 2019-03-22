package br.com.alura.owasp.validator;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.net.URLConnection;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import br.com.alura.owasp.model.Usuario;

@Component
public class ImagemValidator {

	public boolean tratarImagem(MultipartFile imagem, Usuario usuario, HttpServletRequest request) throws IllegalStateException, IOException {

		ByteArrayInputStream bytesImagem = new ByteArrayInputStream(imagem.getBytes());
		// analisando os bytes do arquivo de imagem passado pelo usu�ro. Caso tenhamos o
		// retorno null � porque o m�todo n�o conseguiu confirmar o tipo mime do arquivo
		// e saberemos que n�o � um arquivo de imagem v�lido.

		String mime = URLConnection.guessContentTypeFromStream(bytesImagem);
		if (mime == null) {
			return false;
		}

		usuario.setNomeImagem(imagem.getOriginalFilename());
		File arquivo = new File(request.getServletContext().getRealPath("/image"), usuario.getNomeImagem());
		imagem.transferTo(arquivo);
		return true;
	}
}
