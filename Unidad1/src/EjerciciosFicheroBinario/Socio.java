package EjerciciosFicheroBinario;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Socio implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1234400966591407510L;
	private String dni;
	private String nombre;
	private Date fechaN;
	private boolean activo;
	
	public Socio() {
		
	}

	public Socio(String dni, String nombre, Date fechaN, boolean activo) {
		this.dni = dni;
		this.nombre = nombre;
		this.fechaN = fechaN;
		this.activo = activo;
	}
	
	public void mostrar() {
		SimpleDateFormat formato= new SimpleDateFormat("dd/MM/yyyy");
		System.out.println("DNI: " + dni
				+ "\tNombre: " + nombre
				+ "\tFechaN: " + formato.format(fechaN)
				+ "\tActivo: " + activo);
	}

	public String getDni() {
		return dni;
	}

	public void setDni(String dni) {
		this.dni = dni;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public Date getFechaN() {
		return fechaN;
	}

	public void setFechaN(Date fechaN) {
		this.fechaN = fechaN;
	}

	public boolean isActivo() {
		return activo;
	}

	public void setActivo(boolean activo) {
		this.activo = activo;
	}
	
	
	
}
