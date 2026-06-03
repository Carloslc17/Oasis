package es.uclm.OasisProject.domain.controllers;

import java.util.Collections;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import es.uclm.OasisProject.domain.entities.Propietario;
import es.uclm.OasisProject.domain.entities.SolicitudReserva;
import es.uclm.OasisProject.domain.entities.Usuario;
import es.uclm.OasisProject.persistence.SolicitudReservaDAO;
import es.uclm.OasisProject.persistence.UsuarioDAO;

@Service
public class GestorNotificaciones {
	
	private static final Logger log = LoggerFactory.getLogger(GestorNotificaciones.class);
	
	@Autowired
	private SolicitudReservaDAO solicitudDAO;
	
	@Autowired
	private UsuarioDAO usuarioDAO;
	
	/*
	 *  Obtener solicitudes de reserva por propietario
	 *  El objetivo es notificar al propietario de nuevas solcitudes
	 */
	public List<SolicitudReserva> obtenerSolicitudes(String login) {
		
		Usuario usuario = usuarioDAO.findByLogin(login);
		
		if (usuario == null) {
	        log.warn("Usuario no encontrado");
	        return Collections.emptyList();
	    }
		
		if (!(usuario instanceof Propietario)) {
			log.warn("El usuario no es propietario");
			return Collections.emptyList();
		}
		
		Propietario propietario = (Propietario) usuario;
		
		return solicitudDAO.findByPropietario(propietario);
	}
	
	public SolicitudReserva getSolicitud(int idSolicitud) {
		return solicitudDAO.select(idSolicitud);
	}

}
