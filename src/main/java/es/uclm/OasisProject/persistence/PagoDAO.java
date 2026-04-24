package es.uclm.OasisProject.persistence;

import org.springframework.stereotype.Repository;

import es.uclm.OasisProject.domain.entities.Pago;

@Repository
public class PagoDAO extends EntityDAO<Pago>{

	public PagoDAO() {
		// TODO Auto-generated constructor stub
		super(Pago.class);
	}

}
