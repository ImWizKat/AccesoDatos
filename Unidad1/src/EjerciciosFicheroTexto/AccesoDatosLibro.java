package EjerciciosFicheroTexto;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class AccesoDatosLibro {
	private String nombreFichero;

	private SimpleDateFormat formato = new SimpleDateFormat("dd/MM/yyyy");

	public AccesoDatosLibro(String nombreFichero) {
		this.nombreFichero = nombreFichero;
	}

	public String getNombreFichero() {
		return nombreFichero;
	}

	public void setNombreFichero(String nombreFichero) {
		this.nombreFichero = nombreFichero;
	}

	public ArrayList<Libro> obtenerLibros() {
		// TODO Auto-generated method stub
		ArrayList<Libro> resultado = new ArrayList<>();

		// Declaramos el flujo que va a menejar el fichero
		BufferedReader fichero = null;
		try {
			// Abrimos el fichero para leer de él
			fichero = new BufferedReader(new FileReader(nombreFichero));

			// Leer los datos del fichero
			String linea;
			while ((linea = fichero.readLine()) != null) {
				// Dividimos la línea por ; para obtener los campos
				// separados
				String[] campos = linea.split(";");

				// Pasamos la fecha de String a objeto Date
				Date fecha = formato.parse(campos[3]);

				// Crear libro con lo datos leídos
				Libro l = new Libro(campos[0], campos[1], campos[2], fecha, Integer.parseInt(campos[4]));

				// Añadimos el libro al arraylist resultado
				resultado.add(l);

			}

		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			System.out.println("Error, no existe el fichero de libros");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			System.out.println("Error, hay fechas incorrectas");
		} finally {
			// Cerrar el fichero
			try {
				if (fichero != null) {
					fichero.close();
				}
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		return resultado;
	}

	public boolean crearLibro(Libro l) {
		// TODO Auto-generated method stub
		boolean resultado = false;

		// Declaramos el fichero para
		BufferedWriter fichero = null;

		try {
			// Abrimos el fichero para escritura
			fichero = new BufferedWriter(new FileWriter(nombreFichero, true));
			// Escribimos el libro en el fichero
			fichero.write(l.getIsbn() + ";" + l.getTitulo() + ";" + l.getAutor() + ";"
					+ formato.format(l.getFechaLanzamiento()) + ";" + l.getNumEjemplares() + "\n");

			// Devolvemos resultado true
			resultado = true;

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

	public Libro obtenerLibro(String isbn) {
		// TODO Auto-generated method stub
		Libro resultado = null;

		// Declaramos el fichero para leer
		BufferedReader fichero = null;
		try {
			// Abrimos el fichero
			fichero = new BufferedReader(new FileReader(nombreFichero));
			// Recorremos el fichero leyendo líneas hasta que encuentre
			// el libro buscado o se detecte el fin de fichero
			String linea;
			while ((linea = fichero.readLine()) != null) {

				// Comprobar si el isbn es el buscado
				String[] campos = linea.split(";");
				if (campos[0].equals(isbn)) {
					// Libro encontrado
					resultado = new Libro(campos[0], campos[1], campos[2], formato.parse(campos[3]),
							Integer.parseInt(campos[4]));
					return resultado;

				}

			}

		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			System.out.println("No existe fichero de libros");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (NumberFormatException e) {
			// TODO Auto-generated catch block
			System.out.println("Error, fecha de lanzamiento incorrecta");
		} catch (ParseException e) {
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

	public boolean modificarEjemplares(Libro l) {
		// TODO Auto-generated method stub
		boolean resultado = false;

		// Declaramos fichero original (libros.txt)
		BufferedReader ficheroO = null;
		// Declaramos un fichero temporal donde se escriben los datos con
		// las modificaciones
		BufferedWriter ficheroTmp = null;

		try {
			// Abrimos ficheros
			ficheroO = new BufferedReader(new FileReader(nombreFichero));
			// Een el fichero temporal, debemos abrir de forma que no
			// se añada información
			ficheroTmp = new BufferedWriter(new FileWriter("libros.tmp", false));

			// Leemos todas las líneas del fichero original
			String linea;
			while ((linea = ficheroO.readLine()) != null) {
				// Obtenemos los campos de la línea
				String[] campos = linea.split(";");

				// Comprobar si el libro es el que hay que modificar
				if (campos[0].equals(l.getIsbn())) {
					// Escribimos los datos del libro que ya tiene el
					// nº de ejemplares cambiado
					ficheroTmp.write(l.getIsbn() + ";" + 
							l.getTitulo() + ";" +
							l.getAutor() + ";"
							+ formato.format(l.getFechaLanzamiento()) + ";" + 
							l.getNumEjemplares() + "\n");
				} else {
					// Escribimos en el fichero temporal la línea
					// tal cual se ha leído
					ficheroTmp.write(linea + "\n");
				}
			}

		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			System.out.println("No existe el fichero");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {

			try {
				if (ficheroO != null) {
					ficheroO.close();
				}
				if(ficheroTmp != null) {
					ficheroTmp.close();
				}
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();

			}
		}
		
	
		// Operaciones con ficheros: borrar libros.txt y renombrar libros.tmp
		File fO = new File(nombreFichero);
		if(fO.delete()) {
			//REnombramos
			File fTmp = new File("libros.tmp");
			if(fTmp.renameTo(fO)) {
				resultado = true;
			}
			else {
				System.out.println("Error al renombar el fichero libros.tmp");
			}
			
		}
		else {
			System.out.println("Error al borrar el fichero libros.txt");
		}

		return resultado;
	}

	public boolean borrarLibro(Libro l) {
		// TODO Auto-generated method stub
		boolean resultado = false;

		// Declaramos fichero original (libros.txt)
		BufferedReader ficheroO = null;
		// Declaramos un fichero temporal donde se escriben los datos con
		// las modificaciones
		BufferedWriter ficheroTmp = null;

		try {
			// Abrimos ficheros
			ficheroO = new BufferedReader(new FileReader(nombreFichero));
			// Een el fichero temporal, debemos abrir de forma que no
			// se añada información
			ficheroTmp = new BufferedWriter(new FileWriter("libros.tmp", false));

			// Leemos todas las líneas del fichero original
			String linea;
			while ((linea = ficheroO.readLine()) != null) {
				// Obtenemos los campos de la línea
				String[] campos = linea.split(";");

				// Comprobar si el libro es el que hay que borrar
				if (!campos[0].equals(l.getIsbn())) {
					// Escribimos en el fichero temporal la línea
					// tal cual se ha leído
					ficheroTmp.write(linea + "\n");
				}
			}

		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			System.out.println("No existe el fichero");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {

			try {
				if (ficheroO != null) {
					ficheroO.close();
				}
				if(ficheroTmp != null) {
					ficheroTmp.close();
				}
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();

			}
		}
		
	
		// Operaciones con ficheros: borrar libros.txt y renombrar libros.tmp
		File fO = new File(nombreFichero);
		if(fO.delete()) {
			//REnombramos
			File fTmp = new File("libros.tmp");
			if(fTmp.renameTo(fO)) {
				resultado = true;
			}
			else {
				System.out.println("Error al renombar el fichero libros.tmp");
			}
			
		}
		else {
			System.out.println("Error al borrar el fichero libros.txt");
		}

		return resultado;
		
	}

	public ArrayList<Libro> obtenerLibrosAutor(String autor) {
		// TODO Auto-generated method stub
		ArrayList<Libro> resultado = new ArrayList<>();

		// Declaramos el flujo que va a menejar el fichero
		BufferedReader fichero = null;
		try {
			// Abrimos el fichero para leer de él
			fichero = new BufferedReader(new FileReader(nombreFichero));

			// Leer los datos del fichero
			String linea;
			while ((linea = fichero.readLine()) != null) {
				// Dividimos la línea por ; para obtener los campos
				// separados
				String[] campos = linea.split(";");
				
				//Comprobamos si el libro es del autor
				if(campos[2].equalsIgnoreCase(autor)) {

					// Pasamos la fecha de String a objeto Date
					Date fecha = formato.parse(campos[3]);
		
					// Crear libro con lo datos leídos
					Libro l = new Libro(campos[0], campos[1], campos[2], 
							fecha, Integer.parseInt(campos[4]));
		
					// Añadimos el libro al arraylist resultado
					resultado.add(l);
				}

			}

		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			System.out.println("Error, no existe el fichero de libros");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			System.out.println("Error, hay fechas incorrectas");
		} finally {
			// Cerrar el fichero
			try {
				if (fichero != null) {
					fichero.close();
				}
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		return resultado;
	}

}
