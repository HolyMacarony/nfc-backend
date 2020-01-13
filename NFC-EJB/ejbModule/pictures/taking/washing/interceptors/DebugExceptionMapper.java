package pictures.taking.washing.interceptors;

import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;
import javax.ws.rs.core.Response;

@Provider
public class DebugExceptionMapper implements ExceptionMapper<Exception> {
    private static final String REALM = "example";
    private static final String AUTHENTICATION_SCHEME = "Bearer";
    @Override
    public Response toResponse(Exception exception) {
        exception.printStackTrace();
       return Response.status(Response.Status.UNAUTHORIZED).header(HttpHeaders.WWW_AUTHENTICATE, AUTHENTICATION_SCHEME + " realm=\"" + REALM + "\"").build();
//        return Response.serverError().entity(exception.getMessage()).build();
    }
}
