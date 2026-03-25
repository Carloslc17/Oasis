package es.uclm.OasisProject.domain.entities;

import jakarta.persistence.*;

@Entity
@DiscriminatorValue("INQUILINO")

public class Inquilino extends Usuario{
	
	

	public Inquilino(String login, String pass, String nombre, String apellidos, String direccion) {
		super(login,pass,nombre,apellidos,direccion);
	}
	
	public Inquilino() {}

}