package pictures.taking.washing.persistence.entities.Events;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;

@Entity
@JsonIgnoreProperties(ignoreUnknown = true)
public class SurveyEventMC implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotNull
    @Size(max = 250)
    private String surveyeventmc_question;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "surveyevent_id")
    private SurveyEvent surveyEvent;

    public String getSurveyeventmc_question() {
        return surveyeventmc_question;
    }

    public void setSurveyeventmc_question(String surveyeventmc_question) {
        this.surveyeventmc_question = surveyeventmc_question;
    }

    public SurveyEvent getSurveyEvent() {
        return surveyEvent;
    }

    public void setSurveyEvent(SurveyEvent surveyEvent) {
        this.surveyEvent = surveyEvent;
    }

    public Long getId() {
        return id;
    }
}
