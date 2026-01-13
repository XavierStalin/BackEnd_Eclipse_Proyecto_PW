package ec.edu.ups.ppw.model;
import jakarta.json.bind.annotation.JsonbTransient;
import jakarta.persistence.*;

@Entity
@Table(name = "PW_PROGRAMADOR_DETALLES")
public class ProgramadorDetalles {

    @Id
    @Column(name = "det_programador_id")
    private int id;

    @OneToOne
    @MapsId // Esto hace que comparta la MISMA ID que Usuario (Clave Primaria Compartida)
    @JoinColumn(name = "det_programador_id")
    @JsonbTransient
    private Usuario usuario;

    @Column(name = "det_especialidad", length = 50)
    private String especialidad;

    @Column(name = "det_biografia_breve", columnDefinition = "TEXT")
    private String biografia;
    
    @Column(name = "det_link_github", length = 512)
    private String githubLink;

    public ProgramadorDetalles() {}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

	public String getEspecialidad() {
		return especialidad;
	}

	public void setEspecialidad(String especialidad) {
		this.especialidad = especialidad;
	}

	public String getBiografia() {
		return biografia;
	}

	public void setBiografia(String biografia) {
		this.biografia = biografia;
	}

	public String getGithubLink() {
		return githubLink;
	}

	public void setGithubLink(String githubLink) {
		this.githubLink = githubLink;
	}
    
}