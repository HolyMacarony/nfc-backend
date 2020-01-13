package pictures.taking.washing.persistence.entities.Events;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;

@Entity
@JsonIgnoreProperties(ignoreUnknown = true)
public class TurnamentEvent extends Hikeevent implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotNull
    @Size(max = 250)
    private String turnamentevent_question;
    @NotNull
    @Size(max = 250)
    private String turnamentevent_answer;
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id")
    @MapsId
    private Hikeevent hikeevent;

    public TurnamentEvent() {
        this.clazz = this.getClass();

    }

    public String getTurnamentevent_question() {
        return turnamentevent_question;
    }

    public void setTurnamentevent_question(String turnamentevent_question) {
        this.turnamentevent_question = turnamentevent_question;
    }

    public String getTurnamentevent_answer() {
        return turnamentevent_answer;
    }

    public void setTurnamentevent_answer(String turnamentevent_answer) {
        this.turnamentevent_answer = turnamentevent_answer;
    }

    public Hikeevent getHikeevent() {
        return hikeevent;
    }

    public void setHikeevent(Hikeevent hikeevent) {
        this.hikeevent = hikeevent;
    }

    @Override
    public Long getId() {
        return id;
    }

    @Override
    public String getEditFormName() {
        return null;
    }
}
