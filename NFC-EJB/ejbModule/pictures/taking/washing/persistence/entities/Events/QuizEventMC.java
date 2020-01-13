package pictures.taking.washing.persistence.entities.Events;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;

@Entity
@JsonIgnoreProperties(ignoreUnknown = true)
public class QuizEventMC implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    //	private int sortid;
    @NotNull
    @Size(max = 250)
    private String quizeventmc_answer;
    private boolean quizeventmc_valid;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "quizevent_id")
    private QuizEvent quizevent;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getQuizeventmc_answer() {
        return quizeventmc_answer;
    }

    public void setQuizeventmc_answer(String answer) {
        this.quizeventmc_answer = answer;
    }

    public boolean isQuizeventmc_valid() {
        return quizeventmc_valid;
    }

    public void setQuizeventmc_valid(boolean valid) {
        this.quizeventmc_valid = valid;
    }

    public QuizEvent getQuizevent() {
        return quizevent;
    }

    public void setQuizevent(QuizEvent quizevent) {
        this.quizevent = quizevent;
    }


}
