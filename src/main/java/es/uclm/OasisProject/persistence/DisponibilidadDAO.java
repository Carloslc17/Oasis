package es.uclm.OasisProject.persistence;

import org.springframework.stereotype.Repository;

import es.uclm.OasisProject.domain.entities.Disponibilidad;

@Repository
public class DisponibilidadDAO extends EntityDAO<Disponibilidad>{

	public DisponibilidadDAO() {
		// TODO Auto-generated constructor stub
		super(Disponibilidad.class);
	}

}
