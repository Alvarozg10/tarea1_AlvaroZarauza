package implementaciones;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

import com.alvaro.circo.Credenciales;
import com.alvaro.circo.Perfil;
import com.alvaro.circo.Persona;

public class FicheroPersonas {

    private static final String FILE_PATH = "src/main/resources/credenciales.txt";

    public static boolean existeNombreUsuario(String nombreUsuario) throws IOException {
        File file = new File(FILE_PATH);
        if (!file.exists()) return false;

        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            String linea;
            while ((linea = br.readLine()) != null) {
                String[] partes = linea.split("[;|]");
                if (partes.length > 1 && partes[1].trim().equalsIgnoreCase(nombreUsuario)) {
                    return true;
                }
            }
        }
        return false;
    }

    public static int obtenerSiguienteId() {
        int maxId = 0;
        File file = new File(FILE_PATH);
        if (!file.exists()) return 1;

        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            String linea;
            while ((linea = br.readLine()) != null) {
                String[] partes = linea.split("[;|]");
                if (partes.length > 0) {
                    try {
                        int id = Integer.parseInt(partes[0].trim());
                        if (id > maxId) maxId = id;
                    } catch (NumberFormatException e) {
                        System.out.println("ID inválido en línea: " + linea);
                    }
                }
            }
        } catch (Exception e) {
            System.out.println("Error al obtener ID: " + e.getMessage());
        }
        return maxId + 1;
    }

    public static void guardarPersona(Persona persona) throws IOException {
        File file = new File(FILE_PATH);
        if (!file.exists()) file.createNewFile();

        boolean necesitaSalto = false;
        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            String ultima = null, linea;
            while ((linea = br.readLine()) != null) ultima = linea;
            if (ultima != null && !ultima.isEmpty()) necesitaSalto = true;
        }

        try (BufferedWriter bw = new BufferedWriter(new FileWriter(file, true))) {
            if (necesitaSalto) bw.newLine(); 
            Credenciales c = persona.getCredenciales();

            String linea = c.getIdPersona() + "|" +
                           c.getNombreUsuario() + "|" +
                           c.getPassword() + "|" +
                           c.getEmail() + "|" +
                           c.getNombrePersona() + "|" +
                           c.getNacionalidad() + "|" +
                           c.getPerfil().name();

            bw.write(linea);
        }
    }


    public static List<Credenciales> obtenerCoordinadores() {
        List<Credenciales> coordinadores = new ArrayList<>();
        File file = new File(FILE_PATH);
        if (!file.exists()) return coordinadores;

        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            String linea;
            while ((linea = br.readLine()) != null) {
                String[] partes = linea.split("[;|]");
                if (partes.length == 7 && partes[6].trim().equalsIgnoreCase("coordinacion")) {
                    try {
                        int id = Integer.parseInt(partes[0].trim());
                        String usuario = partes[1].trim();
                        String password = partes[2].trim();
                        String email = partes[3].trim();
                        String nombre = partes[4].trim();
                        String nacionalidad = partes[5].trim();

                        Credenciales c = new Credenciales(
                            id, usuario, password, email, nombre, nacionalidad, Perfil.COORDINACION
                        );
                        coordinadores.add(c);
                    } catch (NumberFormatException e) {
                        System.out.println("Error leyendo ID de coordinador: " + e.getMessage());
                    }
                }
            }
        } catch (IOException e) {
            System.out.println("Error al leer coordinadores: " + e.getMessage());
        }

        return coordinadores;
    }
}


