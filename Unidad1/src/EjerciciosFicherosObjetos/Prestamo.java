package EjerciciosFicherosObjetos;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;

import EjerciciosFicheroBinario.Socio;
import EjerciciosFicheroTexto.Libro;

public class Prestamo implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 2383214451180297924L;
	private Socio socio;
	private Libro libro;
	private Date fechaP, fechaD;
	private boolean devuelto=false;
	
	public Prestamo() {
		
	}
	
	public Prestamo(Socio socio, Libro libro, Date fechaP, Date fechaD, boolean devuelto) {
		super();
		this.socio = socio;
		this.libro = libro;
		this.fechaP = fechaP;
		this.fechaD = fechaD;
		this.devuelto = false;
	}	
	
	public void mostrar() {
		SimpleDateFormat formato=new SimpleDateFormat("dd/MM/yyyy");
		System.out.println("Socio: " + socio.getDni() + " " + socio.getNombre() + 
				"\tLibro: " + libro.getIsbn() + " " + libro.getTitulo() + 
				"\tFechaPréstamo: " + formato.format(fechaP) + 
				"\tFechaDevolución: " + formato.format(fechaD) + 
				"\tDevuelto: " + devuelto);
		
	}
	
	public Socio getSocio() {
		return socio;
	}

	public void setSocio(Socio socio) {
		this.socio = socio;
	}

	public Libro getLibro() {
		return libro;
	}

	public void setLibro(Libro libro) {
		this.libro = libro;
	}

	public Date getFechaP() {
		return fechaP;
	}

	public void setFechaP(Date fechaP) {
		this.fechaP = fechaP;
	}

	public Date getFechaD() {
		return fechaD;
	}

	public void setFechaD(Date fechaD) {
		this.fechaD = fechaD;
	}

	public boolean isDevuelto() {
		return devuelto;
	}

	public void setDevuelto(boolean devuelto) {
		this.devuelto = devuelto;
	}

	
}
