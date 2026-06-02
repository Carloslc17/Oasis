package es.uclm.OasisProject.domain.entities;

import java.time.LocalDate;
import jakarta.persistence.*;


@Entity
@Inheritance(strategy = InheritanceType.JOINED)
public class Reserva {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	@Column(name = "fecha_inicio", nullable = false)
	private LocalDate fechaInicio;
	@Column(name = "fecha_fin", nullable = false)
	private LocalDate fechaFin;
	@Enumerated(EnumType.STRING)
	@Column(name = "politica_cancelacion", nullable = false)
	private PoliticaCancelacion politicaCancelacion;
	
	@OneToOne(mappedBy = "reserva", cascade = CascadeType.ALL)
	private Pago pago;
	
	@ManyToOne(optional = false)
	@JoinColumn(name = "id_inquilino")
	private Inquilino inquilino;
	
	@ManyToOne(optional = false)
	@JoinColumn(name = "id_inmueble")
	private Inmueble inmueble;
	

	public Reserva(LocalDate fechaInicio, LocalDate fechaFin) {
		this.fechaInicio = fechaInicio;
		this.fechaFin = fechaFin;
	}
	
	public Reserva() {}
	

	public boolean isPagado() {
        return pago != null;
    }

    public boolean isActiva() {
        LocalDate hoy = LocalDate.now();
        return !hoy.isBefore(fechaInicio) && !hoy.isAfter(fechaFin);
    }
    
    public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}


	public LocalDate getFechaInicio() {
		return fechaInicio;
	}

	public void setFechaInicio(LocalDate fechaInicio) {
		this.fechaInicio = fechaInicio;
	}

	public LocalDate getFechaFin() {
		return fechaFin;
	}

	public void setFechaFin(LocalDate fechaFin) {
		this.fechaFin = fechaFin;
	}


	public PoliticaCancelacion getPoliticaCancelacion() {
		return politicaCancelacion;
	}

	public void setPoliticaCancelacion(PoliticaCancelacion politicaCancelacion) {
		this.politicaCancelacion = politicaCancelacion;
	}

	public Pago getPago() {
		return pago;
	}

	public void setPago(Pago pago) {
		this.pago = pago;
	}

	public Inquilino getInquilino() {
		return inquilino;
	}

	public void setInquilino(Inquilino inquilino) {
		this.inquilino = inquilino;
	}

	public Inmueble getInmueble() {
		return inmueble;
	}

	public void setInmueble(Inmueble inmueble) {
		this.inmueble = inmueble;
	}

}
