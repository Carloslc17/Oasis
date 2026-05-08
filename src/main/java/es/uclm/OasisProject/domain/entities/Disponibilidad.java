package es.uclm.OasisProject.domain.entities;

import java.time.LocalDate;
import jakarta.persistence.*;

@Entity
public class Disponibilidad {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	@Column(name = "fecha_inicio", nullable = false)
	private LocalDate fechaInicio;
	@Column(name = "fecha_fin", nullable = false)
	private LocalDate fechaFin;
	@Column(name = "precio", nullable = false)
	private double precio;
	@Column(name = "directa", nullable = false)
	private boolean directa;
	@Enumerated(EnumType.STRING)
	@Column(name = "politica_cancelacion", nullable = false)
	private PoliticaCancelacion politicaCancelacion;
	
	@ManyToOne(optional = false)
	@JoinColumn(name = "id_inmueble")
	private Inmueble inmueble;
	

	public Disponibilidad(LocalDate fechaInicio, LocalDate fechaFin, double precio, boolean directa) {
		// TODO Auto-generated constructor stub
		this.fechaInicio = fechaInicio;
		this.fechaFin = fechaFin;
		this.precio = precio;
		this.directa = directa;
	}
	
	public Disponibilidad() {}
	
	
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

	public double getPrecio() {
		return precio;
	}

	public void setPrecio(double precio) {
		this.precio = precio;
	}

	public boolean isDirecta() {
		return directa;
	}

	public void setDirecta(boolean directa) {
		this.directa = directa;
	}

	public PoliticaCancelacion getPoliticaCancelacion() {
		return politicaCancelacion;
	}

	public void setPoliticaCancelacion(PoliticaCancelacion politicaCancelacion) {
		this.politicaCancelacion = politicaCancelacion;
	}

	public Inmueble getInmueble() {
		return inmueble;
	}

	public void setInmueble(Inmueble inmueble) {
		this.inmueble = inmueble;
	}
	
	
	
	

}
