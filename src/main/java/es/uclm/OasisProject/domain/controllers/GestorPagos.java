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
import es.uclm.OasisProject.persistence.ReservaDAO;
import es.uclm.OasisProject.persistence.UsuarioDAO;

@Service
public class GestorPagos {
	
	private static final Logger log = LoggerFactory.getLogger(GestorInmuebles.class);
	
	@Autowired
	private PagoDAO pagoDAO;
	
	@Autowired
	private ReservaDAO reservaDAO;
	
	@Autowired
	private DisponibilidadDAO disponibilidadDAO;
	
	@Autowired 
	private UsuarioDAO usuarioDAO;
	
	
	public boolean realizarPago(int idInquilino, int idDisponibilidad, MetodoPago metodoPago) {
		
		// Buscar disponibilidad
        Disponibilidad disponibilidad = disponibilidadDAO.select(idDisponibilidad);
        
        if (disponibilidad == null) {
            log.warn("La disponibilidad no existe");
            return false;
        }
        
        // Buscar usuario
	    Usuario usuario = usuarioDAO.select(idInquilino);
	
	    if (!(usuario instanceof Inquilino)) {
	        log.warn("El usuario no es inquilino");
	        return false;
	    }
	
	    Inquilino inquilino = (Inquilino) usuario;
	    
	    Reserva reserva = new Reserva();
	    reserva.setFechaInicio(disponibilidad.getFechaInicio());
    	reserva.setFechaFin(disponibilidad.getFechaFin());
    	reserva.setInquilino(inquilino);
    	reserva.setInmueble(disponibilidad.getInmueble());
    	reserva.setPoliticaCancelacion(disponibilidad.getPoliticaCancelacion());
    	reservaDAO.insert(reserva);
		
    	Pago pago = new Pago();
    	
    	pago.setMetodoPago(metodoPago);
    	pago.setReserva(reserva);
    	
    	pagoDAO.insert(pago);
    	
    	log.info("Pago realizado correctamente");
    	
		return true;
	}

}
