package pictures.taking.washing.persistence.entities.Events;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.validator.constraints.Length;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

@Entity
@JsonIgnoreProperties(ignoreUnknown = true)
public class InformationEvent extends Hikeevent implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    //    @Size(min = 3, max = 10000, message = "{error_informationevent_informationevent_text}")
    //    @NotNull(message = "{error_informationevent_null}")
    @NotNull(message = "{error_informationevent_null}")
    @Length(min = 10, max = 10000, message = "{error_informationevent_informationevent_text}")
    private String informationevent_text;
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id")
    @MapsId
    private Hikeevent hikeevent;

    public InformationEvent() {
        this.clazz = this.getClass();
    }

    @Override
    public Long getId() {
        return id;
    }

    @Override
    public void setId(Long id) {
        this.id = id;
    }

    public String getInformationevent_text() {
        return informationevent_text;
    }

    public void setInformationevent_text(String text) {
        this.informationevent_text = text;
    }

    public Hikeevent getHikeevent() {
        return hikeevent;
    }

    public void setHikeevent(Hikeevent hikeevent) {
        this.hikeevent = hikeevent;
    }

    @Override
    public String getEditFormName() {
        return "hikeInformationEventDialogWidget";
    }
}
