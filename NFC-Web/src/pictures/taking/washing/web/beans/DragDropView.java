package pictures.taking.washing.web.beans;

import pictures.taking.washing.ejb.interfaces.HikeeventDAO;
import pictures.taking.washing.ejb.interfaces.HikesectionDAO;
import pictures.taking.washing.helper.MSG;
import pictures.taking.washing.helper.ResourceBundle;
import pictures.taking.washing.persistence.entities.Events.Hikeevent;
import pictures.taking.washing.persistence.entities.Events.InformationEvent;
import pictures.taking.washing.persistence.entities.Events.LocationEvent;
import pictures.taking.washing.persistence.entities.Hikesection;
import com.vividsolutions.jts.geom.Coordinate;
import com.vividsolutions.jts.geom.GeometryFactory;
import com.vividsolutions.jts.geom.Point;
import com.vividsolutions.jts.geom.PrecisionModel;
import org.primefaces.PrimeFaces;
import org.primefaces.component.commandbutton.CommandButton;
import org.primefaces.component.tree.Tree;
import org.primefaces.event.*;
import org.primefaces.model.DefaultTreeNode;
import org.primefaces.model.TreeNode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.PostConstruct;
import javax.annotation.security.DeclareRoles;
import javax.annotation.security.RolesAllowed;
import javax.ejb.EJB;
import javax.el.ELContext;
import javax.el.ExpressionFactory;
import javax.faces.application.Application;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.transaction.Transactional;
import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


//@TransactionAttribute is for EJB3 beans.
//
//@Transactional is for POJOs (for example Seam, Spring/Hibernate).


//@Configuration
//@EnableTransactionManagement
@Named("treeDNDView")
//@Stateful
//@TransactionManagement(TransactionManagementType.CONTAINER)

@ViewScoped
@DeclareRoles(value = {"ADMINISTRATOR", "USER", "GUEST"})
public class DragDropView implements Serializable {
    private static final long serialVersionUID = 1L;

    private static final String SECTION_TYPE = "section";
    private static final String EVENT_TYPE = "event";
    final Logger logger = LoggerFactory.getLogger(DragDropView.class);
    protected String toBeRefreshedWidgetID = ":hikeForm";
    CommandButton addButton;
    Tree myTree;
    Application application;
    FacesContext fc;
    ELContext elContext = FacesContext.getCurrentInstance().getELContext();
    ExpressionFactory factory = FacesContext.getCurrentInstance().getApplication().getExpressionFactory();
    int previousSectionPosition;
    int newSectionPosition;
    Point newLocation;
    int nextSectionPosition;
    int previousEventPosition = -1; // init with -1 to prevent from sanity check
    int newEventPosition;
    int sectionPosition;
    int nextEventPosition;

//    @Resource
//    private EJBContext context;   //ONLY FOR EJB USAGE
//    @Resource(mappedName = "java:comp/TransactionSynchronizationRegistry") // only for CONTAINER based transactions
//    protected TransactionSynchronizationRegistry tsr;
//    @Resource(mappedName = "java:comp/UserTransaction") // only for BEAN based transactions
//    private UserTransaction userTransaction;

    private TreeNode oldHikeTree;
    private TreeNode rootNode;
    private TreeNode selectedNode;
    @PersistenceContext
    private EntityManager em;
    private Hikeevent selectedEvent;
    private Hikesection selectedSection;
    @Inject
    private LocaleBean localeBean;
    @EJB
    private HikesectionDAO hikesectionDAO;
    @EJB
    private HikeeventDAO hikeeventDAO;
    @Inject
    private HikeBean hikeBean;
    @Inject
    private HikeeventBean hikeeventBean;
    @Inject
    private HikesectionBean hikesectionBean;

    public static String getSectionType() {
        return SECTION_TYPE;
    }

    public static String getEventType() {
        return EVENT_TYPE;
    }


    public CommandButton getAddButton() {
        return addButton;
    }

    public void setAddButton(CommandButton addButton) {
        this.addButton = addButton;
    }

    public Logger getLogger() {
        return logger;
    }

    public int getNewEventPosition() {
        return newEventPosition;
    }

    public void setNewEventPosition(int newEventPosition) {
        this.newEventPosition = newEventPosition;
    }

    public int getSectionPosition() {
        return sectionPosition;
    }

    public void setSectionPosition(int sectionPosition) {
        this.sectionPosition = sectionPosition;
    }

    public int getNewSectionPosition() {
        return newSectionPosition;
    }

    public void setNewSectionPosition(int newSectionPosition) {
        this.newSectionPosition = newSectionPosition;
    }

    public Hikeevent getSelectedEvent() {
        return selectedEvent;
    }

    public void setSelectedEvent(Hikeevent selectedEvent) {
        this.selectedEvent = selectedEvent;
    }

    public Hikesection getSelectedSection() {
        return selectedSection;
    }

