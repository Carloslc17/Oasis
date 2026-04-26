package es.uclm.OasisProject.persistence;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import jakarta.persistence.*;

@Repository
@Transactional
public abstract class EntityDAO<E> {

    @Autowired
    protected GestorBD gestorBD;

    private final Class<E> entityClass;

    public EntityDAO(Class<E> entityClass) {
        this.entityClass = entityClass;
    }
    

    // Buscar por id
	public E select(Object id) {
	    return gestorBD.select(entityClass, id);
	}

	// Obtener todas las entidades
    public List<E> selectAll() {
        return gestorBD.selectAll(entityClass);
    }

    // Insertar entidad
    public void insert(E entity) {
        gestorBD.insert(entity);
    }

    // Actualizar entidad
    public E update(E entity) {
        return gestorBD.update(entity);
    }

    // Eliminar entidad
    public void delete(E entity) {
        gestorBD.delete(entity);
    }
    
    // Eliminar por id
    public void deleteById(Object id) {
    	gestorBD.deleteById(entityClass, id);
    }

  
}