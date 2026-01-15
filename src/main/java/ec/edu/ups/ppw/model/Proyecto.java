package ec.edu.ups.ppw.model;

import jakarta.json.bind.annotation.JsonbTransient;
import jakarta.persistence.*;
import java.time.LocalDateTime;

import ec.edu.ups.ppw.model.enums.ProjectCategory;

@Entity
@Table(name = "PW_PROYECTOS")
public class Proyecto {
	public Proyecto() {}
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "pry_id")
    private int id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "pry_programador_id", nullable = false)
    @JsonbTransient
    //AUN EL JSON DEBE MOSTRAR ALGUNA INFORMACION SOBRE 
    //EL PROGRAMADOR, COMO MINIMO UN ID
    private Usuario programador; // El dueño del portafolio

    @Enumerated(EnumType.STRING)
    @Column(name = "pry_categoria", nullable = false)
    private ProjectCategory categoria; // 

    @Column(name = "pry_nombre", length = 100, nullable = false)
    private String nombre;

    @Column(name = "pry_descripcion", columnDefinition = "TEXT")
    private String descripcion;
    
    //FALTA: tecnologias usadas 
    
    @Column(name = "pry_url_repositorio", length = 512)
    private String urlRepositorio;
    
    //FALTA: url despliegue
    //FALTA: url imagen preview
    //FALTA: created at
    //FALTA: updated at
    //FALTA: activo??

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Usuario getProgramador() {
		return programador;
	}

	public void setProgramador(Usuario programador) {
		this.programador = programador;
	}

	public ProjectCategory getCategoria() {
		return categoria;
	}

	public void setCategoria(ProjectCategory categoria) {
		this.categoria = categoria;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public String getUrlRepositorio() {
		return urlRepositorio;
	}

	public void setUrlRepositorio(String urlRepositorio) {
		this.urlRepositorio = urlRepositorio;
	}
    
    
}