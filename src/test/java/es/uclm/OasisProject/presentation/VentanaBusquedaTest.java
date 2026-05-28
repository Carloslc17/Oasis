package es.uclm.OasisProject.presentation;

import es.uclm.OasisProject.domain.controllers.GestorBusquedas;
import es.uclm.OasisProject.domain.entities.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;

import org.springframework.ui.Model;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.security.Principal;
import java.time.LocalDate;
import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class VentanaBusquedaTest {

    @InjectMocks
    private VentanaBusqueda ventana;

    @Mock
    private GestorBusquedas gestor;

    @Mock
    private Model model;

    @Mock
    private RedirectAttributes redirect;

    @Mock
    private Principal principal;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void mostrarFormularioBusqueda() {
        assertEquals("Busquedas", ventana.mostrarFormularioBusqueda());
    }

    @Test
    void buscarInmuebles_fechasInvalidas() {
        LocalDate f1 = LocalDate.of(2025, 2, 1);
        LocalDate f2 = LocalDate.of(2025, 1, 1);

        String vista = ventana.buscarInmuebles(f1, f2, null, null, model);

        verify(model).addAttribute("error", "Fechas inválidas");
        assertEquals("Busquedas", vista);
    }

    @Test
    void buscarInmuebles_ok() {
        LocalDate f1 = LocalDate.of(2025, 1, 1);
        LocalDate f2 = LocalDate.of(2025, 1, 10);

        when(gestor.buscarInmuebles(any(), any(), any(), any()))
                .thenReturn(List.of(new Inmueble()));

        String vista = ventana.buscarInmuebles(f1, f2, true, null, model);

        verify(model).addAttribute(eq("resultados"), any());
        assertEquals("ResultadosBusqueda", vista);
    }

    @Test
    void anadirFavorito_principalNull() {
        String vista = ventana.anadirFavorito(1, null, redirect);

        assertEquals("redirect:/login", vista);
    }

    @Test
    void anadirFavorito_ok() {
        when(principal.getName()).thenReturn("user");
        when(gestor.anadirInmueble("user", 1)).thenReturn(true);

        String vista = ventana.anadirFavorito(1, principal, redirect);

        verify(redirect).addFlashAttribute("mensaje", "Añadido a favoritos");
        assertEquals("redirect:/busqueda", vista);
    }

    @Test
    void verFavoritos() {
        when(principal.getName()).thenReturn("user");
        when(gestor.getLista("user")).thenReturn(Set.of(new Inmueble()));

        String vista = ventana.verFavoritos(principal, model);

        verify(model).addAttribute(eq("favoritos"), any());
        assertEquals("Favoritos", vista);
    }
}
