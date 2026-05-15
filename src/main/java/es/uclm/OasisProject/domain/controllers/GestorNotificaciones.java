package es.uclm.OasisProject.domain.controllers;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import es.uclm.OasisProject.domain.entities.Propietario;
import es.uclm.OasisProject.domain.entities.SolicitudReserva;
import es.uclm.OasisProject.persistence.SolicitudReservaDAO;

@Service
public class GestorNotificaciones {
	
	@Autowired
	private SolicitudReservaDAO solicitudDAO;
	
	/*
	 *  Obtener solicitudes de reserva por propietario
	 *  El objetivo es notificar al propietario de nuevas solcitudes
	 */
	public List<SolicitudReserva> obtenerSolicitudes(Propietario propietario) {
		return solicitudDAO.findByPropietario(propietario);
	}
	

}
