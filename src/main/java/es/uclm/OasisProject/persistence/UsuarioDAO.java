package es.uclm.OasisProject.persistence;

import org.springframework.stereotype.Repository;
import es.uclm.OasisProject.domain.entities.Usuario;

@Repository
public class UsuarioDAO extends EntityDAO<Usuario> {

    public UsuarioDAO() {
        super(Usuario.class);
    }

}