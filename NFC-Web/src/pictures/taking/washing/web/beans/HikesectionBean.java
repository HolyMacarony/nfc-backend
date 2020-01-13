package pictures.taking.washing.web.beans;

import pictures.taking.washing.ejb.interfaces.HikeeventDAO;
import pictures.taking.washing.ejb.interfaces.HikesectionDAO;
import pictures.taking.washing.persistence.entities.Hikesection;
import org.primefaces.behavior.ajax.AjaxBehavior;
import org.primefaces.event.SelectEvent;
import org.primefaces.event.TreeDragDropEvent;
import org.primefaces.model.DefaultTreeNode;
import org.primefaces.model.TreeNode;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.el.ELContext;
import javax.el.ExpressionFactory;
import javax.faces.application.Application;
import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.component.UIViewRoot;
import javax.faces.component.behavior.Behavior;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@ViewScoped
@Named
public class HikesectionBean implements Serializable {
    Application application;
    FacesContext fc;
    ELContext elContext = FacesContext.getCurrentInstance().getELContext();
    ExpressionFactory factory = FacesContext.getCurrentInstance().getApplication().getExpressionFactory();
    @Inject
    DragDropView treeDNDView;
    private Hikesection hikesection;
    private boolean dateEditable = false;
    private boolean locationEditable = false;
    private Hikesection hikesectionToBeDeleted;
    private Long hikesectionId;
    @EJB
    private HikesectionDAO hikesectionDAO;
    @EJB
    private HikeeventDAO hikeeventDAO;
    private List<HikeeventBean> hikeevents = new ArrayList<HikeeventBean>();
    @Inject
    private HikeBean hikeBean;
    @Inject
    private DragDropView dndView;
    public HikesectionBean(Hikesection hikesection) {
        this.hikesection = hikesection;
    }
    public HikesectionBean() {
    }

    public boolean isLocationEditable() {
        return locationEditable;
    }

    public void setLocationEditable(boolean locationEditable) {
        this.locationEditable = locationEditable;
    }

    public void update() {
        hikesectionDAO.update(hikesection);
        dndView.init();
    }

    public void create() {
        prepare();
        this.hikesection = null;
        init();
//        PrimeFaces.current().executeScript("PF('"+treeDNDView.toBeRefreshedWidgetID+"').refresh()");
//		hikesectionDAO.create(hikesection);
    }
//	public List<HikeeventBean> getHikeevents() {
//		hikeevents.clear();
//		for (Hikeevent hikeevent : hikeeventDAO.findAllByHikesection(this.hikesection)) {hikeevents.add(new HikeeventBean(hikeevent));}
//		return hikeevents;
//	}


    public Hikesection getHikesection() {
        return hikesection;
    }


    public void setHikesection(Hikesection hikesection) {
        this.hikesection = hikesection;
    }

    public void prepare() {
        // create drag and drop event
        TreeNode rootNode = dndView.getRootNode();
        UIViewRoot view = FacesContext.getCurrentInstance().getViewRoot();
        UIComponent component = view.findComponent("hikeForm");
        FacesContext fc = FacesContext.getCurrentInstance();
        Behavior ajaxBehavior = fc.getApplication().createBehavior(AjaxBehavior.BEHAVIOR_ID);
        TreeNode sectionNode = new DefaultTreeNode(hikesection);
        sectionNode.setType("section");
        rootNode.getChildren().add(dndView.getNewSectionPosition(), sectionNode);

        TreeDragDropEvent newSection = new TreeDragDropEvent(
                component,
                ajaxBehavior,
                sectionNode,
                rootNode,
                dndView.getNewSectionPosition(),
                false);
        dndView.onDragDrop(newSection);
    }

    public void onDateSelect(SelectEvent event) {
        hikesection.setDate((Date) event.getObject());
        FacesContext facesContext = FacesContext.getCurrentInstance();
        SimpleDateFormat format = new SimpleDateFormat("dd-MM-yyyy");
        facesContext.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Date Selected", format.format(event.getObject())));
    }


    @PostConstruct
    public void init() {
        if (null != hikesection) {
            return;
        }

        if (hikesectionId != null) {
            hikesection = hikesectionDAO.find(hikesectionId);
        }

        if (hikesection == null || hikesection.getId() == null) {
            hikesection = new Hikesection();
            hikesection.setHike(hikeBean.getHike());
        }

        // Copy the data
        // addresses.clear();
        // for (Address address : user.getAddresses()) {
        // addresses.add(new AddressBean(address));
        // }
    }

    public Hikesection getHikesectionToBeDeleted() {
        return hikesectionToBeDeleted;
    }

    public void setHikesectionToBeDeleted(Hikesection hikesectionToBeDeleted) {
        this.hikesectionToBeDeleted = hikesectionToBeDeleted;
    }

    //allow only once
    public boolean isDateEditable() {
        return dateEditable;
    }

    public void setDateEditable(String dateEditable) {
        this.dateEditable = Boolean.valueOf(dateEditable);
    }
}