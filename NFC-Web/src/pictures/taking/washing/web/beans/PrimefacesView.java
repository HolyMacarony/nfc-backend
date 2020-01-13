package pictures.taking.washing.web.beans;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.security.DeclareRoles;
import javax.annotation.security.RolesAllowed;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Named;
import java.io.Serializable;

@Named
@ViewScoped
@DeclareRoles(value = {"ADMINISTRATOR", "USER", "GUEST"})
public class PrimefacesView implements Serializable {
    private static final long serialVersionUID = 1L;


    final Logger logger = LoggerFactory.getLogger(PrimefacesView.class);
    protected String toBeRefreshedWidgetID = "";

    public String getToBeRefreshedWidgetID() {
        return toBeRefreshedWidgetID;
    }

    public void setToBeRefreshedWidgetID(String toBeRefreshedWidgetID) {
        this.toBeRefreshedWidgetID = toBeRefreshedWidgetID;
    }

    @RolesAllowed(value = {"USER"})
    public void setToBeRefreshedWidget() {
        java.util.Map<String, String> params = FacesContext.getCurrentInstance().getExternalContext()
                .getRequestParameterMap();
        try {
            toBeRefreshedWidgetID = params.get("toBeRefreshedWidgetID");
            logger.info("refresh: " + toBeRefreshedWidgetID);
        } catch (Exception e) {
            logger.error("fail to parse toBeRefreshedWidgetID");
            logger.info(e.getMessage());
        }
    }
}
