package pictures.taking.washing.web.beans;

//import HikeeventDAO;
//import HikesectionDAO;

import pictures.taking.washing.customComponents.CustomPanel;
import pictures.taking.washing.ejb.interfaces.HikeeventDAO;
import pictures.taking.washing.ejb.interfaces.HikesectionDAO;
import pictures.taking.washing.persistence.entities.Hikesection;
import org.primefaces.behavior.ajax.AjaxBehavior;
import org.primefaces.behavior.ajax.AjaxBehaviorListenerImpl;
import org.primefaces.component.commandbutton.CommandButton;
import org.primefaces.component.dashboard.Dashboard;
import org.primefaces.component.panel.Panel;
import org.primefaces.event.CloseEvent;
import org.primefaces.event.DashboardReorderEvent;
import org.primefaces.event.ToggleEvent;
import org.primefaces.model.DashboardColumn;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.el.ELContext;
import javax.el.ExpressionFactory;
import javax.el.MethodExpression;
import javax.faces.application.Application;
import javax.faces.application.FacesMessage;
import javax.faces.component.html.HtmlOutcomeTargetLink;
import javax.faces.component.html.HtmlOutputText;
import javax.faces.context.FacesContext;
import javax.faces.event.MethodExpressionActionListener;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.Serializable;

//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import java.util.logging.Level;
//import java.util.logging.Logger;

@Named
@ViewScoped
public class CreateHikeDashboardBean implements Serializable {
    //	private static final Logger LOGGER = Logger.getLogger(TypeData.ClassName.class.getName());
//	final Logger logger = LoggerFactory.getLogger(CreateHikeDashboardBean.class);


    CustomPanel sectionPanel;
    //	Panel sectionPanel;
    CustomPanel eventPanel;
    Panel addButtonPanel;

    CommandButton sectionButton;
    CommandButton addButton;

    FacesContext fc;
    Application application;
    ELContext elContext = FacesContext.getCurrentInstance().getELContext();
    ExpressionFactory factory = FacesContext.getCurrentInstance().getApplication().getExpressionFactory();
    DashboardColumn cl;
    @Inject
    private DashboardModelView dashboardModelView;
    private Dashboard dashboard;
    @EJB
    private HikeeventDAO hikeeventDAO;
    @EJB
    private HikesectionDAO hikesectionDAO;
    @Inject
    private HikeBean hikeBean;


    public CreateHikeDashboardBean() {
    }

    @PostConstruct
    public void init() {
        fc = FacesContext.getCurrentInstance();
        application = fc.getApplication();

        dashboard = (Dashboard) application.createComponent(fc, "org.primefaces.component.Dashboard", "org.primefaces.component.DashboardRenderer");
        dashboard.setId("dashboard");
        dashboard.setModel(dashboardModelView.getModel());

        addSections();
    }

    public Dashboard getDashboard() {
        return dashboard;
    }

    public void setDashboard(Dashboard dashboard) {
        this.dashboard = dashboard;
    }

