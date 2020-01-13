package pictures.taking.washing.persistence.entities;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.sql.Timestamp;

@Entity
@JsonIgnoreProperties(ignoreUnknown = true)
@Table(name = "playerplayshike")

public class PlayerPlaysHike implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotNull
    @Size(max = 250)
    private String name;
    private Timestamp fromdate;
    private Timestamp untildate;
    private int total;
    private int fun;
    private int variety;
    private int interestringlocation;
    private int difficulty;
    private int informative;
    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "hike_id")
    private Hike hike;
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id")
    @MapsId
    private Player player;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Timestamp getFromdate() {
        return fromdate;
    }

    public void setFromdate(Timestamp fromdate) {
        this.fromdate = fromdate;
    }

    public Timestamp getUntildate() {
        return untildate;
    }

    public void setUntildate(Timestamp untildate) {
        this.untildate = untildate;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public int getFun() {
        return fun;
    }

    public void setFun(int fun) {
        this.fun = fun;
    }

    public int getVariety() {
        return variety;
    }

    public void setVariety(int variety) {
        this.variety = variety;
    }

    public int getInterestringlocation() {
        return interestringlocation;
    }

    public void setInterestringlocation(int interestringlocation) {
        this.interestringlocation = interestringlocation;
    }

    public int getDifficulty() {
        return difficulty;
    }

    public void setDifficulty(int difficulty) {
        this.difficulty = difficulty;
    }

    public int getInformative() {
        return informative;
    }

    public void setInformative(int informative) {
        this.informative = informative;
    }

    public Hike getHike() {
        return hike;
    }

    public void setHike(Hike hike) {
        this.hike = hike;
    }

    public Player getPlayer() {
        return player;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }
}
