package es.uclm.OasisProject.domain.controllers;

import java.util.Date;
import java.util.List;
import org.springframework.stereotype.Service;
import es.uclm.OasisProject.domain.entities.Inmueble;
import es.uclm.OasisProject.persistence.InmuebleDAO;

@Service
public class GestorBusquedas {
	
	private InmuebleDAO inmuebleDAO;
	
	public List<Inmueble> buscarInmuebles(Date fechaInicio, Date fechaFin) {
		return inmuebleDAO.findDisponibles(fechaInicio, fechaFin);
	}
	
	

}
