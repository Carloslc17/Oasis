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
    
    @Mock 
    private ReservaDAO reservaDAO;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }


    @Test
    void realizarPago_reservaNoExiste() {

    	when(usuarioDAO.findByLogin("user")).thenReturn(new Inquilino());
    	when(reservaDAO.select(1)).thenReturn(null);

    	Reserva result = gestor.realizarPago("user", 1, MetodoPago.TARJETA_CREDITO);

    	assertNull(result);
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
	    Reserva reserva = new Reserva();
	    reserva.setInquilino(inq);
	
	    when(usuarioDAO.findByLogin("user")).thenReturn(inq);
	    when(reservaDAO.select(1)).thenReturn(reserva);
	
	    Reserva result = gestor.realizarPago("user", 1, MetodoPago.TARJETA_CREDITO);
	
	    assertNotNull(result);
	    verify(pagoDAO).insert(any(Pago.class));
	}

}
