package es.uclm.OasisProject.persistence;

import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;

import es.uclm.OasisProject.domain.entities.Propietario;
import es.uclm.OasisProject.domain.entities.SolicitudReserva;
import es.uclm.OasisProject.persistence.GestorBD;
import es.uclm.OasisProject.persistence.SolicitudReservaDAO;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;


class SolicitudReservaDAOTest {
	

@InjectMocks
    private SolicitudReservaDAO dao;

    @Mock
    private GestorBD gestorBD;

    @Mock
    private EntityManager entityManager;

    @Mock
    private TypedQuery<SolicitudReserva> query;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        when(gestorBD.getEntityManager()).thenReturn(entityManager);
    }

    @Test
    void findByPropietario_ok() {
        Propietario p = new Propietario();

        when(entityManager.createQuery(anyString(), eq(SolicitudReserva.class)))
                .thenReturn(query);

        when(query.setParameter("prop", p)).thenReturn(query);
        when(query.getResultList()).thenReturn(List.of(new SolicitudReserva()));

        List<SolicitudReserva> result = dao.findByPropietario(p);

        assertEquals(1, result.size());
    }

    @Test
    void findByPropietario_empty() {
        Propietario p = new Propietario();

        when(entityManager.createQuery(anyString(), eq(SolicitudReserva.class)))
                .thenReturn(query);

        when(query.setParameter("prop", p)).thenReturn(query);
        when(query.getResultList()).thenReturn(List.of());

        List<SolicitudReserva> result = dao.findByPropietario(p);

        assertTrue(result.isEmpty());
    }


}
