package pictures.taking.washing.web.beans;


import pictures.taking.washing.customComponents.CustomPanel;
import pictures.taking.washing.ejb.interfaces.HikeDAO;
import pictures.taking.washing.ejb.interfaces.HikeeventDAO;
import pictures.taking.washing.ejb.interfaces.HikesectionDAO;
import pictures.taking.washing.persistence.entities.Events.Hikeevent;
import pictures.taking.washing.persistence.entities.Hikesection;
import org.primefaces.component.column.Column;
import org.primefaces.event.DragDropEvent;
import org.primefaces.event.SelectEvent;
import org.primefaces.event.UnselectEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.el.ELContext;
import javax.el.ExpressionFactory;
import javax.faces.application.Application;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.event.ValueChangeEvent;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Named
@ViewScoped
public class CreateHikeBean implements Serializable {
//	private static final Logger logger = Logger.getLogger(CreateHikeBean.class);

    final Logger logger = LoggerFactory.getLogger(CreateHikeBean.class);
    Application application;
    ELContext elContext = FacesContext.getCurrentInstance().getELContext();
    ExpressionFactory factory = FacesContext.getCurrentInstance().getApplication().getExpressionFactory();
    private CustomPanel sectionPanel;
    private CustomPanel eventPanel;
    private FacesContext fc;
    private List<Hikesection> hikeSections;
    private HashMap<Long, ArrayList<Hikeevent>> hikeevents = new HashMap<>();
    private Hikesection currentSection;
    private Hikeevent currentEvent;
    private int oldItemPosition;
    @PersistenceContext
    private EntityManager em;
    @EJB
    private HikeeventDAO hikeeventDAO;
    @EJB
    private HikeDAO hikeDAO;
    @EJB
    private HikesectionDAO hikesectionDAO;
    @Inject
    private HikeBean hikeBean;

    public CreateHikeBean() {
    }

    public HashMap<Long, ArrayList<Hikeevent>> getHikeevents() {
        return hikeevents;
    }

    public void setHikeevents(HashMap<Long, ArrayList<Hikeevent>> hikeevents) {
        this.hikeevents = hikeevents;
    }

    public Hikeevent getCurrentEvent() {
        return currentEvent;
    }

    public void setCurrentEvent(Hikeevent currentEvent) {
        this.currentEvent = currentEvent;
    }

    public HikeBean getHikeBean() {
        return hikeBean;
    }

    public void setHikeBean(HikeBean hikeBean) {
        this.hikeBean = hikeBean;
    }

    public Hikesection getCurrentSection() {
        return currentSection;
    }

    public void setCurrentSection(Hikesection currentSection) {
        this.currentSection = currentSection;
    }

    public List<Hikesection> getHikeSections() {
        return hikeSections;
    }

    public void setHikeSections(List<Hikesection> hikeSections) {
        this.hikeSections = hikeSections;
    }

    @PostConstruct
    public void init() {
        hikeSections = hikesectionDAO.findAllByHike(hikeBean.getHike());
        //add all respective events
        for (Hikesection hs : hikeSections) {
            List<Hikeevent> test = hikeeventDAO.findAllByHikesection(hs);
            if (!test.isEmpty()) {
                hikeevents.putIfAbsent(hs.getId(), (ArrayList<Hikeevent>) test);
            }
        }
        System.out.println(hikeSections.size());
    }

    public ArrayList<Hikeevent> getHikeeventsOfSection(Long id) {
        return hikeevents.get(id);
    }

    public void updateHikesectionList() {
        init();
    }

