package es.uclm.OasisProject.persistence;

import java.util.List;
import org.springframework.stereotype.Repository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;


@Repository
@Transactional
public class GestorBD {

    @PersistenceContext
    private EntityManager entityManager;
    
    public EntityManager getEntityManager() {
        return entityManager;
    }
    
    // Buscar por ID
	public <E> E select(Class<E> entityClass, Object id) {
	    return entityManager.find(entityClass, id);
	}
	
	// Obtener todas las entidades
	public <E> List<E> selectAll(Class<E> entityClass) {
        return entityManager.createQuery("SELECT e FROM " + entityClass.getSimpleName() + " e", entityClass)
                            .getResultList();
    }


	// Insertar entidad
    public <E> void insert(E entity) {
        entityManager.persist(entity);
    }

    
    // Actualizar entidad
    public <E> E update(E entity) {
        return entityManager.merge(entity);
    }

    // Eliminar entidad
    public <E> void delete(E entity) {
        entityManager.remove(entityManager.contains(entity) ? entity : entityManager.merge(entity));
    }
    
    // Eliminar por ID
    public <E> void deleteById(Class <E> entityClass, Object id) {
    	E entidad = entityManager.find(entityClass, id);
    	if(entidad != null) {
    		entityManager.remove(entidad);
    	}
    }
    
    

}