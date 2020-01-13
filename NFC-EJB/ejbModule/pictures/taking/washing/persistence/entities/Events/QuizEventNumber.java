package pictures.taking.washing.persistence.entities.Events;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@JsonIgnoreProperties(ignoreUnknown = true)
public class QuizEventNumber implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private int quizeventnumber_rightvalue;
    private int quizeventnumber_minvalue;
    private int quizeventnumber_maxvalue;
    private int quizeventnumber_pointlossdelta;
    private boolean quizeventnumber_valid;
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "quizevent_id")
    @MapsId
    private QuizEvent quizevent;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public int getQuizeventnumber_rightvalue() {
        return quizeventnumber_rightvalue;
    }

    public void setQuizeventnumber_rightvalue(int rightvalue) {
        this.quizeventnumber_rightvalue = rightvalue;
    }

    public int getQuizeventnumber_minvalue() {
        return quizeventnumber_minvalue;
    }

    public void setQuizeventnumber_minvalue(int minvalue) {
        this.quizeventnumber_minvalue = minvalue;
    }

    public int getQuizeventnumber_maxvalue() {
        return quizeventnumber_maxvalue;
    }

    public void setQuizeventnumber_maxvalue(int maxvalue) {
        this.quizeventnumber_maxvalue = maxvalue;
    }

    public int getQuizeventnumber_pointlossdelta() {
        return quizeventnumber_pointlossdelta;
    }

    public void setQuizeventnumber_pointlossdelta(int pointlossdelta) {
        this.quizeventnumber_pointlossdelta = pointlossdelta;
    }

    public boolean isQuizeventnumber_valid() {
        return quizeventnumber_valid;
    }

    public void setQuizeventnumber_valid(boolean valid) {
        this.quizeventnumber_valid = valid;
    }

    public QuizEvent getQuizevent() {
        return quizevent;
    }

    public void setQuizevent(QuizEvent quizevent) {
        this.quizevent = quizevent;
    }
}