    public void setSelectedSection(Hikesection selectedSection) {
        this.selectedSection = selectedSection;
    }

    public Tree getMyTree() {
        return myTree;
    }

    public void setMyTree(Tree myTree) {
        this.myTree = myTree;
    }

    @PostConstruct
    public void init() {
        rootNode = new DefaultTreeNode("Root", null);

        for (Hikesection section : hikesectionDAO.findAllByHike(hikeBean.getHike())) {
            TreeNode sectionPanelNode = new DefaultTreeNode("section", section, rootNode);
            sectionPanelNode.setExpanded(true);

            for (Hikeevent event : hikeeventDAO.findAllByHikesection(section)) {
                String eventNodeName = "event_" + String.valueOf(event);
                TreeNode eventNode = new DefaultTreeNode("event", event, sectionPanelNode);
            }
        }
        PrimeFaces.current().ajax().update("hikeForm:hikeTree");
    }

    public List getSections() {
        Query q = em.createNativeQuery("with recursive myhikeevent as( select hikeevent.id as joiner, hikeevent.*, turnamentevent.*, taskevent.*, locationevent.*, quizevent.*, informationevent.*, codescanevent.*, case when turnamentevent.id is not null then 1 when surveyevent.id is not null then 2 when taskevent.id is not null then 3 when locationevent.id is not null then 4 when quizevent.id is not null then 5 when informationevent.id is not null then 6 when codescanevent.id is not null then 7 when hikeevent.id is not null then 0 end as clazz_, 1 as level from hikeevent left outer join turnamentevent on hikeevent.id = turnamentevent.id left outer join surveyevent on hikeevent.id = surveyevent.id left outer join taskevent on hikeevent.id = taskevent.id left outer join locationevent on hikeevent.id = locationevent.id left outer join quizevent on hikeevent.id = quizevent.id left outer join informationevent on hikeevent.id = informationevent.id left outer join codescanevent on hikeevent.id = codescanevent.id where hikeevent.previoushikeevent_id is null and hikeevent.hikesection_id = :hikesectionId union all select hikeevent.id, hikeevent.*, turnamentevent.*, taskevent.*, locationevent.*, quizevent.*, informationevent.*, codescanevent.*, case when turnamentevent.id is not null then 1 when surveyevent.id is not null then 2 when taskevent.id is not null then 3 when locationevent.id is not null then 4 when quizevent.id is not null then 5 when informationevent.id is not null then 6 when codescanevent.id is not null then 7 when hikeevent.id is not null then 0 end as clazz_, t.level + 1 from hikeevent left outer join turnamentevent on hikeevent.id = turnamentevent.id left outer join surveyevent on hikeevent.id = surveyevent.id left outer join taskevent on hikeevent.id = taskevent.id left outer join locationevent on hikeevent.id = locationevent.id left outer join quizevent on hikeevent.id = quizevent.id left outer join informationevent on hikeevent.id = informationevent.id left outer join codescanevent on hikeevent.id = codescanevent.id join myhikeevent as t on hikeevent.previoushikeevent_id = t.joiner where hikeevent.hikesection_id = :hikesectionId) select * from myhikeevent order by level"
                , Hikeevent.class);
        q.setParameter("hikesectionId", 39);
//		q.setHint("hibernate.dialect", "org.hibernate.dialect.PostgreSQLDialect");
//		Query q = em.createNativeQuery("WITH RECURSIVE MyHikesection AS (" +
//				"SELECT *,1 as level FROM Hikesection where previoushikesection_id IS null " +
//				" UNION ALL " +
//				"SELECT m.*, t.level + 1  FROM hikesection AS m JOIN MyHikesection AS t " +
//				"ON m.previoushikesection_id = t.Id )" +
//				"SELECT * FROM MyHikesection ORDER BY level");

//		Query q = em.createNativeQuery("SELECT * FROM securityrole",Securityrole.class);
        List<Hikeevent> b = q.getResultList();
        for (Hikeevent a : b) {
            System.out.println(a);
        }
        return b;
    }

    public TreeNode getRootNode() {
        return rootNode;
    }

    public void setRootNode(TreeNode rootNode) {
        this.rootNode = rootNode;
    }

    public TreeNode getSelectedNode() {
        return selectedNode;
    }

    public void setSelectedNode(TreeNode node) {
        this.selectedNode = node;
    }

    public void onSelectNode(NodeSelectEvent e) {
        this.setSelectedNode(e.getTreeNode());
//		FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, "Selected", e.getTreeNode().toString());
//		FacesContext.getCurrentInstance().addMessage(null, message);
    }

    public void buttonAction(ActionEvent actionEvent) {
        addMessage("Welcome to Primefaces!!");
    }

