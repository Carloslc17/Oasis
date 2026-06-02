package es.uclm.OasisProject.persistence;

import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;

import es.uclm.OasisProject.domain.entities.Inmueble;
import es.uclm.OasisProject.domain.entities.Propietario;
import es.uclm.OasisProject.persistence.GestorBD;
import es.uclm.OasisProject.persistence.InmuebleDAO;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;


public class InmuebleDAOTest {
	

@InjectMocks
    private InmuebleDAO dao;

    @Mock
    private GestorBD gestorBD;

    @Mock
    private EntityManager entityManager;

    @Mock
    private TypedQuery<Inmueble> query;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        when(gestorBD.getEntityManager()).thenReturn(entityManager);
    }

    @Test
    void findDisponibles_ok() {
        when(entityManager.createQuery(anyString(), eq(Inmueble.class)))
                .thenReturn(query);

        when(query.setParameter(eq("inicio"), any())).thenReturn(query);
        when(query.setParameter(eq("fin"), any())).thenReturn(query);
        when(query.getResultList()).thenReturn(List.of(new Inmueble()));

        List<Inmueble> result = dao.findDisponibles(
                LocalDate.now(), LocalDate.now().plusDays(1)
        );

        assertEquals(1, result.size());
    }

    @Test
    void findByPropietario_ok() {
        Propietario p = new Propietario();

        when(entityManager.createQuery(anyString(), eq(Inmueble.class)))
                .thenReturn(query);

        when(query.setParameter(eq("prop"), eq(p))).thenReturn(query);
        when(query.getResultList()).thenReturn(List.of(new Inmueble()));

        List<Inmueble> result = dao.findByPropietario(p);

        assertEquals(1, result.size());
    }


}
