package es.uclm.OasisProject.domain.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import es.uclm.OasisProject.domain.entities.Inquilino;
import es.uclm.OasisProject.domain.entities.Propietario;
import es.uclm.OasisProject.domain.entities.Usuario;
import es.uclm.OasisProject.persistence.UsuarioDAO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Service
public class GestorUsuarios {

    private static final Logger log = LoggerFactory.getLogger(GestorUsuarios.class);
    private static final String USER_EXISTS = "El usuario ya existe: {}";
    private static final String USER_NOT_FOUND = "Usuario {} no encontrado";
    private static final String CONTRASENA_INCORRECTA = "Contrasena incorrecta";

    @Autowired
    private UsuarioDAO usuarioDAO;
    @Autowired
    private PasswordEncoder encoder;
    
    //---------------------
    // REGISTRO
    //---------------------

    // Registrar propietario
    
    public boolean registrarPropietario(String login, String pass, String nombre, String apellidos, String direccion) {

        if (usuarioDAO.findByLogin(login) != null) {
            log.warn(USER_EXISTS, login);
            return false;
        }

        Propietario propietario = new Propietario(login, encoder.encode(pass), nombre, apellidos, direccion);

        usuarioDAO.insert(propietario);

        return true;
    }

    // Registrar inquilino
    
    public boolean registrarInquilino(String login, String pass, String nombre, String apellidos, String direccion) {

        if (usuarioDAO.findByLogin(login) != null) {
            log.warn(USER_EXISTS, login);
            return false;
        }

        Inquilino inquilino = new Inquilino(login, encoder.encode(pass), nombre, apellidos, direccion);

        usuarioDAO.insert(inquilino);

        return true;
    }
}