package ec.edu.ups.ppw.business;


import jakarta.annotation.PostConstruct;
import jakarta.ejb.Singleton;
import jakarta.ejb.Startup;
import jakarta.inject.Inject;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;


import ec.edu.ups.ppw.dao.*;
import ec.edu.ups.ppw.model.*;
import ec.edu.ups.ppw.model.enums.*;

@Singleton
@Startup
public class Demo {
	
	
	@Inject
    private UsuarioDAO daoUsuario;
    @Inject
    private ProgramadorDetallesDAO daoDetalles;
    @Inject
    private ProyectoDAO daoProyecto;
    @Inject
    private DisponibilidadDAO daoDisponibilidad;
    @Inject
    private AsesoriaDAO daoAsesoria;
    @Inject
    private NotificacionDAO daoNotificacion;
    @Inject
    private TokenDAO daoToken;
	
	@PostConstruct
	public void init() {
		
		
		//USUARIOS 
        //-------------------------------------
        //DEV
        Usuario programador = new Usuario();
        programador.setCedula("1234567891");
        programador.setNombre("Alex");
        programador.setApellido("Velasco");
        programador.setEmail("alex.dev@mail.com");
        programador.setPassword("admin123"); 
        programador.setRol(Role.DEV);
        daoUsuario.insert(programador);
        //USER
        Usuario cliente = new Usuario();
        cliente.setCedula("1401041544");
        cliente.setNombre("Maria");
        cliente.setApellido("Ortega");
        cliente.setEmail("maria@cliente.com");
        cliente.setPassword("1234");
        cliente.setRol(Role.USER);
        daoUsuario.insert(cliente);

        //DETALLES DEL PROGRAMADOR
        //-------------------------------------
        ProgramadorDetalles detalles = new ProgramadorDetalles();
        detalles.setUsuario(programador); 
        detalles.setEspecialidad("Backend Java & Spring Boot");
        detalles.setBiografia("Estudiante de CS apasionado por Jakarta EE.");
        detalles.setGithubLink("github.com/alexdev");
        daoDetalles.insert(detalles);

        //PROYECTOS (Portafolio)
        //-------------------------------------
        Proyecto pry = new Proyecto();
        pry.setProgramador(programador); // El dueño del proyecto
        pry.setNombre("Sistema de Portafolio");
        pry.setDescripcion("Proyecto final de la materia de Plataformas Web.");
        pry.setCategoria(ProjectCategory.ACADEMICO); // Enum
        pry.setUrlRepositorio("github.com/alexdev/portafolio");
        daoProyecto.insert(pry);

        //DISPONIBILIDAD (Horario)
        //-------------------------------------
        Disponibilidad disp = new Disponibilidad();
        disp.setProgramador(programador);
        disp.setDiaSemana("lunes");
        disp.setHoraInicio(LocalTime.of(14, 0)); // 2:00 PM
        disp.setHoraFin(LocalTime.of(16, 0));// 4:00 PM
        disp.setModalidad(Modality.VIRTUAL);
        daoDisponibilidad.insert(disp);
        
        Disponibilidad disp2 = new Disponibilidad();
        disp2.setProgramador(programador);
        disp2.setDiaSemana("MARTES");
        disp2.setHoraInicio(LocalTime.of(15, 0)); // 2:00 PM
        disp2.setHoraFin(LocalTime.of(17, 0));// 4:00 PM
        disp2.setModalidad(Modality.PRESENCIAL);
        daoDisponibilidad.insert(disp2);

        //ASESORÍA (Cita entre Cliente y Programador)
        //-------------------------------------
        Asesoria cita = new Asesoria();
        cita.setCliente(cliente);//Cliente que pide
        cita.setProgramador(programador);// Dev que da la asesorIa
        cita.setFechaHoraInicio(LocalDateTime.now().plusDays(2)); // EN 2 DIAS
        cita.setEstado(AdvisoryStatus.PENDIENTE);
        cita.setLinkReunion("zoom.us/j/123456");
        daoAsesoria.insert(cita);

        //NOTIFICACIÓN (Simulacro de Email)
        //-------------------------------------
        Notificacion noti = new Notificacion();
        noti.setUsuario(programador); // Avisamos al programador que tiene nueva cita
        noti.setTipo(Notificacion.NotificationType.EMAIL);
        noti.setMensaje("Tienes una nueva solicitud de asesoría de Maria.");
        daoNotificacion.insert(noti);
        imprimirDatos();
        Notificacion noti2 = new Notificacion();
        noti.setUsuario(cliente); // Avisamos al programador que tiene nueva cita
        noti.setTipo(Notificacion.NotificationType.EMAIL);
        noti.setMensaje("Tienes una nueva solicitud de asesoría de Alex!!!.");
        daoNotificacion.insert(noti);
        imprimirDatos();
		
	}
	private void imprimirDatos() {
        System.out.println("========== IMPRIMIENDO REPORTE DE LA BD ==========");
        
        // 1. Usuarios
        List<Usuario> usuarios = daoUsuario.getAll();
        System.out.println("USUARIOS REGISTRADOS: " + usuarios.size());
        for(Usuario u : usuarios) {
            System.out.println(" - " + u.getNombre() + " (" + u.getRol() + ")");
        }

        // 2. Proyectos
        List<Proyecto> proyectos = daoProyecto.getAll();
        System.out.println("\nPROYECTOS EN PORTAFOLIO:");
        for(Proyecto p : proyectos) {
            // Fíjate cómo navegamos el objeto: p.getProgramador().getNombre()
            System.out.println(" - Proyecto: " + p.getNombre() + " | Autor: " + p.getProgramador().getNombre());
        }

        // 3. Asesorías
        List<Asesoria> citas = daoAsesoria.getAll();
        System.out.println("\nCITAS AGENDADAS:");
        for(Asesoria a : citas) {
            System.out.println(" - Cliente: " + a.getCliente().getNombre() + 
                               " con Dev: " + a.getProgramador().getNombre() +
                               " | Estado: " + a.getEstado());
        }
        
        System.out.println("==================================================");
    }
	
}
