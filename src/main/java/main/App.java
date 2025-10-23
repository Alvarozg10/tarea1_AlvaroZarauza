package main;

import java.util.Scanner;
import java.util.List;
import com.alvaro.circo.*;

import implementaciones.EspectaculoDAT;
import implementaciones.GestionEspectaculos;
import implementaciones.Login;
import implementaciones.Registro;

public class App {
    private static final String FICHERO = "espectaculos.dat";
    private static final EspectaculoDAT dao = new EspectaculoDAT(FICHERO);

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        boolean salir = false;

        while (!salir) {
        	System.out.println("=== Gestión Circo ===");
            System.out.println("\n=== Menú principal ===");

            if (!Login.haySesionActiva()) {
                System.out.println("1. Iniciar sesión");
            } else {
                Credenciales user = Login.getUsuarioActual();
                System.out.println("Sesión: " + user.getNombreUsuario() + " (" + user.getPerfil() + ")");
                System.out.println("2. Cerrar sesión");
            }

            System.out.println("3. Ver espectáculos");
            System.out.println("0. Salir");
            System.out.print("> ");

            String opcion = sc.nextLine().trim();

            switch (opcion) {
                case "1":
                    if (!Login.haySesionActiva()) {
                        iniciarSesion(sc);
                    } else {
                        System.out.println("Ya hay una sesión activa.");
                    }
                    break;

                case "2":
                    if (Login.haySesionActiva()) {
                        Login.cerrarSesion();
                        System.out.println("Sesión cerrada correctamente.");
                    } else {
                        System.out.println("No hay sesión activa.");
                    }
                    break;

                case "3":
                    mostrarEspectaculos();
                    break;

                case "0":
                    System.out.println("Saliendo...");
                    salir = true;
                    break;

                default:
                    System.out.println("Opción no válida.");
            }
        }

        sc.close();
        System.out.println("Adiós.");
    }
    
    //Métodos
    private static void mostrarEspectaculos() {
        List<Espectaculo> lista = dao.leerTodos();
        if (lista.isEmpty()) {
            System.out.println("[No hay espectáculos registrados en " + FICHERO + "]");
            return;
        }
        System.out.println("\n--- Lista de espectáculos ---");
        for (Espectaculo e : lista) {
            System.out.println(e);
        }
    }

    private static void iniciarSesion(Scanner sc) {
        System.out.println("\n--- Iniciar sesión ---");
        System.out.print("Usuario: ");
        String user = sc.nextLine().trim();
        System.out.print("Contraseña: ");
        String pass = sc.nextLine().trim();

        if (Login.iniciarSesion(user, pass)) {
            Credenciales actual = Login.getUsuarioActual();
            System.out.println("Sesión iniciada como " + actual.getPerfil().name().toUpperCase() + ".");

            switch (actual.getPerfil()) {
                case ADMIN:
                    menuAdmin(sc);
                    break;
                case COORDINACION:
                	System.out.println("Bienvenido/a, " + actual.getNombrePersona()+".");
                    menuCoordinacion(sc);
                    break;
                case ARTISTA:
                    System.out.println("Bienvenido/a, " + actual.getNombrePersona()+".");
                    break;
                default:
                    System.out.println("Sesión iniciada, pero sin menú específico.");
            }
        } else {
            System.out.println("Credenciales incorrectas.");
        }
    }

    //Menú Admin
    private static void menuAdmin(Scanner sc) {
        boolean volver = false;

        while (!volver) {
            System.out.println("\n=== MENÚ ADMIN ===");
            System.out.println("1. Ver espectáculos");
            System.out.println("2. Crear nuevo espectáculo (CU5A)");
            System.out.println("3. Registrar nueva persona (CU3)");
            System.out.println("4. Cerrar sesión y volver");
            System.out.print("> ");

            String opcion = sc.nextLine().trim();

            switch (opcion) {
                case "1":
                    mostrarEspectaculos();
                    break;

                case "2":
                    GestionEspectaculos.crearEspectaculo(sc);
                    break;

                case "3":
                    System.out.println("\n--- Registrar nueva persona (CU3) ---");
                    Registro.registrarUsuario();
                    break;

                case "4":
                    Login.cerrarSesion();
                    volver = true;
                    System.out.println("Sesión cerrada. Volviendo al menú principal...");
                    break;

                default:
                    System.out.println("Opción no válida. Intente de nuevo.");
            }
        }
    }



    //Menú Coordinación
    private static void menuCoordinacion(Scanner sc) {
        boolean volver = false;
        while (!volver) {
            System.out.println("\n=== MENÚ COORDINACIÓN ===");
            System.out.println("1. Ver espectáculos");
            System.out.println("2. Crear nuevo espectáculo (CU5A)");
            System.out.println("3. Cerrar sesión y volver");
            System.out.print("> ");
            String op = sc.nextLine().trim();

            switch (op) {
                case "1":
                    mostrarEspectaculos();
                    break;
                case "2":
                    GestionEspectaculos.crearEspectaculo(sc);
                    break;
                case "3":
                    Login.cerrarSesion();
                    System.out.println("Sesión cerrada. Volviendo al menú principal...");
                    volver = true;
                    break;
                default:
                    System.out.println("Opción no válida.");
            }
        }
    }
}
