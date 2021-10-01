package EjerciciosFicheroTexto;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Scanner;

public class Principal {

	static Scanner t = new java.util.Scanner(System.in);
	static AccesoDatosLibro datos = new AccesoDatosLibro("libros.txt");

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		int opcion = 0;
		do {
			System.out.println("Introduce una opcíon:");
			System.out.println("0-Salir");
			System.out.println("1-Mostrar Libros");
			System.out.println("2-Crear libro");
			System.out.println("3-Modificar Número de Ejemplares");
			System.out.println("4-Borrar Libro");
			System.out.println("5-Buscar Libro por ISBN");
			System.out.println("6-Buscar Libro por Autor");

			opcion = t.nextInt();
			t.nextLine();

			switch (opcion) {
			case 1: {
				mostrarLibros();
				break;
			}
			case 2: {
				crearLibro();
				break;
			}
			case 3: {
				modificarLibro();
				break;
			}
			case 4: {
				borrarLibro();
				break;
			}
			case 5: {
				buscarLibro();
				break;
			}
			case 6: {
				buscarLibroPorAutor();
				break;
			}

			}

		} while (opcion != 0);

	}

	private static void buscarLibroPorAutor() {
		// TODO Auto-generated method stub
		// Solicitamos el nombre del autor
		System.out.println("Introduce el nombre del autor:");
		String autor = t.nextLine();
		
		ArrayList<Libro>  libros = datos.obtenerLibrosAutor(autor);
		for(Libro l: libros) {
			l.mostrar();
		}
	}

	private static void buscarLibro() {
		// TODO Auto-generated method stub
		mostrarLibros();

		// Solicitamos el isbn del libro a borrar
		System.out.println("Introduce el ISBN del libro a buscar:");
		String isbn = t.nextLine();

		Libro l = datos.obtenerLibro(isbn);
		if (l != null) {
			l.mostrar();
		} else {
			System.out.println("Error, el libro no existe");
		}

	}

	private static void borrarLibro() {
		// TODO Auto-generated method stub
		mostrarLibros();

		// Solicitamos el isbn del libro a borrar
		System.out.println("Introduce el ISBN del libro a borrar:");
		String isbn = t.nextLine();

		// Obtenemos el libro a borrar para ver si existe
		Libro l = datos.obtenerLibro(isbn);
		if (l == null) {
			System.out.println("El libro seleccionado no existe");
		} else {

			// Borramos el libro en el fichero
			if (datos.borrarLibro(l)) {
				System.out.println("Libro borrado");
			} else {
				System.out.println("Error, no se ha borrado el libro");
			}
		}

	}

	private static void modificarLibro() {
		// TODO Auto-generated method stub

		mostrarLibros();

		// Solicitamos el isbn del libro a modificar
		System.out.println("Introduce el ISBN del libro a modificar:");
		String isbn = t.nextLine();

		// Obtenemos el libro a modificar para ver si existe
		Libro l = datos.obtenerLibro(isbn);
		if (l == null) {
			System.out.println("El libro seleccionado no existe");
		} else {
			// Pedimos el nuevo nº de ejemplares
			System.out.println("Introduce el nuevo nº de ejemplares");
			l.setNumEjemplares(t.nextInt());
			t.nextLine();

			// Modificamos el nº de ejemplaes del libro en el fichero
			if (datos.modificarEjemplares(l)) {
				System.out.println("Libro modificado");
			} else {
				System.out.println("Error, no se ha modificado el libro");
			}
		}

	}

	private static void crearLibro() {
		// TODO Auto-generated method stub
		SimpleDateFormat formato = new SimpleDateFormat("dd/MM/yyyy");
		try {
			System.out.println("Introduce ISBN");
			String isbn = t.nextLine();

			Libro l = datos.obtenerLibro(isbn);
			if (l == null) {
				l = new Libro();
				l.setIsbn(isbn);
				System.out.println("Introduce Título");
				l.setTitulo(t.nextLine());
				System.out.println("Introduce Autor");
				l.setAutor(t.nextLine());
				System.out.println("Introduce Fecha Lanazamiento (dd/mm/yyyy)");
				l.setFechaLanzamiento(formato.parse(t.nextLine()));
				System.out.println("Introduce Nº Ejemplares");
				l.setNumEjemplares(Integer.parseInt(t.nextLine()));
				// Guardar el libro en el fichero
				if (datos.crearLibro(l)) {
					System.out.println("Libro creado");
				} else {
					System.out.println("Error, al crear el libro");
				}
			} else {
				System.out.println("Error, ya existe el libro");
			}

		} catch (ParseException e) {
			// TODO Auto-generated catch block
			System.out.println("Error, fecha incorrecta");
		}

	}

	private static void mostrarLibros() {
		// TODO Auto-generated method stub

		// Rellenamos libros con los libros del fichero
		ArrayList<Libro> libros = datos.obtenerLibros();

		// Mostrar libros
		for (Libro l : libros) {
			l.mostrar();
		}
	}

}
