package es.uclm.OasisProject.persistence;

import java.util.Date;
import java.util.List;
import org.springframework.stereotype.Repository;
import es.uclm.OasisProject.domain.entities.Inmueble;
import es.uclm.OasisProject.domain.entities.Propietario;

@Repository
public class InmuebleDAO extends EntityDAO<Inmueble>{

	public InmuebleDAO() {
		// TODO Auto-generated constructor stub
		super(Inmueble.class);
	}
	

	public List<Inmueble> findDisponibles(Date fechaInicio, Date fechaFin) {
        return gestorBD.getEntityManager()
            .createQuery(
                "SELECT DISTINCT i " +
                "FROM Inmueble i JOIN i.disponibilidades d " +
                "WHERE d.fechaInicio <= :inicio " +
                "AND d.fechaFin >= :fin",
                Inmueble.class
            )
            .setParameter("inicio", fechaInicio)
            .setParameter("fin", fechaFin)
            .getResultList();
    }
	
	public List<Inmueble> findByPropietario(Propietario propietario) {
	    return gestorBD.getEntityManager()
	        .createQuery(
	            "SELECT i FROM Inmueble i WHERE i.Owner = :prop",
	            Inmueble.class
	        )
	        .setParameter("prop", propietario)
	        .getResultList();
	}


}
