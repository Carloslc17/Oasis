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
	
	public boolean registrarInmueble(String direccion, String precio_noche, int id_Propietario) {
		
		Propietario propietario;
		if (propietarioDAO.select(id_Propietario) == null) {
			log.warn("El propietario asociado al inmueble no existe:");
			return false;
		} else {
			propietario = (Propietario) propietarioDAO.select(id_Propietario);
		}
			
		Inmueble inmueble = new Inmueble();
        inmueble.setDireccion(direccion);
        inmueble.setPrecio_noche(precio_noche);
        inmueble.setOwner(propietario);
        
        inmuebleDAO.insert(inmueble);
        
        return true;

	}

}
