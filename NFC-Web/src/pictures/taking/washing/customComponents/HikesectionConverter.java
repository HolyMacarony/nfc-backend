package pictures.taking.washing.customComponents;

import pictures.taking.washing.ejb.interfaces.HikesectionDAO;
import pictures.taking.washing.persistence.entities.Hikesection;
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
public class HikesectionConverter implements Converter, Serializable {
    //	final Logger logger = LoggerFactory.getLogger(HikesectionConverter.class);
//	Hike hike;
    @Inject
    private CreateHikeBean orderListBean;
    @EJB
    private HikesectionDAO hikesectionDAO;

    @PostConstruct
    public void init() {
//		if (hike == null) {
//			hike = orderListBean.getHikeBean().getHike();
//		}
    }

    @Override
    public Object getAsObject(FacesContext context, UIComponent component, String value) {
        // Convert here String submitted value to Hikesection object.
        if (value == null) {
            return null;
        }
        Hikesection hikesection = null;
        hikesection = hikesectionDAO.find(Long.valueOf(value));
//		if (hikesection.getId() == Long.parseLong(value)) {
//		}
        if (hikesection == null) {
//			logger.error("Hikesection not found with ID! " + value);

            throw new ConverterException(new FacesMessage("Unknown Hikesection ID: " + value));
        }
        return hikesection;
    }

    @Override
    public String getAsString(FacesContext context, UIComponent component, Object value) {
        // Convert here Hikesection object to String value for use in HTML.
        if (!(value instanceof Hikesection) || ((Hikesection) value).getId() == null) {
            return null;
        }
        return String.valueOf(((Hikesection) value).getId());
    }
}