    public void addMessage(String summary) {
        FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, summary, null);
        FacesContext.getCurrentInstance().addMessage(null, message);
    }

    @Transactional
    public void deleteSection(Hikesection section) {
        //if it is already set for deletion
        if (hikesectionBean.getHikesectionToBeDeleted() != null && hikesectionBean.getHikesectionToBeDeleted().equals(section)) {
            {
                // relink surrounding sections
                Hikesection subsequentHikesection = section.getNextHikesection();
                Hikesection predecessorHikesection = section.getPreviousHikesection();

                if (predecessorHikesection != null) {
                    predecessorHikesection.setNextHikesection(subsequentHikesection);
                    predecessorHikesection = hikesectionDAO.update(predecessorHikesection);
                }
                if (subsequentHikesection != null) {
                    subsequentHikesection.setPreviousHikesection(predecessorHikesection);
                    subsequentHikesection = hikesectionDAO.update(subsequentHikesection);
                }
                // simple but slow!
                hikesectionDAO.remove(section.getId());

                //alternative

                // delete all events that this section posesses
//                Query q = em.createNativeQuery("DELETE FROM hikeevent WHERE hikeevent.hikesection_id = ?");
//                q.setParameter(1, section.getId());

//                Query q = em.createQuery("DELETE FROM Hikeevent");
//                q.executeUpdate();
//                List<Integer> hikeeventIDs = (List<Integer>)q.getResultList();
//                deleteMe(section);

//                logger.info(hikeeventIDs.toString());

                // remove the section
//                q = em.createNativeQuery("DELETE FROM hikesection WHERE hikesection.id is (:id)");
//                q.setParameter("id", section.getId());
//                q.executeUpdate();
//                hikesectionDAO.remove(section.getId());

// remove author
//                em.remove(a);


                hikesectionBean.setHikesectionToBeDeleted(null);
                init();
                FacesContext.getCurrentInstance().
                        addMessage(null,
                                new FacesMessage(FacesMessage.SEVERITY_INFO,
                                        MSG.getValue(ResourceBundle.VALIDATIONS.toString(), "success_delete_section", new String[]{section.getTitle()}, localeBean.getLocale()),
                                        MSG.getValue(ResourceBundle.VALIDATIONS.toString(), "success_delete_section_details", new String[]{section.getTitle()}, localeBean.getLocale())));
            }
        } else {
            hikesectionBean.setHikesectionToBeDeleted(section);
        }
    }



    public void clearToBeDeletedSection() {
        hikesectionBean.setHikesectionToBeDeleted(null);
    }

    public void resetCurrentSection() {
        hikesectionBean.setHikesection(null);
        hikesectionBean.init();
    }

    public void clearToBeDeletedEvent() {
        hikeeventBean.setHikeeventToBeDeleted(null);
    }

    @Transactional
    public void deleteEvent(Hikeevent event) {
        //if it is already set for deletion
        if (hikeeventBean.getHikeeventToBeDeleted() != null && hikeeventBean.getHikeeventToBeDeleted().equals(event)) {
            // relink surrounding events
            Hikeevent subsequentHikeevent = event.getNextHikeevent();
            Hikeevent predecessorHikeevent = event.getPreviousHikeevent();

            if (predecessorHikeevent != null) {
                predecessorHikeevent.setNextHikeevent(subsequentHikeevent);
                predecessorHikeevent = hikeeventDAO.update(predecessorHikeevent);
            }
            if (subsequentHikeevent != null) {
                subsequentHikeevent.setPreviousHikeevent(predecessorHikeevent);
                subsequentHikeevent = hikeeventDAO.update(subsequentHikeevent);
            }
            hikeeventDAO.remove(event.getId());
            hikeeventBean.setHikeeventToBeDeleted(null);
            init();
            FacesContext.getCurrentInstance().
                    addMessage(null,
                            new FacesMessage(FacesMessage.SEVERITY_INFO,
                                    MSG.getValue(ResourceBundle.VALIDATIONS.toString(), "success_delete_event", null, localeBean.getLocale()), ""));
        } else {
            hikeeventBean.setHikeeventToBeDeleted(event);
//            PrimeFaces.current().ajax().addCallheckParam("tests", event.getId().toString());
        }
    }

