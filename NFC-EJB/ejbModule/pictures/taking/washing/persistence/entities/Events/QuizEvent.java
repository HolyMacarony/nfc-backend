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
public class QuizEvent extends Hikeevent implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotNull
    @Size(max = 250)
    private String quizevent_question;
    private int quizevent_points;
    private boolean quizevent_answermandatory;
    private int quizevent_pointlosswronganswer;
    private boolean quizevent_showanswer;
    private int quizevent_pointlosstimeinseconds;
    private boolean quizevent_restricttime;
    private int quizevent_trycount;
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id")
    @MapsId
    private Hikeevent hikeevent;
    @OneToMany(mappedBy = "quizevent", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<QuizEventMC> quizeventmcs = new ArrayList<>();
    @OneToMany(mappedBy = "quizevent", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<QuizEventTip> quizeventtip = new ArrayList<>();

    public QuizEvent() {
        this.clazz = this.getClass();

    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getQuizevent_question() {
        return quizevent_question;
    }

    public void setQuizevent_question(String question) {
        this.quizevent_question = question;
    }

    public int getQuizevent_points() {
        return quizevent_points;
    }

    public void setQuizevent_points(int points) {
        this.quizevent_points = points;
    }

    public boolean isQuizevent_answermandatory() {
        return quizevent_answermandatory;
    }

    public void setQuizevent_answermandatory(boolean answermandatory) {
        this.quizevent_answermandatory = answermandatory;
    }

    public int getQuizevent_pointlosswronganswer() {
        return quizevent_pointlosswronganswer;
    }

    public void setQuizevent_pointlosswronganswer(int pointlosswronganswer) {
        this.quizevent_pointlosswronganswer = pointlosswronganswer;
    }

    public boolean isQuizevent_showanswer() {
        return quizevent_showanswer;
    }

    public void setQuizevent_showanswer(boolean showanswer) {
        this.quizevent_showanswer = showanswer;
    }

    public int getQuizevent_pointlosstimeinseconds() {
        return quizevent_pointlosstimeinseconds;
    }

    public void setQuizevent_pointlosstimeinseconds(int pointlosstimeinseconds) {
        this.quizevent_pointlosstimeinseconds = pointlosstimeinseconds;
    }

    public boolean isQuizevent_restricttime() {
        return quizevent_restricttime;
    }

    public void setQuizevent_restricttime(boolean restricttime) {
        this.quizevent_restricttime = restricttime;
    }

    public int getQuizevent_trycount() {
        return quizevent_trycount;
    }

    public void setQuizevent_trycount(int trycount) {
        this.quizevent_trycount = trycount;
    }

    public Hikeevent getHikeevent() {
        return hikeevent;
    }

    public void setHikeevent(Hikeevent hikeevent) {
        this.hikeevent = hikeevent;
    }

    public List<QuizEventMC> getQuizeventmcs() {
        return quizeventmcs;
    }

    public void setQuizeventmcs(List<QuizEventMC> quizeventmcs) {
        this.quizeventmcs = quizeventmcs;
    }

    public List<QuizEventTip> getQuizeventtip() {
        return quizeventtip;
    }

    public void setQuizeventtip(List<QuizEventTip> quizeventtip) {
        this.quizeventtip = quizeventtip;
    }

    @Override
    public String getEditFormName() {
        return null;
    }
}
