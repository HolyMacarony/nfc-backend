package pictures.taking.washing.web.beans;

import org.primefaces.model.menu.DefaultMenuItem;
import org.primefaces.model.menu.DefaultMenuModel;
import org.primefaces.model.menu.MenuModel;

import javax.inject.Named;

@Named
public class BreadcrumbMenuBean {
    private MenuModel model;

    public BreadcrumbMenuBean() {
        model = new DefaultMenuModel();
        // First submenu
        DefaultMenuItem item = new DefaultMenuItem("External");
        item.setUrl("http://www.primefaces.org");
        item.setIcon("ui-icon-home");
        // Second submenu
        item = new DefaultMenuItem("Dashboard");
        item.setIcon("ui-icon-disk");
        item.setUrl("dashboard");
        item.setUpdate("messages");
        item = new DefaultMenuItem("Delete");
        item.setIcon("ui-icon-close");
        item.setAjax(false);
        item = new DefaultMenuItem("Redirect");
        item.setIcon("ui-icon-search");
        model.addElement(item);
    }

    //	@PostConstruct public void init() {
//		model = new DefaultMenuModel();
//	}
    public MenuModel getModel() {
        return model;
    }

    public void setModel(MenuModel model) {
        this.model = model;
    }
}