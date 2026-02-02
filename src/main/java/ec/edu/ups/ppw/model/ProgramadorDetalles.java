package ec.edu.ups.ppw.model;

import jakarta.json.bind.annotation.JsonbTransient;
import jakarta.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "PW_PROGRAMADOR_DETALLES")
public class ProgramadorDetalles implements Serializable {

    // --- SECCIÓN INTACTA (ID COMPARTIDO CON USUARIO) ---
    @Id
    @Column(name = "det_programador_id")
    private int id;

    @OneToOne
    @MapsId // Esto hace que comparta la MISMA ID que Usuario (Clave Primaria Compartida)
    @JoinColumn(name = "det_programador_id")
    @JsonbTransient
    private Usuario usuario;
    @Column(name = "det_nombre_completo", length = 100)
    private String nombreCompleto;

    @Column(name = "det_especialidad", length = 50)
    private String especialidad;

    @Column(name = "det_biografia_breve", columnDefinition = "TEXT")
    private String biografia;

    // Faltaba este campo
    @Column(name = "det_telefono", length = 15)
    private String telefono;

    // Faltaba este campo
    @Column(name = "det_link_linkedin", length = 512)
    private String linkedinLink;

    @Column(name = "det_link_github", length = 512)
    private String githubLink;

    // Faltaba este campo
    @Column(name = "det_foto_perfil_url", length = 512)
    private String fotoPerfilUrl;

    // Faltaba este campo
    @Column(name = "det_activo")
    private Boolean activo;

    public ProgramadorDetalles() {}

    // --- GETTERS Y SETTERS ---
    
    @PrePersist
    public void prePersist() {
        if (this.activo == null) {
            this.activo = true;
        }
    }
    
    public String getNombreCompleto() {
        return nombreCompleto;
    }

    public void setNombreCompleto(String nombreCompleto) {
        this.nombreCompleto = nombreCompleto;
    }

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

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getLinkedinLink() {
        return linkedinLink;
    }

    public void setLinkedinLink(String linkedinLink) {
        this.linkedinLink = linkedinLink;
    }

    public String getGithubLink() {
        return githubLink;
    }

    public void setGithubLink(String githubLink) {
        this.githubLink = githubLink;
    }

    public String getFotoPerfilUrl() {
        return fotoPerfilUrl;
    }

    public void setFotoPerfilUrl(String fotoPerfilUrl) {
        this.fotoPerfilUrl = fotoPerfilUrl;
    }

    public Boolean getActivo() {
        return activo;
    }

    public void setActivo(Boolean activo) {
        this.activo = activo;
    }
}