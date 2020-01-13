package pictures.taking.washing.web.beans;

import com.ocpsoft.pretty.faces.annotation.URLMapping;
import com.ocpsoft.pretty.faces.annotation.URLMappings;
import pictures.taking.washing.ejb.interfaces.HikeDAO;
import pictures.taking.washing.ejb.interfaces.HikesectionDAO;
import pictures.taking.washing.ejb.interfaces.UserDAO;
import pictures.taking.washing.helper.MSG;
import pictures.taking.washing.helper.ResourceBundle;
import pictures.taking.washing.persistence.entities.Hike;
import pictures.taking.washing.persistence.entities.Hikesection;
import pictures.taking.washing.persistence.entities.User;
import pictures.taking.washing.web.Annotations.AuthenticatedUser;
import pictures.taking.washing.web.Annotations.ImpersonatedUser;
import com.vividsolutions.jts.geom.Coordinate;
import com.vividsolutions.jts.geom.GeometryFactory;
import com.vividsolutions.jts.geom.Point;
import com.vividsolutions.jts.geom.PrecisionModel;
import org.primefaces.PrimeFaces;
import org.primefaces.event.SelectEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.PostConstruct;
import javax.annotation.security.RolesAllowed;
import javax.ejb.EJB;
import javax.ejb.SessionContext;
import javax.enterprise.context.SessionScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;


@URLMappings(mappings = {
        @URLMapping(id = "hikeView", pattern = "/hike/#{ hikeBean.hike.publicurl }", viewId = "/hikeShowPublic"),
        @URLMapping(id = "hikeCreate", pattern = "/create/#{ hikeBean.hike.publicurl }", viewId = "/hikeCreate")})
@SessionScoped
@Named
public class HikeBean implements Serializable {
    final Logger logger = LoggerFactory.getLogger(DragDropView.class);
    @Inject
    @AuthenticatedUser
    User authenticatedUser;
    @Inject
    @ImpersonatedUser
    User impersonatedUser;
    private String chooseLocationInvoker;
    private Hike hikeToBeDeleted;
    @Inject
    private LocaleBean localeBean;
    @Inject
    private AuthenticationBean authBean;
    private SessionContext sessionContext;
    @PersistenceContext
    private EntityManager em;
    private Hike hike;
    private Long hikeId;
    private String singlePlayerOption;
    @EJB
    private HikeDAO hikeDAO;
    @EJB
    private UserDAO userDAO;
    @EJB
    private HikesectionDAO hikesectionDAO;
    private List<HikesectionBean> hikesections = new ArrayList<HikesectionBean>();
    private List<PlayerPlaysHikeBean> playerPlaysHikes = new ArrayList<PlayerPlaysHikeBean>();

    public HikeBean(Hike hike) {
        this.hike = hike;
    }

    public HikeBean() {
    }

    public String getChooseLocationInvoker() {
        return chooseLocationInvoker;
    }

    public void setChooseLocationInvoker(String chooseLocationInvoker) {
        this.chooseLocationInvoker = chooseLocationInvoker;
    }

    public void setTheInvokerOfHikePositionDialog() {
        PrimeFaces.current().ajax().addCallbackParam("invoker", chooseLocationInvoker);
    }


    public void getHikeStartDate() {
        Date d = hike.getStartDate();
        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
        String datumsstring = sdf.format(d);
        PrimeFaces.current().ajax().addCallbackParam("date", datumsstring);
    }

    public void getHikeEndDate() {
        Date d = hike.getEndDate();
        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
        String datumsstring = sdf.format(d);
        PrimeFaces.current().ajax().addCallbackParam("date", datumsstring);
    }

    public Hike getHikeToBeDeleted() {
        return hikeToBeDeleted;
    }

    public void setHikeToBeDeleted(Hike hikeToBeDeleted) {
        this.hikeToBeDeleted = hikeToBeDeleted;
    }

    public String getSinglePlayerOption() {
        return singlePlayerOption;
    }

    public void setSinglePlayerOption(String singlePlayerOption) {
        this.singlePlayerOption = singlePlayerOption;
    }

    public void addHikesection() {
        HikesectionBean hsb = new HikesectionBean(new Hikesection());
        hikesections.add(hsb);
    }

    public void addHikesection(Hikesection hikesection) {
        HikesectionBean hsb = new HikesectionBean(hikesection);
        hikesections.add(hsb);
    }

    public void onDateSelect(SelectEvent event) {
//        hike.setEnabled((Timestamp) event.getObject());
        FacesContext facesContext = FacesContext.getCurrentInstance();
        SimpleDateFormat format = new SimpleDateFormat("dd-MM-yyyy hh:mm:ss");
        facesContext.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, format.format((Date) event.getObject()), ""));
    }

    public void removeHikesection(HikesectionBean hsb) {
        hikesections.remove(hsb);
    }

    public void setHikesections(List<HikesectionBean> hikesections) {
        this.hikesections = hikesections;
    }
