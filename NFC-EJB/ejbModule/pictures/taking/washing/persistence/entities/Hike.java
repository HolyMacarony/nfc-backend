package pictures.taking.washing.persistence.entities;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import pictures.taking.washing.ejb.constraints.annotations.UniqueURL;
import pictures.taking.washing.ejb.constraints.groupsequences.ExpensiveChecks;
import com.vividsolutions.jts.geom.Point;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Date;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Entity
@NamedQueries({
        @NamedQuery(name = Hike.QUERY_FINDALLBYUSER, query = "SELECT h FROM Hike h WHERE h.user.id = :userID ORDER BY h.createdAt DESC"),
        @NamedQuery(name = Hike.QUERY_FINDBYURL, query = "SELECT h FROM Hike h WHERE h.publicurl = :url "),
        @NamedQuery(name = Hike.QUERY_FINDBYURLANDUSERID, query = "SELECT h FROM Hike h WHERE h.publicurl = :url AND  h.user.id = :userID"),
        @NamedQuery(name = Hike.QUERY_FINDALL, query = "SELECT h FROM Hike h")})
@JsonIgnoreProperties(ignoreUnknown = true)
@Table(name = "hike")
public class Hike implements Serializable {

    public static final String QUERY_FINDALLBYUSER = "Hike.FindAllByUser";
    public static final String QUERY_FINDBYURL = "Hike.FindByURL";
    public static final String QUERY_FINDBYURLANDUSERID = "Hike.FindByURLAndUser";
    public static final String QUERY_FINDALL = "Hike.FindALl";

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull(message = "{error_hike_title_NULL}")
    @Size(max = 250)
    private String title;
    @Size(max = 250)
    private String description;
    private boolean privatehike;
    private boolean singleplayer;
    private boolean lineargameplay;

    @Pattern(regexp = "^[a-zA-Z0-9_-]*$", message = "{error_urlsuffix_VALID}")
    @NotNull(message = "{error_url_NULL}")
    @UniqueURL(groups = ExpensiveChecks.class, message = "{error_url_UNIQUE}")
    @Size(max = 250)
    @Column(unique = true)
    private String publicurl;

    @ManyToOne(fetch = FetchType.LAZY, optional = false, cascade = {CascadeType.MERGE})
    @JsonBackReference
    @JoinColumn(name = "author_id", nullable = false)
    private User user;
    @ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE}, fetch = FetchType.LAZY)
    @JoinTable(name = "hike_hikecategory", joinColumns = @JoinColumn(name = "hike_id"), inverseJoinColumns = @JoinColumn(name = "hikecategory_id"))
    private Set<Hikecategory> hikecategories = new HashSet<>();

    @OnDelete(action = OnDeleteAction.CASCADE)
    @JsonManagedReference
    @OneToMany(mappedBy = "hike", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private Set<Hikesection> hikesections = new HashSet<Hikesection>();
    @OneToMany(mappedBy = "hike", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private Set<PlayerPlaysHike> playerPlaysHikes = new HashSet<PlayerPlaysHike>();
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "mapsource_id")
    private Mapsource mapsource;
    @Version
    private Timestamp updatedAt;
    private Timestamp enabled;
    @Column(columnDefinition = "CHAR(156)")
    private String password;
    @Column(columnDefinition = "geometry(Point,4326)")
    private Point startlocation;
    private Date startDate = new Date(System.currentTimeMillis());
    private Date endDate = new Date(System.currentTimeMillis());
    @Column(columnDefinition = "geometry(Point,4326)")
    private Point endlocation;
    @Version
    @NotNull
    private Timestamp createdAt = new Timestamp(System.currentTimeMillis());
    @Version
    private Timestamp lastChanged;

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public boolean isPrivatehike() {
        return privatehike;
    }

    public void setPrivatehike(boolean privatehike) {
        this.privatehike = privatehike;
    }

    public boolean isSingleplayer() {
        return singleplayer;
    }

    public void setSingleplayer(boolean singleplayer) {
        this.singleplayer = singleplayer;
    }

    public boolean isLineargameplay() {
        return lineargameplay;
    }

    public void setLineargameplay(boolean lineargameplay) {
        this.lineargameplay = lineargameplay;
    }

    public String getPublicurl() {
        return publicurl;
    }

    public void setPublicurl(String publicurl) {
        this.publicurl = publicurl;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Set<Hikecategory> getHikecategories() {
        return hikecategories;
    }

    public void setHikecategories(Set<Hikecategory> hikecategories) {
        this.hikecategories = hikecategories;
    }

    public Set<Hikesection> getHikesections() {
        return hikesections;
    }

    public void setHikesections(Set<Hikesection> hikesections) {
        this.hikesections = hikesections;
    }

    public Timestamp getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(Timestamp updatedAt) {
        this.updatedAt = updatedAt;
    }

    public Date getEnabled() {
        if (enabled != null) {
            return new Date(enabled.getTime());
        } else {
            return null;
        }
    }

    public void setEnabled(Date enabled) {
        if (enabled != null) {
            this.enabled = new Timestamp(enabled.getTime());
        }
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Point getStartlocation() {
        return startlocation;
    }

    public void setStartlocation(Point startlocation) {
        this.startlocation = startlocation;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Point getEndlocation() {
        return endlocation;
    }

    public void setEndlocation(Point endlocation) {
        this.endlocation = endlocation;
    }

    public Timestamp getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Timestamp createdAt) {
        this.createdAt = createdAt;
    }

    public void addHikesection(Hikesection hikesection) {
        this.hikesections.add(hikesection);
        hikesection.setHike(this);
    }

    public void removeHikesection(Hikesection hikesection) {
        this.hikesections.remove(hikesection);
        hikesection.setHike(null);
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Hike)) return false;
        Hike hike = (Hike) o;
        return Objects.equals(getId(), hike.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId());
    }
}