    public void addSections() {

        Column testCol = new Column();

        if (hikeBean != null) {
            //						hikeBean = (HikeBean) Hibernate.unproxy(hikeBean);
            for (Hikesection section : hikesectionDAO.findAllByHike(hikeBean.getHike())) {
                //				CustomPanel to distinguish betweeen several classes
                //				sectionPanel = (Panel) application.createComponent(fc, "org.primefaces.component.Panel", "org.primefaces.component.PanelRenderer");
                sectionPanel = (CustomPanel) application.createComponent(fc, "org.primefaces.component.panel.Panel", "org.primefaces.component.PanelRenderer");
                sectionPanel.setId("panel_section_" + section.getId());
                sectionPanel.setToggleable(false);
                sectionPanel.setClosable(false);
                sectionPanel.setPanelClass(section.getClass());

                sectionPanel.setHeader("Abschnitt: " + section.getId());

                //				addButtonPanel = (Panel) application.createComponent(fc, "org.primefaces.component.Panel", "org.primefaces.component.PanelRenderer");
                //				addButtonPanel.setId("panel_addButton_" + currentSection.getId());
                //				addButtonPanel.setToggleable(false);
                //				addButtonPanel.setClosable(false);

                //				HtmlOutcomeTargetLink link = new HtmlOutcomeTargetLink();
                //				link.setValue("Dashboard");
                //				link.setOutcome("orderList");
                //
                //				MethodExpression targetExpression = factory.createMethodExpression(elContext, "#{createHikeDashboardBean.addCommandbutton()}", null, null);
                //				AjaxBehavior ajaxBehavior = (AjaxBehavior) fc.getApplication().createBehavior(AjaxBehavior.BEHAVIOR_ID);


                //				Buttons
                //				sectionButton = (CommandButton) application.createComponent(fc, "org.primefaces.component.CommandButton", "org.primefaces.component.CommandButtonRenderer");
                //				sectionButton.addActionListener(new MethodExpressionActionListener(targetExpression));
                //				sectionButton.setValue("CLICK");
                //				sectionButton.getChildren().add(link);
                //				//				sectionPanel.getChildren().add(sectionButton);
                //				sectionPanel.getFacets().put("actions", sectionButton);

                //				addButton = (CommandButton) application.createComponent(fc, "org.primefaces.component.CommandButton", "org.primefaces.component.CommandButtonRenderer");
                //				addButton.addActionListener(new MethodExpressionActionListener(targetExpression));
                //				addButton.setIcon("fa fa-plus");
                //				sectionPanel.getFacets().put("Footer", addButton);

                //				ajaxBehavior.setProcess("@all");
                //				ajaxBehavior.setUpdate("@all messageGrowl");
                //				ajaxBehavior.setOncomplete("PF('sidebarCreate').show()");
                //				ajaxBehavior.addAjaxBehaviorListener(new AjaxBehaviorListenerImpl(targetExpression, targetExpression));
                //				addButton.addClientBehavior("click", ajaxBehavior);
                //				addButtonPanel.getChildren().add(addButton);
                //				testCol.getChildren().add(sectionPanel);
                //				orderList.getChildren().add(testCol);
                //				orderList.getChildren().add(addButtonPanel);
                //				cl = dashboardModelView.getModel().getColumn(0);
                //				cl.addWidget(sectionPanel.getId());
                //				cl.addWidget(addButtonPanel.getId());
                //				HtmlOutputText text = new HtmlOutputText();
                //				text.setValue("tests");
                //				sectionPanel.getChildren().add(text);
                //				addEvents(currentSection);
            }
        }
    }

    public void addEvents(Hikesection section) {
//		for (Hikeevent event : hikeeventDAO.findAllByHikesection(section)) {
//			//				Panels
//			//			CommandButton theButton = (CommandButton) application.createComponent(fc, "org.primefaces.component.CommandButton", "org.primefaces.component.CommandButtonRenderer");
//
//			eventPanel = (CustomPanel) application.createComponent(fc, "org.primefaces.component.panel.Panel", "org.primefaces.component.PanelRenderer");
////			eventPanel.setId("panel_section_event_" + event.getSortid());
//			eventPanel.setPanelClass(Hikeevent.class);
//			eventPanel.setToggleable(false);
//			eventPanel.setClosable(false);
//			eventPanel.setHeader("Event!: " + event.getClass());
//			//			cl = dashboardModelView.getModel().getColumn(0);
//			//			cl.addWidget(eventPanel.getId());
//			//			cl.addWidget(theButton.getId());
//		}
    }

