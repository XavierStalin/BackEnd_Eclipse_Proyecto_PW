package ec.edu.ups.ppw.services;

public class Error {
	private int codigo;
	private String name;
	private String descripcion;
	
	
	public Error(int codigo, String name, String descripcion) {
		super();
		this.codigo = codigo;
		this.name = name;
		this.descripcion = descripcion;
	}
	public int getCodigo() {
		return codigo;
	}
	public void setCodigo(int codigo) {
		this.codigo = codigo;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getDescripcion() {
		return descripcion;
	}
	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}
	
}
