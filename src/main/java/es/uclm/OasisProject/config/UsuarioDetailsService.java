package es.uclm.OasisProject.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import es.uclm.OasisProject.domain.entities.*;
import es.uclm.OasisProject.persistence.UsuarioDAO;

@Service
public class UsuarioDetailsService implements UserDetailsService {

    @Autowired
    private UsuarioDAO usuarioDAO;

    @Override
    public UserDetails loadUserByUsername(String login)
            throws UsernameNotFoundException {

        Usuario usuario = usuarioDAO.findByLogin(login);

        if (usuario == null) {
            throw new UsernameNotFoundException("Usuario no encontrado");
        }

        String rol =
            (usuario instanceof Propietario)
                ? "ROLE_PROPIETARIO"
                : "ROLE_INQUILINO";

        return User
            .withUsername(usuario.getLogin())
            .password(usuario.getPassword())
            .roles(rol.replace("ROLE_", ""))
            .passwordEncoder(password -> password)
            .build();
    }
}
