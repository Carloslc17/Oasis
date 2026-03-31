package es.uclm.OasisProject.domain.entities;

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
	private String precio_noche;
	
	@ManyToOne
	@JoinColumn(name = "id_propietario", nullable = false)
	private Propietario Owner;

	public Inmueble(String direccion, String precio_noche) {
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

	public String getPrecio_noche() {
		return precio_noche;
	}

	public void setPrecio_noche(String precio_noche) {
		this.precio_noche = precio_noche;
	}
	
	public Propietario getOwner() {
		return Owner;
	}
	
	public void setOwner(Propietario owner) {
		this.Owner = owner;
	}

}
