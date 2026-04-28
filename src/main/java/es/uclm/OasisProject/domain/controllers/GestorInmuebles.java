package es.uclm.OasisProject.domain.controllers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import es.uclm.OasisProject.domain.entities.Inmueble;
import es.uclm.OasisProject.domain.entities.Propietario;
import es.uclm.OasisProject.persistence.InmuebleDAO;
import es.uclm.OasisProject.persistence.UsuarioDAO;

@Service
public class GestorInmuebles {
	
	private static final Logger log = LoggerFactory.getLogger(GestorInmuebles.class);
	
	@Autowired
	private InmuebleDAO inmuebleDAO;
	
	@Autowired 
	private UsuarioDAO propietarioDAO;
	
	
	
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

}
