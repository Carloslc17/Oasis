package es.uclm.OasisProject.domain.controllers;

import es.uclm.OasisProject.domain.entities.*;
import es.uclm.OasisProject.persistence.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class GestorNotificacionesTest {

    @InjectMocks
    private GestorNotificaciones gestor;

    @Mock
    private SolicitudReservaDAO solicitudDAO;

    @Mock
    private UsuarioDAO usuarioDAO;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void obtenerSolicitudes_usuarioNoExiste() {
        when(usuarioDAO.findByLogin("user")).thenReturn(null);

        List<SolicitudReserva> result = gestor.obtenerSolicitudes("user");

        assertTrue(result.isEmpty());
    }

    @Test
    void obtenerSolicitudes_noEsPropietario() {
        when(usuarioDAO.findByLogin("user")).thenReturn(new Inquilino());

        List<SolicitudReserva> result = gestor.obtenerSolicitudes("user");

        assertTrue(result.isEmpty());
    }

    @Test
    void obtenerSolicitudes_ok() {
        Propietario propietario = new Propietario();
        List<SolicitudReserva> lista = List.of(new SolicitudReserva());

        when(usuarioDAO.findByLogin("user")).thenReturn(propietario);
        when(solicitudDAO.findByPropietario(propietario)).thenReturn(lista);

        List<SolicitudReserva> result = gestor.obtenerSolicitudes("user");

        assertEquals(1, result.size());
        verify(solicitudDAO).findByPropietario(propietario);
    }

    @Test
    void getSolicitud_ok() {
        SolicitudReserva solicitud = new SolicitudReserva();

        when(solicitudDAO.select(1)).thenReturn(solicitud);

        SolicitudReserva result = gestor.getSolicitud(1);

        assertNotNull(result);
    }
}
