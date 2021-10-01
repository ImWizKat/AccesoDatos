package EjerciciosFicheroBinarios;


import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Scanner;

public class Principal {

	static Scanner t = new java.util.Scanner(System.in);
	static AccesoDatosSocios datos = new AccesoDatosSocios("socios.bin");
	static SimpleDateFormat formato = new SimpleDateFormat("dd/MM/yyyy");
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		int opcion = 0;
		do {
			System.out.println("Introduce una opcíon:");
			System.out.println("0-Salir");
			System.out.println("1-Mostrar Socios");
			System.out.println("2-Crear Socio");
			System.out.println("3-Baja Socio");
			System.out.println("4-Borrar Socio");
			

			opcion = t.nextInt();
			t.nextLine();

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
				
				break;
			}
			case 5: {
				
				break;
			}
			case 6: {
				
				break;
			}

			}

		} while (opcion != 0);

	}

	private static void bajaSocio() {
		// TODO Auto-generated method stub
		mostrarSocios();
		System.out.println("Introduce el dni del socio a modificar");
		String dni = t.nextLine();
		
		Socio s = datos.obtenerSocio(dni);
		if(s!=null) {
			if(!datos.bajaSocio(s)) {
				System.out.println("Error al dar de baja el socio");
			}
		}
		else {
			System.out.println("No hay socio para ese DNI");
		}
	}

	private static void mostrarSocios() {
		// TODO Auto-generated method stub
		ArrayList<Socio> socios = datos.obtenerSocios();
		for(Socio s: socios) {
			s.mostrar();
		}
	}

	private static void crearSocio() {
		// TODO Auto-generated method stub
		
		try {
			Socio s = new Socio();
			
			System.out.println("Introduce DNI");
			s.setDni(t.nextLine());
			
			System.out.println("Introduce Nombre");
			s.setNombre(t.nextLine());
			
			System.out.println("Introduce Fecha de Nacimiento (dd/mm/yyyy)");
			s.setFechaN(formato.parse(t.nextLine()));
			
			//Siempre se crea el socio activo
			s.setActivo(true);
			
			if(!datos.crearSocio(s)) {
				System.out.println("Error al crear el socio");
			}
			
			
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			System.out.println("Error en la fecha");
		}
	}
	
	
	
	
	
	
	
	
	

	}
