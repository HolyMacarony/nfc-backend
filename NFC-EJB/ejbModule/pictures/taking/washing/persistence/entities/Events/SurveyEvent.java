package pictures.taking.washing.persistence.entities.Events;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Entity
@JsonIgnoreProperties(ignoreUnknown = true)
public class SurveyEvent extends Hikeevent implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotNull
    @Size(max = 250)
    private String surveyevent_question;
    @NotNull
    @Size(max = 250)
    private String surveyevent_answer;
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id")
    @MapsId
    private Hikeevent hikeevent;
    @OneToMany(mappedBy = "surveyEvent", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<SurveyEventMC> surveyEventMCs = new ArrayList<>();

    public SurveyEvent() {
        this.clazz = this.getClass();

    }

    public String getSurveyevent_question() {
        return surveyevent_question;
    }

    public void setSurveyevent_question(String surveyevent_question) {
        this.surveyevent_question = surveyevent_question;
    }

    public String getSurveyevent_answer() {
        return surveyevent_answer;
    }

    public void setSurveyevent_answer(String surveyevent_answer) {
        this.surveyevent_answer = surveyevent_answer;
    }

    public Hikeevent getHikeevent() {
        return hikeevent;
    }

    public void setHikeevent(Hikeevent hikeevent) {
        this.hikeevent = hikeevent;
    }

    public List<SurveyEventMC> getSurveyEventMCs() {
        return surveyEventMCs;
    }

    public void setSurveyEventMCs(List<SurveyEventMC> surveyEventMCs) {
        this.surveyEventMCs = surveyEventMCs;
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
