package pictures.taking.washing.web.beans;

import com.ocpsoft.pretty.faces.annotation.URLMapping;
import com.ocpsoft.pretty.faces.annotation.URLMappings;
import pictures.taking.washing.helper.MSG;
import pictures.taking.washing.helper.ResourceBundle;
import pictures.taking.washing.persistence.entities.User;
import pictures.taking.washing.web.Annotations.*;
import pictures.taking.washing.web.Annotations.*;

import javax.annotation.security.DeclareRoles;
import javax.annotation.security.RolesAllowed;
import javax.enterprise.context.SessionScoped;
import javax.enterprise.event.Event;
import javax.enterprise.event.Observes;
import javax.enterprise.inject.Model;
import javax.enterprise.inject.Produces;
import javax.faces.application.FacesMessage;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.io.IOException;
import java.io.Serializable;
import java.security.Principal;


//@Stateful
@URLMappings(mappings = {
        @URLMapping(id = "impersonateUser", pattern = "/impersonate/#{authBean.impersonationEmail}", viewId = "/dashboard")
})
@Model  // for being found by CDI
@Named("authBean")
@SessionScoped
@DeclareRoles(value = {"ADMINISTRATOR", "USER", "GUEST"})
public class AuthenticationBean implements Serializable {

    private static final long serialVersionUID = 1L;
    @Inject
    @LoggedIn
    Event<User> userLoggedInEvent;
    @Inject
    @AuthenticatedUser
    Event<String> userAuthenticatedEvent;
    @Inject
    @AuthenticatedRESTUser
    Event<String> restUserAuthenticatedEvent;
    @Inject
    @ImpersonatedUser
    Event<String> userImpersonatedEvent;
    @Inject
    @DeleteImpersonatedUser
    Event<User> deleteImpersonatedUserEvent;
    @Inject
    private AuthenticationBean authBean;


    //    @Produces
//    @RequestScoped
//    @AuthenticatedUser
    private User authenticatedUser;

//    public void setAuthenticatedRESTUser(User authenticatedRESTUser) {
//        this.authenticatedRESTUser = authenticatedRESTUser;
//    }

//    @Produces
//    @RequestScoped
//    @AuthenticatedRESTUser
//    private User authenticatedRESTUser;


    //    @Produces
//    @RequestScoped
//    @ImpersonatedUser
    private User impersonatedUser;


    @PersistenceContext
    private EntityManager em;
    @Inject
    private LocaleBean localeBean;

    //	@Inject
    //	@AuthenticatedUser
    //	private User getAuthenticatedUser;
    private String param;
    @NotNull(message = "{error_email_NULL}")
    @Pattern(regexp = "([^.@]+)(\\.[^.@]+)*@([^.@]+\\.)+([^.@]+)", message = "{error_email_VALID}")
    private String email;
    @NotNull(message = "{error_email_NULL}")
    @Pattern(regexp = "([^.@]+)(\\.[^.@]+)*@([^.@]+\\.)+([^.@]+)", message = "{error_email_VALID}")
    private String impersonationEmail;
    @NotNull(message = "{error_password_NULL}")
    @Size(min = 8, max = 156, message = "{error_password_SIZE}")
    private String password;
    private String originalURL;

//    public AuthenticationBean() {
        // :-)
//    }

//    public User getImpersonatedUser() {
//        return getImpersonatedUser;
//    }

    public void setImpersonatedUser(User impersonatedUser) {
        this.impersonatedUser = impersonatedUser;
    }

    @NotNull
    public String getImpersonationEmail() {
        return impersonationEmail;
    }

    public void setImpersonationEmail(@NotNull String impersonationEmail) {
        this.impersonationEmail = impersonationEmail;
    }

//    public User getAuthenticatedUser() {
//        return getAuthenticatedUser;
//    }

    //	@PostConstruct
    public void init() {
        ExternalContext externalContext = FacesContext.getCurrentInstance().getExternalContext();
        originalURL = (String) externalContext.getRequestMap().get(RequestDispatcher.FORWARD_REQUEST_URI);

        if (originalURL == null) {
            originalURL = externalContext.getRequestContextPath() + "/";
        } else {
            String originalQuery = (String) externalContext.getRequestMap().get(RequestDispatcher.FORWARD_QUERY_STRING);

            if (originalQuery != null) {
                originalURL += "?" + originalQuery;
            }
        }
    }


