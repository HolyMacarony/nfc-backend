package pictures.taking.washing.web.Models;

import org.primefaces.model.DashboardColumn;
import org.primefaces.model.DashboardModel;

import java.util.List;

public class OverwriterModel implements DashboardModel {
    @Override
    public void addColumn(DashboardColumn column) {

    }

    @Override
    public List<DashboardColumn> getColumns() {
        return null;
    }

    @Override
    public int getColumnCount() {
        return 0;
    }

    @Override
    public DashboardColumn getColumn(int index) {
        return null;
    }

    @Override
    public void transferWidget(DashboardColumn fromColumn, DashboardColumn toColumn, String widgetId, int index) {

    }
}
