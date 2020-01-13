package pictures.taking.washing.persistence.entities;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import pictures.taking.washing.persistence.entities.Events.Hikeevent;
import com.vividsolutions.jts.geom.Point;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;

@Entity
@NamedNativeQueries({
        @NamedNativeQuery(
                name = Hikesection.QUERY_FINDALLBYHIKE,
                query = "WITH RECURSIVE MyHikesection AS (" +
                        "SELECT *,1 as level FROM Hikesection where previoushikesection_id IS null and hike_id = :hikeId" +
                        " UNION ALL " +
                        "SELECT m.*,t.level + 1  FROM hikesection AS m JOIN MyHikesection AS t " +
                        "ON m.previoushikesection_id = t.Id " +
                        "where m.hike_id = :hikeId)" +
                        "SELECT * FROM MyHikesection ORDER BY level", resultClass = Hikesection.class

        )
})
@JsonIgnoreProperties(ignoreUnknown = true)
@Table(name = "hikesection"
//		uniqueConstraints = {@UniqueConstraint(columnNames = {"hike_id", "previoushikesection_id"}),
//				@UniqueConstraint(columnNames = {"hike_id", "nexthikesection_id"})}
)

public class Hikesection implements Serializable {

    public static final String QUERY_FINDALLBYHIKE = "Hikesection.FindAllByHikeInTree";

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotNull(message = "{error_hikesection_title_NULL}")
    @Size(max = 250)
    private String title;
    @NotNull
    @ManyToOne(fetch = FetchType.LAZY)
    @JsonBackReference
    @JoinColumn(name = "hike_id")
    private Hike hike;
    private Date date = new Date(System.currentTimeMillis());
    @Column(columnDefinition = "geometry(Point,4326)")
    private Point location;
    private boolean invisible;


    @OneToOne
    @JsonIgnore
    @JoinColumn(name = "previoushikesection_id")
    private Hikesection previousHikesection;
    @OneToOne
    @JsonIgnore
    @JoinColumn(name = "nexthikesection_id")
    private Hikesection nextHikesection;
    //	@OneToOne(mappedBy = "previousHikesection")
//	private Hikesection thisHikesection;
    private boolean startByLocation;
    @OneToMany(mappedBy = "hikesection", cascade = CascadeType.MERGE, orphanRemoval = true,
            fetch = FetchType.LAZY)
    @JsonManagedReference
    private List<Hikeevent> hikeevents = new ArrayList<>();

    public Hikesection getNextHikesection() {
        return nextHikesection;
    }

    public void setNextHikesection(Hikesection nextHikesection) {
        this.nextHikesection = nextHikesection;
    }

    //	public Hikesection getThisHikesection() {
//		return thisHikesection;
//	}
//	public void setThisHikesection(Hikesection thisHikesection) {
//		this.thisHikesection = thisHikesection;
//	}
    public Hikesection getPreviousHikesection() {
        return previousHikesection;
    }

    public void setPreviousHikesection(Hikesection previousHikesection) {
        this.previousHikesection = previousHikesection;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String name) {
        this.title = name;
    }

    public List<Hikeevent> getHikeevents() {
        return hikeevents;
    }

    public void setHikeevents(List<Hikeevent> hikeevents) {
        this.hikeevents = hikeevents;
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

    public Hike getHike() {
        return hike;
    }

    public void setHike(Hike hike) {
        this.hike = hike;
    }

    public boolean isInvisible() {
        return invisible;
    }

    public void setInvisible(boolean invisible) {
        this.invisible = invisible;
    }

    public Point getLocation() {
        return location;
    }

    public void setLocation(Point location) {
        this.location = location;
    }

    public void addHikeevent(Hikeevent hikeevent) {
        this.hikeevents.add(hikeevent);
        hikeevent.setHikesection(this);
    }

    public void removeHikeevent(Hikeevent hikeevent) {
        this.hikeevents.remove(hikeevent);
        hikeevent.setHikesection(null);
    }

    @Override
    public String toString() {
        return String.valueOf(id);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Hikesection)) {
            return false;
        }
        Hikesection that = (Hikesection) o;
        if (that.getId() == null) {
            return (Objects.equals(getHike(), that.getHike()));
        }
        return Objects.equals(getId(), that.getId());
    }

    @Override
    public int hashCode() {

        return Objects.hash(getId());
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }
}