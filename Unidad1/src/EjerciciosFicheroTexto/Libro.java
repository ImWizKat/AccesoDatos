package EjerciciosFicheroTexto;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Libro {
	private String isbn,	
	titulo,
	autor;
	private Date fechaLanzamiento;
	private int numEjemplares;
	
	public Libro() {
		
	}

	public Libro(String isbn, String titulo, String autor, Date fechaLanzamiento, int numEjemplares) {
		
		this.isbn = isbn;
		this.titulo = titulo;
		this.autor = autor;
		this.fechaLanzamiento = fechaLanzamiento;
		this.numEjemplares = numEjemplares;
	}
	
	public void mostrar() {
		SimpleDateFormat formato = new SimpleDateFormat("dd/MM/yyyy");
	
		System.out.println("ISBN:" + isbn + 
				"\tTitulo:" + titulo + 
				"\tAutor:" + autor +
				"\tFecha:" + formato.format(fechaLanzamiento) +
				"\tEjemplares:" + numEjemplares);
	}

	public String getIsbn() {
		return isbn;
	}

	public void setIsbn(String isbn) {
		this.isbn = isbn;
	}

	public String getTitulo() {
		return titulo;
	}

	public void setTitulo(String titulo) {
		this.titulo = titulo;
	}

	public String getAutor() {
		return autor;
	}

	public void setAutor(String autor) {
		this.autor = autor;
	}

	public Date getFechaLanzamiento() {
		return fechaLanzamiento;
	}

	public void setFechaLanzamiento(Date fechaLanzamiento) {
		this.fechaLanzamiento = fechaLanzamiento;
	}

	public int getNumEjemplares() {
		return numEjemplares;
	}

	public void setNumEjemplares(int numEjemplares) {
		this.numEjemplares = numEjemplares;
	}
	
	
	
}