//
    // Copy the data
//		 hikesections.clear();
//		 for (Hikesection section : user.getAddresses()) {
//		 hikesections.add(new HikesectionBean(section));
//		 }
//}

//    @PostConstruct
//    public void initIdent(){
//        authenticatedUser = authBean.getAuthenticatedUser();
//        impersonatedUser = authBean.getImpersonatedUser();
//    }

    @PostConstruct
    public void init() {

//        if (hike != null && hike.getId() > 0) {
//            return;
//        }
//        if (hikeId != null && hikeId > 0) {
//            hike = hikeDAO.find(hikeId);
//        }
        if (hike == null) {
            this.hike = new Hike();
        }
        if (hike.getPublicurl() != null && !hike.getPublicurl().isEmpty()) {
            User theUser = (authBean.getImpersonatedUser() != null && authBean.getImpersonatedUser().getId() != null) ? authBean.getImpersonatedUser() : authBean.getAuthenticatedUser();

            hike = hikeDAO.findByURLAndUser(hike.getPublicurl(), theUser);

            if (hike == null) {
                FacesContext facesContext = FacesContext.getCurrentInstance();
                try {
                    facesContext.getExternalContext().responseSendError(HttpServletResponse.SC_NOT_FOUND, "not found");
                    facesContext.responseComplete();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

    }


    public String create() {
        prepare();
        User theUser = (authBean.getImpersonatedUser() != null && authBean.getImpersonatedUser().getId() != null) ? authBean.getImpersonatedUser() : authBean.getAuthenticatedUser();

        hike.setUser(theUser);
        Hike h = hikeDAO.create(hike);
//        hike= null;
        FacesContext.getCurrentInstance().
                addMessage(null,
                        new FacesMessage(FacesMessage.SEVERITY_INFO,
                                MSG.getValue(ResourceBundle.VALIDATIONS.toString(), "success_create_hike", new String[]{h.getTitle()}, localeBean.getLocale()),
                                MSG.getValue(ResourceBundle.VALIDATIONS.toString(), "success_create_hike_details", null, localeBean.getLocale())));
        return "pretty:hikeCreate";
    }


    public void deleteHike(Hike hike) {
        if (hikeToBeDeleted != null && hikeToBeDeleted.equals(hike)) {
            hikeDAO.remove(hike.getId());
            setHikeToBeDeleted(null);
            FacesContext.getCurrentInstance().
                    addMessage(null,
                            new FacesMessage(FacesMessage.SEVERITY_INFO,
                                    MSG.getValue(ResourceBundle.VALIDATIONS.toString(), "success_delete_hike", new String[]{hike.getTitle()}, localeBean.getLocale()), ""));
        } else {
            setHikeToBeDeleted(hike);
        }

    }


    public void clearToBeDeletedHike() {
        setHikeToBeDeleted(null);
    }

    public Hike getHike() {
        return hike;
    }

    public void setHike(Hike hike) {
        this.hike = hike;
    }

    public Long getId() {
        return hike.getId();
    }

    public Long getHikeId() {
        return hikeId;
    }

    public void setHikeId(Long hikeId) {
        this.hikeId = hikeId;
    }

    public void update() {
        hikeDAO.update(hike);
        hike = null;
    }

    public void prepare() {
        //		getAuthenticatedUser = em.merge(getAuthenticatedUser);

        //		hike.setUser(em.find(User.class,getAuthenticatedUser.getId()));
        // Clear all the lists
        // Add all items (again) to the list

//		hike.getHikesections().clear();
//		for (HikesectionBean hs : hikesections) {
//			hike.getHikesections().add(hs.getHikesection());
//		}

    }

    @RolesAllowed(value = {"USER"})
    public void setLocation() {
        java.util.Map<String, String> params = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap();
        try {
            GeometryFactory gf = new GeometryFactory(new PrecisionModel(), 4326);
            Point p1 = gf.createPoint(new Coordinate(Double.parseDouble(params.get("latitude")), Double.parseDouble(params.get("longitude"))));

            //special case of forwarding to another point after reaching a location
            if (!params.get("invoker").isEmpty()) {
                switch (params.get("invoker")) {
                    case "Location_start":
                        hike.setStartlocation(p1);
                        break;
                    case "Location_end":
                        hike.setEndlocation(p1);
                        break;
                }
            }
        } catch (Exception e) {
            logger.error("HikeBean setLocation: fail to parse at least 1 position String");
            logger.info(e.getMessage());
        }
//		Map<String, Object> options = new HashMap<String, Object>();
//		options.put("resizable", false);
//		PrimeFaces.current().dialog().showMessageDynamic(new FacesMessage("hallo"));
//		PrimeFaces.current().dialog().openDynamic("createHikeDialog");
//        PrimeFaces.current().executeScript("PF('sectionDialogWidget').show()");

    }


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        HikeBean hikeBean = (HikeBean) o;
        return Objects.equals(hike.getId(), hikeBean.hike.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(hike);
    }
}