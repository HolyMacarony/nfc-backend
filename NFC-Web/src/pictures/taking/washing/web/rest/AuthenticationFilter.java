package pictures.taking.washing.web.rest;

import com.nimbusds.jose.JWEObject;
import com.nimbusds.jose.crypto.DirectDecrypter;
import com.nimbusds.jose.crypto.MACVerifier;
import com.nimbusds.jwt.SignedJWT;
import pictures.taking.washing.web.Annotations.AuthenticatedRESTUser;

import javax.annotation.Priority;
import javax.enterprise.event.Event;
import javax.inject.Inject;
import javax.ws.rs.Priorities;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.Provider;

/**
 * If any problems happen during the token validation, a response with the
 * status 401 (Unauthorized) will be returned. Otherwise the request will
 * proceed to a resource method
 */
@Secured
@Provider
@Priority(Priorities.AUTHENTICATION)
public class AuthenticationFilter implements ContainerRequestFilter {

    // @Inject
    // private KeyGenerator keyGen;

    private static final String REALM = "example";
    private static final String AUTHENTICATION_SCHEME = "Bearer";

    protected static final String API_TOKEN = "eyJjdHkiOiJKV1QiLCJlbmMiOiJBMjU2R0NNIiwiYWxnIjoiZGlyIn0..3nb82wtxtpERQQZb.yA_TwF2UBTQNwykrcJKUEpV_ezuuWD7nKHCaE9ZIfa9owXB-cn6efjjcureWH49BZRecNZzlb5Dhxq2EA9fNYsbgLC3FRt3sIxA5P7RThDctLLVzQV2j-EgUrAv9CYpOITx2RgoiXKROoCNERvxqUmgmVH9NfhZ6wUTt3uW_-jvi--k3m1U2p6xH5dK8Zj061CgYwGXSgJGIKZ-R_CTGdTB7w9JeXZtVTGTwnLIBdCxPZTqJSfbsTAHrsVeVuzK6hv7VpCekJjuJZANNmh7WQvCTLgMylRWLGbJ5eSQDQd04-IG2mZ5SLsVUJilaK1YPFvTJrxEvNg.VtTMDvJY9UhoijslmMr8YQ";
    protected String authorizationHeader;

//	@Inject
//	@LoggedIn
//	User getAuthenticatedUser;


//    @Inject
//    @AuthenticatedUser
//    Event<String> userAuthenticatedEvent;

    @Inject
    @AuthenticatedRESTUser
    Event<String> restUserAuthenticatedEvent;

//    @Inject
//    @AuthenticatedUser
//    User authenticatedUser;

    @Override
    public void filter(ContainerRequestContext requestContext) {

        // Get the Authorization header from the request
        authorizationHeader = requestContext.getHeaderString(HttpHeaders.AUTHORIZATION);

        // Validate the Authorization header
        // if (!isTokenBasedAuthentication(authorizationHeader)) {
        // abortWithUnauthorized(requestContext);
        // return;
        // }

        // Extract the token from the Authorization header
        // String token = authorizationHeader
        // .substring(AUTHENTICATION_SCHEME.length()).trim();

        //ESP ACCESS
        if (!authorizationHeader.equals(API_TOKEN)) {

            String token = authorizationHeader;
            // REST API CALL
            if (token != null) {

                try {
                    if (!validateToken(token)) {
                        throw new Exception();
                    }

                } catch (Exception e) {
                    abortWithUnauthorized(requestContext);
                }
            }
            // WEB APP CALL
            else {
                // TODO
            }
        }
    }

    private boolean isTokenBasedAuthentication(String authorizationHeader) {

        // Check if the Authorization header is valid
        // It must not be null and must be prefixed with "Bearer" plus a whitespace
        // The authentication scheme comparison must be case-insensitive
        return authorizationHeader != null && authorizationHeader.toLowerCase().startsWith(AUTHENTICATION_SCHEME.toLowerCase() + " ");
    }

    private void abortWithUnauthorized(ContainerRequestContext requestContext) {

        // Abort the filter chain with a 401 status code response
        // The WWW-Authenticate header is sent along with the response
        requestContext.abortWith(Response.status(Response.Status.UNAUTHORIZED).header(HttpHeaders.WWW_AUTHENTICATE, AUTHENTICATION_SCHEME + " realm=\"" + REALM + "\"").build());
    }

    private boolean validateToken(String token) throws Exception {
        // Check if the token was issued by the server and if it's not expired
        // Throw an Exception if the token is invalid

        // Parse the JWE string
        JWEObject jweObject = JWEObject.parse(token);

        // Decrypt with shared key
        jweObject.decrypt(new DirectDecrypter(AuthenticationEndpoint.secretKey.getEncoded()));

        // Extract payload
        SignedJWT signedJWT = jweObject.getPayload().toSignedJWT();

        if (signedJWT.equals(null)) {
            throw new Exception("Payload not a signed JWT");
        }

        if (!signedJWT.verify(new MACVerifier(AuthenticationEndpoint.secretKey.getEncoded()))) {
            throw new Exception("HMAC could not be verified");
        }

        // Retrieve the JWT claims...
        // signedJWT.getJWTClaimsSet().getSubject();

        // checking expiration date... //TODO
        // maybe Roleset given in the token? not really

        // System.out.println(signedJWT.getJWTClaimsSet().getSubject()+"
        // autentifiziert");
//        userAuthenticatedEvent.fire(signedJWT.getJWTClaimsSet().getSubject());
        restUserAuthenticatedEvent.fire(signedJWT.getJWTClaimsSet().getSubject());
        return true;

    }

}