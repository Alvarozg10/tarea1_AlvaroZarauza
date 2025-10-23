package com.alvaro.circo;

import java.io.Serializable;

public class Credenciales implements Serializable {
	private static final long serialVersionUID = 1L;

	private int idPersona;
	private String nombreUsuario;
	private String password;
	private String email;
	private String nombrePersona;
	private String nacionalidad;
	private Perfil perfil;

	// Constructor
	public Credenciales(int idPersona, String nombreUsuario, String password, String email, String nombrePersona,
			String nacionalidad, Perfil perfil) {
		this.idPersona = idPersona;
		this.nombreUsuario = nombreUsuario;
		this.password = password;
		this.email = email;
		this.nombrePersona = nombrePersona;
		this.nacionalidad = nacionalidad;
		this.perfil = perfil;
	}

	// Getters y Setters
	public int getIdPersona() {
		return idPersona;
	}

	public void setIdPersona(int idPersona) {
		this.idPersona = idPersona;
	}

	public String getNombreUsuario() {
		return nombreUsuario;
	}

	public void setNombreUsuario(String nombreUsuario) {
		this.nombreUsuario = nombreUsuario;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getNombrePersona() {
		return nombrePersona;
	}

	public void setNombrePersona(String nombrePersona) {
		this.nombrePersona = nombrePersona;
	}

	public String getNacionalidad() {
		return nacionalidad;
	}

	public void setNacionalidad(String nacionalidad) {
		this.nacionalidad = nacionalidad;
	}

	public Perfil getPerfil() {
		return perfil;
	}

	public void setPerfil(Perfil perfil) {
		this.perfil = perfil;
	}

	@Override
	public String toString() {
		return "ID: " + idPersona + " | Usuario: " + nombreUsuario + " | Nombre: " + nombrePersona + " | Nacionalidad: " + nacionalidad + " | Perfil: " + perfil;
	}
}
