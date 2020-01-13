package pictures.taking.washing.persistence.entities.Events;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.vividsolutions.jts.geom.Point;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;

@Entity
@JsonIgnoreProperties(ignoreUnknown = true)
public class TaskEvent extends Hikeevent implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotNull
    @Size(max = 250)
    private String taskevent_text;
    @Size(max = 250)
    private String taskevent_answer;
    @Size(max = 150)
    private String taskevent_androidaction;
    private String taskevent_media_reference_id;
    private boolean taskevent_findmandatory;
    private boolean taskevent_scanmandatory;
    @Column(columnDefinition = "geometry(Point,4326)")
    private Point taskevent_location;
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id")
    @MapsId
    private Hikeevent hikeevent;

    public TaskEvent() {
        this.clazz = this.getClass();

    }

    @Override
    public Long getId() {
        return id;
    }

    public String getTaskevent_text() {
        return taskevent_text;
    }

    public void setTaskevent_text(String text) {
        this.taskevent_text = text;
    }

    public String getTaskevent_answer() {
        return taskevent_answer;
    }

    public void setTaskevent_answer(String answer) {
        this.taskevent_answer = answer;
    }

    public String getTaskevent_androidaction() {
        return taskevent_androidaction;
    }

    public void setTaskevent_androidaction(String androidaction) {
        this.taskevent_androidaction = androidaction;
    }

    public String getTaskevent_media_reference_id() {
        return taskevent_media_reference_id;
    }

    public void setTaskevent_media_reference_id(String media_reference_id) {
        this.taskevent_media_reference_id = media_reference_id;
    }

    public boolean isTaskevent_findmandatory() {
        return taskevent_findmandatory;
    }

    public void setTaskevent_findmandatory(boolean findmandatory) {
        this.taskevent_findmandatory = findmandatory;
    }

    public boolean isTaskevent_scanmandatory() {
        return taskevent_scanmandatory;
    }

    public void setTaskevent_scanmandatory(boolean scanmandatory) {
        this.taskevent_scanmandatory = scanmandatory;
    }

    public Point getTaskevent_location() {
        return taskevent_location;
    }

    public void setTaskevent_location(Point location) {
        this.taskevent_location = location;
    }

    public Hikeevent getHikeevent() {
        return hikeevent;
    }

    public void setHikeevent(Hikeevent hikeevent) {
        this.hikeevent = hikeevent;
    }

    @Override
    public String getEditFormName() {
        return null;
    }
}