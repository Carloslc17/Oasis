package es.uclm.OasisProject.domain.entities;

import java.time.LocalDate;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class ReservaTest {
	

	@Test
    void isPagado_sinPago() {
        Reserva reserva = new Reserva();

        assertFalse(reserva.isPagado());
    }

    @Test
    void isPagado_conPago() {
        Reserva reserva = new Reserva();
        Pago pago = new Pago();

        reserva.setPago(pago);

        assertTrue(reserva.isPagado());
    }

    @Test
    void isActiva_dentroDelRango() {
        LocalDate hoy = LocalDate.now();

        Reserva reserva = new Reserva();
        reserva.setFechaInicio(hoy.minusDays(1));
        reserva.setFechaFin(hoy.plusDays(1));

        assertTrue(reserva.isActiva());
    }

    @Test
    void isActiva_fueraDelRango_pasado() {
        LocalDate hoy = LocalDate.now();

        Reserva reserva = new Reserva();
        reserva.setFechaInicio(hoy.minusDays(10));
        reserva.setFechaFin(hoy.minusDays(1));

        assertFalse(reserva.isActiva());
    }

    @Test
    void isActiva_fueraDelRango_futuro() {
        LocalDate hoy = LocalDate.now();

        Reserva reserva = new Reserva();
        reserva.setFechaInicio(hoy.plusDays(1));
        reserva.setFechaFin(hoy.plusDays(10));

        assertFalse(reserva.isActiva());
    }

    @Test
    void isActiva_enLimite_inicioHoy() {
        LocalDate hoy = LocalDate.now();

        Reserva reserva = new Reserva();
        reserva.setFechaInicio(hoy);
        reserva.setFechaFin(hoy.plusDays(5));

        assertTrue(reserva.isActiva());
    }

    @Test
    void isActiva_enLimite_finHoy() {
        LocalDate hoy = LocalDate.now();

        Reserva reserva = new Reserva();
        reserva.setFechaInicio(hoy.minusDays(5));
        reserva.setFechaFin(hoy);

        assertTrue(reserva.isActiva());
    }


}
