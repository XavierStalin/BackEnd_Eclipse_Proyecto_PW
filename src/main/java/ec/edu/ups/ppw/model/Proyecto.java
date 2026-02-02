package ec.edu.ups.ppw.model;

import jakarta.json.bind.annotation.JsonbTransient;
import jakarta.persistence.*;
import java.time.LocalDateTime;

import ec.edu.ups.ppw.model.enums.ProjectCategory;

@Entity
@Table(name = "PW_PROYECTOS")
public class Proyecto {
    
    public Proyecto() {}

    // --- SECCIÓN INTACTA ---
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
    // -----------------------

    @Enumerated(EnumType.STRING)
    @Column(name = "pry_categoria", nullable = false)
    private ProjectCategory categoria;

    @Column(name = "pry_nombre", length = 100, nullable = false)
    private String nombre;

    @Column(name = "pry_descripcion", columnDefinition = "TEXT")
    private String descripcion;
    
    // Agregado: pry_tecnologias_usadas
    @Column(name = "pry_tecnologias_usadas", length = 255)
    private String tecnologiasUsadas;
    
    @Column(name = "pry_url_repositorio", length = 512)
    private String urlRepositorio;
    
    // Agregado: pry_url_despliegue
    @Column(name = "pry_url_despliegue", length = 512)
    private String urlDespliegue;

    // Agregado: pry_url_imagen_preview
    @Column(name = "pry_url_imagen_preview", length = 512)
    private String urlImagenPreview;

    // Agregado: pry_created_at
    @Column(name = "pry_created_at")
    private LocalDateTime createdAt;

    // Agregado: pry_updated_at
    @Column(name = "pry_updated_at")
    private LocalDateTime updatedAt;

    // Agregado: pry_activo
    @Column(name = "pry_activo")
    private Boolean activo;

    // --- MÉTODOS DE AUDITORÍA (Opcional, pero recomendado para fechas automáticas) ---
    @PrePersist
    public void prePersist() {
        this.createdAt = LocalDateTime.now();
        this.updatedAt = LocalDateTime.now();
        if(this.activo == null) this.activo = true;
    }

    @PreUpdate
    public void preUpdate() {
        this.updatedAt = LocalDateTime.now();
    }

    // --- GETTERS Y SETTERS ---

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
    
    // TRUCO: Este getter permite que el JSON muestre el ID aunque el objeto Usuario esté oculto
    public Integer getProgramadorId() {
        return programador != null ? programador.getId() : null;
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

    public String getTecnologiasUsadas() {
        return tecnologiasUsadas;
    }

    public void setTecnologiasUsadas(String tecnologiasUsadas) {
        this.tecnologiasUsadas = tecnologiasUsadas;
    }

    public String getUrlRepositorio() {
        return urlRepositorio;
    }

    public void setUrlRepositorio(String urlRepositorio) {
        this.urlRepositorio = urlRepositorio;
    }

    public String getUrlDespliegue() {
        return urlDespliegue;
    }

    public void setUrlDespliegue(String urlDespliegue) {
        this.urlDespliegue = urlDespliegue;
    }

    public String getUrlImagenPreview() {
        return urlImagenPreview;
    }

    public void setUrlImagenPreview(String urlImagenPreview) {
        this.urlImagenPreview = urlImagenPreview;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }

    public Boolean getActivo() {
        return activo;
    }

    public void setActivo(Boolean activo) {
        this.activo = activo;
    }
}