package pictures.taking.washing.persistence.entities.Events;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@JsonIgnoreProperties(ignoreUnknown = true)
public class QuizEventList implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long quizeventlist_id;
    private String quizeventlist_answer;
    //	private int sortid;
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "quizevent_id")
    @MapsId
    private QuizEvent quizevent;

    public Long getQuizeventlist_id() {
        return quizeventlist_id;
    }

    public void setQuizeventlist_id(Long id) {
        this.quizeventlist_id = id;
    }

    public String getQuizeventlist_answer() {
        return quizeventlist_answer;
    }

    public void setQuizeventlist_answer(String answer) {
        this.quizeventlist_answer = answer;
    }

    //	public int getSortid() {
//		return sortid;
//	}
//	public void setSortid(int sortid) {
//		this.sortid = sortid;
//	}
    public QuizEvent getQuizevent() {
        return quizevent;
    }

    public void setQuizevent(QuizEvent quizevent) {
        this.quizevent = quizevent;
    }


}
