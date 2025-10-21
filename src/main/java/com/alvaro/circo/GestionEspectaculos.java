package com.alvaro.circo;

import java.time.LocalDate;
import java.util.List;
import java.util.Scanner;

public class GestionEspectaculos {

    private static final String FICHERO = "espectaculos.dat";
    private static final EspectaculoDAT dao = new EspectaculoDAT(FICHERO);

    public static void crearEspectaculo(Scanner sc) {
        System.out.println("\n--- Crear nuevo espectáculo ---");

        // Cargar los espectáculos existentes del fichero
        List<Espectaculo> lista = dao.leerTodos();
        int nuevoId = dao.obtenerSiguienteId();
        
        System.out.print("Nombre del espectáculo: ");
        String nombre = sc.nextLine().trim();

        // Validar nombre
        if (nombre.isEmpty() || nombre.length() > 25) {
            System.out.println("El nombre no puede estar vacío ni superar los 25 caracteres.");
            return;
        }

        // Comprobar unicidad
        for (Espectaculo e : lista) {
            if (e.getNombre().equalsIgnoreCase(nombre)) {
                System.out.println("Ya existe un espectáculo con ese nombre.");
                return;
            }
        }

        try {
            System.out.print("Fecha de inicio (AAAA-MM-DD): ");
            LocalDate inicio = LocalDate.parse(sc.nextLine().trim());

            System.out.print("Fecha de fin (AAAA-MM-DD): ");
            LocalDate fin = LocalDate.parse(sc.nextLine().trim());

            // Validaciones de fechas
            if (fin.isBefore(inicio)) {
                System.out.println("La fecha de fin no puede ser anterior a la de inicio.");
                return;
            }
            if (inicio.plusYears(1).isBefore(fin)) {
                System.out.println("El periodo no puede superar un año.");
                return;
            }

            // Obtener lista de coordinadores registrados
            List<Credenciales> coordinadores = FicheroPersonas.obtenerCoordinadores();
            if (coordinadores.isEmpty()) {
                System.out.println("No hay coordinadores registrados. No se puede crear el espectáculo.");
                return;
            }

            System.out.println("Seleccione coordinador existente:");
            for (Credenciales c : coordinadores) {
                System.out.println(c.getIdPersona() + " - " + c.getNombrePersona() + " (" + c.getNombreUsuario() + ")");
            }

            System.out.print("ID del coordinador: ");
            int idCoord = Integer.parseInt(sc.nextLine().trim());
            Credenciales coordinador = coordinadores.stream()
                    .filter(c -> c.getIdPersona() == idCoord)
                    .findFirst()
                    .orElse(null);

            if (coordinador == null) {
                System.out.println("ID de coordinador no válido.");
                return;
            }

            // Crear espectáculo
            Espectaculo nuevo = new Espectaculo(nuevoId, nombre, inicio, fin, coordinador);
            lista.add(nuevo);

            // Guardar todos los espectáculos en el fichero binario
            if (dao.guardarTodos(lista)) {
                System.out.println("Espectáculo creado y guardado correctamente.");
            } else {
                System.out.println("Error al guardar el espectáculo.");
            }

        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }
}
