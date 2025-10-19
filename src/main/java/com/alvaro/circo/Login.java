package com.alvaro.circo;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class Login {

    private static Credenciales usuarioActual = null; // Sesión actual
    private static final String RUTA_CREDENCIALES = "/credenciales.txt";

    /**
     * Método para iniciar sesión
     */
    public static boolean iniciarSesion(String nombreUsuario, String password) {
        // Comprobar que los campos no están vacíos
        if (nombreUsuario == null || nombreUsuario.trim().isEmpty() ||
            password == null || password.trim().isEmpty()) {
            System.out.println("Usuario y contraseña no pueden estar vacíos.");
            return false;
        }

        // Comprobar credenciales admin
        if (nombreUsuario.equals("admin") && password.equals("admin")) {
            usuarioActual = new Credenciales(0, "admin", "admin", "admin@circo.es",
                    "Administrador", "N/A", Perfil.ADMIN);
            System.out.println("Sesión iniciada como ADMIN.");
            return true;
        }

        // Buscar en credenciales.txt
        try (InputStream inputStream = Login.class.getResourceAsStream(RUTA_CREDENCIALES);
             BufferedReader br = new BufferedReader(new InputStreamReader(inputStream))) {

            String linea;
            while ((linea = br.readLine()) != null) {
                String[] partes = linea.split("\\|");

                if (partes.length == 7) {
                    int idPersona = Integer.parseInt(partes[0]);
                    String usuario = partes[1];
                    String pass = partes[2];
                    String email = partes[3];
                    String nombre = partes[4];
                    String nacionalidad = partes[5];
                    String perfilStr = partes[6].toLowerCase();

                    Perfil perfil;
                    switch (perfilStr) {
                        case "coordinacion":
                            perfil = Perfil.COORDINACION;
                            break;
                        case "artista":
                            perfil = Perfil.ARTISTA;
                            break;
                        default:
                            perfil = Perfil.INVITADO;
                            break;
                    }

                    // Comprobar si las credenciales coinciden
                    if (usuario.equals(nombreUsuario) && pass.equals(password)) {
                        usuarioActual = new Credenciales(idPersona, usuario, pass, email, nombre, nacionalidad, perfil);
                        System.out.println("Sesión iniciada como " + perfil);
                        return true;
                    }
                }
            }

        } catch (IOException | NumberFormatException e) {
            System.out.println("Error al leer credenciales: " + e.getMessage());
            return false;
        }

        System.out.println("Usuario o contraseña incorrectos.");
        return false;
    }

    /**
     * Método para cerrar sesión
     */
    public static void cerrarSesion() {
        if (usuarioActual != null) {
            System.out.println("Sesión cerrada de " + usuarioActual.getNombreUsuario());
            usuarioActual = null;
        } else {
            System.out.println("No hay ninguna sesión iniciada.");
        }
    }

    /**
     * Devuelve el usuario actual (sesión)
     */
    public static Credenciales getUsuarioActual() {
        return usuarioActual;
    }

    /**
     * Devuelve si hay sesión activa
     */
    public static boolean haySesionActiva() {
        return usuarioActual != null;
    }
}
