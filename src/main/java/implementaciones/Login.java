package implementaciones;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

import com.alvaro.circo.Credenciales;
import com.alvaro.circo.Perfil;

public class Login {

    private static final String FILE_PATH = "src/main/resources/credenciales.txt";

    private static Credenciales usuarioActual = null;
    private static List<Credenciales> credenciales = new ArrayList<>();
    private static boolean loaded = false;

    
    public static boolean iniciarSesion(String nombreUsuario, String password) {
        if (nombreUsuario == null || nombreUsuario.trim().isEmpty() ||
            password == null || password.trim().isEmpty()) {
            System.out.println("Usuario y contraseña no pueden estar vacíos.");
            return false;
        }

        // Menú Admin
        if (nombreUsuario.equalsIgnoreCase("admin") && password.equals("admin")) {
            usuarioActual = new Credenciales(
                0, "admin", "admin", "admin@circo.es",
                "Administrador General", "España", Perfil.ADMIN
            );
            return true;
        }

        // Credenciales cargadas
        ensureLoaded();

        for (Credenciales c : credenciales) {
            if (c.getNombreUsuario().equalsIgnoreCase(nombreUsuario) &&
                c.getPassword().equals(password)) {
                usuarioActual = c;
                return true;
            }
        }

        return false;
    }

    public static void cerrarSesion() {
        usuarioActual = null;
    }

    public static Credenciales getUsuarioActual() {
        return usuarioActual;
    }

    public static boolean haySesionActiva() {
        return usuarioActual != null;
    }

    public static void recargarCredenciales() {
        credenciales = cargarCredenciales();
        loaded = true;
        System.out.println("Lista de credenciales recargada desde fichero.");
    }

    private static void ensureLoaded() {
        if (!loaded) {
            credenciales = cargarCredenciales();
            loaded = true;
        }
    }

    private static List<Credenciales> cargarCredenciales() {
        List<Credenciales> lista = new ArrayList<>();
        File file = new File(FILE_PATH);

        if (!file.exists()) {
            System.out.println("No se encontró el fichero de credenciales en " + FILE_PATH);
            return lista;
        }

        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            String linea;
            while ((linea = br.readLine()) != null) {
                if (linea.trim().isEmpty()) continue;
                String[] partes = linea.split("[;|]");
                if (partes.length < 7) continue;

                try {
                    int idPersona = Integer.parseInt(partes[0].trim());
                    String usuario = partes[1].trim();
                    String pass = partes[2].trim();
                    String email = partes[3].trim();
                    String nombre = partes[4].trim();
                    String nacionalidad = partes[5].trim();
                    String perfilStr = partes[6].trim().toUpperCase();

                    Perfil perfil;
                    switch (perfilStr) {
                        case "COORDINACION": perfil = Perfil.COORDINACION; break;
                        case "ARTISTA": perfil = Perfil.ARTISTA; break;
                        case "ADMIN": perfil = Perfil.ADMIN; break;
                        default: perfil = Perfil.INVITADO; break;
                    }

                    lista.add(new Credenciales(idPersona, usuario, pass, email, nombre, nacionalidad, perfil));
                } catch (NumberFormatException ignored) {}
            }
        } catch (IOException e) {
            System.out.println("Error leyendo credenciales: " + e.getMessage());
        }

        return lista;
    }
}
