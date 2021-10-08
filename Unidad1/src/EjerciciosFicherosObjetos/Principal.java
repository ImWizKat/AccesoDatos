package EjerciciosFicherosObjetos;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Scanner;

import EjerciciosFicheroBinario.AccesoDatosSocios;
import EjerciciosFicheroBinario.Socio;
import EjerciciosFicheroTexto.AccesoDatosLibro;
import EjerciciosFicheroTexto.Libro;

public class Principal {
	static Scanner t=new Scanner(System.in);
	static AccesoDatosPrestamos datosP= new AccesoDatosPrestamos("prestamos.obj");
	static AccesoDatosLibro datosL= new AccesoDatosLibro("libros.txt");
	static AccesoDatosSocios datosS= new AccesoDatosSocios("socios.bin");
	static SimpleDateFormat formato = new SimpleDateFormat("dd/MM/yyyy");

	public static void main(String[] args) {
			int opcion = 0;
			do {
				System.out.println("Introduce una opci�n:");
				System.out.println("0-Salir");
				System.out.println("1-Mostrar Pr�stamo");
				System.out.println("2-Crear Pr�stamo");
				System.out.println("3-Devolver Pr�stamo");
				System.out.println("4-Borrar Pr�stamo");
				System.out.println("5-Mostrar pr�stamos de un socio");

				opcion = t.nextInt();t.nextLine();
				
				switch (opcion) {
					case 1: {
						mostrarPrestamos();
						break;
					}
					case 2: {
						crearPrestamo();
						break;
					}
					case 3: {
						devolverPrestamo();
						break;
					}
					case 4: {
						borrarPrestamo();
						break;
					}
					case 5:{
						mostrarPrestamosSocio();
						break;
					}					
				}				
			} while (opcion != 0);
	}
	private static void mostrarPrestamosSocio() {
		ArrayList<Prestamo> prestamos = datosP.obtenerPrestamos();
		for (Prestamo p: prestamos) {
			p.mostrar();
		}
		System.out.println("Introduce dni del socio");
		String dni = t.nextLine();
		Socio s=datosS.obtenerSocio(dni);
		if (s!=null) {
			
			prestamos= datosP.obtenerPrestamosSocio(s);
			
			if(!prestamos.isEmpty()) {
				for (Prestamo p: prestamos) {
					p.mostrar();
				}
			}
			else {
				System.out.println("Ese socio no tiene prestamos");
			}
			
			
			
			
		}
		else {
			System.out.println("Ese socio no existe");
		}
		
		
	}

	private static void borrarPrestamo() {
		ArrayList<Prestamo> prestamos = datosP.obtenerPrestamos();
		for (Prestamo p: prestamos) {
			p.mostrar();
		}
		
		System.out.println("Introduce dni del socio");
		String dni = t.nextLine();
		
		Socio s = datosS.obtenerSocio(dni);
		if(s!=null) {
			System.out.println("Introduce isbn del libro");
			String isbn=t.nextLine();
			Libro l=datosL.obtenerLibro(isbn);
			if(l!=null) {				
				System.out.println("Introduce la fecha en la que se realiz� el pr�stamo en formato dd/MM/yyyy");
				String fecha = t.nextLine();
				Prestamo p = new Prestamo();
				p.setLibro(l);
				p.setSocio(s);
				try {
					p.setFechaP(formato.parse(fecha));
				} catch (ParseException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}				
				
				if(!datosP.borrar(p)) {
					System.out.println("Error, no se ha encontrado el pr�stamo");
				}
				else {
					System.out.println("Pr�stamo eliminado correctamente");
				}
			}
			else {
				System.out.println("Ese socio no tiene un libro con ese isbn");
			}
		}
		else {
			System.out.println("Error, el socio no existe");
		}
		
	}

	private static void devolverPrestamo() {
		ArrayList<Prestamo> prestamos = datosP.obtenerPrestamos();
		for (Prestamo p: prestamos) {
			p.mostrar();
		}
		System.out.println("Introduce dni del socio que va a realizar la devoluci�n");
		String dni = t.nextLine();
		
		Socio s = datosS.obtenerSocio(dni);
		if(s!=null) {
			System.out.println("Introduce isbn del libro a devolver");
			String isbn=t.nextLine();
			Libro l=datosL.obtenerLibro(isbn);
			if(l!=null) {
				//pedir fechaP y si es correcto devolver
				
				//creo todo un objeto pr�stamo que ser� igual al que hay dentro y luego lo comparo, y si es cierto lo cambio
				System.out.println("Introduce la fecha en la que se realiz� el pr�stamo en formato dd/MM/yyyy");
				String fecha = t.nextLine();
				Prestamo p = new Prestamo();
				p.setLibro(l);
				p.setSocio(s);
				try {
					p.setFechaP(formato.parse(fecha));
				} catch (ParseException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}				
				
				//hacer m�todo que compruebe mi dni(a trav�s del socio), mi isbn(a trav�s del libro) y la fechaP con el pr�stamo
				//y si se da el caso de que es igual, cambie el devuelto a true
				if(!datosP.devolver(p)) {
					System.out.println("Error, no se ha encontrado el pr�stamo");
				}
				else {
					System.out.println("Pr�stamo devuelto correctamente");
				}
			}
			else {
				System.out.println("Ese socio no tiene un libro con ese isbn");
			}
		}
		else {
			System.out.println("Error, el socio no existe");
		}
		
	}

	private static void mostrarPrestamos() {
		ArrayList<Prestamo> prestamos=datosP.obtenerPrestamos();
		for(Prestamo p:prestamos) {
			p.mostrar();
		}
	}

	private static void crearPrestamo() {
		ArrayList<Socio> socios = datosS.obtenerSocios();
		for(Socio s: socios) {
			s.mostrar();
		}
		System.out.println("Introduce el dni del socio");
		String dni = t.nextLine();
		
		Socio s = datosS.obtenerSocio(dni);
		if(s!=null) {
			ArrayList<Libro> libros = datosL.obtenerLibros();
			for(Libro l: libros) {
				l.mostrar();
			}
			System.out.println("Introduce isbn del libro");
			String isbn=t.nextLine();
			
			Libro l= datosL.obtenerLibro(isbn);
			
			if(l!=null) {
				
				if(l.getNumEjemplares()>0) {
					
					if(!datosP.crearPrestamo(s,l)) {
						System.out.println("Error al registrar el pr�stamo");
					}
					else {
						l.setNumEjemplares(l.getNumEjemplares()-1);
						
						if(!datosL.modificarEjemplares(l)) {
							System.out.println("Se ha producido un error al actualizar el n�mero de ejemplares");
						}
					}
				}
				else System.out.println("Error, no hay ejemplares de ese libro");
			}
			else System.out.println("Error, el libro no existe");			
		}
		else System.out.println("Error, el socio no existe");		
	}

}
