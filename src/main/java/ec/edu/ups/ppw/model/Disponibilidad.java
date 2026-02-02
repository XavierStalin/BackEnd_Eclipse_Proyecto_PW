package ec.edu.ups.ppw.model;

import jakarta.json.bind.annotation.JsonbTransient;
import jakarta.persistence.*;
import java.time.LocalTime;

import ec.edu.ups.ppw.model.enums.Modality;

@Entity
@Table(name = "PW_DISPONIBILIDADES")
public class Disponibilidad {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "dsp_id")
    private int id;

    // Relación: Un horario pertenece a un Programador
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "dsp_programador_id", nullable = false)
    @JsonbTransient
    private Usuario programador;

    @Column(name = "dsp_dia_semana", length = 20, nullable = false)
    private String diaSemana; // Ej: "MONDAY", "TUESDAY"

    @Column(name = "dsp_hora_inicio", nullable = false)
    private LocalTime horaInicio;

    @Column(name = "dsp_hora_fin", nullable = false)
    private LocalTime horaFin;

    @Enumerated(EnumType.STRING)
    @Column(name = "dsp_modalidad", nullable = false)
    private Modality modalidad; // VIRTUAL o PRESENCIAL

    public Disponibilidad() {}

 // Permite ver el ID del programador en el JSON aunque el objeto Usuario esté oculto
    public Integer getProgramadorId() {
        return programador != null ? programador.getId() : null;
    }
    
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

	public String getDiaSemana() {
		return diaSemana;
	}

	public void setDiaSemana(String diaSemana) {
		this.diaSemana = diaSemana;
	}

	public LocalTime getHoraInicio() {
		return horaInicio;
	}

	public void setHoraInicio(LocalTime horaInicio) {
		this.horaInicio = horaInicio;
	}

	public LocalTime getHoraFin() {
		return horaFin;
	}

	public void setHoraFin(LocalTime horaFin) {
		this.horaFin = horaFin;
	}

	public Modality getModalidad() {
		return modalidad;
	}

	public void setModalidad(Modality modalidad) {
		this.modalidad = modalidad;
	}
    
    
}