    private void addMessage(FacesMessage message) {
        FacesContext.getCurrentInstance().addMessage(null, message);
    }

    public void moveSectionUp(String sectionId) {
        System.out.println(sectionId);
    }

    public void addCommandbutton() {
        System.out.println("ADD gedrückt neuen button/Panel einfügen");

    }

    public void onHikesectionSelect(SelectEvent event) {
        //		if (null == event || null == event.getObject()) {
        //			return;
        //		}
        //		if (!event.isCtrlKey()) {
        //			setHikeSections(new ArrayList<Hikesection>());
        //		}
        FacesContext context = FacesContext.getCurrentInstance();
        context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Item Selected", event.getObject().toString()));
        System.out.println("Klasse von Evet Object: " + event.getObject().getClass());
        currentSection = (Hikesection) event.getObject();
        logger.debug("aktuelle Section/Hike Position in Liste [ " + hikeSections.indexOf(currentSection) + " ]");
        oldItemPosition = hikeSections.indexOf(currentSection);

        //		if (!getHikeSections().contains(item)) {
        //			getHikeSections().add(item);
        //		}
    }

    public void onHikeeventSelect(SelectEvent event) {
        FacesContext context = FacesContext.getCurrentInstance();
        context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Item Selected", event.getObject().toString()));
        System.out.println("Klasse von Evet Object: " + event.getObject().getClass());
        currentEvent = (Hikeevent) event.getObject();
        logger.debug("aktuelle Event Position in Liste [ " + event.getObject() + "" + hikeSections.indexOf(currentSection) + " ]");
        oldItemPosition = hikeSections.indexOf(currentSection);

        //		if (!getHikeSections().contains(item)) {
        //			getHikeSections().add(item);
        //		}
    }

    public void onHikesectionUnselect(UnselectEvent event) {
        FacesContext context = FacesContext.getCurrentInstance();
        context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Item Unselected", event.getObject().toString()));
    }

    public void onHikeeventUnselect(UnselectEvent event) {
        FacesContext context = FacesContext.getCurrentInstance();
        context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Item Unselected", event.getObject().toString()));
    }

    public void onHikeSectionChange(ValueChangeEvent event) {
        System.out.println(event);
    }

    public void onHikeeventChange(ValueChangeEvent event) {
        System.out.println(event);
    }

    /**
     * SOLL Listenposition wird ermittelt
     * WENN SOLL > IST: Alle elemente von IST Position bis <= SOLL POsition werden decrementiert
     * element kriegt neue SOLL Position
     * <p>
     * es wird zur Zeit nur der Tausch zweier Elemente betrachtet
     * ist element setzen auf 0
     */

    public void onEventDrop(DragDropEvent ddEvent) {
        String draggedId = ddEvent.getDragId(); //Client id of dragged component
        String droppedId = ddEvent.getDropId(); //Client id of dropped component
        Object data = ddEvent.getData(); //Model object of a datasource
    }

