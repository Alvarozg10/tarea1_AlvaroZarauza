package main;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import com.alvaro.circo.Espectaculo;
import com.alvaro.circo.EspectaculoDAT;

public class App {
    private static final String FICHERO = "espectaculos.dat";
    private static final EspectaculoDAT dao = new EspectaculoDAT(FICHERO);

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        System.out.println("=== Gestión Circo ===");

        boolean salir = false;
        while (!salir) {
            System.out.println("\nMenú principal:");
            System.out.println("1. Ver espectáculos");
            System.out.println("2. Iniciar sesión");
            System.out.println("3. Crear fichero de ejemplo (prueba de creación de fichero)");
            System.out.println("4. Salir");
            System.out.print("> ");
            String opcion = sc.nextLine().trim();
            switch (opcion) {
                case "1":
                    mostrarEspectaculos(false);
                    break;
                case "2":
                    iniciarSesion(sc);
                    break;
                case "3":
                    crearEjemplo();
                    break;
                case "4":
                    salir = true;
                    break;
                default:
                    System.out.println("Opción no válida.");
            }
        }

        System.out.println("Adiós.");
        sc.close();
    }

    private static void mostrarEspectaculos(boolean autenticado) {
        List<Espectaculo> lista = dao.leerTodos();
        if (lista.isEmpty()) {
            System.out.println("[No hay espectáculos registrados en " + FICHERO + "]");
            return;
        }
        System.out.println("\n--- Lista de espectáculos ---");
        for (Espectaculo e : lista) {
            System.out.println(e.toString());
        }
    }

    private static void iniciarSesion(Scanner sc) {
        System.out.println("\n--- Iniciar sesión ---");
        System.out.print("Usuario: ");
        String user = sc.nextLine().trim();
        System.out.print("Contraseña: ");
        String pass = sc.nextLine().trim();

        if ("admin".equals(user) && "admin".equals(pass)) {
            System.out.println("Sesión iniciada como ADMIN.");
            menuAdmin(sc);
        } else {
            System.out.println("Credenciales no válidas o usuario no implementado (por ahora solo invitado).");
        }
    }

    private static void menuAdmin(Scanner sc) {
        boolean volver = false;
        while (!volver) {
            System.out.println("\nMenú ADMIN:");
            System.out.println("1) Ver espectáculos (completo)");
            System.out.println("2) Volver al menú principal");
            System.out.print("> ");
            String op = sc.nextLine().trim();
            switch (op) {
                case "1":
                    mostrarEspectaculos(true);
                    break;
                case "2":
                    volver = true;
                    break;
                default:
                    System.out.println("Opción no válida.");
            }
        }
    }

    private static void crearEjemplo() {
        List<Espectaculo> lista = new ArrayList<>();
        lista.add(new Espectaculo(1L, "Gran Gala de la Noche", LocalDate.of(2024, 6, 1), LocalDate.of(2024, 6, 30)));
        lista.add(new Espectaculo(2L, "Circo en la Ciudad", LocalDate.of(2024, 7, 1), LocalDate.of(2024, 7, 31)));
        lista.add(new Espectaculo(3L, "Funciones de Verano", LocalDate.of(2024, 8, 1), LocalDate.of(2024, 8, 31)));
        boolean ok = dao.guardarTodos(lista);
        if (ok) System.out.println("Fichero de ejemplo creado: " + FICHERO);
        else System.out.println("Error al crear el fichero de ejemplo.");
    }
}
