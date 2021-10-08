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
			System.out.println("Introduce una opción:");
			System.out.println("0-Salir");
			System.out.println("1-Mostrar Libros");
			System.out.println("2-Crear libro");
			System.out.println("3-Modificar Número de Ejemplares");
			System.out.println("4-Borrar Libro");
			System.out.println("5-Buscar Libro por ISBN");
			System.out.println("6-Buscar Libros de un autor");

			
			opcion = t.nextInt();t.nextLine();
			
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
					buscarPorAutor();
					break;
				}
				
				
			}
			
		} while (opcion != 0);
		
	}

	private static void buscarPorAutor() {

		System.out.println("Introduce el nombre del autor");
		String autor=t.nextLine();
		ArrayList<Libro> libros = datos.obtenerLibrosAutor(autor);
		
		if(libros.isEmpty()) {
			System.out.println("No hay libros de ese autor");
		}	
		else {
			for(Libro l:libros) {
				l.mostrar();
			}
		}
	}

	private static void modificarLibro() {
		// TODO Auto-generated method stub
		mostrarLibros();
		System.out.println("Introduce el isbn del libro a modificar");
		String isbn=t.nextLine();
		Libro l= datos.obtenerLibro(isbn);
		if (l==null) {
			System.out.println("El libro seleccionado no existe");
		}
		else {
			System.out.println("Introduce el nuevo número de ejemplares");
			l.setNumEjemplares(t.nextInt());
			t.nextLine();
			
			if(datos.modificarEjemplares(l)) {
				System.out.println("Libro modificado");
			}
			else {
				System.out.println("Error, no se ha modificado el libro");
			}
		}
	}

	private static void crearLibro() {
		
		System.out.println("Introduce ISBN");
		String isbn=t.nextLine();
		
		Libro l=datos.obtenerLibro(isbn);

		if(l==null) {
			l=new Libro();
			l.setIsbn(isbn);
			
			System.out.println("Introduce Título");
			l.setTitulo(t.nextLine());
			
			System.out.println("Introduce Autor");
			l.setAutor(t.nextLine());
			
			SimpleDateFormat formato=new SimpleDateFormat("dd/MM/yyyy");
			System.out.println("Introduce Fecha de lanzamiento con formato dd/mm/yyyy");
			try {
				l.setFechaLanzamiento(formato.parse(t.nextLine()));
				
			} catch (ParseException e) {
				System.out.println("Error, fecha incorrecta");
			}		
			
			System.out.println("Introduce Nº de ejemplares");
			l.setNumEjemplares(Integer.parseInt(t.nextLine()));
	
			if(datos.crearLibro(l)) {
				System.out.println("Libro creado");
			}
		}
		else {
			System.out.println("Error, ya existe un libro con ese ISBN");
		}
		
	}

	private static void mostrarLibros() {
		// TODO Auto-generated method stub
		
		//Rellenamos libros con los libros del fichero
		 ArrayList<Libro> libros = datos.obtenerLibros();
		
		//Mostrar libros
		for(Libro l:libros) {
			l.mostrar();
		}
	}
	
	private static void borrarLibro() {
		// TODO Auto-generated method stub
		mostrarLibros();
		System.out.println("Introduce el isbn del libro a borrar");
		String isbn=t.nextLine();
		Libro l= datos.obtenerLibro(isbn);
		if (l==null) {
			System.out.println("El libro seleccionado no existe");
		}
		else {
			
			if(datos.borrarLibro(l)) {
				System.out.println("Libro borrado");
			}
			else {
				System.out.println("Error, no se ha borrado el libro");
			}
		}
	}

	private static void buscarLibro() {
		// TODO Auto-generated method stub
		mostrarLibros();
		System.out.println("Introduce el isbn del libro a buscar");
		String isbn=t.nextLine();
		Libro l= datos.obtenerLibro(isbn);
		if (l!=null) {
			l.mostrar();
		}
		else {
			System.out.println("El libro no existe");
			
		}
	}
	
}
