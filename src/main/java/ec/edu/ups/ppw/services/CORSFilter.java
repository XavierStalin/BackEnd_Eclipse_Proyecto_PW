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

    @Override
    public void filter(ContainerRequestContext requestContext, 
                      ContainerResponseContext responseContext) throws IOException {

        // Extraemos el origen de la solicitud entrante
        String origin = requestContext.getHeaderString("Origin");

        // Si el origen está en nuestra lista, lo devolvemos en el header
        if (origin != null && ALLOWED_ORIGINS.contains(origin)) {
            responseContext.getHeaders().add("Access-Control-Allow-Origin", origin);
        }

        // Headers necesarios para Angular y seguridad
        responseContext.getHeaders().add(
                "Access-Control-Allow-Headers",
                "origin, content-type, accept, authorization, x-requested-with");

        responseContext.getHeaders().add(
                "Access-Control-Allow-Methods",
                "GET, POST, PUT, DELETE, OPTIONS, HEAD");

        // Permitir el envío de credenciales (esencial si usas JWT o cookies)
        responseContext.getHeaders().add("Access-Control-Allow-Credentials", "true");
        
        // Tiempo en caché para la respuesta preflight (opcional, 2 horas)
        responseContext.getHeaders().add("Access-Control-Max-Age", "7200");
    }
}
