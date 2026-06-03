package es.uclm.OasisProject.presentation;

import es.uclm.OasisProject.domain.controllers.GestorInmuebles;
import es.uclm.OasisProject.domain.entities.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;

import org.springframework.ui.Model;

import java.security.Principal;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class VentanaAltaInmueblesTest {

    @InjectMocks
    private VentanaAltaInmuebles ventana;

    @Mock
    private GestorInmuebles gestor;

    @Mock
    private Model model;

    @Mock
    private Principal principal;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void mostrarFormularioInmueble() {
        String vista = ventana.mostrarFormularioInmueble(model);

        verify(model).addAttribute(eq("inmueble"), any(Inmueble.class));
        assertEquals("FormularioInmueble", vista);
    }

    @Test
    void registrarInmueble() {
        when(principal.getName()).thenReturn("user");
        when(gestor.registrarInmueble(any(), any())).thenReturn(true);

        String vista = ventana.registrarInmueble(new Inmueble(), model, principal);

        verify(model).addAttribute("Exito", true);
        assertEquals("redirect:/misInmuebles", vista);
    }

    @Test
    void misInmuebles() {
        when(principal.getName()).thenReturn("user");
        when(gestor.obtenerInmuebles("user")).thenReturn(List.of(new Inmueble()));

        String vista = ventana.misInmuebles(model, principal);

        verify(model).addAttribute(eq("inmuebles"), any());
        assertEquals("Inmuebles", vista);
    }

    @Test
    void mostrarFormularioDisponibilidad() {
        String vista = ventana.mostrarFormularioDisponibilidad(1, model);

        verify(model).addAttribute(eq("disponibilidad"), any(Disponibilidad.class));
        verify(model).addAttribute("idInmueble", 1);
        assertEquals("FormularioDisponibilidad", vista);
    }

    @Test
    void registrarDisponibilidad() {
        when(gestor.anadirDisponibilidad(any(), anyInt())).thenReturn(true);

        String vista = ventana.registrarDisponibilidad(new Disponibilidad(), 1, null, model);

        verify(model).addAttribute("Exito", true);
        assertEquals("redirect:/misInmuebles", vista);
    }

    @Test
    void eliminarInmueble() {
        String vista = ventana.eliminarInmueble(1);

        verify(gestor).eliminarInmueble(1);
        assertEquals("redirect:/misInmuebles", vista);
    }
}

