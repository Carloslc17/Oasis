package es.uclm.OasisProject.domain.entities;

import java.util.List;
import jakarta.persistence.*;

@Entity
@Table (name = "INMUEBLE")
public class Inmueble {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	@Column(name = "direccion", nullable = false)
	private String direccion;
	@Column(name = "precio_noche", nullable = false)
	private double precio_noche;
	
	@ManyToOne
	@JoinColumn(name = "id_propietario", nullable = false)
	private Propietario Owner;
	
	@OneToMany(mappedBy = "inmueble", cascade = CascadeType.ALL, orphanRemoval=true)
	private List<Disponibilidad> disponibilidades;

	public Inmueble(String direccion, double precio_noche) {
		// TODO Auto-generated constructor stub
		this.direccion = direccion;
		this.precio_noche = precio_noche;
	}
	
	public Inmueble() {}
	
	
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getDireccion() {
		return direccion;
	}

	public void setDireccion(String direccion) {
		this.direccion = direccion;
	}

	public double getPrecio_noche() {
		return precio_noche;
	}

	public void setPrecio_noche(double precio_noche) {
		this.precio_noche = precio_noche;
	}
	
	public Propietario getOwner() {
		return Owner;
	}
	
	public void setOwner(Propietario owner) {
		this.Owner = owner;
	}
	
	public List<Disponibilidad> getDisponibilidades() {
		return disponibilidades;
	}

	public void setDisponibilidades(List<Disponibilidad> disponibilidades) {
		this.disponibilidades = disponibilidades;
	}

}
