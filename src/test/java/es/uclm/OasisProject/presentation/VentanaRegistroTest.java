package es.uclm.OasisProject.presentation;

import es.uclm.OasisProject.domain.controllers.GestorUsuarios;
import es.uclm.OasisProject.domain.entities.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;

import org.springframework.ui.Model;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;


class VentanaRegistroTest {
	

@InjectMocks
    private VentanaRegistro ventana;

    @Mock
    private GestorUsuarios gestorUsuarios;

    @Mock
    private Model model;

    @Mock
    private RedirectAttributes redirectAttrs;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void mostrarRegistro() {
        String vista = ventana.mostrarRegistro();
        assertEquals("SeleccionUsuario", vista);
    }

    @Test
    void mostrarFormularioPropietario() {
        String vista = ventana.msostrarFormularioPropietario(model);

        verify(model).addAttribute(eq("usuario"), any(Propietario.class));
        assertEquals("FormularioPropietario", vista);
    }

    @Test
    void registrarPropietario_ok() {
        Propietario p = new Propietario();

        when(gestorUsuarios.registrarPropietario(any(), any(), any(), any(), any()))
                .thenReturn(true);

        String vista = ventana.registrarPropietario(p, redirectAttrs);

        verify(redirectAttrs).addFlashAttribute("mensaje", "Registro correcto");
        assertEquals("redirect:/login", vista);
    }

    @Test
    void registrarPropietario_error() {
        Propietario p = new Propietario();

        when(gestorUsuarios.registrarPropietario(any(), any(), any(), any(), any()))
                .thenReturn(false);

        String vista = ventana.registrarPropietario(p, redirectAttrs);

        verify(redirectAttrs).addFlashAttribute("error", "El usuario ya existe");
        assertEquals("redirect:/login", vista);
    }

    @Test
    void mostrarFormularioInquilino() {
        String vista = ventana.mostrarFormularioInquilino(model);

        verify(model).addAttribute(eq("usuario"), any(Inquilino.class));
        assertEquals("FormularioInquilino", vista);
    }

    @Test
    void registrarInquilino_ok() {
        Inquilino i = new Inquilino();

        when(gestorUsuarios.registrarInquilino(any(), any(), any(), any(), any()))
                .thenReturn(true);

        String vista = ventana.registrarInquilino(i, redirectAttrs);

        verify(redirectAttrs).addFlashAttribute("mensaje", "Registro correcto");
        assertEquals("redirect:/login", vista);
    }

    @Test
    void mostrarLogin() {
        assertEquals("Inicio_Sesion", ventana.mostrarLogin());
    }


}
