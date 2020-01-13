package pictures.taking.washing.ejb.events;

import javax.inject.Named;

@Named
@UserNotification
public class UserEvent {

    private String message;

    public UserEvent(String message) {
        super();
        this.message = message;
    }

    public UserEvent() {
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

}
