package es.uclm.OasisProject.domain.entities;

import java.util.UUID;
import jakarta.persistence.*;

@Entity
public class Pago {
	
	@Id
	@GeneratedValue
	private UUID referencia;
	@Enumerated(EnumType.STRING)
	@Column(name = "metodo_pago", nullable = false)
	private MetodoPago metodoPago;
	@OneToOne
	@JoinColumn(name = "id_reserva", nullable = false)
	private Reserva reserva;

	public Pago(UUID referencia, MetodoPago metodoPago) {
		// TODO Auto-generated constructor stub
		this.referencia = referencia;
		this.metodoPago = metodoPago;
	}
	
	public Pago() {}

	public UUID getReferencia() {
		return referencia;
	}

	public void setReferencia(UUID referencia) {
		this.referencia = referencia;
	}

	public MetodoPago getMetodoPago() {
		return metodoPago;
	}

	public void setMetodoPago(MetodoPago metodoPago) {
		this.metodoPago = metodoPago;
	}

	public Reserva getReserva() {
		return reserva;
	}

	public void setReserva(Reserva reserva) {
		this.reserva = reserva;
	}

}
