package es.uclm.OasisProject.persistence;

import java.util.List;

import org.springframework.stereotype.Repository;

import es.uclm.OasisProject.domain.entities.Inquilino;
import es.uclm.OasisProject.domain.entities.Reserva;
import jakarta.persistence.TypedQuery;

@Repository
public class ReservaDAO extends EntityDAO<Reserva>{

	public ReservaDAO() {
		// TODO Auto-generated constructor stub
		super(Reserva.class);
	}

	public List<Reserva> findByInquilino(Inquilino inquilino) {

	    TypedQuery<Reserva> query = gestorBD.getEntityManager().createQuery(
	    	"SELECT r FROM Reserva r WHERE r.inquilino = :inquilino",
	        Reserva.class
	    );

	    query.setParameter("inquilino", inquilino);

	    return query.getResultList();
	}

}
