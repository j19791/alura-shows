package br.com.alura.owasp.dao;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import org.springframework.stereotype.Repository;

import br.com.alura.owasp.model.Usuario;

@Repository
public class UsuarioDaoImpl implements UsuarioDao {

	@PersistenceContext
	EntityManager manager;

	public void salva(Usuario usuario) {
		manager.persist(usuario);
	}

	public Usuario procuraUsuario(Usuario usuario) {
		// Ao separarmos a query dos par�metros vindos do formul�rio, n�s temos primeiro
		// a execu��o da query e com isso o banco j� sabe como a query est� estruturada.
		// Somente depois os par�metros s�o substitu�dos pelos dados vindos do
		// formul�rio como sendo meramente valores.
		TypedQuery<Usuario> query = manager.createQuery("select u from Usuario u where u.email=:email and u.senha=:senha", Usuario.class);
		query.setParameter("email", usuario.getEmail());
		query.setParameter("senha", usuario.getSenha());

		Usuario usuarioRetornado = query.getResultList().stream().findFirst().orElse(null);
		return usuarioRetornado;
	}
}
