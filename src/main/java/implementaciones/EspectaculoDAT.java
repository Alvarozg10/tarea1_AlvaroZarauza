package implementaciones;

import java.io.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import com.alvaro.circo.Espectaculo;

public class EspectaculoDAT {

    private final File file;

    public EspectaculoDAT(String path) {
        this.file = new File(path);

        // Crear carpeta /ficheros si no existe
        File dir = file.getParentFile();
        if (dir != null && !dir.exists()) {
            dir.mkdirs();
            System.out.println("Carpeta creada: " + dir.getAbsolutePath());
        }

        // Crear fichero si no existe
        if (!file.exists()) {
            try {
                file.createNewFile();
                System.out.println("Fichero creado: " + file.getAbsolutePath());
            } catch (IOException e) {
                throw new RuntimeException("Error al crear el fichero de espectáculos.", e);
            }
        }
    }

    // Leer todos los espectáculos
    public List<Espectaculo> leerTodos() {
        if (!file.exists() || file.length() == 0) return new ArrayList<>();
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(file))) {
            Object obj = ois.readObject();
            if (obj instanceof List) {
                List<Espectaculo> lista = (List<Espectaculo>) obj;
                Collections.sort(lista);
                return lista;
            } else {
                return new ArrayList<>();
            }
        } catch (EOFException eof) {
            return new ArrayList<>();
        } catch (IOException | ClassNotFoundException e) {
            System.out.println("Error leyendo espectaculos.dat: " + e.getMessage());
            return new ArrayList<>();
        }
    }

    // Guardar todos
    public boolean guardarTodos(List<Espectaculo> lista) {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(file))) {
            oos.writeObject(lista);
            return true;
        } catch (IOException e) {
            System.out.println("Error escribiendo espectaculos.dat: " + e.getMessage());
            return false;
        }
    }

    // Obtener siguiente ID
    public int obtenerSiguienteId() {
        List<Espectaculo> lista = leerTodos();
        if (lista.isEmpty()) return 1;
        return (int) (lista.get(lista.size() - 1).getId() + 1);
    }

    // Método auxiliar para limpiar el fichero (opcional)
    public void limpiar() {
        try {
            guardarTodos(new ArrayList<>());
            System.out.println("Fichero de espectáculos limpiado correctamente.");
        } catch (Exception e) {
            System.out.println("Error al limpiar el fichero: " + e.getMessage());
        }
    }
}

