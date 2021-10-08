package EjerciciosFicheroBinario;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Scanner;

public class Principal {

	static Scanner t = new java.util.Scanner(System.in);
	static AccesoDatosSocios datos = new AccesoDatosSocios("socios.bin");
	static SimpleDateFormat formato= new SimpleDateFormat("dd/MM/yyyy");
	
	public static void main(String[] args) {
		int opcion = 0;
		do {
			System.out.println("Introduce una opción:");
			System.out.println("0-Salir");
			System.out.println("1-Mostrar socios");
			System.out.println("2-Crear socio");
			System.out.println("3-Baja socio");
			System.out.println("4-Borrar socio");
			
			opcion = t.nextInt();t.nextLine();
			
			switch (opcion) {
				case 1: {
					mostrarSocios();
					break;
				}
				case 2: {
					crearSocio();
					break;
				}
				case 3: {
					bajaSocio();
					break;
				}
				case 4: {
					borrarSocio();
					break;
				}		
				
			}
			
		} while (opcion != 0);
		
	}

	private static void borrarSocio() {
		System.out.println("Introduce DNI del socio a borrar");
		String dni=t.nextLine();
		Socio s = datos.obtenerSocio(dni);
		if(s!=null) {
			if(!datos.borrar(s)) {System.out.println("Error al borrar el socio");}
		}
		else {System.out.println("No hay ningún socio con ese dni");}
		
	}

	private static void bajaSocio() {
		
		System.out.println("Introduce DNI del socio a dar de baja");
		String dni=t.nextLine();
		
		Socio s = datos.obtenerSocio(dni);
		if(s!=null) {
			if(!datos.bajaSocio(s)) {
				System.out.println("Error al dar de baja el socio");
			}
		}
		else{
			System.out.println("No hay un socio con ese dni");
		}
	}

	private static void mostrarSocios() {
		ArrayList<Socio> socios = datos.obtenerSocios();
		for(Socio s: socios) {
			s.mostrar();
		}
		
	}

	private static void crearSocio() {
		
		try {
			Socio s=new Socio();
			System.out.println("Introduce DNI");
			s.setDni(t.nextLine());
			
			System.out.println("Introduce nombre");
			s.setNombre(t.nextLine());
			
			System.out.println("Introduce fecha de nacimiento con formato dd/MM/yyyy");
			
			s.setFechaN(formato.parse(t.nextLine()));
			
			s.setActivo(true);
			
			if(!datos.crearSocio(s)) {
				System.out.println("Error al crear el socio");
			}
			
		} catch (ParseException e) {
			System.out.println("Ha habido un error con el formato de la fecha");
		}
		
		
	}	

}
