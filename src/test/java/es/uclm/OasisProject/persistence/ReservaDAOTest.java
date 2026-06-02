package es.uclm.OasisProject.persistence;

import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;

import es.uclm.OasisProject.domain.entities.Inquilino;
import es.uclm.OasisProject.domain.entities.Reserva;
import es.uclm.OasisProject.persistence.GestorBD;
import es.uclm.OasisProject.persistence.ReservaDAO;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;


public class ReservaDAOTest {
	

@InjectMocks
    private ReservaDAO dao;

    @Mock
    private GestorBD gestorBD;

    @Mock
    private EntityManager entityManager;

    @Mock
    private TypedQuery<Reserva> query;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        when(gestorBD.getEntityManager()).thenReturn(entityManager);
    }

    @Test
    void findByInquilino_ok() {
        Inquilino inq = new Inquilino();

        when(entityManager.createQuery(anyString(), eq(Reserva.class)))
                .thenReturn(query);

        when(query.setParameter("inquilino", inq)).thenReturn(query);
        when(query.getResultList()).thenReturn(List.of(new Reserva()));

        List<Reserva> result = dao.findByInquilino(inq);

        assertEquals(1, result.size());
    }

    @Test
    void findByInquilino_empty() {
        Inquilino inq = new Inquilino();

        when(entityManager.createQuery(anyString(), eq(Reserva.class)))
                .thenReturn(query);

        when(query.setParameter("inquilino", inq)).thenReturn(query);
        when(query.getResultList()).thenReturn(List.of());

        List<Reserva> result = dao.findByInquilino(inq);

        assertTrue(result.isEmpty());
    }


}
