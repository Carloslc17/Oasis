package es.uclm.OasisProject.domain.controllers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import es.uclm.OasisProject.domain.entities.Disponibilidad;
import es.uclm.OasisProject.domain.entities.Inquilino;
import es.uclm.OasisProject.domain.entities.MetodoPago;
import es.uclm.OasisProject.domain.entities.Pago;
import es.uclm.OasisProject.domain.entities.Reserva;
import es.uclm.OasisProject.domain.entities.Usuario;
import es.uclm.OasisProject.persistence.DisponibilidadDAO;
import es.uclm.OasisProject.persistence.PagoDAO;
import es.uclm.OasisProject.persistence.UsuarioDAO;
import jakarta.transaction.Transactional;

@Service
public class GestorPagos {
	
	private static final Logger log = LoggerFactory.getLogger(GestorPagos.class);
	
	@Autowired
	private PagoDAO pagoDAO;
	
	@Autowired
	private GestorReservas gestorReservas;
	
	@Autowired
	private DisponibilidadDAO disponibilidadDAO;
	
	@Autowired
	private UsuarioDAO usuarioDAO;
	
	// Realizar pago del inmueble
	@Transactional
	public Reserva realizarPago(String login, int idDisponibilidad, MetodoPago metodoPago) {
		

		Disponibilidad disp = disponibilidadDAO.select(idDisponibilidad);
		
		if (disp == null) {
		    throw new RuntimeException("Disponibilidad no existe");
		}
		
		Usuario usuario = usuarioDAO.findByLogin(login);
		
		if (usuario == null) {
	        log.warn("Usuario no encontrado");
	        return null;
	    }
		
		if (!(usuario instanceof Inquilino)) {
			log.warn("El usuario no es propietario");
			return null;
		}
		
		Inquilino inquilino = (Inquilino) usuario;
	    
		// Crear reserva
		Reserva reserva = gestorReservas.crearReserva(inquilino.getId(), idDisponibilidad);
		
    	Pago pago = new Pago();
    	
    	pago.setMetodoPago(metodoPago);
    	pago.setReserva(reserva);
    	
    	pagoDAO.insert(pago);
    	
    	log.info("Pago realizado correctamente");
    	
		return reserva;
	}

}