    /**
     * gethikesections from DB and show them
     */
    public void addSections() {

        dashboard.getChildren().clear();

        if (hikeBean != null) {
            //						hikeBean = (HikeBean) Hibernate.unproxy(hikeBean);
            for (Hikesection section : hikesectionDAO.findAllByHike(hikeBean.getHike())) {
                //				CustomPanel to distinguish betweeen several classes
                //				sectionPanel = (Panel) application.createComponent(fc, "org.primefaces.component.Panel", "org.primefaces.component.PanelRenderer");
                sectionPanel = (CustomPanel) application.createComponent(fc, "org.primefaces.component.panel.Panel", "org.primefaces.component.PanelRenderer");
                sectionPanel.setId("panel_section_" + section.getId());
                sectionPanel.setWidgetVar(sectionPanel.getId());
                sectionPanel.setToggleable(false);
                sectionPanel.setClosable(false);
                sectionPanel.setPanelClass(section.getClass());

                sectionPanel.setHeader("Abschnitt: " + section.getId());

                addButtonPanel = (Panel) application.createComponent(fc, "org.primefaces.component.Panel", "org.primefaces.component.PanelRenderer");
                addButtonPanel.setId("panel_addButton_" + section.getId());
                addButtonPanel.setToggleable(false);
                addButtonPanel.setClosable(false);

                HtmlOutcomeTargetLink link = new HtmlOutcomeTargetLink();
                link.setValue("Dashboard");
                link.setOutcome("dashboard");

                MethodExpression targetExpression = factory.createMethodExpression(elContext, "#{createHikeDashboardBean.addCommandbutton()}", null, null);
                AjaxBehavior ajaxBehavior = (AjaxBehavior) fc.getApplication().createBehavior(AjaxBehavior.BEHAVIOR_ID);


                //				Buttons
                sectionButton = (CommandButton) application.createComponent(fc, "org.primefaces.component.CommandButton", "org.primefaces.component.CommandButtonRenderer");
                sectionButton.addActionListener(new MethodExpressionActionListener(targetExpression));
                sectionButton.setValue("UP");
                sectionButton.getChildren().add(link);
                //				sectionPanel.getChildren().add(sectionButton);
                sectionPanel.getFacets().put("actions", sectionButton);

                addButton = (CommandButton) application.createComponent(fc, "org.primefaces.component.CommandButton", "org.primefaces.component.CommandButtonRenderer");
                addButton.addActionListener(new MethodExpressionActionListener(targetExpression));
                addButton.setIcon("fa fa-plus");
                sectionPanel.getFacets().put("Footer", addButton);

                ajaxBehavior.setProcess("@all");
                ajaxBehavior.setUpdate("@all messageGrowl");

                ajaxBehavior.setOncomplete("PF('" + sectionPanel.getWidgetVar() + "').moveUp()");
                //				ajaxBehavior.setOncomplete("PF('sidebarCreate').show()");
                ajaxBehavior.addAjaxBehaviorListener(new AjaxBehaviorListenerImpl(targetExpression, targetExpression));
                addButton.addClientBehavior("click", ajaxBehavior);
                addButtonPanel.getChildren().add(addButton);
                dashboard.getChildren().add(sectionPanel);
                //				dashboard.getChildren().add(addButtonPanel);
                cl = dashboardModelView.getModel().getColumn(0);
                cl.addWidget(sectionPanel.getId());
                //				cl.addWidget(addButtonPanel.getId());
                HtmlOutputText text = new HtmlOutputText();
                text.setValue("test");
                sectionPanel.getChildren().add(text);
                addEvents(section);
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
//			dashboard.getChildren().add(eventPanel);
//			cl = dashboardModelView.getModel().getColumn(0);
//			cl.addWidget(eventPanel.getId());
//			//			cl.addWidget(theButton.getId());
//		}
    }

    public void handleReorder(DashboardReorderEvent event) {
        FacesMessage message = new FacesMessage();
        System.out.println(event.getSource());
        System.out.println(event.getComponent().getChildren().get(event.getItemIndex()));
        message.setSeverity(FacesMessage.SEVERITY_INFO);
        message.setDetail("Item index: " + event.getItemIndex() + ", Column index: " + event.getColumnIndex() + ", Sender index: " + event.getSenderColumnIndex());
        message.setSummary("Reordered: " + event.getWidgetId());


        DashboardColumn column = getDashboardColumn(event.getWidgetId());
//		logger.debug("das verschobene Widget, was hat es für infos? " + column.getWidget(event.getItemIndex()));
//		logger.debug("aktuell verschoben: " + event.getComponent().getChildren().get(event.getItemIndex()).getId());

        //		logger.debug(cl.getWidget(event.getItemIndex()));
        //		logger.debug(event.getComponent().getChildren().get(event.getItemIndex()).getId());
        //		logger.debug(String.valueOf(event.getComponent().getChildren()));
        //		logger.debug(String.valueOf(event.getComponent().getChildren().get(event.getItemIndex())));
        //		logger.debug("Ermittel die Position des neu verschobenen Widgets: " + dashboardModelView.getModel().getColumn(0).getWidget(event.getItemIndex()));
        //		logger.debug("Ermittel die Position des neu verschobenen Widgets: " + dashboardModelView.getModel().getColumn(0).getWidget(event.getItemIndex()));
        //.get(event.getItemIndex()).getId()
        //.get(event.getItemIndex()).getId()
        addMessage(message);
    }

    private DashboardColumn getDashboardColumn(String widgetId) {
        for (DashboardColumn column : dashboardModelView.getModel().getColumns()) {
            for (String id : column.getWidgets()) {
                if (id.equals(widgetId)) {
                    return column;
                }
            }
        }
        return null;
    }

    private DashboardColumn getWidgetByID(String widgetId) {
        for (DashboardColumn column : dashboardModelView.getModel().getColumns()) {
            for (String id : column.getWidgets()) {
                if (id.equals(widgetId)) {
                    //					dashboardModelView.getModel().getColumn(0)
                    return column;
                }
            }
        }
        return null;
    }

    public void moveWidgetToDown(String widgetId) {
        DashboardColumn column = getDashboardColumn(widgetId);
        int index = 0;
        for (int i = 0; i < column.getWidgetCount(); i++) {
            if (column.getWidget(i).equals(widgetId)) {
                index = i;
                break;
            }
        }
        if ((index + 1) != column.getWidgetCount()) {
            column.reorderWidget(++index, widgetId);
//			logger.debug("Widget {0} moved to down at column in the dashboard", widgetId);
        }
    }

    public void handleClose(CloseEvent event) {
        FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, "Panel Closed", "Closed panel id:'" + event.getComponent().getId() + "'");

        addMessage(message);
    }

    public void handleToggle(ToggleEvent event) {
        FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, event.getComponent().getId() + " toggled", "Status:" + event.getVisibility().name());

        addMessage(message);
    }

    private void addMessage(FacesMessage message) {
        FacesContext.getCurrentInstance().addMessage(null, message);
    }

    public void addCommandbutton() {
        System.out.println("ADD gedrückt neuen button/Panel einfügen");

    }

}
