package pictures.taking.washing.web.rest;

import com.nimbusds.jose.*;
import com.nimbusds.jose.crypto.DirectEncrypter;
import com.nimbusds.jose.crypto.MACSigner;
import com.nimbusds.jwt.JWTClaimsSet;
import com.nimbusds.jwt.SignedJWT;
import pictures.taking.washing.ejb.interfaces.UserDAO;
import pictures.taking.washing.persistence.entities.User;
import pictures.taking.washing.persistence.security.PBKDF2WithHmacSHA1;
import pictures.taking.washing.web.Annotations.AuthenticatedUser;
import pictures.taking.washing.wrapper.Credentials;

import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.ejb.EJB;
import javax.ejb.Singleton;
import javax.enterprise.event.Event;
import javax.inject.Inject;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.util.Calendar;
import java.util.Date;
import java.util.UUID;

//@RequestScoped //unsing CDI but EJB
@Singleton
@Path("/authentication")
public class AuthenticationEndpoint {

    // encryption/decryption key for JWTs
    static SecretKey secretKey;

    @Inject
    @AuthenticatedUser
    Event<String> userAuthenticatedEvent;

    @EJB
    private UserDAO userDAO;


    // @GET
    // @Path("{id}")
    // @Produces(MediaType.APPLICATION_JSON)
    // public Response myUnsecuredMethod(@QueryParam("id") Long id) {
    // return Response.ok().entity(id == null ? "no message" : id).build();
    // // This method is not annotated with @Secured
    // // The authentication filter won't be executed before invoking this method
    // }

    public User getUserById(@PathParam("id") UUID id) {
        return userDAO.find(id);
    }

    // This method is annotated with @Secured
    // The authentication filter will be executed before invoking this method
    // The HTTP request must be performed with a valid token
    @Path("/user/{email}")
    @POST
    @Produces(MediaType.APPLICATION_JSON)
    public User getUserByEmail(@PathParam("email") String email) {
        return userDAO.findByEmail(email);
    }

    @Path("/apiLogin")
    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response authenticateUser(Credentials credentials) {
        // Authenticate the user, issue a token and return a response
//        System.out.println("ANFRAGE");
        String email = credentials.getEmail();
        String password = credentials.getPassword();

        try {

            // Authenticate the user using the credentials provided
            if (authenticate(email, password)) {
                // Issue a token for the user
                String token = issueToken(email);

                // Return the token on the response
                return Response.ok(token).build();
                // return Response.ok("SHIT").build();

            } else {
                throw new Exception("Falsches passwort/falscher Benutzername");
            }

        } catch (Exception e) {
            System.out.println(e.getMessage());
            return Response.status(Response.Status.FORBIDDEN).build();
        }

    }

//	@Path("/formLogin")
//	@POST
//	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
//	public HttpServletResponse authenticateUser(@Context HttpServletRequest request,
//			@Context HttpServletResponse response, @FormParam("username") String username,
//			@FormParam("password") String password) throws ServletException, IOException {
//
//		try {
//			// log into Application Server (Cookie)
//			request.login(username, password);
//			userAuthenticatedEvent.fire(username);
//			response.sendRedirect("/Washing/index.xhtml");
//
//		} catch (javax.ws.rs.ClientErrorException e) {
//			// response.setStatus(HttpServletResponse.SC_FORBIDDEN);
//		} catch (ServletException e) {
//			// response.setStatus(HttpServletResponse.SC_FORBIDDEN);
//		} catch (Exception e) {
////			response.setStatus(HttpServletResponse.SC_FORBIDDEN);
//			response.sendError(HttpServletResponse.SC_FORBIDDEN);
//			request.logout();
//		} finally {
//
//		}
//		return response;
//	}

    private boolean authenticate(String email, String password) throws Exception {
        String userPasswordFromDB = userDAO.findPasswordByEmail(email);
        return PBKDF2WithHmacSHA1.validatePassword(password, userPasswordFromDB);
    }

    private String issueToken(String username) throws JOSEException, NoSuchAlgorithmException {
        // Issue a token (can be a random String persisted to a database or a JWT token)
        // The issued token must be associated to a user
        // Return the issued token

        // Generate 256-bit AES key for HMAC as well as encryption
        KeyGenerator keyGen = KeyGenerator.getInstance("AES");
        keyGen.init(256);
        if(secretKey == null){
            secretKey = keyGen.generateKey();
        }

        // Create HMAC signer
        JWSSigner signer = new MACSigner(secretKey.getEncoded());

        // set expiration date
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.HOUR_OF_DAY, 3);

        // Prepare JWT with claims set
        JWTClaimsSet claimsSet = new JWTClaimsSet.Builder().subject(username).issueTime(new Date())
                .expirationTime(cal.getTime()).jwtID(UUID.randomUUID().toString()).issuer("https://washing.taking.pictures")
                .build();

        SignedJWT signedJWT = new SignedJWT(new JWSHeader(JWSAlgorithm.HS256), claimsSet);

        // Apply the HMAC protection
        signedJWT.sign(signer);

        // Create JWE object with signed JWT as payload
        JWEObject jweObject = new JWEObject(
                new JWEHeader.Builder(JWEAlgorithm.DIR, EncryptionMethod.A256GCM).contentType("JWT") // required to
                        // signal nested
                        // JWT
                        .build(),
                new Payload(signedJWT));

        // Perform encryption
        jweObject.encrypt(new DirectEncrypter(secretKey.getEncoded()));

        // Serialise to JWE compact form
        String jweString = jweObject.serialize();

        return jweString;

    }

    @GET
    @Path("/logout")
    public HttpServletResponse logout(@Context HttpServletRequest request, @Context HttpServletResponse response) {
        try {
            request.logout();
            response.sendRedirect("/Washing");

        } catch (ServletException e) {
            System.out.println("Logout Exception: " + e.getMessage());
        } catch (IOException e) {
            e.printStackTrace();
        }
        return response;
    }
}