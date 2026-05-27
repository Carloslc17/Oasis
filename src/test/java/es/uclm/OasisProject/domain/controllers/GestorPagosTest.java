package es.uclm.OasisProject.domain.controllers;


import es.uclm.OasisProject.domain.entities.*;
import es.uclm.OasisProject.persistence.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class GestorPagosTest {

    @InjectMocks
    private GestorPagos gestor;

    @Mock
    private PagoDAO pagoDAO;

    @Mock
    private GestorReservas gestorReservas;

    @Mock
    private DisponibilidadDAO disponibilidadDAO;

    @Mock
    private UsuarioDAO usuarioDAO;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void realizarPago_disponibilidadNoExiste() {
        when(disponibilidadDAO.select(1)).thenReturn(null);

        assertThrows(RuntimeException.class, () -> {
            gestor.realizarPago("user", 1, MetodoPago.TARJETA_CREDITO);
        });
    }

    @Test
    void realizarPago_usuarioNoExiste() {
        when(disponibilidadDAO.select(1)).thenReturn(new Disponibilidad());
        when(usuarioDAO.findByLogin("user")).thenReturn(null);

        Reserva result = gestor.realizarPago("user", 1, MetodoPago.TARJETA_CREDITO);

        assertNull(result);
    }

    @Test
    void realizarPago_noEsInquilino() {
        when(disponibilidadDAO.select(1)).thenReturn(new Disponibilidad());
        when(usuarioDAO.findByLogin("user")).thenReturn(new Propietario());

        Reserva result = gestor.realizarPago("user", 1, MetodoPago.TARJETA_CREDITO);

        assertNull(result);
    }

    @Test
    void realizarPago_ok() {
        Inquilino inq = new Inquilino();
        inq.setId(1);

        when(disponibilidadDAO.select(1)).thenReturn(new Disponibilidad());
        when(usuarioDAO.findByLogin("user")).thenReturn(inq);
        when(gestorReservas.crearReserva(1, 1)).thenReturn(new Reserva());

        Reserva result = gestor.realizarPago("user", 1, MetodoPago.TARJETA_CREDITO);

        assertNotNull(result);
        verify(pagoDAO).insert(any());
    }
}
