package es.uclm.OasisProject.presentation;

import es.uclm.OasisProject.domain.controllers.*;
import es.uclm.OasisProject.domain.entities.*;
import es.uclm.OasisProject.persistence.DisponibilidadDAO;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;

import org.springframework.ui.Model;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.security.Principal;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class VentanaReservasTest {

    @InjectMocks
    private VentanaReservas ventana;

    @Mock
    private GestorReservas gestorReservas;

    @Mock
    private DisponibilidadDAO disponibilidadDAO;

    @Mock
    private GestorNotificaciones gestorNotificaciones;

    @Mock
    private Model model;

    @Mock
    private Principal principal;

    @Mock
    private RedirectAttributes redirect;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void mostrarFormularioReserva() {
        when(disponibilidadDAO.select(1)).thenReturn(new Disponibilidad());

        String vista = ventana.mostrarFormularioReserva(1, model);

        verify(model).addAttribute(eq("disponibilidad"), any());
        assertEquals("FormularioReserva", vista);
    }

    @Test
    void reservarInmueble_principalNull() {
        String vista = ventana.reservarInmueble(1, null, redirect);

        assertEquals("redirect:/login", vista);
    }

    @Test
    void reservarInmueble_directa() {
        when(principal.getName()).thenReturn("user");
        when(gestorReservas.directa(1)).thenReturn(true);

        String vista = ventana.reservarInmueble(1, principal, redirect);

        assertEquals("redirect:/completarPago?idDisponibilidad=1", vista);
    }

    @Test
    void reservarInmueble_solicitudOk() {
        when(principal.getName()).thenReturn("user");
        when(gestorReservas.directa(1)).thenReturn(false);
        when(gestorReservas.crearSolicitud("user", 1)).thenReturn(true);

        String vista = ventana.reservarInmueble(1, principal, redirect);

        verify(redirect).addFlashAttribute("mensaje", "Solicitud enviada correctamente");
        assertEquals("redirect:/homeInquilino", vista);
    }

    @Test
    void reservarInmueble_solicitudError() {
        when(principal.getName()).thenReturn("user");
        when(gestorReservas.directa(1)).thenReturn(false);
        when(gestorReservas.crearSolicitud("user", 1)).thenReturn(false);

        String vista = ventana.reservarInmueble(1, principal, redirect);

        verify(redirect).addFlashAttribute("error", "No se pudo crear la solicitud");
        assertEquals("redirect:/homeInquilino", vista);
    }

    @Test
    void mostrarReservas() {
        when(principal.getName()).thenReturn("user");
        when(gestorReservas.getReservas("user")).thenReturn(List.of(new Reserva()));

        String vista = ventana.mostrarReservas(model, principal);

        verify(model).addAttribute(eq("reservas"), any());
        assertEquals("ReservasUsuario", vista);
    }

    @Test
    void verSolicitudes() {
        when(principal.getName()).thenReturn("user");
        when(gestorNotificaciones.obtenerSolicitudes("user"))
                .thenReturn(List.of(new SolicitudReserva()));

        String vista = ventana.verSolicitudes(principal, model);

        verify(model).addAttribute(eq("solicitudes"), any());
        assertEquals("Solicitudes", vista);
    }
}