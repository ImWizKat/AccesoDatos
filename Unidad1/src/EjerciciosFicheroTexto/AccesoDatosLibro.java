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
		
		//Declaramos el flujo que va a menejar el fichero		
		BufferedReader fichero = null;
		
		try {
			//Abrimos el fichero para leer de él
			fichero = new BufferedReader(new FileReader(nombreFichero));
			
			//Leer los datos del fichero
			String linea;
			while((linea=fichero.readLine())!=null) {
				//Dividimos la línea por ; para obtener los campos
				//separados
				String[] campos = linea.split(";");
				
				//Pasamos la fecha de String a objeto Date
				Date fecha = formato.parse(campos[3]);
				
				//Crear libro con lo datos leídos
				Libro l = new Libro(campos[0], campos[1], campos[2], 
						fecha, Integer.parseInt(campos[4]));
				
				//Añadimos el libro al arraylist resultado
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
			e.printStackTrace();
		} 
		finally {
			//Cerrar el fichero
			try {
				if(fichero !=null) {
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
		boolean resultado=false;
		//esto lo añadí yo aaaa
		
			BufferedWriter fichero =null;
		try {
			fichero= new BufferedWriter(new FileWriter(nombreFichero,true));
			
			fichero.write(l.getIsbn()
					+";"+l.getTitulo()
					+";"+l.getAutor()
					+";"+formato.format(l.getFechaLanzamiento())
					+";"+l.getNumEjemplares());
			fichero.newLine();
			resultado=true;
			
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		finally {
			
			if(fichero!=null)
				try {
					fichero.close();
				} 
			catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		return resultado;
		
	}

	public Libro obtenerLibro(String isbn) {
		// TODO Auto-generated method stub
		Libro resultado=null;
		BufferedReader fichero=null;
		
		try {
			fichero=new BufferedReader(new FileReader(nombreFichero));
			String linea;
			while ((linea=fichero.readLine())!=null) {
				String[] campos=linea.split(";");
				if(campos[0].equals(isbn)) {
					resultado=new Libro(campos[0],campos[1],campos[2],
							formato.parse(campos[3]),Integer.parseInt(campos[4]));
				return resultado;
				}
				else {
					
				}
			}
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			System.out.println("No existe el fichero libros");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (NumberFormatException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		finally {
			if(fichero!=null) {
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
		boolean resultado=false;
		
		BufferedReader ficheroO=null;
		BufferedWriter ficheroN=null;
		
		try {
			ficheroO=new BufferedReader(new FileReader(nombreFichero));
			ficheroN=new BufferedWriter(new FileWriter("libros.tmp",false));
			
			String linea;
			while((linea=ficheroO.readLine())!=null){				
				String[] campos=linea.split(";");
				
				if(campos[0].equals(l.getIsbn())) {
					ficheroN.write(l.getIsbn()+";"+
							l.getTitulo()+";"+	
							l.getAutor()+";"+
							formato.format(l.getFechaLanzamiento())+";"+
							l.getNumEjemplares());
					ficheroN.newLine();
					
				}
				else {
					ficheroN.write(linea);
					ficheroN.newLine();
				}
			}
			
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			System.out.println("No existe el fichero");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		finally {
			
			
			try {
				
				if(ficheroO!= null) {
					ficheroO.close();
				}
				if(ficheroN!= null) {
						
					ficheroN.close();					
				}
			}
				
			catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				}
		}
		/*fichero original*/
		File fO = new File(nombreFichero);
		if (fO.delete()) {
			//fichero nuevo
			File fN = new File("libros.tmp");
			if(fN.renameTo(fO)) {
				resultado=true;
			}
			else {
				System.out.println("Error al renombrar el fichero libros.tmp");
			}			
		}
		else {
			System.out.println("Error al borrar el fichero libros.txt");
		}
		
		return resultado;
	}


	public boolean borrarLibro(Libro l) {

		boolean resultado=false;
		BufferedReader ficheroO=null;
		BufferedWriter ficheroN=null;
		
		try {
			ficheroO=new BufferedReader(new FileReader(nombreFichero));
			ficheroN=new BufferedWriter(new FileWriter("libros.tmp",false));
			
			String linea;
			while((linea=ficheroO.readLine())!=null){				
				String[] campos=linea.split(";");
				
				if(!campos[0].equals(l.getIsbn())) {
					ficheroN.write(linea);
					ficheroN.newLine();				
				}
			}
			
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			System.out.println("No existe el fichero");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		finally {
			
			
			try {
				
				if(ficheroO!= null) {
					ficheroO.close();
				}
				if(ficheroN!= null) {
						
					ficheroN.close();					
				}
			}
				
			catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				}
		}
		/*fichero original*/
		File fO = new File(nombreFichero);
		if (fO.delete()) {
			//fichero nuevo
			File fN = new File("libros.tmp");
			if(fN.renameTo(fO)) {
				resultado=true;
			}
			else {
				System.out.println("Error al renombrar el fichero libros.tmp");
			}			
		}
		else {
			System.out.println("Error al borrar el fichero libros.txt");
		}
		
		return resultado;
	}

	public ArrayList<Libro> obtenerLibrosAutor(String autor) {
		
		ArrayList<Libro> resultado = new ArrayList<>();
		
		BufferedReader fichero = null;
		
		try {
			fichero = new BufferedReader(new FileReader(nombreFichero));
			
			String linea;
			while((linea=fichero.readLine())!=null) {
				String[] campos = linea.split(";");
				
				Date fecha = formato.parse(campos[3]);
				
				Libro l = new Libro(campos[0], campos[1], campos[2], 
						fecha, Integer.parseInt(campos[4]));
				
				if(l.getAutor().equalsIgnoreCase(autor))
				resultado.add(l);
			}
			
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			System.out.println("Error, no existe el fichero de libros");
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ParseException e) {
			System.out.println("Error, hay fechas incorrectas");
		} 
		
		finally {
			try {
				if(fichero !=null) {
					fichero.close();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
		return resultado;
	}
	
	
}
