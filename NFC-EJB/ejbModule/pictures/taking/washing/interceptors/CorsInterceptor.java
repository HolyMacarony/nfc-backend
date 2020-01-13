package pictures.taking.washing.interceptors;

import javax.ws.rs.container.*;
import javax.ws.rs.ext.Provider;

@Provider
@PreMatching
public class CorsInterceptor implements ContainerRequestFilter, ContainerResponseFilter {
    @Override
    public void filter(ContainerRequestContext requestContext) {
        requestContext.getHeaders().putSingle("Access-Control-Allow-Origin", "*");
        requestContext.getHeaders().putSingle("Access-Control-Allow-Methods", "GET, POST, DELETE, PUT");
    }

    @Override
    public void filter(ContainerRequestContext requestContext, ContainerResponseContext responseContext) {
        responseContext.getHeaders().add("Access-Control-Allow-Origin", "*");
        responseContext.getHeaders().add("Access-Control-Allow-Methods", "GET, POST, DELETE, PUT");

    }

}
