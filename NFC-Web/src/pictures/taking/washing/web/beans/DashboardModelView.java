package pictures.taking.washing.web.beans;

import org.primefaces.model.DashboardColumn;
import org.primefaces.model.DashboardModel;
import org.primefaces.model.DefaultDashboardColumn;
import org.primefaces.model.DefaultDashboardModel;

import javax.annotation.PostConstruct;
import javax.faces.view.ViewScoped;
import javax.inject.Named;
import java.io.Serializable;


@Named
@ViewScoped
public class DashboardModelView implements Serializable {

    private static final int DEFAULT_COLUMN_COUNT = 3;

    private DashboardModel model;

    @PostConstruct
    public void init() {
        System.out.println("creating model");
        if (model == null) {
            model = new DefaultDashboardModel();
            DashboardColumn column = new DefaultDashboardColumn();
            model.addColumn(column);
        }
    }

    public DashboardModel getModel() {
        return model;
    }

    public void setModel(DashboardModel model) {
        this.model = model;
    }
}