package com.alvaro.circo;

import java.io.Serializable;

public abstract class Persona implements Serializable {
	private static final long serialVersionUID = 1L;
	private Long id;
	private String nombre;
	private String email;
	private String nacionalidad;
	private Credenciales credenciales;

	public Persona(Long id, String nombre, String email, String nacionalidad, Credenciales credenciales) {
		super();
		this.id = id;
		this.nombre = nombre;
		this.email = email;
		this.nacionalidad = nacionalidad;
		this.credenciales = credenciales;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getNacionalidad() {
		return nacionalidad;
	}

	public void setNacionalidad(String nacionalidad) {
		this.nacionalidad = nacionalidad;
	}

	public Credenciales getCredenciales() {
		return credenciales;
	}

	public void setCredenciales(Credenciales credenciales) {
		this.credenciales = credenciales;
	}

}
