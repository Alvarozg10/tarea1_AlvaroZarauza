package com.alvaro.circo;

import java.io.IOException;
import java.util.List;
import java.util.Scanner;

public class Registro {

	public static void registrarUsuario() {
		Scanner sc = new Scanner(System.in);

		try {
			System.out.println("=== REGISTRO DE NUEVA PERSONA ===");

			System.out.print("Nombre real: ");
			String nombreReal = sc.nextLine().trim();

			System.out.print("Email: ");
			String email = sc.nextLine().trim();

			List<String[]> paises = PaisesXMLReader.leerPaises();
			System.out.println("\n--- Lista de Países ---");
			for (String[] p : paises) {
				System.out.println(p[0] + " - " + p[1]);
			}

			String nacionalidad;
			while (true) {
				System.out.print("Introduzca el ID de su nacionalidad: ");
				String id = sc.nextLine().trim().toUpperCase();
				nacionalidad = PaisesXMLReader.obtenerNombrePaisPorId(paises, id);
				if (nacionalidad != null)
					break;
				System.out.println("ID no válido. Intente nuevamente.");
			}

			System.out.print("Perfil (1=Coordinacion, 2=Artista): ");
			int tipo = Integer.parseInt(sc.nextLine());
			Perfil perfil = (tipo == 1) ? Perfil.COORDINACION : Perfil.ARTISTA;

			boolean senior = false;
			String fechaSenior = null;
			String apodo = null;
			String especialidades = null;

			if (perfil == Perfil.COORDINACION) {
				System.out.print("¿Es senior? (S/N): ");
				senior = sc.nextLine().equalsIgnoreCase("S");
				if (senior) {
					System.out.print("Fecha desde que es senior (dd/mm/yyyy): ");
					fechaSenior = sc.nextLine();
				}
			} else if (perfil == Perfil.ARTISTA) {
				System.out.print("¿Tiene apodo? (S/N): ");
				if (sc.nextLine().equalsIgnoreCase("S")) {
					System.out.print("Apodo: ");
					apodo = sc.nextLine();
				}
				System.out.println(
						"Especialidades (separadas por coma): ACROBACIA, HUMOR, MAGIA, EQUILIBRISMO, MALABARISMO");
				System.out.print("Ingrese especialidades: ");
				especialidades = sc.nextLine();
			}

			int nuevoId = FicheroPersonas.obtenerSiguienteId();

			String nombreUsuario;
			while (true) {
				System.out.print("Nombre de usuario: ");
				nombreUsuario = sc.nextLine().trim().toLowerCase();
				if (FicheroPersonas.existeNombreUsuario(nombreUsuario)) {
					System.out.println("Nombre de usuario ya existe. Intente otro.");
					continue;
				}
				if (!nombreUsuario.matches("^[a-z]{3,}$")) {
					System.out.println("Usuario inválido (mínimo 3 letras, sin espacios ni números).");
					continue;
				}
				break;
			}

			String password;
			while (true) {
				System.out.print("Contraseña: ");
				password = sc.nextLine().trim();
				if (password.length() < 3 || password.contains(" ")) {
					System.out.println("La contraseña debe tener mínimo 3 caracteres y sin espacios.");
				} else
					break;
			}

			Credenciales cred = new Credenciales(nuevoId, nombreUsuario, password, email, nombreReal, nacionalidad,
					perfil);

			Persona nuevaPersona = new Persona((long) nuevoId, nombreReal, email, nacionalidad, cred) {
			};

			FicheroPersonas.guardarPersona(nuevaPersona);

			System.out.println("\n Persona registrada exitosamente:");
			System.out.println(nuevaPersona);

		} catch (IOException e) {
			System.out.println("Error al leer o escribir ficheros: " + e.getMessage());
		} catch (Exception e) {
			System.out.println("Error inesperado: " + e.getMessage());
		}
	}
}
