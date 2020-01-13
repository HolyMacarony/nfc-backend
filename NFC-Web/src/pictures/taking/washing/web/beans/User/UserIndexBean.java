package pictures.taking.washing.web.beans.User;

import com.ocpsoft.pretty.faces.annotation.URLMapping;
import com.ocpsoft.pretty.faces.annotation.URLMappings;
import pictures.taking.washing.ejb.dto.BaseUserData;
import pictures.taking.washing.ejb.interfaces.HikeDAO;
import pictures.taking.washing.ejb.interfaces.UserDAO;
import pictures.taking.washing.web.beans.HikeBean;
import pictures.taking.washing.web.beans.MediaBean;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.enterprise.inject.Model;
import javax.faces.view.ViewScoped;
import javax.inject.Named;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

//@Stateful
@URLMappings(mappings = {
        @URLMapping(id = "userIndex", pattern = "/user", viewId = "/userIndex")
})
@Model
@Named
@ViewScoped
public class UserIndexBean implements Serializable {
    private static final long serialVersionUID = 1L;



    @EJB
    private UserDAO userDAO;
    @EJB
    private HikeDAO hikeDAO;


    private List<MediaBean> mediaBeans = new ArrayList<MediaBean>();
    private List<BaseUserData> userBeans = new ArrayList<>();
    private List<HikeBean> hikeBeans = new ArrayList<HikeBean>();

    public List<BaseUserData> getUserBeans() {
        return userBeans;
    }

    public void setUserBeans(List<BaseUserData> userBeans) {
        this.userBeans = userBeans;
    }

    @PostConstruct public void init() {

        if (userBeans != null && !userBeans.isEmpty()) {
            return;
        }
        userBeans = userDAO.findUsersBaseInfo();
    }
}