//    public void getEventToBeDeleted() {
//        PrimeFaces.current().ajax().addCallheckParam("tests", "some message");
//    }

    public void getHikesectionDate() {
        Date d = hikesectionBean.getHikesection().getDate();
        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
        String datumsstring = sdf.format(d);

        PrimeFaces.current().ajax().addCallbackParam("date", datumsstring);
//        PrimeFaces.current().executeScript("setCurrentDate(xhr, status, args);choosePointDialog(); PF('choosePointDialogWidget').show();");
    }

    public void getChooseLocationInvoker() {
        PrimeFaces.current().ajax().addCallbackParam("invoker", hikeeventBean.getChooseLocationInvoker());
//        PrimeFaces.current().executeScript("setCurrentDate(xhr, status, args);choosePointDialog(); PF('choosePointDialogWidget').show();");
    }

    public void addChildNode() {
        Hikeevent he = new InformationEvent();
        TreeNode eventNode = new DefaultTreeNode("event", he, getSelectedNode());
        System.out.println("Selected Node: " + getSelectedNode().toString());
            /*TreeNode newNode = new DefaultTreeNode("Node New", getSelectedNode());
            getSelectedNode().setExpanded(true);*/
    }

    public void editEvent(Hikeevent event) {
        hikeeventBean.setHikeevent(event);
//        PrimeFaces.current().executeScript("PF('" + event.getEditFormName() + "').show()");
    }

    public void editSection(Hikesection section) {
        hikesectionBean.setHikesection(section);
//        PrimeFaces.current().executeScript("PF('sectionDialogWidget').show()");
    }

    //    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    @Transactional
    public void moveUpEvent(Hikeevent event) {
//		TreeNode eventNode = rootNode.getChildren().get(nodepos);
//		TreeNode oldpreviousNode = rootNode.getChildren().get(nodepos -1);
//		System.out.println(eventNode);
//		System.out.println(nodepos);
//		 rootNode.getChildren().r  rootNode.getChildren().get(nodepos)

        Hikeevent oldPreviousEvent = event.getPreviousHikeevent();
        Hikeevent oldNextEvent = event.getNextHikeevent();
        if (oldPreviousEvent != null) {
            Hikeevent oldBeforePreviousEvent = oldPreviousEvent.getPreviousHikeevent();
            if (oldBeforePreviousEvent != null) {
                oldBeforePreviousEvent.setNextHikeevent(event);
                hikeeventDAO.update(oldBeforePreviousEvent);
            }

            event.setPreviousHikeevent(oldPreviousEvent.getPreviousHikeevent());
            event.setNextHikeevent(oldPreviousEvent);

            oldPreviousEvent.setPreviousHikeevent(event);
            oldPreviousEvent.setNextHikeevent(oldNextEvent);

            hikeeventDAO.update(oldPreviousEvent);
            hikeeventDAO.update(event);

            if (oldNextEvent != null) {
                oldNextEvent.setPreviousHikeevent(oldPreviousEvent);
                hikeeventDAO.update(oldNextEvent);
            }
            FacesContext.getCurrentInstance().
                    addMessage(null,
                            new FacesMessage(FacesMessage.SEVERITY_INFO,
                                    MSG.getValue(ResourceBundle.VALIDATIONS.toString(), "success_move_up_event", null, localeBean.getLocale()), ""));
        } else {
            FacesContext.getCurrentInstance().
                    addMessage(null,
                            new FacesMessage(FacesMessage.SEVERITY_WARN,
                                    MSG.getValue(ResourceBundle.VALIDATIONS.toString(), "error_move_up_event", null, localeBean.getLocale()), ""));
        }
    }

    //    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    @Transactional
    public void moveDownEvent(Hikeevent event) {

        Hikeevent oldPreviousEvent = event.getPreviousHikeevent();
        Hikeevent oldNextEvent = event.getNextHikeevent();
        if (oldNextEvent != null) {
            Hikeevent oldAfterNextEvent = oldNextEvent.getNextHikeevent();
            if (oldAfterNextEvent != null) {
                oldAfterNextEvent.setPreviousHikeevent(event);
                hikeeventDAO.update(oldAfterNextEvent);
            }

            event.setNextHikeevent(oldNextEvent.getNextHikeevent());
            event.setPreviousHikeevent(oldNextEvent);

            oldNextEvent.setPreviousHikeevent(oldPreviousEvent);
            oldNextEvent.setNextHikeevent(event);

            hikeeventDAO.update(oldNextEvent);
            hikeeventDAO.update(event);

            if (oldPreviousEvent != null) {
                oldPreviousEvent.setNextHikeevent(oldNextEvent);
                hikeeventDAO.update(oldPreviousEvent);
            }
            FacesContext.getCurrentInstance().
                    addMessage(null,
                            new FacesMessage(FacesMessage.SEVERITY_INFO,
                                    MSG.getValue(ResourceBundle.VALIDATIONS.toString(), "success_move_down_event", null, localeBean.getLocale()), ""));
        } else {
            FacesContext.getCurrentInstance().
                    addMessage(null,
                            new FacesMessage(FacesMessage.SEVERITY_WARN,
                                    MSG.getValue(ResourceBundle.VALIDATIONS.toString(), "error_move_down_event", null, localeBean.getLocale()), ""));
        }


    }

    //    @TransactionAttribute(TransactionAttributeType.REQUIRED)  //FOR EJB
    @Transactional
    public void moveUpSection(Hikesection section) {
        // checking transaction status -> javax.transaction.Status.STATUS_NO_TRANSACTION == tsr.getTransactionStatus()
        // https://docs.oracle.com/cd/E19798-01/821-1841/bnciv/index.html
        // http://www.developerscrappad.com/547/java/java-ee/ejb3-x-jpa-when-to-use-rollback-and-setrollbackonly/
        // https://stackoverflow.com/questions/19563088/a-clear-explanation-of-system-exception-vs-application-exception
        // https://stackoverflow.com/questions/32853167/handling-service-layer-exception-in-java-ee-frontend-method

        Hikesection oldPreviousSection = section.getPreviousHikesection();
        Hikesection oldNextSection = section.getNextHikesection();
        if (oldPreviousSection != null) {
            Hikesection oldBeforePreviousSection = oldPreviousSection.getPreviousHikesection();
            if (oldBeforePreviousSection != null) {
                oldBeforePreviousSection.setNextHikesection(section);
                hikesectionDAO.update(oldBeforePreviousSection);
            }

            section.setPreviousHikesection(oldPreviousSection.getPreviousHikesection());
            section.setNextHikesection(oldPreviousSection);

            oldPreviousSection.setPreviousHikesection(section);
            oldPreviousSection.setNextHikesection(oldNextSection);

            hikesectionDAO.update(oldPreviousSection);
            hikesectionDAO.update(section);

            if (oldNextSection != null) {
                oldNextSection.setPreviousHikesection(oldPreviousSection);
                hikesectionDAO.update(oldNextSection);
            }

            FacesContext.getCurrentInstance().
                    addMessage(null,
                            new FacesMessage(FacesMessage.SEVERITY_INFO,
                                    MSG.getValue(ResourceBundle.VALIDATIONS.toString(), "success_move_up_section", null, localeBean.getLocale()), ""));


        } else {
            FacesContext.getCurrentInstance().
                    addMessage(null,
                            new FacesMessage(FacesMessage.SEVERITY_WARN,
                                    MSG.getValue(ResourceBundle.VALIDATIONS.toString(), "error_move_up_section", null, localeBean.getLocale()), ""));
        }

    }

    //    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    @Transactional
    public void moveDownSection(Hikesection section) {
        Hikesection oldPreviousSection = section.getPreviousHikesection();
        Hikesection oldNextSection = section.getNextHikesection();
        if (oldNextSection != null) {
            Hikesection oldAfterNextSection = oldNextSection.getNextHikesection();
            if (oldAfterNextSection != null) {
                oldAfterNextSection.setPreviousHikesection(section);
                hikesectionDAO.update(oldAfterNextSection);
            }

            section.setNextHikesection(oldNextSection.getNextHikesection());
            section.setPreviousHikesection(oldNextSection);

            oldNextSection.setPreviousHikesection(oldPreviousSection);
            oldNextSection.setNextHikesection(section);

            hikesectionDAO.update(oldNextSection);
            hikesectionDAO.update(section);

            if (oldPreviousSection != null) {
                oldPreviousSection.setNextHikesection(oldNextSection);
                hikesectionDAO.update(oldPreviousSection);
            }
            FacesContext.getCurrentInstance().
                    addMessage(null,
                            new FacesMessage(FacesMessage.SEVERITY_INFO,
                                    MSG.getValue(ResourceBundle.VALIDATIONS.toString(), "success_move_down_section", null, localeBean.getLocale()), ""));
        } else {
            FacesContext.getCurrentInstance().
                    addMessage(null,
                            new FacesMessage(FacesMessage.SEVERITY_WARN,
                                    MSG.getValue(ResourceBundle.VALIDATIONS.toString(), "error_move_down_section", null, localeBean.getLocale()), ""));
        }
    }

    @RolesAllowed(value = {"USER"})
    public void setSurroundingPositions() {
        java.util.Map<String, String> params = FacesContext.getCurrentInstance().getExternalContext()
                .getRequestParameterMap();
        try {
            String previousSectionPosition_temp = params.get("oldSectionPosition");
            if (null != previousSectionPosition_temp && !previousSectionPosition_temp.equals("")) {
                previousSectionPosition = Integer.parseInt(previousSectionPosition_temp);
            }
            String previousEventPosition_temp = params.get("oldEventPosition").substring(params.get("oldEventPosition").lastIndexOf("_") + 1, params.get("oldEventPosition").length());
            if (null != previousEventPosition_temp && !previousEventPosition_temp.equals("")) {
                previousEventPosition = Integer.parseInt(previousEventPosition_temp);
            }
        } catch (Exception e) {
            logger.error("fail to parse at least 1 SurroundingPositions String");
            logger.info(e.getMessage());
        }
    }

    @RolesAllowed(value = {"USER"})
    public void setToBeRefreshedWidget() {
        java.util.Map<String, String> params = FacesContext.getCurrentInstance().getExternalContext()
                .getRequestParameterMap();
        try {
            toBeRefreshedWidgetID = params.get("toBeRefreshedWidgetID");
            logger.info("refresh: " + toBeRefreshedWidgetID);
        } catch (Exception e) {
            logger.error("setToBeRefreshedWidget: fail to parse toBeRefreshedWidgetID String");
            logger.info(e.getMessage());
        }
    }

    @RolesAllowed(value = {"USER"})
    public void setToBeRefreshedWidgetByWidgetVar() {
        java.util.Map<String, String> params = FacesContext.getCurrentInstance().getExternalContext()
                .getRequestParameterMap();
        try {
            toBeRefreshedWidgetID = params.get("toBeRefreshedWidgetID");
        } catch (Exception e) {
            logger.error("setToBeRefreshedWidgetByWidgetVar: fail to parse toBeRefreshedWidgetID String");
            logger.info(e.getMessage());
        }
    }

