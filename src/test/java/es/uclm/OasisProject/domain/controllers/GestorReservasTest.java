package es.uclm.OasisProject.domain.controllers;

import es.uclm.OasisProject.domain.entities.*;
import es.uclm.OasisProject.persistence.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class GestorReservasTest {

    @InjectMocks
    private GestorReservas gestor;

    @Mock
    private ReservaDAO reservaDAO;

    @Mock
    private UsuarioDAO usuarioDAO;

    @Mock
    private DisponibilidadDAO disponibilidadDAO;

    @Mock
    private SolicitudReservaDAO solicitudDAO;

    private Disponibilidad disp;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        disp = new Disponibilidad();
        disp.setInmueble(new Inmueble());
    }

    // -----------------------------
    // crearReserva
    // -----------------------------

    @Test
    void crearReserva_disponibilidadNoExiste() {
        when(disponibilidadDAO.select(1)).thenReturn(null);

        Reserva result = gestor.crearReserva(1, 1);

        assertNull(result);
    }

    @Test
    void crearReserva_usuarioNoEsInquilino() {
        when(disponibilidadDAO.select(1)).thenReturn(disp);
        when(usuarioDAO.select(1)).thenReturn(new Propietario());

        Reserva result = gestor.crearReserva(1, 1);

        assertNull(result);
    }

    @Test
    void crearReserva_ok() {
        Inquilino inq = new Inquilino();

        when(disponibilidadDAO.select(1)).thenReturn(disp);
        when(usuarioDAO.select(1)).thenReturn(inq);

        Reserva result = gestor.crearReserva(1, 1);

        assertNotNull(result);
        verify(reservaDAO).insert(any());
    }

    // -----------------------------
    // crearSolicitud
    // -----------------------------

    @Test
    void crearSolicitud_disponibilidadNoExiste() {
        when(disponibilidadDAO.select(1)).thenReturn(null);

        boolean result = gestor.crearSolicitud("user", 1);

        assertFalse(result);
    }

    @Test
    void crearSolicitud_usuarioNoExiste() {
        when(disponibilidadDAO.select(1)).thenReturn(disp);
        when(usuarioDAO.findByLogin("user")).thenReturn(null);

        boolean result = gestor.crearSolicitud("user", 1);

        assertFalse(result);
    }

    @Test
    void crearSolicitud_noEsInquilino() {
        when(disponibilidadDAO.select(1)).thenReturn(disp);
        when(usuarioDAO.findByLogin("user")).thenReturn(new Propietario());

        boolean result = gestor.crearSolicitud("user", 1);

        assertFalse(result);
    }

    @Test
    void crearSolicitud_ok() {
        Inquilino inq = new Inquilino();

        when(disponibilidadDAO.select(1)).thenReturn(disp);
        when(usuarioDAO.findByLogin("user")).thenReturn(inq);

        boolean result = gestor.crearSolicitud("user", 1);

        assertTrue(result);
        verify(solicitudDAO).insert(any());
    }

    // -----------------------------
    // directa
    // -----------------------------

    @Test
    void directa_disponibilidadNull() {
        when(disponibilidadDAO.select(1)).thenReturn(null);

        boolean result = gestor.directa(1);

        assertFalse(result);
    }

    @Test
    void directa_true() {
        disp.setDirecta(true);
        when(disponibilidadDAO.select(1)).thenReturn(disp);

        boolean result = gestor.directa(1);

        assertTrue(result);
    }

    @Test
    void directa_false() {
        disp.setDirecta(false);
        when(disponibilidadDAO.select(1)).thenReturn(disp);

        boolean result = gestor.directa(1);

        assertFalse(result);
    }

    // -----------------------------
    // getReservas
    // -----------------------------

    @Test
    void getReservas_noEsInquilino() {
        when(usuarioDAO.findByLogin("user")).thenReturn(new Propietario());

        List<Reserva> result = gestor.getReservas("user");

        assertTrue(result.isEmpty());
    }

    @Test
    void getReservas_ok() {
        Inquilino inq = new Inquilino();
        List<Reserva> lista = List.of(new Reserva());

        when(usuarioDAO.findByLogin("user")).thenReturn(inq);
        when(reservaDAO.findByInquilino(inq)).thenReturn(lista);

        List<Reserva> result = gestor.getReservas("user");

        assertEquals(1, result.size());
    }
}
