package es.uclm.OasisProject.domain.entities;

import java.util.UUID;
import jakarta.persistence.*;

@Entity
public class Pago {
	
	@Id
	@GeneratedValue
	private UUID referencia;
	@Column(name = "metodo_pago", nullable = false)
	private MetodoPago metodoPago;
	@OneToOne
	@JoinColumn(name = "id_reserva", nullable = false)
	private Reserva reserva;

	public Pago() {
		// TODO Auto-generated constructor stub
	}

}
