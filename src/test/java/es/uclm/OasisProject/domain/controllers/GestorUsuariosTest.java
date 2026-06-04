package es.uclm.OasisProject.domain.controllers;


import es.uclm.OasisProject.domain.entities.Propietario;
import es.uclm.OasisProject.persistence.UsuarioDAO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import org.springframework.security.crypto.password.PasswordEncoder;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class GestorUsuariosTest {

    @InjectMocks
    private GestorUsuarios gestorUsuarios;

    @Mock
    private UsuarioDAO usuarioDAO;

    @Mock
    private PasswordEncoder encoder;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void registrarPropietario_usuarioExiste() {
        when(usuarioDAO.findByLogin("user1")).thenReturn(new Propietario()); 

        boolean resultado = gestorUsuarios.registrarPropietario("user1", "pass", "nombre", "apellidos", "dir");

        assertFalse(resultado);
        verify(usuarioDAO, never()).insert(any());
    }

    @Test
    void registrarPropietario_ok() {
        when(usuarioDAO.findByLogin("user1")).thenReturn(null);
        when(encoder.encode("pass")).thenReturn("hashed");

        boolean resultado = gestorUsuarios.registrarPropietario("user1", "pass", "nombre", "apellidos", "dir");

        assertTrue(resultado);
        verify(usuarioDAO, times(1)).insert(any());
    }

    @Test
    void registrarInquilino_ok() {
        when(usuarioDAO.findByLogin("user2")).thenReturn(null);
        when(encoder.encode("pass")).thenReturn("hashed");

        boolean resultado = gestorUsuarios.registrarInquilino("user2", "pass", "nombre", "apellidos", "dir");

        assertTrue(resultado);
        verify(usuarioDAO).insert(any());
    }
}
