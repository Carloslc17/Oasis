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
    
    public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
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
