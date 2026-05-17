package es.uclm.OasisProject.persistence;

import org.springframework.stereotype.Repository;
import es.uclm.OasisProject.domain.entities.Usuario;

@Repository
public class UsuarioDAO extends EntityDAO<Usuario> {

    public UsuarioDAO() {
        super(Usuario.class);
    }
    
    // Buscar por nombre de usuario
    public Usuario findByLogin(String login) {
        return gestorBD.getEntityManager()
            .createQuery("SELECT u FROM Usuario u WHERE u.login = :login", Usuario.class)
            .setParameter("login", login)
            .getResultStream()
            .findFirst()
            .orElse(null);
    }

}