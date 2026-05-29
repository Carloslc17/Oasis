package es.uclm.OasisProject.domain.controllers;


import es.uclm.OasisProject.domain.entities.*;
import es.uclm.OasisProject.persistence.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import java.time.LocalDate;
import java.util.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class GestorBusquedasTest {

    @InjectMocks
    private GestorBusquedas gestor;

    @Mock
    private InmuebleDAO inmuebleDAO;

    @Mock
    private UsuarioDAO usuarioDAO;

    private Inmueble inmueble;
    private Disponibilidad disp;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        disp = new Disponibilidad();
        disp.setFechaInicio(LocalDate.of(2025, 1, 1));
        disp.setFechaFin(LocalDate.of(2025, 1, 10));
        disp.setDirecta(true);
        disp.setPoliticaCancelacion(PoliticaCancelacion.REEMBOLSABLE);

        inmueble = new Inmueble();
        inmueble.setDisponibilidades(new ArrayList<>());
        inmueble.getDisponibilidades().add(disp);
    }

    @Test
    void buscarInmuebles_fechasInvalidas() {

    	LocalDate inicio = LocalDate.of(2025, 1, 10);
    	LocalDate fin = LocalDate.of(2025, 1, 1);

        assertThrows(IllegalArgumentException.class, 
        	() -> gestor.buscarInmuebles(inicio, fin, true, null)
        );
    }

    @Test
    void buscarInmuebles_ok() {
        when(inmuebleDAO.selectAll()).thenReturn(List.of(inmueble));

        List<Inmueble> result = gestor.buscarInmuebles(
                LocalDate.of(2025, 1, 2),
                LocalDate.of(2025, 1, 5),
                true,
                PoliticaCancelacion.REEMBOLSABLE
        );

        assertEquals(1, result.size());
    }

    @Test
    void buscarInmuebles_noCoincide() {
        when(inmuebleDAO.selectAll()).thenReturn(List.of(inmueble));

        List<Inmueble> result = gestor.buscarInmuebles(
                LocalDate.of(2030, 1, 1),
                LocalDate.of(2030, 1, 5),
                true,
                PoliticaCancelacion.REEMBOLSABLE
        );

        assertTrue(result.isEmpty());
    }

    @Test
    void anadirInmueble_usuarioNoExiste() {
        when(usuarioDAO.findByLogin("user")).thenReturn(null);

        boolean result = gestor.anadirInmueble("user", 1);

        assertFalse(result);
    }

    @Test
    void anadirInmueble_noEsInquilino() {
        when(usuarioDAO.findByLogin("user")).thenReturn(new Propietario());

        boolean result = gestor.anadirInmueble("user", 1);

        assertFalse(result);
    }

    @Test
    void anadirInmueble_inmuebleNoExiste() {
        Inquilino inq = new Inquilino();
        inq.setListaDeseos(new HashSet<>());

        when(usuarioDAO.findByLogin("user")).thenReturn(inq);
        when(inmuebleDAO.select(1)).thenReturn(null);

        boolean result = gestor.anadirInmueble("user", 1);

        assertFalse(result);
    }

    @Test
    void anadirInmueble_ok() {
        Inquilino inq = new Inquilino();
        inq.setListaDeseos(new HashSet<>());

        when(usuarioDAO.findByLogin("user")).thenReturn(inq);
        when(inmuebleDAO.select(1)).thenReturn(inmueble);

        boolean result = gestor.anadirInmueble("user", 1);

        assertTrue(result);
        verify(usuarioDAO).update(inq);
    }

    @Test
    void getLista_ok() {
        Inquilino inq = new Inquilino();
        Set<Inmueble> lista = new HashSet<>();
        inq.setListaDeseos(lista);

        when(usuarioDAO.findByLogin("user")).thenReturn(inq);

        Set<Inmueble> result = gestor.getLista("user");

        assertNotNull(result);
    }
}
