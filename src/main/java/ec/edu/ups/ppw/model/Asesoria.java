package ec.edu.ups.ppw.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;

import ec.edu.ups.ppw.model.enums.AdvisoryStatus;

@Entity
@Table(name = "PW_ASESORIAS")
public class Asesoria {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ase_id")
    private int id;

    // Relación con el Cliente (Quien pide la cita)
    @ManyToOne
    @JoinColumn(name = "ase_cliente_id", nullable = false)
    private Usuario cliente;

    // Relación con el Programador (Quien da la cita)
    @ManyToOne
    @JoinColumn(name = "ase_programador_id", nullable = false)
    private Usuario programador;

    @Column(name = "ase_fecha_hora_inicio", nullable = false)
    private LocalDateTime fechaHoraInicio;
    //aqui si imprime todo sin errores, ver porque

    @Enumerated(EnumType.STRING)
    @Column(name = "ase_estado")
    private AdvisoryStatus estado;

    @Column(name = "ase_link_reunion", length = 512)
    private String linkReunion; // Para cuando la modalidad sea Virtual [cite: 40]

    public Asesoria() {
        this.estado = AdvisoryStatus.PENDIENTE;
    }

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Usuario getCliente() {
		return cliente;
	}

	public void setCliente(Usuario cliente) {
		this.cliente = cliente;
	}

	public Usuario getProgramador() {
		return programador;
	}

	public void setProgramador(Usuario programador) {
		this.programador = programador;
	}

	public LocalDateTime getFechaHoraInicio() {
		return fechaHoraInicio;
	}

	public void setFechaHoraInicio(LocalDateTime fechaHoraInicio) {
		this.fechaHoraInicio = fechaHoraInicio;
	}

	public AdvisoryStatus getEstado() {
		return estado;
	}

	public void setEstado(AdvisoryStatus estado) {
		this.estado = estado;
	}

	public String getLinkReunion() {
		return linkReunion;
	}

	public void setLinkReunion(String linkReunion) {
		this.linkReunion = linkReunion;
	}
    
    
}