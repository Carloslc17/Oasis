package es.uclm.OasisProject.domain.controllers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import es.uclm.OasisProject.domain.entities.*;
import es.uclm.OasisProject.persistence.*;
import jakarta.transaction.Transactional;

@Service
public class GestorReservas {
	
	private static final Logger log = LoggerFactory.getLogger(GestorReservas.class);
	
	@Autowired
    private ReservaDAO reservaDAO;

    @Autowired
    private UsuarioDAO usuarioDAO;
    
    @Autowired 
    private DisponibilidadDAO disponibilidadDAO;
    
    @Autowired
    private SolicitudReservaDAO solicitudDAO;
    
    /*
     * Crear la reserva de inmueble
     * Esto se hace despues de haber realizado el pago
     */
    @Transactional
    public Reserva crearReserva(int idInquilino, int idDisponibilidad) {
    	
    	Disponibilidad disp = disponibilidadDAO.select(idDisponibilidad);
    	
    	if (disp == null) {
    		log.warn("Disponibilidad no encontrada");
    		return null;
    	}
    	
    	Usuario usuario = usuarioDAO.select(idInquilino);
    	
    	if (!(usuario instanceof Inquilino)) {
    	    log.warn("Usuario no encontrado");
    	    return null;
    	}
    	
    	Inquilino inquilino = (Inquilino) usuario;
    	
	    Reserva reserva = new Reserva();
	    reserva.setFechaInicio(disp.getFechaInicio());
	    reserva.setFechaFin(disp.getFechaFin());
	    reserva.setInquilino(inquilino);
	    reserva.setInmueble(disp.getInmueble());
	    reserva.setPoliticaCancelacion(disp.getPoliticaCancelacion());
	    reservaDAO.insert(reserva);
	    	
	    log.info("Inmueble reservado correctamente.");
	        
	    return reserva;
    }
    
    /*
     * Crear solicitud de reserva
     * Se envia al propietario
    */    
    
    public boolean crearSolicitud(int idInquilino, int idDisponibilidad) {
    	
    	Disponibilidad disp = disponibilidadDAO.select(idDisponibilidad);
    	
    	if (disp == null) {
    		log.warn("Disponibilidad no encontrada");
    		return false;
    	}
    	
    	Usuario usuario = usuarioDAO.select(idInquilino);
    	
    	if (!(usuario instanceof Inquilino)) {
    	    return false;
    	}
    	
    	Inquilino inquilino = (Inquilino) usuario;
    	
    	SolicitudReserva solicitud = new SolicitudReserva();
		solicitud.setFechaInicio(disp.getFechaInicio());
		solicitud.setFechaFin(disp.getFechaFin());
		solicitud.setPoliticaCancelacion(disp.getPoliticaCancelacion());
		solicitud.setInquilino(inquilino);
		solicitud.setInmueble(disp.getInmueble());
		solicitudDAO.insert(solicitud);
		
		log.info("Solicitud de reserva creada");

		return true;
    	
    }	

}