    //	@RolesAllowed("USER")
    //	public void checkSignedIn()
    //	{
    //		String referrer = FacesContext.getCurrentInstance().getExternalContext().getRequestHeaderMap().get("referer");
    //		System.out.println(referrer);
    //
    //		if (!FacesContext.getCurrentInstance().getExternalContext().getSessionMap().containsKey("getAuthenticatedUser"))
    //		{
    //			System.out.println("signout FAIL message");
    //			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, MSG.getValue(ResourceBundle.VALIDATIONS.toString(), "success_signout", null, localeBean.getLocale()), MSG.getValue(ResourceBundle.VALIDATIONS.toString(), "success_signout_details", new String[]{""}, localeBean.getLocale())));
    //		}
    //	}

    //    @Produces
//    @LoggedIn
//    public  User getCurrentUser() {
//        if (getAuthenticatedUser == null) {
//            return null;
//        } else {
//            return getAuthenticatedUser;
//        }
//    }
//
//
    @Produces
    @Named("authenticatedUser")
    @AuthenticatedUser
    @SessionScoped
    public User  getAuthenticatedUser() {
        if (authenticatedUser == null) {
            return new User();
        } else {
            return authenticatedUser;
        }
    }

//    @Produces
//    @Named("authenticatedRESTUser")
//    @AuthenticatedRESTUser
//    @RequestScoped
//    public User  getAuthenticatedRESTUser() {
//        if (authenticatedRESTUser == null) {
//            return new User();
//        } else {
//            return authenticatedRESTUser;
//        }
//    }


    @Produces
    @Named("impersonatedUser")
    @ImpersonatedUser
    @SessionScoped
    public User getImpersonatedUser() {
        if (impersonatedUser == null) {
            return new User();
        } else {
            return impersonatedUser;
        }
    }


    //	public User getCurrentUser()
    //	{
    //		User u = null;
    //		FacesContext fc = FacesContext.getCurrentInstance();
    //		ExternalContext externalContext = fc.getExternalContext();
    //		if (externalContext.getUserPrincipal() == null)
    //		{
    //			System.out.println("current principal is null");
    //		} else
    //		{
    //			String email = externalContext.getUserPrincipal().getName();
    //			u = findUserByEmail(email);
    //		}
    //		return u;
    //	}

    private User findUserByEmail(String email) {
        try {
            return (User) em.createNamedQuery(User.QUERY_FINDBYEMAIL).setParameter("email", email).getSingleResult();
        } catch (NoResultException e) {
            return null;
        }
    }

    public void handleAuthenticationEvent(@Observes @AuthenticatedUser String email) {
        this.authenticatedUser = findUserByEmail(email);
    }
//    public void handleRESTAuthenticationEvent(@Observes @AuthenticatedRESTUser String email) {
//        this.authenticatedRESTUser = findUserByEmail(email);
//    }

