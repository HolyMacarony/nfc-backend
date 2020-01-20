package pictures.taking.washing.web.beans.User;

import com.ocpsoft.pretty.faces.annotation.URLMapping;
import com.ocpsoft.pretty.faces.annotation.URLMappings;
import pictures.taking.washing.ejb.dto.BaseUserData;
import pictures.taking.washing.ejb.events.UserEvent;
import pictures.taking.washing.ejb.events.UserNotification;
import pictures.taking.washing.ejb.interfaces.UserDAO;
import pictures.taking.washing.helper.MSG;
import pictures.taking.washing.helper.ResourceBundle;
import pictures.taking.washing.persistence.entities.User;
import pictures.taking.washing.web.Annotations.AuthenticatedUser;
import pictures.taking.washing.web.Annotations.ImpersonatedUser;
import pictures.taking.washing.web.beans.AuthenticationBean;
import pictures.taking.washing.web.beans.LocaleBean;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.enterprise.event.Event;
import javax.faces.application.FacesMessage;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.Serializable;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.UUID;

//@Stateful
@URLMappings(mappings = {
        @URLMapping(id = "userShow", pattern = "/user/#{userBean.userId}", viewId = "/userShow"),
        @URLMapping(id = "userDashboard", pattern = "/dashboard", viewId = "/dashboard")
})
@ViewScoped
@Named
public class UserBean implements Serializable {
    public static final String OUTCOME_INDEX = "/start?faces-redirect=true";
    public static final String OUTCOME_REGISTER = "/register?faces-redirect=true";
    public static final String OUTCOME_UPDATED = "/userDetails?faces-redirect=true&amp;id=%s";
    public static final String OUTCOME_DELETED = "/userIndex?faces-redirect=true";
    private static final long serialVersionUID = 1L;

    @Inject
    @AuthenticatedUser
    User authenticatedUser;

    @Inject
    @ImpersonatedUser
    User impersonatedUser;

    @Inject
    AuthenticationBean authBean;

    @Inject
    UserIndexBean userIndexBean;

    @UserNotification
    @Inject
    private Event<UserEvent> event;
    @Inject
    private LocaleBean localeBean;
    @EJB
    private UserDAO userDAO;




    private User user;
    private BaseUserData userToBeDeleted;
    private UUID userId;

    private boolean isEditing = false;

    public UserBean(User user) {
        this.user = user;
    }

    public UserBean() {
    }

    public BaseUserData getUserToBeDeleted() {
        return userToBeDeleted;
    }

    public void setUserToBeDeleted(BaseUserData userToBeDeleted) {
        this.userToBeDeleted = userToBeDeleted;
    }

    public boolean isEditing() {
        return isEditing;
    }

    public void setEditing(boolean editing) {
        isEditing = editing;
    }

    public void toggleEditing() {
        isEditing = !isEditing;
    }

    public UserDAO getUserDAO() {
        return userDAO;
    }

    public void setUserDAO(UserDAO userDAO) {
        this.userDAO = userDAO;
    }



    public void clearToBeDeletedUser() {
        setUserToBeDeleted(null);
    }

    /**
     * initialises Dashboard with user specific hikeBeans
     */
    @PostConstruct
    public void init() {

        if (user != null) {
            return;
        }

        if (authenticatedUser != null && userId != null ) {
            user = userDAO.find(userId);
        } else {
            user = (authBean.getImpersonatedUser() != null && authBean.getImpersonatedUser().getId() != null) ? authBean.getImpersonatedUser() : authBean.getAuthenticatedUser();

//            user = impersonatedUser.getId() != null ? impersonatedUser : authenticatedUser;
        }

        if (user == null) {
            user = new User();
        }
//        user.getHikes().size();

        // Copy the data of existing user into local representation for WEB view and editing
//        if (user.getId() != null) {
//            hikeBeans.clear();
//            for (Hike hike : hikeDAO.findAllByUser(user)) {
//                for (Hikesection section : hikesectionDAO.findAllByHike(hike)) {
//                    for (Hikeevent event : hikeeventDAO.findAllByHikesection(section)) {
//                        section.addHikeevent(event);
//                    }
//                    hike.addHikesection(section);
//                }
//                hikeBeans.add(new HikeBean(hike));
//            }
//        }

//        System.out.println(hikeBeans);

//		mediaBeans.clear();
//		for (Media media : user.getMedia()) {
//			mediaBeans.add(new MediaBean(media));
//		}

    }


    /**
     * prepares user data for saving, write data back
     */
    private void prepare() {

        // Clear all the lists
        // Add all items (again) to the list
        //


//        hikeBeans.clear();
//        for (Hike hike : hikeDAO.findAllByUser(getAuthenticatedUser)) {
//            for (Hikesection section : hikesectionDAO.findAllByHike(hike)) {
//                for (Hikeevent event : hikeeventDAO.findAllByHikesection(section)) {
//                    section.addHikeevent(event);
//                }
//                hike.addHikesection(section);
//            }
//            hikeBeans.add(new HikeBean(hike));
//        }


        //
        //		user.getMedia().clear();
        //		for (MediaBean mb : mediaBeans) {
        //			user.getMedia().add(mb.getMedia());
        //		}


    }

    public String create() {
        prepare();
        if (userDAO.create(user) != null) {
//            authenticatedUser.setEmail(user.getEmail());
//            authenticatedUser.setPassword(user.getPassword_clear());
            authBean.setEmail(user.getEmail());
            authBean.setPassword(user.getPassword_clear());

            ExternalContext externalContext = FacesContext.getCurrentInstance().getExternalContext();
            FacesContext context = FacesContext.getCurrentInstance();
            context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, MSG.getValue(ResourceBundle.VALIDATIONS.toString(), "success_signup", null, localeBean.getLocale()), MSG.getValue(ResourceBundle.VALIDATIONS.toString(), "success_signup_details", new String[]{authBean.getEmail()}, localeBean.getLocale())));
            externalContext.getFlash().setKeepMessages(true);
            authBean.login();
            return "pretty:userDashboard";
        }
        return "pretty:userDashboard";
        //		return OUTCOME_REGISTER;
    }

    public String update() {
        prepare();

        userDAO.update(user);

        return "pretty:userIndex";
    }

    public User delete() {
      return  userDAO.remove(user.getId());
    }

    public void delete(BaseUserData user) {
        if (getUserToBeDeleted() != null && getUserToBeDeleted().equals(user)) {
            {
                userDAO.remove(user.getId());
                setUserToBeDeleted(null);
                userIndexBean.setUserBeans(null);
                if (authBean.getImpersonatedUser() != null && authBean.getImpersonatedUser().getId() != null && authBean.getImpersonatedUser().getId().equals(user.getId())) {
                    authBean.unimpersonateUser();
                }else{
//                    userIndexBean.init();
                    //                PrimeFaces.current().ajax().update("userTableWidget");
                }
                FacesContext.getCurrentInstance().
                        addMessage(null,
                                new FacesMessage(FacesMessage.SEVERITY_INFO,
                                        MSG.getValue(ResourceBundle.VALIDATIONS.toString(), "success_delete_user", new String[]{user.getUsername()}, localeBean.getLocale()),
                                        MSG.getValue(ResourceBundle.VALIDATIONS.toString(), "success_delete_user_details", new String[]{user.getEmail()}, localeBean.getLocale())));
            }
        } else {
            setUserToBeDeleted(user);
        }
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public UUID getUserId() {
        return userId;
    }

    public void setUserId(UUID userId) {
        this.userId = userId;
    }


}
