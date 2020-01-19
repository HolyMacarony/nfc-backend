package pictures.taking.washing.web.beans;

import pictures.taking.washing.ejb.events.UserEvent;
import pictures.taking.washing.ejb.events.UserNotification;
import pictures.taking.washing.ejb.interfaces.UserDAO;
import pictures.taking.washing.helper.MSG;
import pictures.taking.washing.helper.ResourceBundle;
import pictures.taking.washing.persistence.entities.User;

import javax.ejb.EJB;
import javax.enterprise.event.Event;
import javax.faces.application.FacesMessage;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@ViewScoped
@Named
public class SampleBean implements Serializable {

    public static final String OUTCOME_INDEX = "/start?faces-redirect=true";

    public static final String OUTCOME_REGISTER = "/register?faces-redirect=true";

    public static final String OUTCOME_UPDATED = "/userDetails?faces-redirect=true&amp;id=%s";

    public static final String OUTCOME_DELETED = "/userIndex?faces-redirect=true";

    private static final long serialVersionUID = 1L;
    @Inject
    AuthenticationBean authBean;
    @UserNotification
    @Inject
    private Event<UserEvent> event;
    @Inject
    private LocaleBean localeBean;

    @EJB
    private UserDAO userDAO;

    private User user;

    private UUID userId;


    // private List<AddressBean> addresses = new ArrayList<AddressBean>();
    //
    // private List<CommunicationBean> communications = new
    // ArrayList<CommunicationBean>();
    //
    // private List<StatementWebBean> statements = new
    // ArrayList<StatementWebBean>();
    //
    // private List<UserRatesStatementBean> userRatesStatements = new
    // ArrayList<UserRatesStatementBean>();

    public void init() {

        if (null != user) {
            return;
        }

        if (userId != null) {
            user = userDAO.find(userId);
        }

        if (user == null) {
            user = new User();
        }

        // Copy the data


        // addresses.clear();
        // for (Address address : user.getAddresses()) {
        // addresses.add(new AddressBean(address));
        // }
        //
        // communications.clear();
        // for (Communication communication : user.getCommunications()) {
        // communications.add(new CommunicationBean(communication));
        // }
        //
        // statements.clear();
        // for (Statement statement : user.getStatements()) {
        // statements.add(new StatementWebBean(statement));
        // }
        //
        // userRatesStatements.clear();
        // for (UserRatesStatement statementRating : user.getUserRatesStatements()) {
        // userRatesStatements.add(new UserRatesStatementBean(statementRating));
        // }

    }

    /**
     * Bereitet den User-Datensatz für das Speichern vor
     */
    private void prepare() {

        // Clear the list of addresses
        // user.getAddresses().clear();

        // Add all addresses (again) to the list
        // for (AddressBean adb : addresses) {
        // user.getAddresses().add(adb.getAddress());
        // }

        // Clear the communications
        // user.getCommunications().clear();

        // Add all communications (again) to the list
        // for (CommunicationBean cmb : communications) {
        // user.getCommunications().add(cmb.getCommunication());
        // }

        // clear the statements
        // user.getStatements().clear();

        // Add all statements (again) to the list
        // for (StatementWebBean smb : statements) {
        // user.getStatements().add(smb.getStatement());
        // }

        // clear the ratings
        // user.getUserRatesStatements().clear();

        // Add all ratings (again) to the list
        // for (UserRatesStatementBean urs : userRatesStatements) {
        // user.getUserRatesStatements().add(urs.getUserRatesStatement());
        // }
    }

    public String create() {
        prepare();
        if (userDAO.create(user) != null) {
            authBean.setEmail(user.getEmail());
            authBean.setPassword(user.getPassword_clear());
            ExternalContext externalContext = FacesContext.getCurrentInstance().getExternalContext();
            FacesContext context = FacesContext.getCurrentInstance();
            context.addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_INFO,
                            MSG.getValue(ResourceBundle.VALIDATIONS.toString(), "success_signup", null, localeBean.getLocale()),
                            MSG.getValue(ResourceBundle.VALIDATIONS.toString(), "success_signup_details", new String[]{authBean.getEmail()},
                                    localeBean.getLocale())));
            externalContext.getFlash().setKeepMessages(true);
            authBean.login();
            // return OUTCOME_INDEX;
        }
        return OUTCOME_REGISTER;

    }

    public String update() {
        prepare();

        userDAO.update(user);

        return String.format(OUTCOME_UPDATED, userId);
    }

    public String delete() {
        userDAO.remove(user.getId());

        return OUTCOME_DELETED;
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
