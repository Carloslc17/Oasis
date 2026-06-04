package es.uclm.OasisProject.presentation;

import es.uclm.OasisProject.domain.controllers.GestorPagos;
import es.uclm.OasisProject.domain.entities.*;
import es.uclm.OasisProject.persistence.ReservaDAO;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;

import org.springframework.ui.Model;

import java.security.Principal;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class VentanaPagoTest {

    @InjectMocks
    private VentanaPago ventana;

    @Mock
    private GestorPagos gestorPagos;

    @Mock
    private Model model;

    @Mock
    private Principal principal;
    
    @Mock
    private ReservaDAO reservaDAO;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }


	@Test
	void mostrarFormularioPago() {
	
	    when(reservaDAO.select(1)).thenReturn(new Reserva());
	
	    String vista = ventana.mostrarFormularioPago(1, model);
	
	    verify(model).addAttribute(eq("reserva"), any());
	    verify(model).addAttribute(eq("pago"), any(Pago.class));
	    assertEquals("FormularioPago", vista);
	}




	@Test
	void completarPago_ok() {
	
	    when(principal.getName()).thenReturn("user");
	
	    String vista = ventana.completarPago(1, MetodoPago.TARJETA_CREDITO, principal);
	
	    verify(gestorPagos).realizarPago("user", 1, MetodoPago.TARJETA_CREDITO);
	    assertEquals("redirect:/homeInquilino", vista);
	}

}
