package pictures.taking.washing.web.beans;

import pictures.taking.washing.ejb.interfaces.UserDAO;
import pictures.taking.washing.persistence.entities.User;

import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;

@RequestScoped
@Named("mailSender")
public class SendMailBean {

    private static final String OUTCOME_DETAILS = "/pages/details?faces-redirect=true&amp;id=%s";

    @Inject
    private MailSender mailSender;

    @EJB
    private UserDAO userDAO;

    private Long userId;

    private User user;

    private String subject;

    private String message;

    private boolean mailSent;

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public boolean isMailSent() {
        return mailSent;
    }

    public void setMailSent(boolean mailSent) {
        this.mailSent = mailSent;
    }

    public void initialize() {

        if (userId > 0) {
            user = userDAO.find(userId);
        }

        if (user == null) {
            user = new User();
        }
    }

    public void send() {
        mailSender.sendMail(message, subject, userId);
        mailSent = true;

    }

}
