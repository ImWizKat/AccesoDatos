package EjerciciosFicheroBinarios;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.EOFException;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;

public class AccesoDatosSocios {
	private String nombreFichero;

	public AccesoDatosSocios(String nombreFichero) {

		this.nombreFichero = nombreFichero;
	}

	public String getNombreFichero() {
		return nombreFichero;
	}

	public void setNombreFichero(String nombreFichero) {
		this.nombreFichero = nombreFichero;
	}

	public boolean crearSocio(Socio s) {
		// TODO Auto-generated method stub
		boolean resultado = false;

		// Declaramos el fichero para escribir
		DataOutputStream fichero = null;

		try {
			// Abrimos el fichero. Si no existe se crea y si existe
			// añadiremos información
			fichero = new DataOutputStream(new FileOutputStream(nombreFichero, true));

			// Escribimos los datos del socio en el fichero
			// Hacemos que el dni sea de un tamaño fijo de 9
			StringBuffer dniCon9 = new StringBuffer(s.getDni());
			dniCon9.setLength(9);
			fichero.writeChars(dniCon9.toString());

			fichero.writeChars(s.getNombre() + "\n");
			fichero.writeLong(s.getFechaN().getTime());
			fichero.writeBoolean(s.isActivo());

			resultado = true;

		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			System.out.println("Error, no existe fichero de socios");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			if (fichero != null) {
				try {
					fichero.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
		return resultado;
	}

	public ArrayList<Socio> obtenerSocios() {
		// TODO Auto-generated method stub
		ArrayList<Socio> resultado = new ArrayList<>();

		DataInputStream fichero = null;

		try {
			fichero = new DataInputStream(new FileInputStream(nombreFichero));

			// Recorremos el fichero leyendo de forma secuencial hasta que
			// lleguemos al final de fichero. Cuando se llega al final del
			// fichero, se produce la excepción EOFException, que hay que capturar
			// pero no hay que informar.
			while (true) {

				Socio s = new Socio();

				// Leemos el dni. Al ser un String tenemos que leer cada uno
				// de sus caracteres.
				String dni = "";
				for (int i = 0; i < 9; i++) {
					dni += fichero.readChar();
				}
				s.setDni(dni);

				// Leemos el nombre. Al ser un String de tamaño variable,
				// hay que leer todos los caracteres hasta encontrar el \n
				String nombre = "";
				char letra;
				while ((letra = fichero.readChar()) != '\n') {
					nombre += letra;
				}
				s.setNombre(nombre);

				// Leemos el long de la fecha
				s.setFechaN(new Date(fichero.readLong()));

				// Leemos activo
				s.setActivo(fichero.readBoolean());

				// Añadimos el socio al resultado
				resultado.add(s);

			}
		} catch (EOFException e) {
			// TODO: handle exception
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			System.out.println("Aún no existen socios");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			if (fichero != null) {
				try {
					fichero.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}

		return resultado;
	}

	public Socio obtenerSocio(String pDni) {
		Socio resultado = null;

		DataInputStream fichero = null;

		try {
			fichero = new DataInputStream(new FileInputStream(nombreFichero));
			
			while(true) {				
				String dni="";
				for(int i=0;i<9;i++) {
					dni += fichero.readChar();
				}				
				
				StringBuffer pDniCon9 = new StringBuffer(pDni);
				pDniCon9.setLength(9);
				
				pDni=pDniCon9.toString();
				
				if(dni.equalsIgnoreCase(pDni)) {
					resultado = new Socio();
					resultado.setDni(dni);
					String nombre="";
					char letra;
					while((letra=fichero.readChar())!='\n') {
						nombre+=letra;
					}
					resultado.setNombre(nombre);
					
					resultado.setFechaN(new Date(fichero.readLong()));
					
					resultado.setActivo(fichero.readBoolean());
					
					return resultado;
				}
				else {
					char letra;
					String nombre="";
					while((letra=fichero.readChar())!='\n') {
						nombre+=letra;
					}					
					fichero.readLong();					
					//Leemos activo
					fichero.readBoolean();
				}
			}

		} catch (EOFException e) {

		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		finally {
			if (fichero != null)
				try {
					fichero.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
		}

		return resultado;
	}

	public boolean bajaSocio(Socio s) {
		boolean resultado=false;
		
		DataInputStream ficheroO=null;
		DataOutputStream ficheroN=null;
		ArrayList<Socio> socios=new ArrayList<>();
		
		try {
			ficheroO= new DataInputStream(new FileInputStream(nombreFichero));
			while (true) {

				Socio socio = new Socio();
				String dni = "";
				for (int i = 0; i < 9; i++) {
					dni += ficheroO.readChar();
				}
				socio.setDni(dni);

				char letra;
				String nombre = "";
				while ((letra = ficheroO.readChar()) != '\n') {
					nombre += letra;
				}
				socio.setNombre(nombre);

				socio.setFechaN(new Date(ficheroO.readLong()));
				
				socio.setActivo(ficheroO.readBoolean());
				
				//un if para ver si es el socio que tenemos que cambiar y cambiarlo al guardarlo en el array, luego lo escribimos todo igual 
				if(s.getDni().equals(socio.getDni())) socio.setActivo(false);
				
				socios.add(socio);
			}		
			
		} catch (EOFException e) {
				
		} catch (FileNotFoundException e) {
			System.out.println("Archivo no encontrado");
		} catch (IOException e) {
			System.out.println("Error de i/o");
		}
		
		finally {
			try {
				if(ficheroO!=null)ficheroO.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
		}		
		try {
			ficheroN= new DataOutputStream(new FileOutputStream("socios.tmp",false));		
		for(Socio soc: socios) {
			StringBuffer dniCon9 = new StringBuffer(soc.getDni());
			dniCon9.setLength(9);
			ficheroN.writeChars(dniCon9.toString());
			ficheroN.writeChars(soc.getNombre() + "\n");
			ficheroN.writeLong(soc.getFechaN().getTime());
			ficheroN.writeBoolean(soc.isActivo());
		}
		
		} catch (FileNotFoundException e) {
			System.out.println("Archivo no encontrado");
		}
		 catch (IOException e) {
			System.out.println("Error de i/o");
		}
		
		finally {
		if(ficheroN!=null)
			try {
				ficheroN.close();
			} catch (IOException e) {
				System.out.println("Error al cerrar el archivo");
			}
		}
		File fO = new File(nombreFichero);
		if(fO.delete()) {
			File fN = new File("socios.tmp");
			if(fN.renameTo(fO)) {
				resultado = true;
			}
			else {
				System.out.println("Error al renombar el fichero");
			}
		}
		else {
			System.out.println("Error al borrar el fichero");
		}		
		return resultado;
	}

}
