package es.uclm.OasisProject.domain.controllers;


import es.uclm.OasisProject.domain.entities.*;
import es.uclm.OasisProject.persistence.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class GestorInmueblesTest {

    @InjectMocks
    private GestorInmuebles gestor;

    @Mock
    private InmuebleDAO inmuebleDAO;

    @Mock
    private UsuarioDAO usuarioDAO;

    @Mock
    private DisponibilidadDAO disponibilidadDAO;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void registrarInmueble_usuarioNoExiste() {
        when(usuarioDAO.findByLogin("user")).thenReturn(null);

        boolean result = gestor.registrarInmueble(new Inmueble(), "user");

        assertFalse(result);
    }

    @Test
    void registrarInmueble_usuarioNoEsPropietario() {
        when(usuarioDAO.findByLogin("user")).thenReturn(new Inquilino());

        boolean result = gestor.registrarInmueble(new Inmueble(), "user");

        assertFalse(result);
    }

    @Test
    void registrarInmueble_ok() {
        Propietario propietario = new Propietario();
        when(usuarioDAO.findByLogin("user")).thenReturn(propietario);

        boolean result = gestor.registrarInmueble(new Inmueble(), "user");

        assertTrue(result);
        verify(inmuebleDAO).insert(any());
    }

    @Test
    void obtenerInmuebles_usuarioNoExiste() {
        when(usuarioDAO.findByLogin("user")).thenReturn(null);

        List<Inmueble> result = gestor.obtenerInmuebles("user");

        assertTrue(result.isEmpty());
    }

    @Test
    void anadirDisponibilidad_inmuebleNoExiste() {
        when(inmuebleDAO.select(1)).thenReturn(null);

        boolean result = gestor.anadirDisponibilidad(new Disponibilidad(), 1);

        assertFalse(result);
    }

    @Test
    void anadirDisponibilidad_ok() {
        Inmueble inmueble = new Inmueble();
        inmueble.setDisponibilidades(new java.util.ArrayList<>());

        when(inmuebleDAO.select(1)).thenReturn(inmueble);

        boolean result = gestor.anadirDisponibilidad(new Disponibilidad(), 1);

        assertTrue(result);
        verify(disponibilidadDAO).insert(any());
    }

    @Test
    void eliminarInmueble() {
        Inmueble inmueble = new Inmueble();
        when(inmuebleDAO.select(1)).thenReturn(inmueble);

        gestor.eliminarInmueble(1);

        verify(inmuebleDAO).delete(inmueble);
    }
}
