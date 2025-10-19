package com.alvaro.circo;

import java.io.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

//Clase EspectaculoDAT para el manejo del fichero espectaculos.dat
public class EspectaculoDAT {
	private final File file;

	public EspectaculoDAT(String path) {
		this.file = new File(path);
	}
	
	//Método para leer el fichero
	public List<Espectaculo> leerTodos() {
        if (!file.exists()) return new ArrayList<>();
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

	//Método para escribir el fichero
    public boolean guardarTodos(List<Espectaculo> lista) {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(file))) {
            oos.writeObject(lista);
            return true;
        } catch (IOException e) {
            System.out.println("Error escribiendo espectaculos.dat: " + e.getMessage());
            return false;
        }
    }
}

