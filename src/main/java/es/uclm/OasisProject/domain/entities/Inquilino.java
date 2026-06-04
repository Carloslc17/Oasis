package es.uclm.OasisProject.domain.entities;

import jakarta.persistence.*;
import java.util.Set;

@Entity
@DiscriminatorValue("INQUILINO")

public class Inquilino extends Usuario{
	
	@ManyToMany
	@JoinTable(name = "LISTA_DESEOS", joinColumns = @JoinColumn(name = "id_inquilino"),
			   inverseJoinColumns = @JoinColumn(name = "id_inmueble"))
	private Set<Inmueble> listaDeseos;

	public Inquilino(String login, String pass, String nombre, String apellidos, String direccion) {
		super(login,pass,nombre,apellidos,direccion);
	}
	
	public Inquilino() {}
	
	public Set<Inmueble> getListaDeseos() {
		return listaDeseos;
	}

	public void setListaDeseos(Set<Inmueble> listaDeseos) {
		this.listaDeseos = listaDeseos;
	}
	
	public void addListaDeseos(Inmueble inmueble) {
		this.listaDeseos.add(inmueble);
	}

}