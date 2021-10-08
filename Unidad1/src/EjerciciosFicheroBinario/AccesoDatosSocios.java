package EjerciciosFicheroBinario;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.EOFException;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class AccesoDatosSocios {
	
	private String nombreFichero;
	static SimpleDateFormat formato= new SimpleDateFormat("dd/MM/yyyy");
	
	public AccesoDatosSocios(String nombreFichero) {
		this.nombreFichero=nombreFichero;
		}

	public String getNombreFichero() {
		return nombreFichero;
	}

	public void setNombreFichero(String nombreFichero) {
		this.nombreFichero = nombreFichero;
	}

	public boolean crearSocio(Socio s) {
		boolean resultado=false;
		
		DataOutputStream fichero=null;
		
		try {
			fichero = new DataOutputStream(new FileOutputStream(nombreFichero,true));
			
			StringBuffer dniCon9= new StringBuffer(s.getDni());
			dniCon9.setLength(9);
			fichero.writeChars(dniCon9.toString());
			
			fichero.writeChars(s.getNombre()+"\n");
			
			fichero.writeLong(s.getFechaN().getTime());
			
			fichero.writeBoolean(s.isActivo());
			
			resultado=true;
			
		} catch (FileNotFoundException e) {
			System.out.println("Error. Fichero no encontrado");
		} catch (IOException e) {
			System.out.println("Error de I/O");
		}
		
		finally {
			if (fichero!=null)
				try {
					fichero.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
		}
		
		return resultado;
	}

	public ArrayList<Socio> obtenerSocios() {
		ArrayList<Socio> resultado= new ArrayList<>();
		DataInputStream fichero=null;
		try {		
			fichero= new DataInputStream(new FileInputStream(nombreFichero));				
				while(true){
					Socio s=new Socio();
					
					String dni="";					
					for(int i=0;i<9;i++) {
						dni+=fichero.readChar();
					}
					s.setDni(dni);
					
					String nombre="";
					char letra;
					while((letra=fichero.readChar())!='\n') {
						nombre+=letra;
					}
					s.setNombre(nombre);
					
					s.setFechaN(new Date(fichero.readLong()));
					s.setActivo(fichero.readBoolean());
					
					resultado.add(s);
				}				
		} catch(EOFException e) {
			
		} catch (FileNotFoundException e) {
			System.out.println("Error. Fichero no encontrado");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		finally {
			if(fichero!=null)
				try {
					fichero.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
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
					//solo avanzamos en el archivo para volver a estar al principio, no nos interesa nada de aquí
					char letra;
					while((letra=fichero.readChar())!='\n') {
					}					
					fichero.readLong();					
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

		try {
			ficheroO= new DataInputStream(new FileInputStream(nombreFichero));
			ficheroN= new DataOutputStream(new FileOutputStream("socios.tmp",false));		

			while (true) {

				Socio registro = new Socio();
				String dni = "";
				for (int i = 0; i < 9; i++) {
					dni += ficheroO.readChar();
				}
				registro.setDni(dni);

				char letra;
				String nombre = "";
				while ((letra = ficheroO.readChar()) != '\n') {
					nombre += letra;
				}
				registro.setNombre(nombre);

				registro.setFechaN(new Date(ficheroO.readLong()));

				registro.setActivo(ficheroO.readBoolean());

				//un if para ver si es el socio que tenemos que cambiar, luego lo escribimos todo (si fuera el que buscamos se escribiría cambiado
				//aquí podemos checkear si está dado de baja
				if(s.getDni().equals(registro.getDni())) registro.setActivo(false);
				

				//introducimos todos los datos
				StringBuffer dniCon9 = new StringBuffer(registro.getDni());
				dniCon9.setLength(9);
				ficheroN.writeChars(dniCon9.toString());
				ficheroN.writeChars(registro.getNombre() + "\n");
				ficheroN.writeLong(registro.getFechaN().getTime());
				ficheroN.writeBoolean(registro.isActivo());
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
				if(ficheroN!=null)ficheroN.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
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

	public boolean borrar(Socio s) {
		boolean resultado=false;

		DataInputStream ficheroO=null;
		DataOutputStream ficheroN=null;

		try {
			ficheroO= new DataInputStream(new FileInputStream(nombreFichero));
			ficheroN= new DataOutputStream(new FileOutputStream("socios.tmp",false));		

			while (true) {

				Socio registro = new Socio();
				String dni = "";
				for (int i = 0; i < 9; i++) {
					dni += ficheroO.readChar();
				}
				registro.setDni(dni);

				char letra;
				String nombre = "";
				while ((letra = ficheroO.readChar()) != '\n') {
					nombre += letra;
				}
				registro.setNombre(nombre);

				registro.setFechaN(new Date(ficheroO.readLong()));

				registro.setActivo(ficheroO.readBoolean());

				if(!s.getDni().equals(registro.getDni())) {
				StringBuffer dniCon9 = new StringBuffer(registro.getDni());
				dniCon9.setLength(9);
				ficheroN.writeChars(dniCon9.toString());
				ficheroN.writeChars(registro.getNombre() + "\n");
				ficheroN.writeLong(registro.getFechaN().getTime());
				ficheroN.writeBoolean(registro.isActivo());
				}
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
				if(ficheroN!=null)ficheroN.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
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
