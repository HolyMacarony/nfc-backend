package pictures.taking.washing.web.beans;

import pictures.taking.washing.persistence.entities.User;
import pictures.taking.washing.web.Annotations.AuthenticatedUser;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.inject.Named;

@Named
@ApplicationScoped
public class NavigationBean {

    @Inject
    @AuthenticatedUser
    User authenticatedUser;

    public String showUsers() {
        if (authenticatedUser != null) {
            return "success";
        }

        return "failure";
    }

}

