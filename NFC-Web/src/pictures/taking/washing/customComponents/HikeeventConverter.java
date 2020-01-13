package pictures.taking.washing.customComponents;

import pictures.taking.washing.ejb.interfaces.HikeeventDAO;
import pictures.taking.washing.persistence.entities.Events.Hikeevent;
import pictures.taking.washing.web.beans.CreateHikeBean;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.ConverterException;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.Serializable;

//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;

@Named
@ViewScoped
public class HikeeventConverter implements Converter, Serializable {
    //	final Logger logger = LoggerFactory.getLogger(HikeeventConverter.class);
    //	Hikesection hikesection;
    @Inject
    private CreateHikeBean createHikeBean;
    @EJB
    private HikeeventDAO hikeeventDAO;

    @PostConstruct
    public void init() {
//		if (hikesection == null) {
//			hikesection = createHikeBean.getHikeevents()[];
//
//		}
    }

    @Override
    public Object getAsObject(FacesContext context, UIComponent component, String value) {
        // Convert here String submitted value to Hikeevent object.
        if (value == null) {
            return null;
        }
        Hikeevent hikeevent = null;
        hikeevent = hikeeventDAO.find(Long.valueOf(value));
//		if (hikesection.getId() == Long.parseLong(value)) {
//		}
        if (hikeevent == null) {
//			logger.error("Hikeevent not found with ID! " + value);

            throw new ConverterException(new FacesMessage("Unknown Hikeevent ID: " + value));
        }
        return hikeevent;
    }

    @Override
    public String getAsString(FacesContext context, UIComponent component, Object value) {
        // Convert here Hikesection object to String value for use in HTML.
        if (!(value instanceof Hikeevent) || ((Hikeevent) value).getId() == null) {
            return null;
        }
        return String.valueOf(((Hikeevent) value).getId());
    }
}
