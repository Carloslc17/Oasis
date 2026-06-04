package es.uclm.OasisProject.persistence;

import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;

import es.uclm.OasisProject.persistence.GestorBD;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;


class GestorBDTest {
	

@InjectMocks
    private GestorBD gestorBD;

    @Mock
    private EntityManager entityManager;

    @Mock
    private TypedQuery<Object> query;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void getEntityManager() {
        assertNotNull(gestorBD.getEntityManager());
    }

    @Test
    void select_ok() {
        Object entidad = new Object();

        when(entityManager.find(Object.class, 1)).thenReturn(entidad);

        Object result = gestorBD.select(Object.class, 1);

        assertEquals(entidad, result);
    }

    @Test
    void selectAll_ok() {
        when(entityManager.createQuery(anyString(), eq(Object.class)))
                .thenReturn(query);

        when(query.getResultList()).thenReturn(List.of(new Object()));

        List<Object> result = gestorBD.selectAll(Object.class);

        assertNotNull(result);
        assertEquals(1, result.size());
    }

    @Test
    void insert_ok() {
        Object entidad = new Object();

        gestorBD.insert(entidad);

        verify(entityManager).persist(entidad);
    }

    @Test
    void update_ok() {
        Object entidad = new Object();

        when(entityManager.merge(entidad)).thenReturn(entidad);

        Object result = gestorBD.update(entidad);

        assertEquals(entidad, result);
    }

    @Test
    void delete_entityContained() {
        Object entidad = new Object();

        when(entityManager.contains(entidad)).thenReturn(true);

        gestorBD.delete(entidad);

        verify(entityManager).remove(entidad);
    }

    @Test
    void delete_entityNotContained() {
        Object entidad = new Object();

        when(entityManager.contains(entidad)).thenReturn(false);
        when(entityManager.merge(entidad)).thenReturn(entidad);

        gestorBD.delete(entidad);

        verify(entityManager).merge(entidad);
        verify(entityManager).remove(entidad);
    }

    @Test
    void deleteById_ok() {
        Object entidad = new Object();

        when(entityManager.find(Object.class, 1)).thenReturn(entidad);

        gestorBD.deleteById(Object.class, 1);

        verify(entityManager).remove(entidad);
    }

    @Test
    void deleteById_notFound() {
        when(entityManager.find(Object.class, 1)).thenReturn(null);

        gestorBD.deleteById(Object.class, 1);

        verify(entityManager, never()).remove(any());
    }
}



