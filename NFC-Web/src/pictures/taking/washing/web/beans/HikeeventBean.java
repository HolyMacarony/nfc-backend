package pictures.taking.washing.web.beans;

import pictures.taking.washing.ejb.interfaces.HikeeventDAO;
import pictures.taking.washing.persistence.entities.Events.Hikeevent;
import pictures.taking.washing.persistence.entities.Events.InformationEvent;
import pictures.taking.washing.persistence.entities.Events.LocationEvent;
import pictures.taking.washing.persistence.entities.Hikesection;
import pictures.taking.washing.persistence.enums.EventEnum;
import org.primefaces.behavior.ajax.AjaxBehavior;
import org.primefaces.event.TreeDragDropEvent;
import org.primefaces.model.DefaultTreeNode;
import org.primefaces.model.TreeNode;

import javax.ejb.EJB;
import javax.faces.component.UIComponent;
import javax.faces.component.UIViewRoot;
import javax.faces.component.behavior.Behavior;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.Serializable;

@ViewScoped
@Named
public class HikeeventBean implements Serializable {

    private Hikeevent hikeevent;
    private Hikeevent hikeeventToBeDeleted;
    private String chooseLocationInvoker;
    //	private InformationEvent informationEvent;
    private Long hikeeventId;
    @Inject
    private DragDropView dndView;
    @Inject
    private HikesectionBean hikeSectionBean;
    @EJB
    private HikeeventDAO hikeeventDAO;

    public HikeeventBean(Hikeevent Hikeevent) {
        this.hikeevent = Hikeevent;
    }

    public HikeeventBean() {
    }

    //	public InformationEvent getInformationEvent() {
//		return informationEvent;
//	}
//	public void setInformationEvent(InformationEvent informationEvent) {
//		this.informationEvent = informationEvent;
//	}
    public void create() {
        prepare();
//        hikeeventDAO.create(hikeevent);

    }

    public void update() {
        hikeeventDAO.update(hikeevent);
//        hikeevent = null;
        dndView.init();
    }

    public Hikeevent getHikeevent() {
        return hikeevent;
    }

    public void setHikeevent(Hikeevent Hikeevent) {
        this.hikeevent = Hikeevent;
    }

    public void prepare() {
        // create drag and drop event
        TreeNode rootNode = dndView.getRootNode();
        UIViewRoot view = FacesContext.getCurrentInstance().getViewRoot();
        UIComponent component = view.findComponent("hikeForm");
        FacesContext fc = FacesContext.getCurrentInstance();
        Behavior ajaxBehavior = fc.getApplication().createBehavior(AjaxBehavior.BEHAVIOR_ID);
        TreeNode eventNode = new DefaultTreeNode(hikeevent);
        eventNode.setType("event");
        rootNode.getChildren().get(dndView.getSectionPosition()).getChildren().add(dndView.getNewEventPosition(), eventNode);

        //Todo das Entsprechende event im Frontend erstellen und anschließend in das hier existierende Hikeevent Speichern, damit es in die DB persistiert werden kann
        TreeDragDropEvent newEvent = new TreeDragDropEvent(
                component,
                ajaxBehavior,
                eventNode,
                rootNode.getChildren().get(dndView.getSectionPosition()),
                dndView.getNewEventPosition(),
                false);
        dndView.onDragDrop(newEvent);

    }

    public void cleanCurrentHikeevent() {
        this.hikeevent = null;
    }

    public void init(EventEnum e) {
        switch (e) {
            case InformationEvent:
                hikeevent = new InformationEvent();
                break;
            case LocationEvent:
                hikeevent = new LocationEvent();
                break;
        }
        TreeNode rootNode = dndView.getRootNode();

//		hikeevent.setHikesection(hikeSectionBean.getHikesection());
        hikeevent.setHikesection((Hikesection) rootNode.getChildren().get(dndView.getSectionPosition()).getData());


        if (null != hikeevent) {
            return;
        }

        if (hikeeventId != null) {
            hikeevent = hikeeventDAO.find(hikeeventId);
        }

//		if (hikeevent == null) {
////			hikeevent = new Hikeevent();
//		}


//		if (informationEvent == null || informationEvent.getId() == null) {
//			informationEvent = new InformationEvent();
//			informationEvent.setHikesection(hikeSectionBean.getHikesection());
//		}


    }

    public Hikeevent getHikeeventToBeDeleted() {
        return hikeeventToBeDeleted;
    }

    public void setHikeeventToBeDeleted(Hikeevent hikeeventToBeDeleted) {
        this.hikeeventToBeDeleted = hikeeventToBeDeleted;
    }

    public String getChooseLocationInvoker() {
        return chooseLocationInvoker;
    }

    public void setChooseLocationInvoker(String chooseLocationInvoker) {
        this.chooseLocationInvoker = chooseLocationInvoker;
    }
}