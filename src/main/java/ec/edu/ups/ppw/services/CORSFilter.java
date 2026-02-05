package ec.edu.ups.ppw.services;

import jakarta.ws.rs.container.ContainerRequestContext;
import jakarta.ws.rs.container.ContainerResponseContext;
import jakarta.ws.rs.container.ContainerResponseFilter;
import jakarta.ws.rs.ext.Provider;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

@Provider
public class CORSFilter implements ContainerResponseFilter {

    // Lista de tus dominios permitidos en Firebase
    private static final List<String> ALLOWED_ORIGINS = Arrays.asList(
        "https://portafolio-integrador-2025.web.app",
        "https://portafolio-integrador-2025.firebaseapp.com",
        "http://localhost:4200" // Para tus pruebas locales
    );

    public void filter(ContainerRequestContext req, ContainerResponseContext res) {
    // Para probar, devuelve siempre el origen que llega
    String origin = req.getHeaderString("Origin");
    if (origin != null) {
        res.getHeaders().add("Access-Control-Allow-Origin", origin);
    }
    res.getHeaders().add("Access-Control-Allow-Headers", "*");
    res.getHeaders().add("Access-Control-Allow-Credentials", "true");
    res.getHeaders().add("Access-Control-Allow-Methods", "GET, POST, PUT, DELETE, OPTIONS");
}
}
