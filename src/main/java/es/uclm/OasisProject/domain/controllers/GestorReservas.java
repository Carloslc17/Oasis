package es.uclm.OasisProject.domain.controllers;

import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import es.uclm.OasisProject.domain.entities.*;
import es.uclm.OasisProject.persistence.*;

@Service
public class GestorReservas {
	
	private static final Logger log = LoggerFactory.getLogger(GestorReservas.class);
	

	@Autowired
    private ReservaDAO reservaDAO;

    @Autowired
    private InmuebleDAO inmuebleDAO;

    @Autowired
    private UsuarioDAO usuarioDAO;
    
    public boolean crearSolicitud(int idInquilino, int idInmueble, Date fechaInicio, 
    		                      Date fechaFin, PoliticaCancelacion politica) {
    	

    	if (fechaInicio.after(fechaFin)) {
            log.warn("Fechas inválidas: inicio posterior a fin");
            return false;
        }
    	

    	Inmueble inmueble = inmuebleDAO.select(idInmueble);
        if (inmueble == null) {
            log.warn("El inmueble no existe");
            return false;
        }
        

        Usuario usuario = usuarioDAO.select(idInquilino);
        if (!(usuario instanceof Inquilino)) {
            log.warn("El inquilino no existe");
            return false;
        }
        
        Inquilino inquilino = (Inquilino) usuario;


        SolicitudReserva solicitud = new SolicitudReserva();
        solicitud.setFechaInicio(fechaInicio);
        solicitud.setFechaFin(fechaFin);
        solicitud.setPoliticaCancelacion(politica);
        solicitud.setInquilino(inquilino);
        solicitud.setInmueble(inmueble);

        reservaDAO.insert(solicitud);
        

        log.info("Solicitud de reserva creada");

        
        return true;

    	
    }


	

}
