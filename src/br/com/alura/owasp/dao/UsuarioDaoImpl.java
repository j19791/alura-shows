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

		String salto = BCrypt.gensalt();// O salto é um valor aleatório gerado o qual será adicionado a entrada padrão
										// da senha com o objetivo de gerar o código hash
		String senhaHashed = BCrypt.hashpw(usuario.getSenha(), salto);
		usuario.setSenha(senhaHashed);

	}

	public Usuario procuraUsuario(Usuario usuario) {
		// Ao separarmos a query dos parâmetros vindos do formulário, nós temos primeiro
		// a execução da query e com isso o banco já sabe como a query está estruturada.
		// Somente depois os parâmetros são substituídos pelos dados vindos do
		// formulário como sendo meramente valores.
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
