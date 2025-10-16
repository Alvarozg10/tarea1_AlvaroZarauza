package com.alvaro.circo;

import java.io.Serializable;

public class Credenciales implements Serializable {
	private static final long serialVersionUID = 1L;
	private String nombre;
	private String password;
	private Perfil perfil;

	public Credenciales(String nombre, String password, Perfil perfil) {
		super();
		this.nombre = nombre;
		this.password = password;
		this.perfil = perfil;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public Perfil getPerfil() {
		return perfil;
	}

	public void setPerfil(Perfil perfil) {
		this.perfil = perfil;
	}

}
