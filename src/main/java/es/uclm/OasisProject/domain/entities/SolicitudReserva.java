package es.uclm.OasisProject.domain.entities;

import jakarta.persistence.*;

@Entity
public class SolicitudReserva extends Reserva {
	
	private boolean confirmada;

	public SolicitudReserva() {
		this.confirmada = false;
	}
	

	public void confirmarReserva() {
        this.confirmada = true;
    }

    public boolean isConfirmada() {
        return confirmada;
    }


}