//    @RolesAllowed(value = {"USER"})
//    public void editEvent() {
////        logger.info("edit event");
////
////        java.HibernateUtil.Map<String, String> params = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap();
////        try {
////            String sectionPosition_temp = params.get("toEditEventSection");
////            if (null != sectionPosition_temp && !sectionPosition_temp.equals("")) {
////                sectionPosition = Integer.parseInt(sectionPosition_temp);
////            }
////        } catch (Exception e) {
////            logger.error("fail to parse at least 1 position String");
////            logger.info(e.getMessage());
////        }
////		Map<String, Object> options = new HashMap<String, Object>();
////		options.put("resizable", false);
////		PrimeFaces.current().dialog().showMessageDynamic(new FacesMessage("hallo"));
////		PrimeFaces.current().dialog().openDynamic("createHikeDia  log");
////        PrimeFaces.current().executeScript("PF('sideher_right').show()");
//    }


    //takes values from Javascript in myTree.js when clicked on "addEvent"
    @Transactional
    @RolesAllowed(value = {"USER"})
    public void addEvent() {
        logger.info("set event position");

        java.util.Map<String, String> params = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap();
        try {
            String newEventPosition_temp = params.get("newEventPosition");
            if (null != newEventPosition_temp && !newEventPosition_temp.equals("")) {
                newEventPosition = Integer.parseInt(newEventPosition_temp);
            }
            String sectionPosition_temp = params.get("sectionPosition");
            if (null != sectionPosition_temp && !sectionPosition_temp.equals("")) {
                sectionPosition = Integer.parseInt(sectionPosition_temp);
            }
        } catch (Exception e) {
            logger.error("addEvent: fail to parse at least 1 position String");
            logger.info(e.getMessage());
        }
//		Map<String, Object> options = new HashMap<String, Object>();
//		options.put("resizable", false);
//		PrimeFaces.current().dialog().showMessageDynamic(new FacesMessage("hallo"));
//		PrimeFaces.current().dialog().openDynamic("createHikeDia  log");
        PrimeFaces.current().executeScript("PF('sidebar_right').show()");
    }

    @Transactional
    @RolesAllowed(value = {"ADMINISTRATOR"})
    public void addSection() {
        java.util.Map<String, String> params = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap();
        try {
            String newSectionPosition_temp = params.get("newSectionPosition");
            if (null != newSectionPosition_temp && !newSectionPosition_temp.equals("")) {
                newSectionPosition = Integer.parseInt(newSectionPosition_temp);
            }
        } catch (Exception e) {
            logger.error("addSection: fail to parse at least 1 position String");
            logger.info(e.getMessage());
        }
//		Map<String, Object> options = new HashMap<String, Object>();
//		options.put("resizable", false);
//		PrimeFaces.current().dialog().showMessageDynamic(new FacesMessage("hallo"));
//		PrimeFaces.current().dialog().openDynamic("createHikeDialog");
        hikesectionBean.setHikesection(null);
        hikesectionBean.init();
        PrimeFaces.current().executeScript("PF('sectionDialogWidget').show()");
//        PrimeFaces.current().executeScript("PF('sectionDialogWidget').refresh()");

    }

    @RolesAllowed(value = {"USER"})
    public void setLocation() {
        java.util.Map<String, String> params = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap();
        Point p1 = null;
        try {
            GeometryFactory gf = new GeometryFactory(new PrecisionModel(), 4326);
            p1 = gf.createPoint(new Coordinate(Double.parseDouble(params.get("latitude")), Double.parseDouble(params.get("longitude"))));

            //special case of forwarding to another point after reaching a location
            if (!params.get("invoker").isEmpty()) {
                switch (params.get("invoker")) {
                    case "Location_goal":
                        ((LocationEvent) hikeeventBean.getHikeevent()).setLocationevent_locationToGo(p1);
                        break;
                }
            } else {
                hikesectionBean.getHikesection().setLocation(p1);
                hikeeventBean.getHikeevent().setLocation(p1);
            }
        } catch (Exception e) {
            logger.error(e.getMessage()+ "setLocation: fail to parse Location_goal String: "+params+"\n"+p1);
            logger.info(e.getMessage());
        }
//		Map<String, Object> options = new HashMap<String, Object>();
//		options.put("resizable", false);
//		PrimeFaces.current().dialog().showMessageDynamic(new FacesMessage("hallo"));
//		PrimeFaces.current().dialog().openDynamic("createHikeDialog");
//        PrimeFaces.current().executeScript("PF('sectionDialogWidget').show()");

    }

    public void onDragDrop(TreeDragDropEvent event) {
        TreeNode dragNode = event.getDragNode();
        TreeNode dropNode = event.getDropNode();
        if ((rootNode.getChildren().get(previousSectionPosition) == dropNode) && (previousEventPosition == dropNode.getChildren().indexOf(dragNode))) {
            init();
            return;
        }
        if ((dragNode.getType().equals(EVENT_TYPE) && dropNode.getType().equals(SECTION_TYPE)) || (dragNode.getType().equals(SECTION_TYPE) && dropNode.getType().equals("default"))) {
            if ((dragNode.getType().equals(EVENT_TYPE) && dropNode.getType().equals(SECTION_TYPE))) {

                // 1. check for closing the created gap:
                // if position of the moved hikeevent < last position in hikesection,
                // then set the previousEvent of the subsequent event to its preceding event
                // and the nextEvent of the predecessorEvent to the subsequentEvent
//				TreeNode previousHikesectionNode = rootNode.getChildren().get(previousSectionPosition);

                //update child in section to close the gap
                Hikeevent droppedHikeevent = (Hikeevent) dragNode.getData();
                if (droppedHikeevent.getId() == null) {
                    droppedHikeevent = hikeeventDAO.create(droppedHikeevent);
                }
                Hikeevent subsequentHikeevent = droppedHikeevent.getNextHikeevent();
                Hikeevent predecessorHikeevent = droppedHikeevent.getPreviousHikeevent();

                if (predecessorHikeevent != null) {
                    predecessorHikeevent.setNextHikeevent(subsequentHikeevent);
                    predecessorHikeevent = hikeeventDAO.update(predecessorHikeevent);
                }
                if (subsequentHikeevent != null) {
                    subsequentHikeevent.setPreviousHikeevent(predecessorHikeevent);
                    subsequentHikeevent = hikeeventDAO.update(subsequentHikeevent);
                }

                // 2. now update new inserted event
                Hikeevent previousDroppedHikeevent = dropNode.getChildren().indexOf(dragNode) > 0 ? (Hikeevent) dropNode.getChildren().get(dropNode.getChildren().indexOf(dragNode) - 1).getData() : null;
                if (previousDroppedHikeevent != null) {
                    previousDroppedHikeevent.setNextHikeevent(droppedHikeevent);
                    previousDroppedHikeevent = hikeeventDAO.update(previousDroppedHikeevent);
                }
                droppedHikeevent.setPreviousHikeevent(previousDroppedHikeevent);

                Hikeevent subsequentDroppedHikeevent = dropNode.getChildren().indexOf(dragNode) < dropNode.getChildCount() - 1 ? (Hikeevent) dropNode.getChildren().get(dropNode.getChildren().indexOf(dragNode) + 1).getData() : null;
                if (subsequentDroppedHikeevent != null) {
                    subsequentDroppedHikeevent.setPreviousHikeevent(droppedHikeevent);
                    subsequentDroppedHikeevent = hikeeventDAO.update(subsequentDroppedHikeevent);
                }
                droppedHikeevent.setNextHikeevent(subsequentDroppedHikeevent);
                droppedHikeevent.setHikesection((Hikesection) dropNode.getData());

                hikeeventDAO.update(droppedHikeevent);
//                hikeeventBean.setHikeevent(null);

                init();
                if (dropNode.getChildren().contains(dragNode)) {
                    FacesContext.getCurrentInstance().
                            addMessage(null,
                                    new FacesMessage(FacesMessage.SEVERITY_INFO,
                                            MSG.getValue(ResourceBundle.VALIDATIONS.toString(), "success_dragdrop_event", null, localeBean.getLocale()),
                                            MSG.getValue(ResourceBundle.VALIDATIONS.toString(), "success_dragdrop_event_details", new String[]{((Hikesection) dropNode.getData()).getTitle(), String.valueOf(dropNode.getChildren().indexOf(dragNode))}, localeBean.getLocale())));
                } else {
                    FacesContext.getCurrentInstance().
                            addMessage(null,
                                    new FacesMessage(FacesMessage.SEVERITY_INFO,
                                            MSG.getValue(ResourceBundle.VALIDATIONS.toString(), "success_create_event", null, localeBean.getLocale()), ""));

                }

            } else {
                System.out.println("section");
                Hikesection droppedHikesection = (Hikesection) dragNode.getData();
                if (droppedHikesection.getId() == null) {
                    droppedHikesection = hikesectionDAO.create(droppedHikesection);
                }
                Hikesection subsequentHikesection = droppedHikesection.getNextHikesection();
                Hikesection predecessorHikesection = droppedHikesection.getPreviousHikesection();

                if (predecessorHikesection != null) {
                    predecessorHikesection.setNextHikesection(subsequentHikesection);
                    predecessorHikesection = hikesectionDAO.update(predecessorHikesection);
                }
                if (subsequentHikesection != null) {
                    subsequentHikesection.setPreviousHikesection(predecessorHikesection);
                    subsequentHikesection = hikesectionDAO.update(subsequentHikesection);
                }

                // 2. now update new inserted event
                Hikesection previousDroppedHikesection = dropNode.getChildren().indexOf(dragNode) > 0 ? (Hikesection) dropNode.getChildren().get(dropNode.getChildren().indexOf(dragNode) - 1).getData() : null;
                if (previousDroppedHikesection != null) {
                    previousDroppedHikesection.setNextHikesection(droppedHikesection);
                    previousDroppedHikesection = hikesectionDAO.update(previousDroppedHikesection);
                }
                droppedHikesection.setPreviousHikesection(previousDroppedHikesection);

                Hikesection subsequentDroppedHikesection = dropNode.getChildren().indexOf(dragNode) < dropNode.getChildCount() - 1 ? (Hikesection) dropNode.getChildren().get(dropNode.getChildren().indexOf(dragNode) + 1).getData() : null;
                if (subsequentDroppedHikesection != null) {
                    subsequentDroppedHikesection.setPreviousHikesection(droppedHikesection);
                    subsequentDroppedHikesection = hikesectionDAO.update(subsequentDroppedHikesection);
                }
                droppedHikesection.setNextHikesection(subsequentDroppedHikesection);
//				droppedHikesection.setHikesection((Hikesection) dropNode.getData());

                hikesectionDAO.update(droppedHikesection);
                hikesectionBean.setHikesection(null);
                init();
                if (dropNode.getChildren().contains(dragNode)) {

                    FacesContext.getCurrentInstance().
                            addMessage(null,
                                    new FacesMessage(FacesMessage.SEVERITY_INFO,
                                            MSG.getValue(ResourceBundle.VALIDATIONS.toString(), "success_dragdrop_section", new String[]{droppedHikesection.getTitle()}, localeBean.getLocale()),
                                            MSG.getValue(ResourceBundle.VALIDATIONS.toString(), "success_dragdrop_section_details", new String[]{String.valueOf(dropNode.getChildren().indexOf(dragNode))}, localeBean.getLocale())));
                } else {
                    FacesContext.getCurrentInstance().
                            addMessage(null,
                                    new FacesMessage(FacesMessage.SEVERITY_INFO,
                                            MSG.getValue(ResourceBundle.VALIDATIONS.toString(), "success_create_section", new String[]{droppedHikesection.getTitle()}, localeBean.getLocale()),
                                            MSG.getValue(ResourceBundle.VALIDATIONS.toString(), "success_create_section_details", new String[]{droppedHikesection.getTitle()}, localeBean.getLocale())));
                }
            }


//            FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, "Dragged " + dragNode.getData(), "Dropped on " + dropNode.getData() + " at " + dropNode.getChildren().indexOf(dragNode));
//            FacesContext.getCurrentInstance().addMessage(null, message);


        } else {
            FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_WARN, "should not have happened", "NO NO NO");
            FacesContext.getCurrentInstance().addMessage(null, message);
        }

        //reset hikeeventpos for the to skip the check of a new event
        previousEventPosition = -1;

    }

    public void displaySelectedSingle() {
        if (selectedNode != null) {
            FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, "Selected", selectedNode.getData().toString());
            FacesContext.getCurrentInstance().addMessage(null, message);
        }
    }

    public void nodeExpand(NodeExpandEvent event) {
        event.getTreeNode().setExpanded(false);
    }

    public void nodeCollapse(NodeCollapseEvent event) {
        event.getTreeNode().setExpanded(true);
    }

    public void openLevel1() {
        Map<String, Object> options = new HashMap<String, Object>();
        options.put("modal", true);
        PrimeFaces.current().dialog().openDynamic("forms/choosePointDialog", options, null);
    }

    public void onReturnFromLevel1(SelectEvent event) {
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Data Returned", event.getObject().toString()));
    }

    public void viewBLA() {
        Map<String, Object> options = new HashMap<String, Object>();
        options.put("resizable", false);
        PrimeFaces.current().dialog().openDynamic("forms/choosePointDialog", options, null);
    }

    public String getToBeRefreshedWidgetID() {
        return toBeRefreshedWidgetID;
    }

    public void setToBeRefreshedWidgetID(String toBeRefreshedWidgetID) {
        this.toBeRefreshedWidgetID = toBeRefreshedWidgetID;
    }
}