package es.uclm.OasisProject.persistence;

import org.springframework.stereotype.Repository;

import es.uclm.OasisProject.domain.entities.SolicitudReserva;

@Repository
public class SolicitudReservaDAO extends EntityDAO<SolicitudReserva>{

	public SolicitudReservaDAO() {
		// TODO Auto-generated constructor stub
		super(SolicitudReserva.class);
	}

}
