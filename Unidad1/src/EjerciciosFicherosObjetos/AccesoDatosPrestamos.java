package EjerciciosFicherosObjetos;

import java.io.EOFException;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.ArrayList;
import java.util.Calendar;

import EjerciciosFicheroBinario.Socio;
import EjerciciosFicheroTexto.Libro;

public class AccesoDatosPrestamos {
	
	static SimpleDateFormat formato = new SimpleDateFormat("dd/MM/yyyy");

	
	private String nombreFichero;

	public AccesoDatosPrestamos(String nombreFichero) {
		this.nombreFichero = nombreFichero;
	}

	public boolean crearPrestamo(Socio s, Libro l) {
		boolean resultado = false;

		ObjectOutputStream fichero = null;

		try {

			File f = new File(nombreFichero);

			if (f.exists()) {
				fichero = new MiObjectOutputStream(new FileOutputStream(nombreFichero, true));
			} else {
				fichero = new ObjectOutputStream(new FileOutputStream(nombreFichero, true));
			}

			Prestamo p = new Prestamo();
			p.setSocio(s);
			p.setLibro(l);
			p.setFechaP(formato.parse(formato.format(new Date())));
			// calculamos la fecha de devolución
			Calendar c = Calendar.getInstance(); // calendario instanciado(no se hace new, es como que ya existe)
			c.setTime(p.getFechaP()); // le damos la fecha que ya tenemos
			c.add(Calendar.DATE, 15); // a esa fecha le sumamos 15 días
			p.setFechaD(c.getTime()); // y la guardamos en fecha devolución
			// p.setDevuelto(false); //este está definido así en la clase préstamo

			fichero.writeObject(p);
			resultado = true;

		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
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
			} else
				System.out.println("Error al cerrar el fichero");
		}
		return resultado;
	}

	public ArrayList<Prestamo> obtenerPrestamos() {
		ArrayList<Prestamo> resultado = new ArrayList<Prestamo>();

		ObjectInputStream fichero = null;
		try {
			fichero = new ObjectInputStream(new FileInputStream(nombreFichero));

			while (true) {
				Prestamo p = (Prestamo) fichero.readObject();
				resultado.add(p);
			}

		} catch (EOFException e) {
			// TODO: handle exception
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
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

	public boolean devolver(Prestamo pP) {
		boolean resultado = false;
		
		ObjectInputStream ficheroO = null;
		ObjectOutputStream ficheroN = null;

		try {
			ficheroO = new ObjectInputStream(new FileInputStream(nombreFichero));

			ficheroN = new ObjectOutputStream(new FileOutputStream("prestamos.tmp", false));

			// leer ficheros hasta encontrar el objeto préstamos con el dni isbn y fechaP
			// si está lo escribimos cambiado, si no lo escribimos tal cual(con un while)

			while (true) {
				Prestamo p = (Prestamo) ficheroO.readObject();
				
				Calendar c = Calendar.getInstance();
				c.setTime(p.getFechaP());
				int dia1= c.get(c.DAY_OF_WEEK);
				int mes1=c.get(c.MONTH);
				int ano1=c.get(c.YEAR);
				
				Calendar c2 = Calendar.getInstance();
				c2.setTime(pP.getFechaP());
				int dia2=c2.get(c.DAY_OF_WEEK);
				int mes2=c2.get(c.MONTH);
				int ano2=c2.get(c.YEAR);

				if (/*(p.getFechaP().getTime()==pP.getFechaP().getTime())*/
						dia1==dia2&&mes1==mes2&&ano1==ano2
						
						&& (p.getSocio().getDni().equals(pP.getSocio().getDni()))
						&& (p.getLibro().getIsbn().equals(pP.getLibro().getIsbn()))) {
					p.setDevuelto(true);
				}
				else {
				}
				ficheroN.writeObject(p);
			}
			
		} catch(EOFException e) {
			
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {

			try {
				if (ficheroO != null) {
					ficheroO.close();
					if (ficheroN != null) {
						ficheroN.close();
					} else {
						System.out.println("Error al cerrar el fichero prestamos.tmp");
					}
				} else {
					System.out.println("Error al cerrar el archivo prestamos.obj");
				}
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		// cambiar nombre archivos
		
		File fO= new File(nombreFichero);
		if(fO.delete()) {
			File fN= new File("prestamos.tmp");
			if(fN.renameTo(fO)) {
				resultado = true;
			}
			else {
				System.out.println("Error al renombrar el archivo prestamos.tmp");
			}
		}
		else {
			System.out.println("Error al borrar el archivo prestamos.obj");
		}	

		return resultado;
	}

	public boolean borrar(Prestamo pP) {

		boolean resultado=false;
		
		ObjectInputStream ficheroO = null;
		ObjectOutputStream ficheroN = null;

		try {
			ficheroO = new ObjectInputStream(new FileInputStream(nombreFichero));

			ficheroN = new ObjectOutputStream(new FileOutputStream("prestamos.tmp", false));

			// leer ficheros hasta encontrar el objeto préstamos con el dni isbn y fechaP
			// si está lo escribimos cambiado, si no lo escribimos tal cual(con un while)

			while (true) {
				Prestamo p = (Prestamo) ficheroO.readObject();
				
				Calendar c = Calendar.getInstance();
				c.setTime(p.getFechaP());
				int dia1= c.get(c.DAY_OF_WEEK);
				int mes1=c.get(c.MONTH);
				int ano1=c.get(c.YEAR);
				
				Calendar c2 = Calendar.getInstance();
				c2.setTime(pP.getFechaP());
				int dia2=c2.get(c.DAY_OF_WEEK);
				int mes2=c2.get(c.MONTH);
				int ano2=c2.get(c.YEAR);

				//esta comparación es mucho mejor directamente comparando las fechas en forma de String
				
				if (/*(p.getFechaP().getTime()==pP.getFechaP().getTime())*/
						dia1==dia2&&mes1==mes2&&ano1==ano2
						
						&& (p.getSocio().getDni().equals(pP.getSocio().getDni()))
						&& (p.getLibro().getIsbn().equals(pP.getLibro().getIsbn()))) {
					//if vacío para ese objeto borrarlo, simplemente no lo escribe
				}
				else {
					ficheroN.writeObject(p);
				}
			}			
		} catch(EOFException e) {
			
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} finally {

			try {
				if (ficheroO != null) {
					ficheroO.close();
					if (ficheroN != null) {
						ficheroN.close();
					} else {
						System.out.println("Error al cerrar el fichero prestamos.tmp");
					}
				} else {
					System.out.println("Error al cerrar el archivo prestamos.obj");
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
		File fO= new File(nombreFichero);
		if(fO.delete()) {
			File fN= new File("prestamos.tmp");
			if(fN.renameTo(fO)) {
				resultado = true;
			}
			else {
				System.out.println("Error al renombrar el archivo prestamos.tmp");
			}
		}
		else {
			System.out.println("Error al borrar el archivo prestamos.obj");
		}
		
		return resultado;
	}

	public ArrayList<Prestamo> obtenerPrestamosSocio(Socio s) {
		ArrayList<Prestamo> resultado= new ArrayList<Prestamo>();
		
		ObjectInputStream fichero=null;
		
		try {
			fichero=new ObjectInputStream(new FileInputStream(nombreFichero));
			
			while(true) {
				//añadir solo los préstamos que sean del socio
				Prestamo p= (Prestamo)fichero.readObject();
				if(p.getSocio().getDni().equals(s.getDni())) {
					resultado.add(p);
				}
				else {
					
				}
			}
			
		} catch(EOFException e) {
			
		}catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return resultado;
	}
}
