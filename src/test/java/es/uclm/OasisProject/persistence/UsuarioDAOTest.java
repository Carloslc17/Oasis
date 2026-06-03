package es.uclm.OasisProject.persistence;

import es.uclm.OasisProject.domain.entities.Usuario;
import es.uclm.OasisProject.persistence.GestorBD;
import es.uclm.OasisProject.persistence.UsuarioDAO;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;

import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class UsuarioDAOTest {
	

@InjectMocks
    private UsuarioDAO dao;

    @Mock
    private GestorBD gestorBD;

    @Mock
    private EntityManager entityManager;

    @Mock
    private TypedQuery<Usuario> query;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        when(gestorBD.getEntityManager()).thenReturn(entityManager);
    }

    @Test
    void findByLogin_ok() {
        Usuario usuario = mock(Usuario.class);

        when(entityManager.createQuery(anyString(), eq(Usuario.class))).thenReturn(query);
        when(query.setParameter(eq("login"), any())).thenReturn(query);
        when(query.getResultStream()).thenReturn(Stream.of(usuario));

        Usuario result = dao.findByLogin("user");

        assertNotNull(result);
    }

    @Test
    void findByLogin_noResult() {
        when(entityManager.createQuery(anyString(), eq(Usuario.class))).thenReturn(query);
        when(query.setParameter(eq("login"), any())).thenReturn(query);
        when(query.getResultStream()).thenReturn(Stream.empty());

        Usuario result = dao.findByLogin("user");

        assertNull(result);
    }

}
