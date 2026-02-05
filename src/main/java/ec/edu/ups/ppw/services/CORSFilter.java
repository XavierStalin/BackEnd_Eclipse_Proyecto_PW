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

    @Override
    public void filter(ContainerRequestContext req, ContainerResponseContext res) {

        String origin = req.getHeaderString("Origin");

        if (origin != null) {
            res.getHeaders().putSingle("Access-Control-Allow-Origin", origin);
        }

        res.getHeaders().putSingle(
            "Access-Control-Allow-Headers",
            "Origin, Content-Type, Accept, Authorization"
        );

        res.getHeaders().putSingle(
            "Access-Control-Allow-Methods",
            "GET, POST, PUT, DELETE, OPTIONS"
        );

        res.getHeaders().putSingle("Access-Control-Allow-Credentials", "true");
    }
}
