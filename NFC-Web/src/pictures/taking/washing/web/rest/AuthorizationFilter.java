package pictures.taking.washing.web.rest;

import pictures.taking.washing.persistence.entities.User;
import pictures.taking.washing.persistence.entities.Usergroup;
import pictures.taking.washing.persistence.enums.SecurityroleEnum;
import pictures.taking.washing.web.Annotations.AuthenticatedRESTUser;
import pictures.taking.washing.web.beans.AuthenticationBean;
import pictures.taking.washing.persistence.entities.Securityrole;

import javax.annotation.Priority;
import javax.inject.Inject;
import javax.ws.rs.Priorities;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;
import javax.ws.rs.container.ResourceInfo;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.Provider;
import java.lang.reflect.AnnotatedElement;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Secured
@Provider
@Priority(Priorities.AUTHORIZATION)
public class AuthorizationFilter implements ContainerRequestFilter {

    @Inject
    private AuthenticationBean authBean;

    @Inject
    @AuthenticatedRESTUser
    User authenticatedRESTUser;

    @Context
    private ResourceInfo resourceInfo;

    protected String authorizationHeader;


    @Override
    public void filter(ContainerRequestContext requestContext) {

        // Get the resource class which matches with the requested URL
        // Extract the roles declared by it
        Class<?> resourceClass = resourceInfo.getResourceClass();
        List<SecurityroleEnum> classRoles = extractRoles(resourceClass);

        // Get the resource method which matches with the requested URL
        // Extract the roles declared by it
        Method resourceMethod = resourceInfo.getResourceMethod();
        List<SecurityroleEnum> methodRoles = extractRoles(resourceMethod);

        authorizationHeader = requestContext.getHeaderString(HttpHeaders.AUTHORIZATION);


        //ESP ACCESS
        if (!AuthenticationFilter.API_TOKEN.equals(authorizationHeader)) {
            try {

                // Check if the user is allowed to execute the method
                // The method annotations override the class annotations
                if (methodRoles.isEmpty()) {
                    checkPermissions(classRoles);
                } else {
                    checkPermissions(methodRoles);
                }

            } catch (Exception e) {
                requestContext.abortWith(Response.status(Response.Status.FORBIDDEN).build());
            }
        }
    }

    // Extract the roles from the annotated element
    private List<SecurityroleEnum> extractRoles(AnnotatedElement annotatedElement) {
        if (annotatedElement == null) {
            return new ArrayList<SecurityroleEnum>();
        } else {
            Secured secured = annotatedElement.getAnnotation(Secured.class);
            if (secured == null) {
                return new ArrayList<SecurityroleEnum>();
            } else {
                SecurityroleEnum[] allowedRoles = secured.value();
                return Arrays.asList(allowedRoles);
            }
        }
    }

    private void checkPermissions(List<SecurityroleEnum> allowedRoles) throws Exception {
        // Check if the user contains one of the allowed roles
        // Throw an Exception if the user does not have permission to execute the method

        boolean isAllowed = false;
        if (authenticatedRESTUser.getId() != null) {
            // Role USER is implicit set for every authenticated user and thus not stored in the DB
            // every authenticated user is allowed to execute
            if (allowedRoles.contains(SecurityroleEnum.USER)) {
                return;
            }

            for (Usergroup group : authenticatedRESTUser.getUsergroups()) {
                for (Securityrole roleEntity : group.getSecurityroles()) {
                    for (SecurityroleEnum roleEnum : allowedRoles) {
                        if (roleEnum.name().equals(roleEntity.getName())) {
                            isAllowed = true;
                        }
                    }
                }
            }
        }
        if (!isAllowed) {
            throw new Exception(Response.Status.FORBIDDEN.getReasonPhrase());
        }
//        authBean.setAuthenticatedRESTUser(null);
    }
}
