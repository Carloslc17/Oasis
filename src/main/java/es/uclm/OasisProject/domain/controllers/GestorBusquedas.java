package es.uclm.OasisProject.domain.controllers;

import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import es.uclm.OasisProject.domain.entities.Inmueble;
import es.uclm.OasisProject.persistence.InmuebleDAO;

@Service
public class GestorBusquedas {
	
	@Autowired
	private InmuebleDAO inmuebleDAO;
	
	public List<Inmueble> buscarInmuebles(LocalDate fechaInicio, LocalDate fechaFin) {
		return inmuebleDAO.findDisponibles(fechaInicio, fechaFin);
	}
	
	

}
