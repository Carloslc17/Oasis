package es.uclm.OasisProject.persistence;

import es.uclm.OasisProject.domain.entities.Inmueble;
import es.uclm.OasisProject.domain.entities.Propietario;
import java.time.LocalDate;
import java.util.List;
import org.springframework.stereotype.Repository;

@Repository
public class InmuebleDAO extends EntityDAO<Inmueble>{

	public InmuebleDAO() {
		// TODO Auto-generated constructor stub
		super(Inmueble.class);
	}
	
	// Encontrar inmuebles disponibles
	public List<Inmueble> findDisponibles(LocalDate fechaInicio, LocalDate fechaFin) {
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
	
	// Encontrar inmueble por propietario
	public List<Inmueble> findByPropietario(Propietario propietario) {
	    return gestorBD.getEntityManager()
	        .createQuery(
	            "SELECT i FROM Inmueble i WHERE i.owner = :prop",
	            Inmueble.class
	        )
	        .setParameter("prop", propietario)
	        .getResultList();
	}


}
