package es.uclm.OasisProject.domain.controllers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import es.uclm.OasisProject.domain.entities.Inquilino;
import es.uclm.OasisProject.domain.entities.MetodoPago;
import es.uclm.OasisProject.domain.entities.Pago;
import es.uclm.OasisProject.domain.entities.Reserva;
import es.uclm.OasisProject.domain.entities.Usuario;
import es.uclm.OasisProject.persistence.PagoDAO;
import es.uclm.OasisProject.persistence.ReservaDAO;
import es.uclm.OasisProject.persistence.UsuarioDAO;
import jakarta.transaction.Transactional;

@Service
public class GestorPagos {
	
	private static final Logger log = LoggerFactory.getLogger(GestorPagos.class);
	
	@Autowired
	private PagoDAO pagoDAO;

	@Autowired
	private UsuarioDAO usuarioDAO;
	
	@Autowired
	private ReservaDAO reservaDAO;
	
	// Realizar pago del inmueble
	@Transactional
	public Reserva realizarPago(String login, int idReserva, MetodoPago metodoPago) {
		
		Usuario usuario = usuarioDAO.findByLogin(login);
		
		if (usuario == null) {
	        log.warn("Usuario no encontrado");
	        return null;
	    }
		
		if (!(usuario instanceof Inquilino)) {
			log.warn("El usuario no es inquilino");
			return null;
		}
		
		Inquilino inquilino = (Inquilino) usuario;
		
		Reserva reserva = reservaDAO.select(idReserva);
		
		if (reserva == null) {
			log.warn("Reserva inexistente");
			return null;
		}
		
		if (reserva.isPagado() == true) {
			log.warn("Reserva ya esta pagada");
			return null;
		}
		
		if(reserva.getInquilino() != inquilino) {
			log.warn("La reserva no pertenece a ese inquilino");
			return null;
		}
		
    	Pago pago = new Pago();    	
    	pago.setMetodoPago(metodoPago);
    	pago.setReserva(reserva);
    	
    	pagoDAO.insert(pago);
    	
    	log.info("Pago realizado correctamente");
    	
		return reserva;
	}

}
