package pictures.taking.washing.persistence.entities.Events;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import pictures.taking.washing.persistence.entities.Hikesection;
import com.vividsolutions.jts.geom.Point;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@NamedNativeQueries({
        @NamedNativeQuery(
                name = Hikeevent.QUERY_FINDALLBYHIKESECTION,
                query = "with recursive myhikeevent as( select hikeevent.id as joiner, hikeevent.*, turnamentevent.*, taskevent.*, locationevent.*, quizevent.*, informationevent.*, codescanevent.*, case when turnamentevent.id is not null then 1 when surveyevent.id is not null then 2 when taskevent.id is not null then 3 when locationevent.id is not null then 4 when quizevent.id is not null then 5 when informationevent.id is not null then 6 when codescanevent.id is not null then 7 when hikeevent.id is not null then 0 end as clazz_, 1 as level from hikeevent left outer join turnamentevent on hikeevent.id = turnamentevent.id left outer join surveyevent on hikeevent.id = surveyevent.id left outer join taskevent on hikeevent.id = taskevent.id left outer join locationevent on hikeevent.id = locationevent.id left outer join quizevent on hikeevent.id = quizevent.id left outer join informationevent on hikeevent.id = informationevent.id left outer join codescanevent on hikeevent.id = codescanevent.id where hikeevent.previoushikeevent_id is null and hikeevent.hikesection_id = :hikesectionId union all select hikeevent.id, hikeevent.*, turnamentevent.*, taskevent.*, locationevent.*, quizevent.*, informationevent.*, codescanevent.*, case when turnamentevent.id is not null then 1 when surveyevent.id is not null then 2 when taskevent.id is not null then 3 when locationevent.id is not null then 4 when quizevent.id is not null then 5 when informationevent.id is not null then 6 when codescanevent.id is not null then 7 when hikeevent.id is not null then 0 end as clazz_, t.level + 1 from hikeevent left outer join turnamentevent on hikeevent.id = turnamentevent.id left outer join surveyevent on hikeevent.id = surveyevent.id left outer join taskevent on hikeevent.id = taskevent.id left outer join locationevent on hikeevent.id = locationevent.id left outer join quizevent on hikeevent.id = quizevent.id left outer join informationevent on hikeevent.id = informationevent.id left outer join codescanevent on hikeevent.id = codescanevent.id join myhikeevent as t on hikeevent.previoushikeevent_id = t.joiner where hikeevent.hikesection_id = :hikesectionId ) select * from myhikeevent order by level",
                resultClass = Hikeevent.class)
})
@NamedQueries({
        @NamedQuery(name = Hikeevent.QUERY_FINDALLBYHIKESECTION2342,
                query = "SELECT h FROM Hikeevent h "),
        @NamedQuery(name = Hikeevent.QUERY_FINDHIKESECTIONBYHIKEEVENT,
                query = "SELECT h FROM Hikesection AS h, Hikeevent AS he where he = :hikeevent  ")
})
@JsonIgnoreProperties(ignoreUnknown = true)

@Table(
        indexes = {@Index(columnList = "previoushikeevent_id", name = "Hikeevent_previoushikeevent_id")})
@Inheritance(strategy = InheritanceType.JOINED)
public abstract class
Hikeevent implements Serializable, IEnum {
    public static final String QUERY_FINDALLBYHIKESECTION = "Hikeevent.FindAllByHikesection";
    public static final String QUERY_FINDALLBYHIKESECTION2342 = "Hikeevent.FindAllByHikesection2";
    public static final String QUERY_FINDHIKESECTIONBYHIKEEVENT = "Hikeevent.FindHikesectionByHikeevent";
    public static final String QUERY_INCREMENTSORTIDBEFOREINSERT = "Hikeevent.IncrementSortIdBeforeInsert";
    private static final long serialVersionUID = 1L;
    @Transient
    protected Class<?> clazz;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @OneToOne
    @JoinColumn(name = "previoushikeevent_id")
    @JsonIgnore
    private Hikeevent previousHikeevent;

    @OneToOne
    @JoinColumn(name = "nexthikeevent_id")
    @JsonIgnore
    private Hikeevent nextHikeevent;
    //	@OneToOne(mappedBy = "previousHikeevent")
//	private Hikeevent thisHikeevent;
    private boolean startByLocation = true;
    private int scorepoints;
    @Column(columnDefinition = "geometry(Point,4326)")
    private Point location;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "hikesection_id", nullable = false)
    @JsonBackReference
    private Hikesection hikesection;

    public Hikeevent getNextHikeevent() {
        return nextHikeevent;
    }

    public void setNextHikeevent(Hikeevent nextHikeevent) {
        this.nextHikeevent = nextHikeevent;
    }

    public String getClazz() {
        return clazz.getSimpleName();
    }

    public void setClazz(Class<?> clazz) {
        this.clazz = clazz;
    }

    //	public Hikeevent getThisHikeevent() {
//		return thisHikeevent;
//	}
//	public void setThisHikeevent(Hikeevent thisHikeevent) {
//		this.thisHikeevent = thisHikeevent;
//	}
    public Hikeevent getPreviousHikeevent() {
        return previousHikeevent;
    }

    public void setPreviousHikeevent(Hikeevent previousHikeevent) {
        this.previousHikeevent = previousHikeevent;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public boolean isStartByLocation() {
        return startByLocation;
    }

    public void setStartByLocation(boolean startByLocation) {
        this.startByLocation = startByLocation;
    }

    public int getScorepoints() {
        return scorepoints;
    }

    public void setScorepoints(int scorepoints) {
        this.scorepoints = scorepoints;
    }

    public Point getLocation() {
        return location;
    }

    public void setLocation(Point location) {
        this.location = location;
    }

    public Hikesection getHikesection() {
        return hikesection;
    }

    public void setHikesection(Hikesection hikesection) {
        this.hikesection = hikesection;
    }
}
