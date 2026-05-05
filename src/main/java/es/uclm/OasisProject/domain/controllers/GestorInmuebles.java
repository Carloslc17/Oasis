package es.uclm.OasisProject.domain.controllers;

import java.util.Collections;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import es.uclm.OasisProject.domain.entities.Disponibilidad;
import es.uclm.OasisProject.domain.entities.Inmueble;
import es.uclm.OasisProject.domain.entities.Propietario;
import es.uclm.OasisProject.domain.entities.Usuario;
import es.uclm.OasisProject.persistence.DisponibilidadDAO;
import es.uclm.OasisProject.persistence.InmuebleDAO;
import es.uclm.OasisProject.persistence.UsuarioDAO;

@Service
public class GestorInmuebles {
	
	private static final Logger log = LoggerFactory.getLogger(GestorInmuebles.class);
	
	@Autowired
	private InmuebleDAO inmuebleDAO;
	
	@Autowired 
	private UsuarioDAO propietarioDAO;
	
	@Autowired
	private DisponibilidadDAO disponibilidadDAO;
	
	// Registar nuevo inmueble en el sistema
	
	public boolean registrarInmueble(Inmueble inmueble) {
		
		Propietario propietario = (Propietario) propietarioDAO.select(inmueble.getOwner().getId());

		// Comprobar existencia del propietario
	    if (propietario == null) {
	        log.warn("El propietario no existe");
	        return false;
	    }

	    inmueble.setOwner(propietario);
	    inmuebleDAO.insert(inmueble);

	    return true;

	}
	
	// Obtener todos los inmuebles de un propietario
	
	public List<Inmueble> obtenerInmuebles(String login) {

	    Usuario usuario = propietarioDAO.findByLogin(login);

	    if (usuario == null) {
	        log.warn("Usuario no encontrado");
	        return Collections.emptyList();
	    }

	    if (!(usuario instanceof Propietario)) {
	        log.warn("El usuario no es propietario");
	        return Collections.emptyList();
	    }

	    Propietario propietario = (Propietario) usuario;

	    return inmuebleDAO.findByPropietario(propietario);
	}
	
	// Añadir disponibilidad a inmueble
	
	public boolean anadirDisponibilidad(Disponibilidad disp, int idInmueble) {

	    Inmueble inmueble = inmuebleDAO.select(idInmueble);

	    if (inmueble == null) {
	        log.warn("El inmueble no existe");
	        return false;
	    }

	    disp.setInmueble(inmueble);
	    inmueble.getDisponibilidades().add(disp);

	    disponibilidadDAO.insert(disp);

	    return true;
	}

}
