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
		System.out.println(">>> INICIANDO CARGA DE DATOS DE PRUEBA...");

        // ==========================================
        // 1. CREACIÓN DE USUARIOS (El Programador y el Cliente)
        // ==========================================
        
        // Usuario 1: El Programador (DEV)
        Usuario programador = new Usuario();
        programador.setNombre("Alex");
        programador.setApellido("Velasco");
        programador.setEmail("alex.dev@mail.com");
        programador.setPassword("admin123"); // En la vida real esto iría encriptado
        programador.setRol(Role.DEV); // Usamos el Enum
        daoUsuario.insert(programador);
        
        // Usuario 2: El Cliente (USER)
        Usuario cliente = new Usuario();
        cliente.setNombre("Maria");
        cliente.setApellido("Cliente");
        cliente.setEmail("maria@cliente.com");
        cliente.setPassword("1234");
        cliente.setRol(Role.USER);
        daoUsuario.insert(cliente);

        System.out.println(">>> Usuarios creados correctamente.");

        // ==========================================
        // 2. DETALLES DEL PROGRAMADOR (1 a 1)
        // ==========================================
        ProgramadorDetalles detalles = new ProgramadorDetalles();
        // IMPORTANTE: Aquí vinculamos el detalle al objeto Usuario 'programador'
        detalles.setUsuario(programador); 
        detalles.setEspecialidad("Backend Java & Spring Boot");
        detalles.setBiografia("Estudiante de CS apasionado por Jakarta EE.");
        detalles.setGithubLink("github.com/alexdev");
        daoDetalles.insert(detalles);

        // ==========================================
        // 3. PROYECTOS (Portafolio)
        // ==========================================
        Proyecto pry = new Proyecto();
        pry.setProgramador(programador); // El dueño del proyecto
        pry.setNombre("Sistema de Portafolio");
        pry.setDescripcion("Proyecto final de la materia de Plataformas Web.");
        pry.setCategoria(ProjectCategory.ACADEMICO); // Enum
        pry.setUrlRepositorio("github.com/alexdev/portafolio");
        daoProyecto.insert(pry);

        // ==========================================
        // 4. DISPONIBILIDAD (Horario)
        // ==========================================
        Disponibilidad disp = new Disponibilidad();
        disp.setProgramador(programador);
        disp.setDiaSemana("MONDAY");
        disp.setHoraInicio(LocalTime.of(14, 0)); // 2:00 PM
        disp.setHoraFin(LocalTime.of(16, 0));    // 4:00 PM
        disp.setModalidad(Modality.VIRTUAL);
        daoDisponibilidad.insert(disp);

        // ==========================================
        // 5. ASESORÍA (Cita entre Cliente y Programador)
        // ==========================================
        Asesoria cita = new Asesoria();
        cita.setCliente(cliente);        // Quién pide
        cita.setProgramador(programador);// Quién da la asesoría
        cita.setFechaHoraInicio(LocalDateTime.now().plusDays(2)); // Cita para dentro de 2 días
        cita.setEstado(AdvisoryStatus.PENDIENTE);
        cita.setLinkReunion("zoom.us/j/123456");
        daoAsesoria.insert(cita);

        // ==========================================
        // 6. NOTIFICACIÓN (Simulacro de Email)
        // ==========================================
        Notificacion noti = new Notificacion();
        noti.setUsuario(programador); // Avisamos al programador que tiene nueva cita
        noti.setTipo(Notificacion.NotificationType.EMAIL);
        noti.setMensaje("Tienes una nueva solicitud de asesoría de Maria.");
        daoNotificacion.insert(noti);
        
        // ==========================================
        // 7. TOKEN (Simulacro de Login)
        // ==========================================
        

        // ==========================================
        // VERIFICACIÓN EN CONSOLA
        // ==========================================
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
