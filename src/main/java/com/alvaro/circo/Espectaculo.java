package com.alvaro.circo;

import java.io.Serializable;
import java.time.LocalDate;

public class Espectaculo implements Serializable, Comparable<Espectaculo> {
	private static final long serialVersionUID = 1L;
	private Long id;
	private String nombre;
	private LocalDate fechaInicio;
	private LocalDate fechaFin;

	public Espectaculo(Long id, String nombre, LocalDate fechaInicio, LocalDate fechaFin) {
		this.id = id;
		this.nombre = nombre;
		this.fechaInicio = fechaInicio;
		this.fechaFin = fechaFin;
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

	@Override
	public int compareTo(Espectaculo o) {
		return this.id.compareTo(o.id);
	}

	@Override
	public String toString() {
		return String.format("ID: %d | %s | %s - %s", id, nombre, fechaInicio, fechaFin);
	}
}
