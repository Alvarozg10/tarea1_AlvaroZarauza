package com.alvaro.circo;

import java.io.Serializable;
import java.time.LocalDate;

public class Espectaculo implements Serializable, Comparable<Espectaculo> {
    private static final long serialVersionUID = 1L;
    private Long id;
    private String nombre;
    private LocalDate fechaInicio;
    private LocalDate fechaFin;
    private Credenciales coordinador;

    // Constructor CU1 
    public Espectaculo(Long id, String nombre, LocalDate fechaInicio, LocalDate fechaFin) {
        this.id = id;
        this.nombre = nombre;
        this.fechaInicio = fechaInicio;
        this.fechaFin = fechaFin;
    }

    // Nuevo constructor 
    public Espectaculo(int id, String nombre, LocalDate fechaInicio, LocalDate fechaFin, Credenciales coordinador) {
        this.id = Long.valueOf(id);
        this.nombre = nombre;
        this.fechaInicio = fechaInicio;
        this.fechaFin = fechaFin;
        this.coordinador = coordinador;
    }

    // Getters y Setters
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

    public Credenciales getCoordinador() {
        return coordinador;
    }

    public void setCoordinador(Credenciales coordinador) {
        this.coordinador = coordinador;
    }

    @Override
    public int compareTo(Espectaculo o) {
        return this.id.compareTo(o.id);
    }

    @Override
    public String toString() {
        String infoCoordinador = (coordinador != null)
                ? " | Coordinador: " + coordinador.getNombrePersona(): "";
        return "ID: " + id + " | " + nombre + " (" + fechaInicio + " - " + fechaFin + ")" + infoCoordinador;
    }
}
