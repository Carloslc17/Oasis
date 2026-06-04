package es.uclm.OasisProject.domain.entities;

import jakarta.persistence.*;

@Entity
@Table(name = "USUARIO")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "tipo_usuario", discriminatorType = DiscriminatorType.STRING)
public abstract class Usuario {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	protected int id;
	@Column(name = "login", unique = true, nullable = false)
	protected String login;
	@Column(name = "password", nullable = false)
	protected String password;
	@Column(name = "nombre", nullable = false)
	protected String nombre;
	@Column(name = "apellidos", nullable = false)
	protected String apellidos;
	@Column(name = "direccion", nullable = false)
	protected String direccion;

	protected Usuario(String login, String password, String nombre, String apellidos, String direccion) {
		this.login = login;
		this.password = password;
		this.nombre = nombre;
		this.apellidos = apellidos;
		this.direccion = direccion;
	}
	
	protected Usuario() {}
	
	public int getId() {
		return this.id;
	}
	
	public String getLogin() {
		return this.login;
	}
	
	public String getPassword() {
		return this.password;
	}
	
	public String getNombre() {
		return this.nombre;
	}
	
	public String getApellidos() {
		return this.apellidos;
	}
	
	public String getDireccion() {
		return this.direccion;
	}
	
	public void setId(int id) {
		this.id = id;
	}

	public void setLogin(String login) {
		this.login = login;
	}

	public void setPassword(String pass) {
		this.password = pass;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public void setApellidos(String apellidos) {
		this.apellidos = apellidos;
	}

	public void setDireccion(String direccion) {
		this.direccion = direccion;
	}
	
	

}