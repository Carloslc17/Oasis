package es.uclm.OasisProject.domain.controllers;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import es.uclm.OasisProject.domain.entities.Propietario;
import es.uclm.OasisProject.domain.entities.SolicitudReserva;
import es.uclm.OasisProject.persistence.SolicitudReservaDAO;

@Service
public class GestorNotificaciones {
	
	private static final Logger log = LoggerFactory.getLogger(GestorInmuebles.class);
	
	@Autowired
	private SolicitudReservaDAO solicitudDAO;
	
	public List<SolicitudReserva> obtenerSolicitudes(Propietario propietario) {
		return solicitudDAO.findByPropietario(propietario);
	}
	

}
