package EjerciciosClaseFile;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Scanner;

public class Ejercicio0 {
	static Scanner t = new java.util.Scanner(System.in);
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		int opcion = 0;
		do {
			System.out.println("Introduce una opcíon:");
			System.out.println("0-Salir");
			System.out.println("1-Opción 1");
			System.out.println("2-Opción 2");
			System.out.println("3-Opción 3");
			System.out.println("4-Opción 4");
			System.out.println("5-Opción 5");
			System.out.println("6-Opción 6");
			
			opcion = t.nextInt();t.nextLine();
			
			switch (opcion) {
				case 1: {
					//1.-Mostrar la ruta absoluta de la carpeta 
					//en la que está el programa java
					mostrarRuta();					
					break;
				}
				case 2: {
					//2.- Pedir por teclado una ruta de fichero o carpeta 
					//y mostrar si lo que se ha introducido existe,
					// si es un fichero o una carpeta, 
					//la fecha de modificación y el tamaño.
					datosElemento();
					break;
				}
				case 3: {
					//Mostrar contenido carpeta
					mostrarCarpeta();
					break;
				}
				case 4: {
					//Crear carpeta en la ruta por defecto (.)
					crearCarpeta();
					
					break;
				}
				case 5: {
					//Crear fichero en la ruta por defecto (.)
					crearFichero();
					
					
					break;
				}
				case 6: {
					
					//REnombrar Fichero
					renombrarFichero();
					break;
				}
				
			}
			
		} while (opcion != 0);
		
	}

	private static void renombrarFichero() {
		// TODO Auto-generated method stub
		//Pedimos nombre de fichero
		System.out.println("Introduce el nombre del fichero");
		String fichero = t.nextLine();
		
		//Creamos objeto File
		File f = new File(fichero);
		
		//comprobamos si existe y es fichero
		if(f.exists() && f.isFile()) {
			//Pedimos el nuevo nombre
			System.out.println("Introduce el nuevo nombre");
			String nuevo = t.nextLine();
			
			if(!f.renameTo(new File(nuevo))) {
				System.out.println("Error, no se ha renombrado");
			}
		}
		else {
			
			
		}
	}

	private static void crearFichero() {
		// TODO Auto-generated method stub
		//Pedimos nombre de fichero
		System.out.println("Introduce el nombre del fichero");
		String fichero = t.nextLine();
		
		//Creamos objeto File
		File f = new File(fichero);
		
		//comprobamos si exsite
		if(f.exists()) {
			System.out.println("Error, el fichero ya existe");
		}
		else {
			try {
				if(!f.createNewFile()) {
					System.out.println("Error, no se ha creado el fichero");
				}
			} 
			
			catch (IOException e) {
				// TODO Auto-generated catch block
				System.out.println("Error, se ha producido un error de E/S");
				//e.printStackTrace();
			}
			
		}
	}

	private static void crearCarpeta() {
		// TODO Auto-generated method stub
		//Pedimos nombre de la carpeta a crear
		System.out.println("Introduce el nombre de la carpeta");
		String carpeta = t.nextLine();
		
		//Crear objeto File
		File c  = new File(carpeta);
		
		//Comprobamos si existe
		if(c.exists()) {
			System.out.println("Error, la carpeta ya existe");
		}
		else {
			if(!c.mkdir()) {
				System.out.println("Error, no se ha podido crear la carpeta");
			}
		
			
		}
		
	}

	private static void mostrarCarpeta() {
		// TODO Auto-generated method stub
		//Pedimos la ruta de la carpeta
		System.out.println("Introduce la ruta de una carpeta");
		String carpeta = t.nextLine();
		
		//Creamos objeto File al elemento
		File f = new File(carpeta);
		
		//Comprobamos que existe y es carpeta
		if(f.exists() && f.isDirectory()) {
			//Obtenemos el contenido de la carpeta
			File[] contenido = f.listFiles();
			//Mostramos el contenido
			for(int i=0;i<contenido.length;i++) {
				String tipo, propiedades;
				if(contenido[i].isDirectory()) {
					tipo = "c";
				}
				else {
					tipo = "f";
				}
				if(contenido[i].canRead())
					propiedades = "r";
				else
					propiedades = "-";
				
				if(contenido[i].canWrite())
					propiedades += "w";
				else
					propiedades += "-";
				
				if(contenido[i].canExecute())
					propiedades += "x";
				else
					propiedades += "-";
				
				System.out.println(contenido[i].getName() + 
						"\t" + tipo + 
						"\t" + propiedades);
				
				
				
			}
			
		}
		else {
			System.out.println("El elemento introducido no existe o no es "
					+ "una carpeta");
		}

	}

	private static void datosElemento() {
		// TODO Auto-generated method stub
		//Pedimos la ruta del elemento
		System.out.println("Introduce la ruta de un fichero o carpeta");
		String elto = t.nextLine();
		
		//Creamos objeto File al elemento
		File f = new File(elto);
		
		//Comprobar si existe
		if(f.exists()) {
			//Comprobar si es una carpeta o un fichero
			if(f.isDirectory()) {
				System.out.println("La ruta introducida es de una carpeta");
			}
			else {
				System.out.println("La ruta introducida es de un fichero");
				//Mostrar la fecha de modificación
				//Creamos un objeto con el formato de la fecha
				SimpleDateFormat formato = new SimpleDateFormat("dd/MM/yyyy hh:mm:ss");
				//Crear un objeto fecha con la fecha de modificación del fichero
				Date fechaM = new Date(f.lastModified());
				//Mostra la fecha con el formato correcto
				System.out.println("Fecha:" + formato.format(fechaM));
				//Mostrar el tamaño
				System.out.println("Tamaño:" + f.length());
				
			}
			
		}
		else {
			System.out.println("La ruta introducida no existe");
		}
	}

	private static void mostrarRuta() {
		// TODO Auto-generated method stub
		
		//Declaramos un objeto File sobre la carpeta actual
		// Podemos hacerlo de tres formas f, f1 y f2:
		
		//Con ruta relativa
		File f  = new File("src\\EjerciciosClaseFile\\Ejercicio0.java");		
		
		//Con la carpeta actual
		//El . hace referencia a la carpeta actual,
				// a la carpeta en la que está el programa
		File f1  = new File(".\\src\\EjerciciosClaseFile\\Ejercicio0.java");
		
		//Con separador portable
		File f2  = new File("."+File.pathSeparator+"src"+File.pathSeparator
				+ "EjerciciosClaseFile"+File.pathSeparator+
				"Ejercicio0.java");
	
		
		System.out.println("La ruta absoluta de la carpeta acutal es:"+
							f.getAbsolutePath());
		System.out.println("La ruta absoluta de la carpeta acutal es:"+
				f1.getAbsolutePath());

		System.out.println("La ruta absoluta de la carpeta acutal es:"+
				f2.getAbsolutePath());

		
	}

}
