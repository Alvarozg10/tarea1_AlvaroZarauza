package com.alvaro.circo;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class FicheroPersonas {

    private static final String FILE_PATH = "src/main/resources/credenciales.txt";

    public static boolean existeNombreUsuario(String nombreUsuario) throws IOException {
        File file = new File(FILE_PATH);
        if (!file.exists()) return false;

        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            String linea;
            while ((linea = br.readLine()) != null) {
                String[] partes = linea.split(";");
                if (partes.length > 1 && partes[1].equalsIgnoreCase(nombreUsuario)) {
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
                String[] partes = linea.split(";");
                if (partes.length > 0) {
                    int id = Integer.parseInt(partes[0]);
                    if (id > maxId) maxId = id;
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

        try (BufferedWriter bw = new BufferedWriter(new FileWriter(file, true))) {
            Credenciales c = persona.getCredenciales();
            // formato: id;usuario;password;email;nombre;nacionalidad;perfil
            String linea = c.getIdPersona() + ";" +
                           c.getNombreUsuario() + ";" +
                           c.getPassword() + ";" +
                           c.getEmail() + ";" +
                           c.getNombrePersona() + ";" +
                           c.getNacionalidad() + ";" +
                           c.getPerfil().name();
            bw.write(linea);
            bw.newLine();
        }
    }
}

