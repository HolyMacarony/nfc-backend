package pictures.taking.washing.persistence.entities.Events;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;

@Entity
@JsonIgnoreProperties(ignoreUnknown = true)
public class QuizEventTip implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private boolean quizeventtip_valid;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "quizevent_id")
    private QuizEvent quizevent;
    @NotNull
    @Size(max = 250)
    private String quizeventtip_text;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getQuizeventtip_text() {
        return quizeventtip_text;
    }

    public void setQuizeventtip_text(String text) {
        this.quizeventtip_text = text;
    }

    public boolean isQuizeventtip_valid() {
        return quizeventtip_valid;
    }

    public void setQuizeventtip_valid(boolean valid) {
        this.quizeventtip_valid = valid;
    }

    public QuizEvent getQuizevent() {
        return quizevent;
    }

    public void setQuizevent(QuizEvent quizevent) {
        this.quizevent = quizevent;
    }
}
