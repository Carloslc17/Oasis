package es.uclm.OasisProject.domain.controllers;

import java.time.LocalDate;
import java.util.List;
import java.util.Objects;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import es.uclm.OasisProject.domain.entities.Disponibilidad;
import es.uclm.OasisProject.domain.entities.Inmueble;
import es.uclm.OasisProject.domain.entities.Inquilino;
import es.uclm.OasisProject.domain.entities.PoliticaCancelacion;
import es.uclm.OasisProject.domain.entities.Usuario;
import es.uclm.OasisProject.persistence.InmuebleDAO;
import es.uclm.OasisProject.persistence.UsuarioDAO;

@Service
public class GestorBusquedas {
	
	private static final Logger log = LoggerFactory.getLogger(GestorBusquedas.class);
	
	@Autowired
	private InmuebleDAO inmuebleDAO;
	
	@Autowired
	private UsuarioDAO usuarioDAO;
	
	/*
	 * Buscar inmuebles
	 * Busqueda en base a:
	 * - Fechas
	 * - Reserva directa o no
	 * - Politica de cancelacion
	 * 
	 */
	
	public List<Inmueble> buscarInmuebles(LocalDate inicio, LocalDate fin, Boolean directa, PoliticaCancelacion politica) {

		// Verificar fechas
		
		if (inicio != null && fin != null && inicio.isAfter(fin)) {
		    throw new IllegalArgumentException("Fechas inválidas");
		}

		
		List<Inmueble> inmuebles = inmuebleDAO.selectAll(); 
		log.info("Inmuebles encontrados");
		
		return inmuebles.stream().filter(inmueble -> inmueble.getDisponibilidades().stream().anyMatch(disp -> cumpleFiltros(disp, inicio, fin, directa, politica))).toList();
	}
	
	/*
	 * Aniadir inmueble a lista de deseos
	 * El usuario anade el inmueble a su lista de deseos
	 */
	
	public boolean anadirInmueble(String login, int idInmueble) {
		
		Usuario usuario = usuarioDAO.findByLogin(login);
		
		if (usuario == null) {
	        log.warn("El usuario no existe");
	        return false;
	    }
		
		if (!(usuario instanceof Inquilino)) {
	        log.warn("El usuario no es un inquilino");
	        return false;
	    }
		
		Inmueble inmueble = inmuebleDAO.select(idInmueble);
		
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
		log.info("Inmueble correctamente anadido a la lista de deseos. Usuario: {}", inquilino.getLogin());
		
		return true;		
		
	}
	
	/*
	 * Cumplimiento de filtros
	 * Comprobar si el inmueble cumple filtros o no
	 */
	private boolean cumpleFiltros(Disponibilidad disp, LocalDate inicio, LocalDate fin, Boolean directa, PoliticaCancelacion politica) {

			// FILTRO FECHAS
		 	boolean fechas = !disp.getFechaInicio().isAfter(fin) && !disp.getFechaFin().isBefore(inicio);
			
			if (!fechas) return false;
			
			// FILTRO RESERVA DIRECTA
			if (directa != null && directa && !disp.isDirecta()) {
				return false;
			}
			
			// FILTRO POLITICA
			if (politica != null && disp.getPoliticaCancelacion() != politica) {
				return false;
			}
			
			return true;
	}
	
	public Set<Inmueble> getLista(String login) {
		
		Usuario usuario = usuarioDAO.findByLogin(login);
		
		if (usuario == null) {
	        log.warn("El usuario no existe");
	        throw new IllegalArgumentException("Usuario no encontrado");
	    }
		
		if (!(usuario instanceof Inquilino)) {
	        log.warn("El usuario no es un inquilino");
	        throw new IllegalArgumentException("El usuario no es inquilino");
	    }
		
		Inquilino inquilino = (Inquilino) usuario;
		
		return Objects.requireNonNull(inquilino.getListaDeseos());
	}
	

}
