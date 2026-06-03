package es.uclm.OasisProject.presentation;

import es.uclm.OasisProject.domain.controllers.GestorNotificaciones;
import es.uclm.OasisProject.domain.entities.SolicitudReserva;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;

import org.springframework.ui.Model;

import java.security.Principal;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class VentanaConfirmacionReservasTest {

    @InjectMocks
    private VentanaConfirmacionReservas ventana;

    @Mock
    private GestorNotificaciones gestor;

    @Mock
    private Model model;

    @Mock
    private Principal principal;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void mostrarSolicitudReserva() {
        when(gestor.getSolicitud(1)).thenReturn(new SolicitudReserva());

        String vista = ventana.mostrarSolicitudReserva(1, principal, model);

        verify(model).addAttribute(eq("solicitud"), any());
        assertEquals("ConfirmacionReserva", vista);
    }
}
