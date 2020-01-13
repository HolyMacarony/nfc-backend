package pictures.taking.washing.persistence.entities.Events;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.vividsolutions.jts.geom.Point;
import org.hibernate.validator.constraints.Range;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;

@Entity
@JsonIgnoreProperties(ignoreUnknown = true)
public class LocationEvent extends Hikeevent implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotNull(message = "{error_informationevent_null}")
    @Size(min = 0, max = 10000)
    private String locationevent_text;
    @Range(min = 0, max = 250)
    private int locationevent_points;
    @Column(columnDefinition = "boolean default true", nullable = false)
    private Boolean locationevent_showmapnotarrow = true;
    @Column(columnDefinition = "boolean default true", nullable = false)
    private Boolean locationevent_findmandatory = false;

    @NotNull(message = "{error_locationevent_locationToGo_null}")
    @Column(columnDefinition = "geometry(Point,4326)")
    private Point locationevent_locationToGo;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id")
    @MapsId
    private Hikeevent hikeevent;

    public LocationEvent() {
        this.clazz = this.getClass();
    }

    public Boolean getLocationevent_showmapnotarrow() {
        return locationevent_showmapnotarrow;
    }

    ;

    public void setLocationevent_showmapnotarrow(Boolean locationevent_showmapnotarrow) {
        this.locationevent_showmapnotarrow = locationevent_showmapnotarrow;
    }

    public Boolean getLocationevent_findmandatory() {
        return locationevent_findmandatory;
    }

    public void setLocationevent_findmandatory(Boolean locationevent_findmandatory) {
        this.locationevent_findmandatory = locationevent_findmandatory;
    }

    public String getLocationevent_text() {
        return locationevent_text;
    }

    public void setLocationevent_text(String locationevent_text) {
        this.locationevent_text = locationevent_text;
    }

    public int getLocationevent_points() {
        return locationevent_points;
    }

    public void setLocationevent_points(int locationevent_points) {
        this.locationevent_points = locationevent_points;
    }

    public Point getLocationevent_locationToGo() {
        return locationevent_locationToGo;
    }

    public void setLocationevent_locationToGo(Point locationevent_locationToGo) {
        this.locationevent_locationToGo = locationevent_locationToGo;
    }

    @Override
    public Long getId() {
        return id;
    }

    @Override
    public void setId(Long id) {
        this.id = id;
    }

    public Hikeevent getHikeevent() {
        return hikeevent;
    }

    public void setHikeevent(Hikeevent hikeevent) {
        this.hikeevent = hikeevent;
    }

    @Override
    public String getEditFormName() {
        return "hikeLocationEventDialogWidget";
    }
}
