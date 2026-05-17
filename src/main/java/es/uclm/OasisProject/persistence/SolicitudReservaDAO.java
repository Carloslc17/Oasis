package es.uclm.OasisProject.persistence;

import java.util.List;
import org.springframework.stereotype.Repository;
import es.uclm.OasisProject.domain.entities.Propietario;
import es.uclm.OasisProject.domain.entities.SolicitudReserva;

@Repository
public class SolicitudReservaDAO extends EntityDAO<SolicitudReserva>{

	public SolicitudReservaDAO() {
		// TODO Auto-generated constructor stub
		super(SolicitudReserva.class);
	}
	
	// Buscar solicitudes de reserva por propietario
	public List<SolicitudReserva> findByPropietario(Propietario propietario) {

	    return gestorBD.getEntityManager().createQuery(
	        "SELECT s FROM SolicitudReserva s " +
	        "WHERE s.inmueble.owner = :prop",
	        SolicitudReserva.class
	    )
	    .setParameter("prop", propietario)
	    .getResultList();
	}

}
