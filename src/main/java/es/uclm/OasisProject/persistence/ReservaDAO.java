package es.uclm.OasisProject.persistence;

import org.springframework.stereotype.Repository;

import es.uclm.OasisProject.domain.entities.Reserva;

@Repository
public class ReservaDAO extends EntityDAO<Reserva>{

	public ReservaDAO() {
		// TODO Auto-generated constructor stub
		super(Reserva.class);
	}

}
