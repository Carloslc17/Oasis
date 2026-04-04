package es.uclm.OasisProject.persistence;

import org.springframework.stereotype.Repository;

import es.uclm.OasisProject.domain.entities.Inmueble;

@Repository
public class InmuebleDAO extends EntityDAO<Inmueble>{

	public InmuebleDAO() {
		// TODO Auto-generated constructor stub
		super(Inmueble.class);
	}

}
