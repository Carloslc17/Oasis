package es.uclm.OasisProject.domain.entities;

import java.util.Date;
import jakarta.persistence.*;


@Entity
@Inheritance(strategy = InheritanceType.JOINED)
public class Reserva {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	@Temporal(TemporalType.DATE)
	private Date fechaInicio;
	@Temporal(TemporalType.DATE)
	private Date fechaFin;
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
	

	public Reserva(Date fechaInicio, Date fechaFin) {
		// TODO Auto-generated constructor stub
		this.fechaInicio = fechaInicio;
		this.fechaFin = fechaFin;
	}
	
	public Reserva() {}
	

	public boolean isPagado() {
        return pago != null;
    }

    public boolean isActiva() {
        Date hoy = new Date();
        return fechaInicio.before(hoy) && fechaFin.after(hoy);
    }


	public Date getFechaInicio() {
		return fechaInicio;
	}

	public void setFechaInicio(Date fechaInicio) {
		this.fechaInicio = fechaInicio;
	}

	public Date getFechaFin() {
		return fechaFin;
	}

	public void setFechaFin(Date fechaFin) {
		this.fechaFin = fechaFin;
	}

}
