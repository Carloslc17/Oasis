package es.uclm.OasisProject.domain.entities;

import jakarta.persistence.*;

@Entity
@DiscriminatorValue("PROPIETARIO")

public class Propietario extends Usuario{
	
	

	public Propietario(String login, String pass, String nombre, String apellidos, String direccion) {
		super(login,pass,nombre,apellidos,direccion);
	}
	
	public Propietario() {}

}