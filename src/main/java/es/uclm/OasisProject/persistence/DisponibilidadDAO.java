package es.uclm.OasisProject.persistence;

import org.springframework.stereotype.Repository;
import es.uclm.OasisProject.domain.entities.Inmueble;

@Repository
public class DisponibilidadDAO extends EntityDAO<Inmueble>{

	public DisponibilidadDAO() {
		// TODO Auto-generated constructor stub
		super(Inmueble.class);
	}

}
