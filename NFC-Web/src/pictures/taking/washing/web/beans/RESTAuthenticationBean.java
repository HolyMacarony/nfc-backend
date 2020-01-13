package pictures.taking.washing.web.beans;

import pictures.taking.washing.persistence.entities.User;
import pictures.taking.washing.web.Annotations.*;
import pictures.taking.washing.web.Annotations.AuthenticatedRESTUser;

import javax.annotation.security.DeclareRoles;
import javax.enterprise.context.RequestScoped;
import javax.enterprise.event.Event;
import javax.enterprise.event.Observes;
import javax.enterprise.inject.Model;
import javax.enterprise.inject.Produces;
import javax.inject.Inject;
import javax.inject.Named;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import java.io.Serializable;


@Model  // for being found by CDI
@Named("RESTauthBean")
@RequestScoped
@DeclareRoles(value = {"ADMINISTRATOR", "USER", "GUEST"})
public class RESTAuthenticationBean implements Serializable {

    private static final long serialVersionUID = 1L;

    @Inject
    @AuthenticatedRESTUser
    Event<String> restUserAuthenticatedEvent;

    @Inject
    private RESTAuthenticationBean authBean;




    public void setAuthenticatedRESTUser(User authenticatedRESTUser) {
        this.authenticatedRESTUser = authenticatedRESTUser;
    }

    @Produces
    @RequestScoped
    @AuthenticatedRESTUser
    private User authenticatedRESTUser;


    @PersistenceContext
    private EntityManager em;


    private User findUserByEmail(String email) {
        try {
            return (User) em.createNamedQuery(User.QUERY_FINDBYEMAIL).setParameter("email", email).getSingleResult();
        } catch (NoResultException e) {
            return null;
        }
    }

    public void handleRESTAuthenticationEvent(@Observes @AuthenticatedRESTUser String email) {
        this.authenticatedRESTUser = findUserByEmail(email);
    }}