    //	public void initParam() throws IOException
    //	{
    //		System.out.println("wtf");
    //		if (!param.equals(null))
    //		{
    //			System.out.println("InvokeActionBean.initParam() " + Faces.getCurrentPhaseId());
    //			Messages.addFlashInfo("param", "Param \"{0}\" is successfully set and you have been redirected!", param);
    //			Faces.refresh();
    //		}
    //	}
    @RolesAllowed("USER")
    public void login() {
        FacesContext context = FacesContext.getCurrentInstance();
        ExternalContext externalContext = context.getExternalContext();
        HttpServletRequest request = (HttpServletRequest) externalContext.getRequest();
        //		System.out.println(request.getUserPrincipal().getName());

        try {
            request.login(this.email, this.password);
            //			getAuthenticatedUser = findUserByEmail(this.email);
            userAuthenticatedEvent.fire(email);
            //			System.out.println(getCurrentUser());
            externalContext.getSessionMap().put("getAuthenticatedUser", authenticatedUser);
            context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, MSG.getValue(ResourceBundle.VALIDATIONS.toString(), "success_signin", null, localeBean.getLocale()), MSG.getValue(ResourceBundle.VALIDATIONS.toString(), "success_signin_details", new String[]{authenticatedUser.getUserName()}, localeBean.getLocale())));
            externalContext.getFlash().setKeepMessages(true);
            externalContext.redirect("dashboard");
        } catch (ServletException e) {
            System.out.println(e.getMessage());
            // Handle unknown email/password in request.login().
            context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, MSG.getValue(ResourceBundle.VALIDATIONS.toString(), "error_signin_fail", null, localeBean.getLocale()), MSG.getValue(ResourceBundle.VALIDATIONS.toString(), "error_signin_fail_details", null, localeBean.getLocale())));
        } catch (IOException e) {
            System.out.println("failed redirecting after login");
        }
    }

    //    @PostConstruct
    @RolesAllowed("ADMINISTRATOR")
    public void impersonateUser(String email) {
        impersonationEmail = email;
        if (impersonationEmail != null && !impersonationEmail.isEmpty()) {
            FacesContext context = FacesContext.getCurrentInstance();
            ExternalContext externalContext = context.getExternalContext();
            HttpServletRequest request = (HttpServletRequest) externalContext.getRequest();
            //		System.out.println(request.getUserPrincipal().getName());

            try {
                userImpersonatedEvent.fire(email);
                impersonationEmail = "";
//                System.out.println(getCurrentUser());
                externalContext.getSessionMap().put("getAuthenticatedUser", impersonatedUser);
                context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, MSG.getValue(ResourceBundle.VALIDATIONS.toString(), "success_signin", null, localeBean.getLocale()), MSG.getValue(ResourceBundle.VALIDATIONS.toString(), "success_signin_details", new String[]{impersonatedUser.getUserName()}, localeBean.getLocale())));
                externalContext.getFlash().setKeepMessages(true);
                externalContext.redirect("dashboard");
                // Handle unknown email/password in request.login().
//                context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, MSG.getValue(ResourceBundle.VALIDATIONS.toString(), "error_signin_fail", null, localeBean.getLocale()), MSG.getValue(ResourceBundle.VALIDATIONS.toString(), "error_signin_fail_details", null, localeBean.getLocale())));
            } catch (IOException e) {
                System.out.println("failed redirecting after login");
            }
        }
    }

    //    @PostConstruct
    @RolesAllowed("ADMINISTRATOR")
    public void unimpersonateUser() {
        impersonationEmail = email;
        if (impersonationEmail != null && !impersonationEmail.isEmpty()) {
            FacesContext context = FacesContext.getCurrentInstance();
            ExternalContext externalContext = context.getExternalContext();
            HttpServletRequest request = (HttpServletRequest) externalContext.getRequest();
            //		System.out.println(request.getUserPrincipal().getName());

            try {
//                System.out.println(getCurrentUser());
                externalContext.getSessionMap().put("getAuthenticatedUser", authenticatedUser);
                context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, MSG.getValue(ResourceBundle.VALIDATIONS.toString(), "success_signout", null, localeBean.getLocale()), MSG.getValue(ResourceBundle.VALIDATIONS.toString(), "success_signout_details", new String[]{impersonatedUser.getUserName()}, localeBean.getLocale())));
                deleteImpersonatedUserEvent.fire(impersonatedUser);
                externalContext.getFlash().setKeepMessages(true);
                externalContext.redirect("dashboard");
                // Handle unknown email/password in request.login().
//                context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, MSG.getValue(ResourceBundle.VALIDATIONS.toString(), "error_signin_fail", null, localeBean.getLocale()), MSG.getValue(ResourceBundle.VALIDATIONS.toString(), "error_signin_fail_details", null, localeBean.getLocale())));
            } catch (IOException e) {
                System.out.println("failed redirecting after login");
            }
        }
    }


    public String logout() {
        //		ExternalContext externalContext = FacesContext.getCurrentInstance().getExternalContext();
        //		externalContext.invalidateSession();
        FacesContext.getCurrentInstance().getExternalContext().invalidateSession();
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, MSG.getValue(ResourceBundle.VALIDATIONS.toString(), "success_signout", null, localeBean.getLocale()), MSG.getValue(ResourceBundle.VALIDATIONS.toString(), "success_signout_details", new String[]{authenticatedUser.getUserName()}, localeBean.getLocale())));

        authenticatedUser = null;

        FacesContext context = FacesContext.getCurrentInstance();
        context.getExternalContext().getFlash().setKeepMessages(true);

        // FacesContext context2 = FacesContext.getCurrentInstance();
        // ExternalContext externalContext2 = context2.getExternalContext();
        // externalContext.getFlash().setKeepMessages(true);
        return "/home?faces-redirect=true";

    }

    @SessionScoped
    public User getUser() {
        if (authenticatedUser == null) {
            Principal principal = FacesContext.getCurrentInstance().getExternalContext().getUserPrincipal();
            if (principal != null) {
                authenticatedUser = findUserByEmail(principal.getName()); // Find User by j_username.
            }
        }
        return authenticatedUser;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public LocaleBean getLocaleBean() {
        return localeBean;
    }

    public void setLocaleBean(LocaleBean localeBean) {
        this.localeBean = localeBean;
    }

    public String getParam() {
        return param;
    }

    public void setParam(String param) {
        this.param = param;
    }

}
