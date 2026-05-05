package es.uclm.OasisProject.domain.controllers;

import java.time.LocalDate;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import es.uclm.OasisProject.domain.entities.Disponibilidad;
import es.uclm.OasisProject.domain.entities.Inmueble;
import es.uclm.OasisProject.domain.entities.Inquilino;
import es.uclm.OasisProject.domain.entities.Usuario;
import es.uclm.OasisProject.persistence.InmuebleDAO;
import es.uclm.OasisProject.persistence.UsuarioDAO;

@Service
public class GestorBusquedas {
	
	private static final Logger log = LoggerFactory.getLogger(GestorInmuebles.class);
	
	@Autowired
	private InmuebleDAO inmuebleDAO;
	
	@Autowired
	private UsuarioDAO usuarioDAO;
	
	public List<Inmueble> buscarInmuebles(LocalDate inicio, LocalDate fin, Boolean directa, String politica) {

		List<Inmueble> inmuebles = inmuebleDAO.selectAll(); 
		
		return inmuebles.stream().filter(inmueble -> inmueble.getDisponibilidades().stream().anyMatch(disp -> cumpleFiltros(disp, inicio, fin, directa, politica))).toList();
	}
	
	public boolean anadirInmueble(String login, Inmueble inmueble) {
		
		Usuario usuario = usuarioDAO.findByLogin(login);
		
		if (usuario == null) {
	        log.warn("El usuario no existe");
	        return false;
	    }
		
		if (!(usuario instanceof Inquilino)) {
	        log.warn("El usuario no es un inquilino");
	        return false;
	    }
		
		
		if (inmueble == null) {
	        log.warn("El inmueble no existe");
	        return false;
	    }
		
		Inquilino inquilino = (Inquilino) usuario;
		
		if (inquilino.getListaDeseos().contains(inmueble)) {
		    log.warn("El inmueble ya está en la lista de deseos");
		    return false;
		}
		
		inquilino.addListaDeseos(inmueble);
		usuarioDAO.update(inquilino);
		
		return true;
		
		
		
	}
	
	private boolean cumpleFiltros(Disponibilidad disp, LocalDate inicio, LocalDate fin, Boolean directa, String politica) {

			// FILTRO FECHAS
			boolean fechas = !disp.getFechaInicio().isAfter(inicio) && !disp.getFechaFin().isBefore(fin);
			
			if (!fechas) return false;
			
			// FILTRO RESERVA DIRECTA
			if (directa != null && directa && !disp.isDirecta()) {
				return false;
			}
			
			// FILTRO POLÍTICA
			if (politica != null && !politica.isEmpty()) {
				if (!disp.getPoliticaCancelacion().name().equals(politica)) {
					return false;
				}
			}
			
			return true;
	}
	
	

}
