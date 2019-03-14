package br.com.alura.owasp.dao;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import org.mindrot.jbcrypt.BCrypt;
import org.springframework.stereotype.Repository;

import br.com.alura.owasp.model.Usuario;

@Repository
public class UsuarioDaoImpl implements UsuarioDao {

	@PersistenceContext
	EntityManager manager;

	public void salva(Usuario usuario) {
		transformaASenhaDoUsuarioEmHash(usuario);
		manager.persist(usuario);
	}

	public void transformaASenhaDoUsuarioEmHash(Usuario usuario) {

		String salto = BCrypt.gensalt();// O salto � um valor aleat�rio gerado o qual ser� adicionado a entrada padr�o
										// da senha com o objetivo de gerar o c�digo hash
		String senhaHashed = BCrypt.hashpw(usuario.getSenha(), salto);
		usuario.setSenha(senhaHashed);

	}

	public Usuario procuraUsuario(Usuario usuario) {
		// Ao separarmos a query dos par�metros vindos do formul�rio, n�s temos primeiro
		// a execu��o da query e com isso o banco j� sabe como a query est� estruturada.
		// Somente depois os par�metros s�o substitu�dos pelos dados vindos do
		// formul�rio como sendo meramente valores.
		TypedQuery<Usuario> query = manager.createQuery("select u from Usuario u where u.email=:email", Usuario.class);
		query.setParameter("email", usuario.getEmail());

		Usuario usuarioRetornado = query.getResultList().stream().findFirst().orElse(null);

		// passado o usuario do form e o usuario retornado pela consulta do banco
		if (validaASenhaDoUsuarioComOHashDoBanco(usuario, usuarioRetornado)) {
			return usuarioRetornado;
		}

		return null;
	}

	private boolean validaASenhaDoUsuarioComOHashDoBanco(Usuario usuario, Usuario usuarioRetornado) {

		if (usuarioRetornado == null) {
			return false;
		}
		return BCrypt.checkpw(usuario.getSenha(), usuarioRetornado.getSenha());

	}
}