    @Transactional
    public void onHikesectionReorder() {

//		logger.debug("Section Position in Liste NACH REORDER [ " + hikeSections.indexOf(currentSection) + " ]");
//		int oldOriginalSortId = currentSection.getSortid();
//		currentSection.setSortid(0);
//		currentSection = hikesectionDAO.update(currentSection);
//		em.flush();
//		currentSection.setSortid(hikeSections.get(oldItemPosition).getSortid());
//		hikeSections.get(oldItemPosition).setSortid(oldOriginalSortId);
        hikesectionDAO.update(hikeSections.get(oldItemPosition));
        em.flush();
        hikesectionDAO.update(currentSection);


        //
        //		int i = 1;
        //		for (Hikesection hs : hikeSections) {
        //			hs.setSortid(i++);
        //			em.flush();
        //			em.clear();
        //			Query query = em.createQuery("UPDATE Book b SET b.price = b.price*1.1");
        //			query.executeUpdate();
        //		}

//		for (Hikesection hs : hikeSections) {
//			hikesectionDAO.update(hs);
//		}
        //		Configuration configuration = new Configuration().configure();
        //
        //		StandardServiceRegistryBuilder ssrb = new StandardServiceRegistryBuilder();
        //		SessionFactory sessionFactory = configuration.buildSessionFactory(ssrb.build());
        //		Session session = sessionFactory.openSession();
        //		Transaction tx = session.beginTransaction();
        //
        //		ScrollableResults customers = session.getNamedQuery("GetCustomers")
        //				.setCacheMode(CacheMode.IGNORE)
        //				.scroll(ScrollMode.FORWARD_ONLY);
        //		int count=0;
        //		while ( customers.next() ) {
        //			Customer customer = (Customer) customers.get(0);
        //			customer.updateStuff(...);
        //			if ( ++count % 20 == 0 ) {
        //				//flush a batch of updates and release memory:
        //				session.flush();
        //				session.clear();
        //			}
        //		}
        //
        //		tx.commit();
        //		session.close();

        //		FacesContext context = FacesContext.getCurrentInstance();
        //		context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, currentSection.getName() + " " + currentSection.getId(), null));
        //		for (Hikesection s : hikeSections) {
        //			System.out.println(s.getId() + " " + s.getName() + "Pos: " + s.getSortid());
        //		}
    }

    @Transactional
    public void onHikeeventReorder() {

//		logger.debug("Section Position in Liste NACH REORDER [ " + hikeevents[Math.toIntExact(currentEvent.getHikesection().getId())].indexOf(currentEvent) + " ]");
//		int oldOriginalSortId = currentEvent.getSortid();
//		currentEvent.setSortid(0);
//		currentEvent = hikeeventDAO.update(currentEvent);
//		em.flush();
//		currentEvent.setSortid(hikeevents.get()  [Math.toIntExact(currentEvent.getHikesection().getId())].get(oldItemPosition).getSortid());
//		hikeevents[Math.toIntExact(currentEvent.getHikesection().getId())].get(oldItemPosition).setSortid(oldOriginalSortId);
//		hikeeventDAO.update(hikeevents[Math.toIntExact(currentEvent.getHikesection().getId())].get(oldItemPosition));
//		em.flush();
//		hikeeventDAO.update(currentEvent);


        //
        //		int i = 1;
        //		for (Hikesection hs : hikeSections) {
        //			hs.setSortid(i++);
        //			em.flush();
        //			em.clear();
        //			Query query = em.createQuery("UPDATE Book b SET b.price = b.price*1.1");
        //			query.executeUpdate();
        //		}

//		for (Hikesection hs : hikeSections) {
//			hikesectionDAO.update(hs);
//		}
        //		Configuration configuration = new Configuration().configure();
        //
        //		StandardServiceRegistryBuilder ssrb = new StandardServiceRegistryBuilder();
        //		SessionFactory sessionFactory = configuration.buildSessionFactory(ssrb.build());
        //		Session session = sessionFactory.openSession();
        //		Transaction tx = session.beginTransaction();
        //
        //		ScrollableResults customers = session.getNamedQuery("GetCustomers")
        //				.setCacheMode(CacheMode.IGNORE)
        //				.scroll(ScrollMode.FORWARD_ONLY);
        //		int count=0;
        //		while ( customers.next() ) {
        //			Customer customer = (Customer) customers.get(0);
        //			customer.updateStuff(...);
        //			if ( ++count % 20 == 0 ) {
        //				//flush a batch of updates and release memory:
        //				session.flush();
        //				session.clear();
        //			}
        //		}
        //
        //		tx.commit();
        //		session.close();

        //		FacesContext context = FacesContext.getCurrentInstance();
        //		context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, currentSection.getName() + " " + currentSection.getId(), null));
        //		for (Hikesection s : hikeSections) {
        //			System.out.println(s.getId() + " " + s.getName() + "Pos: " + s.getSortid());
        //		}
    }
}
